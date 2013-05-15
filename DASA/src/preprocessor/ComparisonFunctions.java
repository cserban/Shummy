package preprocessor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import preprocessor.Preprocessor.BFSNode;
import preprocessor.Preprocessor.SentenceWithTag;
import preprocessor.Preprocessor.WordWithTag;
import wordnet.WordNetInterface;

public class ComparisonFunctions {
    public ArrayList<DependencyNode> firstGraphArrayList;
    public ArrayList<DependencyNode> secondGraphArrayList;
    

    
	
	
    public int subgraphComparisonSum;
    ArrayList<SentenceWithTag> allSentences;
    SentenceWithTag sentence1, sentence2;
    Hashtable<String,  ArrayList<NodeComparisonResultClass>> compArray;
    double gScore;
    
    public class ParentChildRelationClass{
    	DependencyNode parentNode;
    	DependencyNode childNode;
    	String relation;
    	/**
    	 * @brief Constructs a class that has a parent and the relation between parent and child
    	 * @param parentNode
    	 * @param childNode
    	 * @param relation
    	 */
    	public ParentChildRelationClass(DependencyNode parentNode, DependencyNode childNode, String relation){
    		this.parentNode = parentNode;
    		this.relation = relation;
    		this.childNode = childNode;
    	}
    }
    
    ArrayList<ParentChildRelationClass> abbrevForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> abbrevForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> acompForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> acompForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> advclForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> advclForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> advmodForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> advmodForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> agentForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> agentForSecondGraph = new ArrayList<ParentChildRelationClass>();

	ArrayList<ParentChildRelationClass> amodForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> amodForSecondGraph = new ArrayList<ParentChildRelationClass>();

	ArrayList<ParentChildRelationClass> apposForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> apposForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> attrForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> attrForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> auxForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> auxForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> auxpassForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> auxpassForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> ccForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> ccForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> ccompForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> ccompForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> complmForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> complmForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> conjForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> conjForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> copForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> copForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> csubjForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> csubjForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> csubjpassForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> csubjpassForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> depForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> depForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> detForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> detForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> dobjForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> dobjForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> explForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> explForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> infmodForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> infmodForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> iobjForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> iobjForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> markForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> markForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> mweForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> mweForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> nnForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> nnForSecondGraph = new ArrayList<ParentChildRelationClass>();

	ArrayList<ParentChildRelationClass> negForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> negForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> npadvmodForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> npadvmodForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> nsubjForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> nsubjForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> nsubjpassForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> nsubjpassForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> numForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> numForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> numberForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> numberForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> parataxisForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> parataxisForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> partmodForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> partmodForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> pcompForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> pcompForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> pobjForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> pobjForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> possForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> possForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> possessiveForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> possessiveForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> preconjForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> preconjForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	
	ArrayList<ParentChildRelationClass> predetForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> predetForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> prepForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> prepForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> prepcForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> prepcForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> prtForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> prtForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> purpclForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> purpclForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> quantmodForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> quantmodForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> rcmodForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> rcmodForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> refForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> refForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> relForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> relForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> rootForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> rootForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> tmodForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> tmodForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> xcompForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> xcompForSecondGraph = new ArrayList<ParentChildRelationClass>();
	
	ArrayList<ParentChildRelationClass> xsubjForFirstGraph = new ArrayList<ParentChildRelationClass>();
	ArrayList<ParentChildRelationClass> xsubjForSecondGraph = new ArrayList<ParentChildRelationClass>();
    
    public class SentenceComparisonResultClass{
        String word1;
        String word2;
        double sum;
        double wsScore;
        
        
        public SentenceComparisonResultClass(String word1, String word2, double sum){
            this.word1 = word1;
            this.word2 = word2;
            this.sum = sum;
        }
        
        public SentenceComparisonResultClass(String word1){
            this.word1 = word1;
        }
    }
    
    public class NodeComparisonResultClass{
        DependencyNode firstNode;
        DependencyNode secondNode;
        String firstNodeValue;
        String secondNodeValue;
        double sum;
        
