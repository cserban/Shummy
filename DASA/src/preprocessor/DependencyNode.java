package preprocessor;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.stanford.nlp.ling.IndexedWord;

public class DependencyNode {
	 public int sentenceId;
	 public Hashtable<String, ArrayList<DependencyNode>> neighbours;
	 public ArrayList<DependencyNode> parents;
	 public ArrayList<DependencyNode> link;
	 public IndexedWord value;
	 public String lemValue;
	 public String posTag;
	 public String ner;
	 public String str;

	 // get all synonyms using: WordNetInterface.getAllSynonymsForPOS(str, pos_tag, Constants.MAX_SYNONIMS);
	 public ArrayList<String> synonims;
	 
	 public DependencyNode(IndexedWord value,int id,String str)
	 {
		 this.sentenceId = id;
		 this.neighbours = new Hashtable<String, ArrayList<DependencyNode>>();
		 this.parents = new ArrayList<>();
		 this.link = new ArrayList<>();
		 this.value = value;
		 this.synonims = new ArrayList<>();
		 this.str = str;
	 }
}
