package preprocessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import tagger.Tagger;

import common.Constants;
import common.FileInOut;
import common.StringParse;

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

    File corpus_folder;
    File stopwords_file;

    Hashtable<String, ArrayList<Sentence>> clean_sentences;
    ArrayList<String> corpus_filenames;

    public Preprocessor() {
        corpus_folder = new File(Constants.CORPUS_FOLDERNAME);
    }

    /*
     * Preprocessing includes:
     *         - splitting all corpus in sentences, group by files
     *         - tagging all words using Stanford Tagger
     *         - removing punctuation ?
     *         - removing all stopwords
     *         - saving the parsed graph from all tagged words
     */
    public void preprocess() {
        corpus_filenames = FileInOut.getFiles(corpus_folder);
        Hashtable<String, String> tagged_content = tagSentences(corpus_filenames);
        Hashtable<String, ArrayList<Sentence>> tagged_sentences = StringParse.splitInSentences(tagged_content);
        clean_sentences = StringParse.removeStopwordsAndPunctuation(tagged_sentences);
        addSynonims(clean_sentences);
        System.out.println("Done preprocessing! :)");
    }

    public Hashtable<String, String> tagSentences(ArrayList<String> filenames) {
        Hashtable<String, String> tagged_sentences = new Hashtable<>();
        for (String filename : filenames) {
            Tagger tagger = new Tagger(filename);
            tagged_sentences.put(filename, tagger.addTags());
        }
        return tagged_sentences;
    }

    @SuppressWarnings("unchecked")
    public void addSynonims(Hashtable<String, ArrayList<Sentence>> sentences) {
        // add synonims to all words in sentences
        Iterator<?> it = sentences.entrySet().iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry pairs = (Map.Entry)it.next();
            for (Sentence s : (ArrayList<Sentence>)pairs.getValue()) {
                s.addSynonims();
            }
        }
    }

    public void printSentences()
    {
        for (String fileName:corpus_filenames)
        {
            for (Sentence sentence:clean_sentences.get(fileName))
            {
                System.out.println(sentence);
            }
        }
    }

    public static void coref() {
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

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

            // This is the coreference link graph
            // Each chain stores a set of mentions that link to each other,
            // along with a method for getting the most representative mention
            // Both sentence and token offsets start at 1!
            Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
        }
    }

    // TODO: delete this main
    public static void main(String[] args) {
        ReadXMLFile.read();
        Preprocessor preprocessor = new Preprocessor();
        preprocessor.preprocess();
        preprocessor.printSentences();
        coref();
    }
}
