package preprocessor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import common.Constants;
import common.FileInOut;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;


public class Preprocessor {

    public DependencyGraph dependencyGraph;

    public Preprocessor() {
        dependencyGraph = new DependencyGraph();
    }

    public void stanfordPreprocess() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String corpus = "";
        try {
            corpus = FileInOut.readFile(new File(Constants.QA4MRE_FOLDER + "docs/1.txt"));
        }
        catch (IOException e) {}

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(corpus);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(NamedEntityTagAnnotation.class);
            }
            
            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeAnnotation.class);
            //System.out.println("TreeAnnotation ");
            //printTree(tree,0);

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
            dependencyGraph.addSentence(dependencies, sentences.indexOf(sentence));
        }
        // This is the coreference link graph
        // Each chain stores a set of mentions that link to each other,
        // along with a method for getting the most representative mention
        // Both sentence and token offsets start at 1!
        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
    }
    
    void printTree(Tree tree, int lev)
    {
    	for (int i =0;i<lev;i++)
    		System.out.print(" ");
    	System.out.println(tree.value());
    	for (Tree copil : tree.children())
    		printTree(copil,lev+1);
    }

    // TODO: delete this main
    public static void main(String[] args) {
        // ReadXMLFile.read();
        Preprocessor preprocessor = new Preprocessor();
        preprocessor.stanfordPreprocess();
        System.out.println("--------------------------------------------------");
        for (DependencyNode curentNode : preprocessor.dependencyGraph.graph)
			System.out.println(curentNode.value.value());
    }
}