        /**
         * 
         * @param firstGraphNode - the node from the first graph
         * @param secondGraphNode - the node from the second graph
         * @param nodesum - the similarity between them
         * @param firstNodeValue - first node's lemmatized value, real value or synonym value 
         * @param secondNodeValue - second node's lemmatized value, real value or synonym value 
         */
        public NodeComparisonResultClass(DependencyNode firstGraphNode,
                DependencyNode secondGraphNode, 
                double nodesum, 
                String firstNodeValue, 
                String secondNodeValue) {
            // TODO Auto-generated constructor stub
            this.firstNode = firstGraphNode;
            this.secondNode = secondGraphNode;
            this.sum = nodesum;
            this.firstNodeValue = firstNodeValue;
            this.secondNodeValue = secondNodeValue;
        }
        
    }
    
    /**
     * @brief Constructor for comparing sentences
     * @param SentenceWithTag : sentence1 - first sentence to be compared
     * @param SentenceWithTag : sentence2 - sentence to compare the first on with
     * @param ArrayList<SentenceWithTag> : allSentences - all sentences in the document
     */
    public ComparisonFunctions(SentenceWithTag sentence1, 
            SentenceWithTag sentence2, 
            ArrayList<SentenceWithTag> allSentences){
        this.sentence1 = sentence1;
        this.sentence2 = sentence2; 
        this.allSentences = allSentences;
    }
    
    /**
     * @brief compares 2 graphs
     * @param ArrayList<DependencyNode> : firstGraphArrayList - all nodes in subgraph
     * @param ArrayList<DependencyNode> : secondGraphArrayList - all nodes in subgraph
     */
    public ComparisonFunctions(ArrayList<DependencyNode> firstGraphArrayList,
            ArrayList<DependencyNode> secondGraphArrayList)
    {
        this.firstGraphArrayList = firstGraphArrayList;
        this.secondGraphArrayList = secondGraphArrayList;
        this.subgraphComparisonSum = 0;
        this.compArray = new Hashtable<String,  ArrayList<NodeComparisonResultClass>>();
    }
    
    /**
     * @brief computes the tfidf score for a word: w in a sentence: sentence
     * @param w 
     * @param sentence
     * @return tfidf score
     */
    private double getTfIdfScoreForWord(WordWithTag w, SentenceWithTag sentence){
        double tfidf = 0;
        double tf = 0;
        double idf = 0;
        double wordFreqInSentence = sentence.searchForWord(w).size();
        double maxWordFrequencyInSentence = sentence.getMaxWordFrequency();
        tf =  (0.5 + (0.5 * wordFreqInSentence)/maxWordFrequencyInSentence);
        
        int noSentences = this.allSentences.size();
        // avoid division by 0 
        int noSentencesContainingTerm = 1;
        for(int i = 0; i < noSentences; i++){
            if (this.allSentences.get(i).sentenceContainsWord(w)){
                noSentencesContainingTerm++;
            }
        }
        idf = Math.log(noSentences/noSentencesContainingTerm);
        tfidf = tf*idf;
        
        return tfidf;
    }
    
    public double getWikiScore(WordWithTag w, SentenceWithTag sentence){
        double tfidf = 0;
        double tf = 0;
        double idf = 0;
        double wordFreqInSentence = sentence.searchForWord(w).size();
        double maxWordFrequencyInSentence = sentence.getMaxWordFrequency();
        tf =  (0.5 + (0.5 * wordFreqInSentence)/maxWordFrequencyInSentence);
        
        int noSentences = this.allSentences.size();
        // avoid division by 0 
        int noSentencesContainingTerm = 1;
        for(int i = 0; i < noSentences; i++){
            if (this.allSentences.get(i).sentenceContainsWord(w)){
                noSentencesContainingTerm++;
            }
        }
        idf = Math.log(noSentences/noSentencesContainingTerm);
        
        return (1 + Math.log(tf)) * (1 + Math.log(idf));
    }
    
    public void getGoogleScore(){
        gScore = 0;
        
        for (WordWithTag w : this.sentence1.wordsList){
            double cs = 1.0;
            double distance = 0;
            ArrayList<Integer> positionsForWord = this.sentence2.searchForWord(w);
            if (positionsForWord != null && positionsForWord.size() > 0)
                distance = positionsForWord.get(0);
            cs = cs * Math.pow(2, Math.pow((1+distance), -1));
            gScore += cs;
        }
        gScore = gScore/this.sentence2.wordsList.size();
    }
    
