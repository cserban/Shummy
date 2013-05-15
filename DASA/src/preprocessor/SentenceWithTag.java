package preprocessor;

import java.util.ArrayList;

public class SentenceWithTag{
	/**
	 * list that contains all words in a sentence
	 */
	public ArrayList<WordWithTag> wordsList;
	
	public SentenceWithTag()
	{
		wordsList = new ArrayList<WordWithTag>();
	}
	
	/**
	 * @brief adds a tagged word in arraylist
	 * @param w
	 */
	public void add (WordWithTag w){
		wordsList.add(w);
	}
	
	/**
	 * @brief Returns an arraylist that contains the positions of w in sentence
	 * @param w - Searched word
	 * @return ArrayList<Integer> 
	 */
	public ArrayList<Integer> searchForWord(WordWithTag w){
		ArrayList <Integer> positionsOfWordInSentence = new ArrayList<Integer>();
 		for (int i = 0; i < wordsList.size(); i++){
			if (wordsList.get(i).word.equals(w.word) &&
					wordsList.get(i).postag.equals(w.postag)){
				positionsOfWordInSentence.add(wordsList.get(i).poz);
			}
 		}
 		return positionsOfWordInSentence;
	}
	
	/**
	 * @brief function that returns the max frequency of a word in the sentence
	 * @return float : maxfrequency
	 */
	public double getMaxWordFrequency(){
		double maxfrequency = 0;
		int max = 0;
		for (int i = 0; i < this.wordsList.size(); i++){
			int size = (searchForWord(this.wordsList.get(i))).size();
			if (max < size)
				max  = size;
			
		}
		maxfrequency = ((double)max)/this.wordsList.size();
		return maxfrequency;
	}
	
	/**
	 * @brief returns true if the word is contained in sentece, false otherwise
	 * @param w - searched word
	 * @return true or false
	 */
	public boolean sentenceContainsWord(WordWithTag w){
		for (int i = 0; i < wordsList.size(); i++){
			if (wordsList.get(i).word.equals(w.word)){
				return true;
			}
		
 		}
 		return false;
	}
	
	@Override
	public String toString(){
		String str = "";
		for (WordWithTag w : wordsList){
			str += " " + w.toString();
		}
		if (str.length() > 1)
			str = str.substring(1);
		
		return str;
	}
}