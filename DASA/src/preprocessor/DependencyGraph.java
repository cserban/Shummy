package preprocessor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import wordnet.WordNetInterface;

import common.Constants;
import common.TaggerUtils;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;

public class DependencyGraph {
	public ArrayList<DependencyNode> graph;
	Hashtable<String, ArrayList<DependencyNode>> ners;

	public DependencyGraph() {
		this.graph = new ArrayList<>();
		this.ners = new Hashtable<>();
	}

	void addSentence(SemanticGraph dependencies, int sentenceId) {
		//dependencies.prettyPrint();
		if (dependencies.getRoots().size() != 1) {
			System.err.println("Sentence " + sentenceId + " has " +
								dependencies.getRoots().size() + " roots !!!");
		} else {
			ArrayList<DependencyNode> tmpList = new ArrayList<>();
			IndexedWord root = dependencies.getFirstRoot();
			generateGraph(dependencies, root, tmpList, sentenceId);

			for (DependencyNode curentNode : tmpList) {
				for (DependencyNode possibleChildNode : tmpList) {
					if (!possibleChildNode.equals(curentNode)
							&& dependencies.getChildList(curentNode.value)
									.contains(possibleChildNode.value)) {
						
						if (curentNode.neighbours.get(dependencies.getEdge(curentNode.value,possibleChildNode.value).toString()) == null)
						{
							ArrayList<DependencyNode> nodeList = new ArrayList<>();
							nodeList.add(possibleChildNode);
							curentNode.neighbours.put(dependencies.getEdge(curentNode.value,possibleChildNode.value).toString(),nodeList);
						}
						else
						{
							curentNode.neighbours.get(dependencies.getEdge(curentNode.value,possibleChildNode.value).toString()).add(possibleChildNode);
						}
						if (!possibleChildNode.parents.contains(curentNode))
							possibleChildNode.parents.add(curentNode);
					}
				}
			}
			for (DependencyNode curentNode : tmpList){
				if (curentNode.value.equals(root))
					graph.add(curentNode);
			}
		}
	}

	void generateGraph(SemanticGraph dependencies, IndexedWord curentWord,
			ArrayList<DependencyNode> tmpList, int sentenceId) {

		DependencyNode curentNode = null;
		boolean exists = false;
		for (DependencyNode checkNode : tmpList) {
			if (checkNode.value.equals(curentWord)) {
				exists = true;
			}
		}
		if (!exists) {
			curentNode = new DependencyNode(curentWord, sentenceId);
			curentNode.lemValue = curentWord.lemma();
			curentNode.posTag = curentWord.tag();
			curentNode.ner = curentWord.ner();
			for (String synonim : WordNetInterface.getAllSynonymsForPOS(curentWord.value(),
					TaggerUtils.fromStanfordTagToWordnetTag(curentNode.posTag), Constants.MAX_SYNONIMS))
				curentNode.synonims.add(synonim);
			tmpList.add(curentNode);
		}
		if (dependencies.getChildList(curentWord).size() != 0) {
			for (IndexedWord child : dependencies.getChildList(curentWord))
				generateGraph(dependencies, child, tmpList, sentenceId);
		}
	}
	
	ArrayList<String> setNers() {
		Hashtable<DependencyNode, HashSet<String>> ners = new Hashtable<>();
		for (DependencyNode root : this.graph) {
			ArrayList<DependencyNode> nodes = Preprocessor.BFS(root);
			// get all ners of this subgraph
			HashSet<String> ner_set = new HashSet<>();
			for (DependencyNode node : nodes)
				if (!node.ner.equals(Constants.DEFAULT_NER))
					ner_set.add(node.ner);
			ners.put(root, ner_set);
		}
        Enumeration<DependencyNode> enumKey = ners.keys();
        while(enumKey.hasMoreElements()) {
            DependencyNode key = enumKey.nextElement();
            System.out.print(key.value + ": ");
            HashSet<String> val = ners.get(key);
            Iterator<String> it = val.iterator();
            while(it.hasNext()) {
            	String ne = it.next();
            	if (this.ners.get(ne) == null)
            		this.ners.put(ne, new ArrayList<DependencyNode>());
            	ArrayList<DependencyNode> ne_list = this.ners.get(ne);
            	ne_list.add(key);
            	this.ners.put(ne, ne_list);
            }
            System.out.println();
        }
		return null;
	}	
}