    public ArrayList<SentenceComparisonResultClass> compareSentences(){
        ArrayList<SentenceComparisonResultClass> compareArrayList = new ArrayList<SentenceComparisonResultClass>();
        for (WordWithTag w : this.sentence1.wordsList){
            SentenceComparisonResultClass compWordScore = new SentenceComparisonResultClass(w.word);
            compWordScore.wsScore = getWikiScore(w, this.sentence2);
            compareArrayList.add(compWordScore);
        }
        
        return compareArrayList;
    }
    
    private void printSecondGraphArrayList(){
    	System.out.println("--------------------------------------------------");
    	System.out.println("Second list");
    	 for (DependencyNode secondGraphNode : this.secondGraphArrayList){
    		 System.out.println(secondGraphNode.value.value());
    	 }
    	 System.out.println("--------------------------------------------------");
    }
    
    private void printFirstGraphArrayList(){
    	System.out.println("--------------------------------------------------");
    	System.out.println("First list");
    	 for (DependencyNode secondGraphNode : this.firstGraphArrayList){
    		 System.out.println(secondGraphNode.value.value()); 
    	 }
    	System.out.println("--------------------------------------------------");
    }
    
    private void printComparisonNodes(){
    	 System.out.println("--------------------------------------------------");
         Enumeration<String> enumKey = compArray.keys();
      	while(enumKey.hasMoreElements()) {
      		 String key = enumKey.nextElement();
      		 ArrayList<NodeComparisonResultClass> nodeArr = compArray.get(key);
      		for(int i = 0; i < nodeArr.size(); i++){
      	    	NodeComparisonResultClass n = nodeArr.get(i);
      	    	System.out.println(n.firstNode.posTag + ":" + n.firstNodeValue + ":" + n.secondNodeValue + ":" + n.sum);
      		}
      	}
      	
      	System.out.println("--------------------------------------------------");
    }
    
    /**
     * @brief returneaza parintele din lista, in functie de nod
     * @param list - lista de dependinte
     * @childNode - copilul pentru care vreau parinte din lista
     * @return
     */
    private ArrayList<DependencyNode> getSpecificParentsForNode(ArrayList<ParentChildRelationClass> list, DependencyNode childNode){
    	ArrayList<DependencyNode> parents = new ArrayList<DependencyNode>();
    	for (int i = 0; i < list.size(); i++){
    		ParentChildRelationClass np = list.get(i);
    		if (np.childNode.equals(childNode))
    			parents.add(np.parentNode);
    	}
    	return parents;
    }
    
    /**
     * calculeaza scorul BM25Score
     * @return scorul BM25Score
     */
    public double computeBM25Score(){
    	double BM25Score = 0.0;
    	int noSentences =  this.allSentences.size();
    	double avgdl = 0.0;
    	int now = 0;
    	double k1 = 1.5;
		double b = 0.75;
		
    	for (int i = 0; i < noSentences; i++){
    		now += this.allSentences.get(i).wordsList.size();
    	}
    	avgdl = (double)now/(double)noSentences;
    	
    	for (WordWithTag w : this.sentence1.wordsList){
    		double idf = 0;
    		
        	double wordFrequencyInSentence = 0.0;
        	
            // avoid division by 0 
            int noSentencesContainingTerm = 0;
            for(int i = 0; i < noSentences; i++){
                if (this.allSentences.get(i).sentenceContainsWord(w)){
                    noSentencesContainingTerm++;
                }
            }
            idf = Math.log((noSentences - noSentencesContainingTerm + 0.5)/(noSentencesContainingTerm + 0.5));
            
            wordFrequencyInSentence = this.sentence2.getMaxWordFrequency();
            BM25Score += idf * wordFrequencyInSentence *(k1+1)/(wordFrequencyInSentence + k1 * (1 - b + b * (double)this.sentence2.wordsList.size()/avgdl));
    	}
    	
    	System.out.println("BM25Score:"+BM25Score);
    	return BM25Score;
    }
    
