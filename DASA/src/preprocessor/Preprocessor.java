package preprocessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;

import common.Constants;
import common.FileInOut;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
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

	
	public Double answersScore;
    public DependencyGraph dependencyGraph;
    public ArrayList<SentenceWithTag> sentencesFromCorpus;
    public static  ArrayList<ArrayList<DependencyNode>> subgraphs;
    public Preprocessor() {
        this.dependencyGraph = new DependencyGraph();
        this.sentencesFromCorpus = new ArrayList<SentenceWithTag>();
        this.subgraphs = new ArrayList<ArrayList<DependencyNode>>();
    }

    public void stanfordPreprocess(String corpus) {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        if (corpus == null) {
	        corpus = "";
	        try {
	            corpus = FileInOut.readFile(new File(Constants.QA4MRE_FOLDER + "docs/1.txt"));
	        }
	        catch (IOException e) {}
        }

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(corpus);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            // traversing the words in the current sentence
        	SentenceWithTag newTaggedSentence = new SentenceWithTag();
        	int poz = 0;
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(NamedEntityTagAnnotation.class);
             // this is the list with all sentences tagged
                WordWithTag w = new WordWithTag(word, pos, ne, poz);
                // word position in sentence
                poz++;
                // add word in sentence
                newTaggedSentence.add(w);
            }
            this.sentencesFromCorpus.add (newTaggedSentence);
            
            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeAnnotation.class);
            //System.out.println("TreeAnnotation ");
            //printTree(tree,0);

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
            dependencyGraph.addSentence(dependencies, sentences.indexOf(sentence),sentence.toString());
        }
        dependencyGraph.setNers();
        // This is the coreference link graph
        // Each chain stores a set of mentions that link to each other,
        // along with a method for getting the most representative mention
        // Both sentence and token offsets start at 1!
        Map<Integer, CorefChain> coref = document.get(CorefChainAnnotation.class);

        for(Map.Entry<Integer, CorefChain> entry : coref.entrySet()) {
            CorefChain c = entry.getValue();

            //this is because it prints out a lot of self references which aren't that useful
            if(c.getMentionsInTextualOrder().size() <= 1)
                continue;

            CorefMention cm = c.getRepresentativeMention();
            String clust = "";
            List<CoreLabel> tks = document.get(SentencesAnnotation.class).get(cm.sentNum-1).get(TokensAnnotation.class);
            for(int i = cm.startIndex-1; i < cm.endIndex-1; i++)
                clust += tks.get(i).get(TextAnnotation.class) + " ";
            clust = clust.trim();
            System.out.println("representative mention: \"" + clust + "\" is mentioned by:");

            for(CorefMention m : c.getMentionsInTextualOrder()){
                String clust2 = "";
                tks = document.get(SentencesAnnotation.class).get(m.sentNum-1).get(TokensAnnotation.class);
                for(int i = m.startIndex-1; i < m.endIndex-1; i++)
                    clust2 += tks.get(i).get(TextAnnotation.class) + " ";
                clust2 = clust2.trim();
                //don't need the self mention
                if(clust.equals(clust2))
                    continue;
                System.out.println("\t" + clust2  +" "+m.sentNum);
                if (dependencyGraph.graph.get(m.sentNum-1).str.contains(clust2))
                {
            		System.out.println();
                }
            }
        }


    }
    
    void printTree(Tree tree, int lev)
    {
    	for (int i =0;i<lev;i++)
    		System.out.print(" ");
    	System.out.println(tree.value());
    	for (Tree copil : tree.children())
    		printTree(copil,lev+1);
    }

    /**
	 * @brief performs BFS starting with root node and adds nodes related to root in graph
	 * @param rootNode - root node for a sentence
	 * @return ArrayList<DependencyNode> : contains all nodes for a sentence
	 */
	public static ArrayList<DependencyNode> BFS (DependencyNode rootNode){
	    ArrayList <DependencyNode> nodesInGraph = new ArrayList<DependencyNode>();
	    System.out.println("\n");
	    Queue<BFSNode> q = new LinkedList<BFSNode>();
	    q.add(new BFSNode(rootNode, false));

	    System.out.print(" " + rootNode.value.value());
	    while (q.isEmpty() == false){
	        BFSNode v = q.poll();
	        for(BFSNode newNode : v.neighbours){
	            if(newNode.visited == false){
	                q.add(newNode);
	            }
	        }
	        v.visited = true;
	        nodesInGraph.add(v.n);
	    }
	    System.out.println("\n");
	    return nodesInGraph;
	}

    // TODO: delete this main
    public static void main(String[] args) {
        // ReadXMLFile.read();
        Preprocessor preprocessor = new Preprocessor();
        preprocessor.stanfordPreprocess(null);
        System.out.println("--------------------------------------------------");
        for (DependencyNode curentNode : preprocessor.dependencyGraph.graph)
        {
            subgraphs.add(BFS(curentNode));
        }
        ComparisonFunctions comp = new ComparisonFunctions(subgraphs.get(0), subgraphs.get(1));
        comp.getResemblanceScoreBetweenGraphs(0);
        
        System.out.println("--------------------------------------------------");
        // print NER list of all root nodes
        Hashtable<String, ArrayList<DependencyNode>> table = preprocessor.dependencyGraph.ners;
        Enumeration<String> enumKey = table.keys();
        while(enumKey.hasMoreElements()) {
            String key = enumKey.nextElement();
            System.out.print(key + ": ");
            ArrayList<DependencyNode> val = table.get(key);
            Iterator<DependencyNode> it = val.iterator();
            while(it.hasNext())
                System.out.print(it.next().sentenceId + ", ");
            System.out.println();
        }
    }
    
    public ArrayList<DependencyNode> compareWithGraph(DependencyNode questionNode,ArrayList<DependencyNode> toCompareGraph,Integer returnCandidatesNumber, int type)
    {

    	ArrayList<DependencyNode> candidat = new ArrayList<>();
    	ArrayList<Double> values = new ArrayList<>();
    	double minScore = 0.0;
    	subgraphs.clear();
        subgraphs.add(BFS(questionNode));
        
        for (DependencyNode curentNode : toCompareGraph)
        {
        	subgraphs.add(BFS(curentNode));
        	ComparisonFunctions comp = new ComparisonFunctions(subgraphs.get(0), subgraphs.get(1));
        	double score = comp.getResemblanceScoreBetweenGraphs(type);
        	
        	if (candidat.size() < returnCandidatesNumber)
        	{
        		candidat.add(curentNode);
        		values.add(score);
        	}
        	
        	else if (score > minScore)
        	{
        		for (int i = 0; i<candidat.size();i++)
        		{
        			if(values.get(i) == minScore)
        			{
        				values.set(i, score);
        				candidat.set(i, curentNode);
        				break;
        			}
        		}
        	}
        	
        	minScore = values.get(0);
        	for (Double v : values)
        	{
        		if (v < minScore)
        		{
        			minScore = v;
        		}
        	}
        	
            subgraphs.remove(1);
        }
        if (candidat.size() == 1)
        {
        	answersScore = minScore;
        }
        return candidat;
    }

}
