package preprocessor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import preprocessor.Preprocessor.SentenceWithTag;
import preprocessor.Preprocessor.WordWithTag;
import wordnet.WordNetInterface;

public class ComparisonFunctions {
    public ArrayList<DependencyNode> firstGraphArrayList;
    public ArrayList<DependencyNode> secondGraphArrayList;
    public int subgraphComparisonSum;
    ArrayList<SentenceWithTag> allSentences;
    SentenceWithTag sentence1, sentence2;
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
    
    /**
     * @brief Compares nodes in DependencyGraphs
     * @return An ArrayList containing all similar nodes
     */
    public Hashtable<String, ArrayList<NodeComparisonResultClass>> compareSubgraphs(){
        Hashtable<String,  ArrayList<NodeComparisonResultClass>> compArray = new Hashtable<String,  ArrayList<NodeComparisonResultClass>>();
        this.subgraphComparisonSum = 0;
        double nodesum = 0;
        String pos = "";
        
        printFirstGraphArrayList();
        printSecondGraphArrayList();
        
        
        for (DependencyNode firstGraphNode : this.firstGraphArrayList){
        	boolean added = false;
        	nodesum = 0;
        	String key = firstGraphNode.value.value()+firstGraphNode.posTag;
        	ArrayList<NodeComparisonResultClass> firstGraphArr = compArray.get(key);
        	
        	if (firstGraphArr == null)
        		firstGraphArr = new ArrayList<NodeComparisonResultClass>();
        	
        	for (DependencyNode secondGraphNode : this.secondGraphArrayList){
            	//TODO: verifica ce relatie are cu parintele si in ce relatie sunt parintii celor 2 subgrafuri
                nodesum = 0;
                if (firstGraphNode.posTag.equals(secondGraphNode.posTag)){
                    if (firstGraphNode.value.value().equals(secondGraphNode.value.value())){
                        if (nodesum < 100){
                        	 nodesum = 100;
                        	
                        	 firstGraphArr.add(new NodeComparisonResultClass(firstGraphNode, 
                                     secondGraphNode, 
                                     nodesum, 
                                     firstGraphNode.value.value(), 
                                     secondGraphNode.value.value()));
                             added = true;
                        }            
                    }
                    else if (firstGraphNode.lemValue != null && 
                            secondGraphNode.lemValue != null &&
                            firstGraphNode.lemValue.equals(secondGraphNode.lemValue))
                    {
                        
                        if (nodesum < 90){
                        	nodesum = 90;
                        	firstGraphArr.add(new NodeComparisonResultClass(firstGraphNode, 
                                    secondGraphNode, nodesum, 
                                    firstGraphNode.lemValue, 
                                    secondGraphNode.lemValue));
                        	added = true;
                        }
                               
                    }
                    //calcul distanta dintre cuvinte
                    else if(firstGraphNode.lemValue != null && 
                            secondGraphNode.lemValue != null && 
                            (pos = normalizePOSTag(secondGraphNode.posTag)) != null) {
                        double distance = WordNetInterface.wordnet.getDistance(firstGraphNode.lemValue , secondGraphNode.lemValue, pos);
                        if (nodesum < ((1 - distance) * 100)){
                    		nodesum = ((1 - distance) * 100);
                    
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
                }
                this.subgraphComparisonSum += nodesum;
            }
        }
        
        return compArray;
    }
   
    public double getResemblanceScoreBetweenGraphs(){
    	double resemblanceFactor = 0.0;
    	Hashtable<String, ArrayList<NodeComparisonResultClass>> arr = compareSubgraphs();
    	int count = 0;
    	ArrayList<DependencyNode> subjectsForFirstGraph = new ArrayList<DependencyNode>();
    	ArrayList<DependencyNode> subjectsForSecondGraph = new ArrayList<DependencyNode>();
    	ArrayList<DependencyNode> predicatesForFirstGraph = new ArrayList<DependencyNode>();
    	ArrayList<DependencyNode> predicatesForSecondGraph = new ArrayList<DependencyNode>();
    	Enumeration<String> enumKey = arr.keys();
     	while(enumKey.hasMoreElements()) {
     		int index = -1;
     		double score = 0.0;
     	    String key = enumKey.nextElement();
     	    ArrayList<NodeComparisonResultClass> nodeArr = arr.get(key);
     	    String pos = "";
//     	    System.out.println(key);
//     	    System.out.println("--------------------------------------------------");
     	    for(int i = 0; i < nodeArr.size(); i++){
//     	    	System.out.println("--------------------------------------------------");
     	    	NodeComparisonResultClass n = nodeArr.get(i);
//     	    	System.out.println(n.firstNode.posTag + ":" + n.firstNodeValue + ":" + n.secondNodeValue + ":" + n.sum);
     	    	if (score < n.sum && n.firstNode.posTag.charAt(0) != 'V'){
     	    		ArrayList<ParentChildRelationClass> firstNodeParents = new ArrayList<ParentChildRelationClass>();
     	    		ArrayList<ParentChildRelationClass> secondNodeParents = new ArrayList<ParentChildRelationClass>();
     	    		if (n.firstNode.parents.isEmpty() == false){
     	    			for(DependencyNode np : n.firstNode.parents){
     	    				if (np.neighbours != null){
     	            			Enumeration<String> enumKey1 = np.neighbours.keys();
     	                    	while(enumKey1.hasMoreElements()) {
     	                    	    String key1 = enumKey1.nextElement();
     	                    	    ArrayList<DependencyNode> valuesList = np.neighbours.get(key1);
     	                    	    if(valuesList.contains(n.firstNode)){
     	                    	    	//System.out.println("Cheie nod1 parinte " + np.value.value() + " catre copil:"+key1);
     	                    	    	firstNodeParents.add(new ParentChildRelationClass(np, n.firstNode, key1));
     	                    	    }
     	                    	}
     	            		}
     	    				
     	    			}
     	    		}
     	    		
     	    		if (n.secondNode.parents.isEmpty() == false){
     	    			for(DependencyNode np : n.secondNode.parents){
     	    				if (np.neighbours != null){
     	            			Enumeration<String> enumKey1 = np.neighbours.keys();
     	                    	while(enumKey1.hasMoreElements()) {
     	                    	    String key1 = enumKey1.nextElement();
     	                    	    ArrayList<DependencyNode> valuesList = np.neighbours.get(key1);
     	                    	    if(valuesList.contains(n.secondNode)){
     	                    	    	//System.out.println("Cheie nod2 parinte " + np.value.value() + " catre copil:"+key1);
     	                    	    	secondNodeParents.add(new ParentChildRelationClass(np, n.secondNode, key1));
     	                    	    }
     	                    	}
     	            		}	
     	    			}
     	    		}
     	    		
     	    		if(n.secondNode.parents.isEmpty() == false && n.firstNode.parents.isEmpty() == false){
     	    		//	System.out.println("Tag nod:" + n.secondNode.posTag);
     	    			for(ParentChildRelationClass parentChild : firstNodeParents){
 	    					for(ParentChildRelationClass parentChild2 : secondNodeParents){
//     	    					System.out.println("Parinte1:" + parentChild.parentNode.value.value() +
//     	    							" Parinte2:" + parentChild2.parentNode.value.value());
     	    					
     	    					// daca am aceleasi taguri pentru parinti
     	    					if(parentChild.parentNode.posTag.equals(parentChild2.parentNode.posTag)){
     	    						System.out.println(parentChild.relation +":" + parentChild2.relation);
     	    						if(parentChild.relation.equals(parentChild2.relation)){
     	    							if (parentChild.relation.contains("subj")){
//     	    								System.out.println("Parinte:"+parentChild.parentNode.value.value());
     	    								if(!subjectsForFirstGraph.contains(parentChild.childNode))
     	    									subjectsForFirstGraph.add(parentChild.childNode);
     	    								if(!subjectsForSecondGraph.contains(parentChild2.childNode))
     	    									subjectsForSecondGraph.add(parentChild2.childNode);
     	    								
     	    								// daca in lista de predicate, am verbe care sunt parintii subiectului meu si sunt
     	    								// egale pentru ambele grafuri
     	    								for(DependencyNode pred : predicatesForFirstGraph){
     	    									System.out.println("subj:"+parentChild2.childNode.value.value() +" " +pred.value.value());
     	    									System.out
														.println(parentChild.parentNode.value.value());
     	    									System.out
														.println(parentChild2.parentNode.value.value());
     	    									if(parentChild.parentNode.value.value().equals(pred.value.value())
     	    											&& 
     	    											parentChild2.parentNode.value.value().equals(pred.value.value())){
     	    											
     	    									}
     	    								}
     	    							}
     	    						}
     	    						
     	    						// daca relatiile dintre parinte si copil sun aceleasi
     	    					//	if(parentChild.relation.equals(parentChild2.relation)){
     	    							if(parentChild.parentNode.value.value().equals(parentChild2.parentNode.value.value()) 
     	    									|| 
     	    									parentChild.parentNode.link.contains(parentChild2.parentNode)
     	    									|| parentChild2.parentNode.link.contains(parentChild.parentNode)){
     	    									index = i;
                         	 	    			score = n.sum;
     	    							}
     	    							
     	    							else if(parentChild.parentNode.lemValue.equals(parentChild2.parentNode.lemValue)){
     	    									index = i;
                         	 	    			score = n.sum;
     	    							}
     	    							
     	    							else if((pos = normalizePOSTag(parentChild2.parentNode.posTag)) != null){
     	    								double distance = WordNetInterface.wordnet.getDistance(parentChild.parentNode.lemValue , parentChild2.parentNode.lemValue, pos);
     	    		                        if ( ((1 - distance) * 100) > 75){
     	    		                    		score = n.sum;
     	    		                    		index = i;
     	    		                        }
     	    							}
     	    						}
     	    					}
     	    				}	
     	    		}
     	    			
     	    		
     	    		else if(n.secondNode.parents.isEmpty() == true && n.firstNode.parents.isEmpty() == true){
     	    			index = i;
 	 	    			score = n.sum; 	 	    			
     	    		}
 	    		}
     	    	else{
     	    		if(score < n.sum && n.firstNode.posTag.charAt(0) == 'V'){
     	    			if(!predicatesForFirstGraph.contains(n.firstNode))
     	    				predicatesForFirstGraph.add(n.firstNode);
     	    			if(!predicatesForSecondGraph.contains(n.secondNode))
     	    				predicatesForSecondGraph.add(n.secondNode);
     	    			index = i;
	 	    			score = n.sum;
         	    	}
     	    	}
     	    	
     	    }
     	   System.out.println("--------------------------------------------------");
     	   System.out.println("Scorul:");
     	    if (index > -1){
     	    	NodeComparisonResultClass n = nodeArr.get(index);
     	    	System.out.println(n.firstNodeValue + ":" + n.secondNodeValue + ":" + n.sum);
     	    	resemblanceFactor += n.sum;
     	    	count++;
     	    }
     	   System.out.println("--------------------------------------------------");
     	}
     	ArrayList<String> nodeValues = new ArrayList<String>();
     	// nu ia in calcul si ordinea 
     	for(DependencyNode n : this.firstGraphArrayList){
     		if(nodeValues.contains(n.value.value()) == false)
     			nodeValues.add(n.value.value());
     	}
     	double f = nodeValues.size() * 100.0;
     	System.out.println("listadim:"+nodeValues.size()+" count:"+count);
     	resemblanceFactor /= f;
     	System.out.println("Factor de asemanare:" + resemblanceFactor );
    	return resemblanceFactor;
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