    /**
     * @brief functia care adauga efectiv in array
     * @param relation - relatia dintre parinte si copil, in functie de relatii se adauga in vector
     * @param parentNode - nodul parinte
     * @param childNode - nodul copil
     * @param firstOrSecond - daca se adauga pt primul (1) sau al doilea graf (2)
     */
    private void addToArray(String relation, DependencyNode parentNode, DependencyNode childNode, int firstOrSecond)
    {
    	if(relation.equals("abbrev"))
    	{
			if (firstOrSecond == 1)
				this.abbrevForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.abbrevForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("acomp"))
		{
			if (firstOrSecond == 1)
				this.acompForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.acompForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("advcl"))
		{
			if (firstOrSecond == 1)
				this.advclForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.advclForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("advmod"))
		{
			if (firstOrSecond == 1)
				this.advmodForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.advmodForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("agent"))
		{
			if (firstOrSecond == 1)
				this.agentForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.agentForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("amod"))
		{
			if (firstOrSecond == 1)
				this.amodForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.amodForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("appos"))
		{
			if (firstOrSecond == 1)
				this.apposForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.apposForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("attr"))
		{
			if (firstOrSecond == 1)
				this.attrForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.attrForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("aux"))
		{
			if (firstOrSecond == 1)
				this.auxForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.auxForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("auxpass"))
		{
			if (firstOrSecond == 1)
				this.auxpassForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.auxpassForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("cc"))
		{
			if (firstOrSecond == 1)
				this.ccForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.ccForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("ccomp"))
		{
			if (firstOrSecond == 1)
				this.ccompForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.ccompForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("complm"))
		{
			if (firstOrSecond == 1)
				this.complmForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.complmForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.contains("conj_"))
		{
			if (firstOrSecond == 1)
				this.conjForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.conjForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("cop"))
		{
			if (firstOrSecond == 1)
				this.copForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.copForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("csubj"))
		{
			if (firstOrSecond == 1)
				this.csubjForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.csubjForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("dep"))
		{
			if (firstOrSecond == 1)
				this.depForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.depForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("det"))
		{
			if (firstOrSecond == 1)
				this.detForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.detForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("dobj"))
		{
			if (firstOrSecond == 1)
				this.dobjForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.dobjForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("expl"))
		{
			if (firstOrSecond == 1)
				this.explForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.explForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("infmod"))
		{
			if (firstOrSecond == 1)
				this.infmodForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.infmodForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("iobj"))
		{
			if (firstOrSecond == 1)
				this.iobjForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.iobjForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("mark"))
		{
			if (firstOrSecond == 1)
				this.markForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.markForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("mwe"))
		{
			if (firstOrSecond == 1)
				this.mweForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.mweForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("neg"))
		{
			if (firstOrSecond == 1)
				this.negForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.negForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("nn"))
		{
			if (firstOrSecond == 1)
				this.nnForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.nnForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("npadavmod"))
		{
			if (firstOrSecond == 1)
				this.npadvmodForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.npadvmodForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("nsubj"))
		{
			if (firstOrSecond == 1)
				this.nsubjForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.nsubjForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		
		else if(relation.equals("nsubjpass"))
		{
			if (firstOrSecond == 1)
				this.nsubjpassForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.nsubjpassForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("num"))
		{
			if (firstOrSecond == 1)
				this.numForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.numForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("number"))
		{
			if (firstOrSecond == 1)
				this.numberForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.numberForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("parataxis"))
		{
			if (firstOrSecond == 1)
				this.parataxisForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.parataxisForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("partmod"))
		{
			if (firstOrSecond == 1)
				this.partmodForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.partmodForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("pcomp"))
		{
			if (firstOrSecond == 1)
				this.pcompForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.pcompForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("pobj"))
		{
			if (firstOrSecond == 1)
				this.pobjForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.pobjForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("poss"))
		{
			if (firstOrSecond == 1)
				this.possForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.possForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("possessive"))
		{
			if (firstOrSecond == 1)
				this.possessiveForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.possessiveForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("preconj"))
		{
			if (firstOrSecond == 1)
				this.preconjForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.preconjForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("predet"))
		{
			if (firstOrSecond == 1)
				this.predetForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.predetForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.contains("prep_"))
		{
			if (firstOrSecond == 1)
				this.prepForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.prepForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("prt"))
		{
			if (firstOrSecond == 1)
				this.prtForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.prtForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("purpcl"))
		{
			if (firstOrSecond == 1)
				this.purpclForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.purpclForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("quantmod"))
		{
			if (firstOrSecond == 1)
				this.quantmodForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.quantmodForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("rcmod"))
		{
			if (firstOrSecond == 1)
				this.rcmodForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.rcmodForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("ref"))
		{
			if (firstOrSecond == 1)
				this.refForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.refForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("rel"))
		{
			if (firstOrSecond == 1)
				this.relForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.relForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("root"))
		{
			if (firstOrSecond == 1)
				this.rootForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.rootForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("tmod"))
		{
			if (firstOrSecond == 1)
				this.tmodForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.tmodForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("xcomp"))
		{
			if (firstOrSecond == 1)
				this.xcompForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.xcompForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
		else if(relation.equals("xsubj"))
		{
			if (firstOrSecond == 1)
				this.xsubjForFirstGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
			else
				this.xsubjForSecondGraph.add(new ParentChildRelationClass(parentNode, childNode, relation));
		}
    }
    	
    /**
     * @brief populates the relations arrays
     */
    public void populateRelationArrays(){
    	System.out.println("Populez vectorii in care retin relatii");
    	// for each node I have a parent and the parent/child relation
    	  for (DependencyNode firstGraphNode : this.firstGraphArrayList){
    		  if (firstGraphNode.parents.isEmpty() == false){
	    			for(DependencyNode np : firstGraphNode.parents){
	    				if (np.neighbours != null){
	            			Enumeration<String> enumKey1 = np.neighbours.keys();
	                    	while(enumKey1.hasMoreElements()) {
	                    	    String key1 = enumKey1.nextElement();
	                    	    ArrayList<DependencyNode> valuesList = np.neighbours.get(key1);
	                    	    if(valuesList.contains(firstGraphNode))
	                    	    {
	                    	    	System.out.println("parinte: " + np.value.value() + " copil: " + firstGraphNode.value.value() + " relatie: "+key1);
	                    	    	//secondNodeParents.add(new ParentChildRelationClass(np, n.secondNode, key1));
	                    	    	addToArray(key1, np, firstGraphNode, 1);
	                    	    }
	                    	}
	            		}	
	    			}
	    		}
    	  }
    	  System.out.println("--------------------------------------------------");
    	  for (DependencyNode secondGraphNode : this.secondGraphArrayList){
    		  if (secondGraphNode.parents.isEmpty() == false){
	    			for(DependencyNode np : secondGraphNode.parents){
	    				if (np.neighbours != null){
	            			Enumeration<String> enumKey1 = np.neighbours.keys();
	                    	while(enumKey1.hasMoreElements()) {
	                    	    String key1 = enumKey1.nextElement();
	                    	    ArrayList<DependencyNode> valuesList = np.neighbours.get(key1);
	                    	    if(valuesList.contains(secondGraphNode))
	                    	    {
	                    	    	System.out.println("parinte: " + np.value.value() + " copil: " + secondGraphNode.value.value() + " relatie: "+key1);
	             	               	
//	                    	    	System.out.println("Cheie nod2 parinte " + np.value.value() + " catre copil:"+key1);
	                    	    	//secondNodeParents.add(new ParentChildRelationClass(np, n.secondNode, key1));
	                    	    	addToArray(key1, np, secondGraphNode, 2);
	                    	    }
	                    	}
	            		}	
	    			}
	    		}
    	  }
    	  System.out.println("Am terminat de populat");
	}
    
    /**
     * @brief Compares nodes in DependencyGraphs
     * @return An ArrayList containing all similar nodes
     */
    public Hashtable<String, ArrayList<NodeComparisonResultClass>> compareSubgraphs(){
        this.subgraphComparisonSum = 0;
        double nodesum = 0;
        String pos = "";
        
        printFirstGraphArrayList();
        printSecondGraphArrayList();
        populateRelationArrays();
        
        for (DependencyNode firstGraphNode : this.firstGraphArrayList){
        	boolean added = false;
        	nodesum = 0;
        	String key = firstGraphNode.value.value()+firstGraphNode.posTag;
        	System.out.println(key);
        	ArrayList<NodeComparisonResultClass> firstGraphArr = compArray.get(key);
        	
        	if (firstGraphArr == null)
        		firstGraphArr = new ArrayList<NodeComparisonResultClass>();
        	
        	for (DependencyNode secondGraphNode : this.secondGraphArrayList){
                nodesum = 0;
                if (firstGraphNode.posTag.equals(secondGraphNode.posTag) && firstGraphNode.value.value().equals(secondGraphNode.value.value())){
                    if (nodesum < 100){
                    	 nodesum = 100;
                    	// System.out.println(firstGraphNode.posTag + ":" + firstGraphNode.value.value() + ":" + secondGraphNode.value.value() + ":" + nodesum);
                    	 firstGraphArr.add(new NodeComparisonResultClass(firstGraphNode, 
                                 secondGraphNode, 
                                 nodesum, 
                                 firstGraphNode.value.value(), 
                                 secondGraphNode.value.value()));
                         added = true;
                    }            
                }
                else if (firstGraphNode.posTag.equals(secondGraphNode.posTag) && firstGraphNode.lemValue != null && 
                        secondGraphNode.lemValue != null &&
                        firstGraphNode.lemValue.equals(secondGraphNode.lemValue))
                {
                    
                    if (nodesum < 90){
                    	
                    	nodesum = 90;
                    	System.out.println(firstGraphNode.posTag + ":" + firstGraphNode.value.value() + ":" + secondGraphNode.value.value() + ":" + nodesum);
                    	firstGraphArr.add(new NodeComparisonResultClass(firstGraphNode, 
                                secondGraphNode, nodesum, 
                                firstGraphNode.lemValue, 
                                secondGraphNode.lemValue));
                    	added = true;
                    }
                           
                }
                //calcul distanta dintre cuvinte
                else if((pos = normalizePOSTag(secondGraphNode.posTag)) != null && firstGraphNode.lemValue != null && 
                        secondGraphNode.lemValue != null && 
                        (pos = normalizePOSTag(secondGraphNode.posTag)) != null) {
                    double distance = WordNetInterface.wordnet.getDistance(firstGraphNode.lemValue , secondGraphNode.lemValue, pos);
                    if (nodesum < ((1 - distance) * 100)){
                		nodesum = ((1 - distance) * 100);
                		System.out.println(firstGraphNode.posTag + ":" + firstGraphNode.value.value() + ":" + secondGraphNode.value.value() + ":" + nodesum);
                		firstGraphArr.add(new NodeComparisonResultClass(firstGraphNode, 
                                secondGraphNode, 
                                nodesum, 
                                firstGraphNode.lemValue, 
                                secondGraphNode.lemValue));
                		added = true;
                	}
                }

                else{
                	if (added == false){
                		nodesum = 0;
                	//	System.out.println(firstGraphNode.posTag + ":" + firstGraphNode.value.value() + ":" + secondGraphNode.value.value() + ":" + nodesum);
                		firstGraphArr.add(new NodeComparisonResultClass(firstGraphNode, 
                                secondGraphNode, 
                                nodesum, 
                                firstGraphNode.value.value(), 
                                secondGraphNode.value.value()));
                		added = true;
                	}
                    
                }
                added = false;
                compArray.put(key, firstGraphArr);
                this.subgraphComparisonSum += nodesum;
            }
        }
        
       
        return compArray;
    }
    
    
   
    public double getResemblanceScoreBetweenGraphs(){
    	double resemblanceFactor = 0.0;
    	Hashtable<String, ArrayList<NodeComparisonResultClass>> arr = compareSubgraphs();
    	int count = 0;
    	
    	Enumeration<String> enumKey = arr.keys();
     	while(enumKey.hasMoreElements()) {
     		int index = -1;
     		double score = 0.0;
     	    String key = enumKey.nextElement();
     	    ArrayList<NodeComparisonResultClass> nodeArr = arr.get(key);
     	    String pos = "";
//     	    System.out.println(key);
     	    double max = 0.0;
     	   
//     	    System.out.println("--------------------------------------------------");
     	    for(int i = 0; i < nodeArr.size(); i++){
     	    	NodeComparisonResultClass n = nodeArr.get(i);
     	    	System.out.println(n.firstNode.posTag + ":" + n.firstNodeValue + ":" + n.secondNodeValue + ":" + n.sum);
     	    	// daca scorul e mai mare de 70, pot sa-l adaug linistita la lista
     	    	if(n.sum > max){
     	    		max = n.sum;
     	    		index  = i;
     	    	}
     	    		
     	    }
     	  
     	   if(max > 70 && index > -1){
	    		System.out.println("Scorul:");
    	    	NodeComparisonResultClass n1 = nodeArr.get(index);
    	    	System.out.println("Am ales:" + n1.firstNodeValue + ":" + n1.secondNodeValue + ":" + n1.sum);
    	    	resemblanceFactor += n1.sum;
    	    	count++;
	    	}
     	   
//     	   System.out.println("--------------------------------------------------");
     	}
     	
     	int m = (this.secondGraphArrayList.size() > this.firstGraphArrayList.size() ? this.secondGraphArrayList.size() : this.firstGraphArrayList.size());
     	int s = 0;
     	ArrayList<DependencyNode> uniqueValues = new ArrayList<DependencyNode>();
     	for(DependencyNode dp : this.secondGraphArrayList){
     		if(!uniqueValues.contains(dp)){
     			s++;
     		}
     	}
     	double f = m * 100.0;
     	System.out.println("listadim:" + m + " count:"+count +" total score:" + f + " and resemblance:" + resemblanceFactor);
     	// cuvinte gasite la fel/sau sinonime
     	resemblanceFactor = resemblanceFactor/f;
     	System.out.println("Factor de asemanare:" + resemblanceFactor);
    	return resemblanceFactor;
    }
    
    /**
     * @brief noua functie pentru scor
     * @return double : noul scor dupa ce a luat in considerare si parintii
     */
    public double getSyntacticResemblanceScoreBetweenGraphs(){
    	double syntacticResem = 0.0;
    	// am toate arraylisturile de relatii filled
    	//populateRelationArrays();
        for (DependencyNode firstGraphNode : this.firstGraphArrayList){
        	ArrayList<DependencyNode> parents;
//        	System.out.println(""+firstGraphNode.value.value() + ":" + firstGraphNode.posTag);
        	
        	if((parents = getSpecificParentsForNode(this.nsubjForFirstGraph, firstGraphNode)).size() > 0){
//        		System.out.println("Subiectul pe care il caut e:"+firstGraphNode.value.value());
        		if((parents = getSpecificParentsForNode(this.conjForFirstGraph, parents.get(0))).size() > 0){
//        			for(DependencyNode vbs : parents)
//        				System.out.println("Subiectul pe care il mai caut este:" + vbs.value.value());
        		}
        	}
        	
        	if((parents = getSpecificParentsForNode(this.nsubjForFirstGraph, firstGraphNode)).size() > 0){
        		System.out.println("Subiectul pe care il caut e:"+firstGraphNode.value.value());
        		if((parents = getSpecificParentsForNode(this.conjForFirstGraph, parents.get(0))).size() > 0){
//        			for(DependencyNode vbs : parents)
//        				System.out.println("Subiectul pe care il mai caut este:" + vbs.value.value());
        		}
        	}
        	
        	if(getSpecificParentsForNode(this.nsubjpassForFirstGraph, firstGraphNode).size() > 0){
        		System.out.println("Subiectul pasiv pe care il caut e:"+firstGraphNode.value.value());
        		if((parents = getSpecificParentsForNode(this.conjForFirstGraph, parents.get(0))).size() > 0){
//        			for(DependencyNode vbs : parents)
//        				System.out.println("Subiectul pasiv pe care il mai caut este:" + vbs.value.value());
        		}
        	}
        	
        	if((parents = getSpecificParentsForNode(this.negForFirstGraph, firstGraphNode)).size() == 1){
//        		System.out.println("Verbul negat pe care il caut este:"+parents.get(0).value.value());
        	}
        	
        	if((parents = getSpecificParentsForNode(this.rootForFirstGraph, firstGraphNode)).size() == 1){
        		System.out.println("Verbul pe care il caut e:"+parents.get(0).value.value());
        		// pentru verb trebuie sa mai caut toate verbele legate de el prin conjunctii
        		if((parents = getSpecificParentsForNode(this.conjForFirstGraph, parents.get(0))).size() > 0){
//        			for(DependencyNode vbs : parents)
//        				System.out.println("Verbul pe care il mai caut e:" + vbs.value.value());
        		}
        	}
        	
        	if((parents = getSpecificParentsForNode(this.conjForFirstGraph, firstGraphNode)).size() > 0){
    			for(DependencyNode vbs : parents)
    				System.out.println("Cuvinte dependente:" + vbs.value.value());
    		}
        	
        	
        	
        }
        
    	
    	return syntacticResem;
    }
    
    
    private String normalizePOSTag(String pos) {
        if( pos!= null) {
           if(pos.startsWith("JJ")) {
               return "a";
           } else if (pos.startsWith("N")) {
               return "n";
           } else if (pos.startsWith("R")) {
              return "r";
           } else if (pos.startsWith("V")) {
             return "v";
           }
         }
         return null;
    }

}