package wordnet;

import java.io.IOException;
import java.util.HashMap;

import rita.wordnet.*;

public class WordNetInterface {
	//http://rednoise.org/rita/wordnet/documentation/riwordnet_class_riwordnet.htm
	static RiWordnet wordnet = new RiWordnet(null);// System.getProperty("user.dir") + File.separator + "WordNet");

	/** Get all the synonyms for a certain part of speech, like a or v (verb)
	 */

	public static String[] getAllSynonymsForPOS(String word, String pos, int maxResults) {
		String[] ret = wordnet.getAllSynonyms(word, pos, maxResults);
		return ((ret == null)? new String[0] : ret);
	}

	public static String[] getSynonymsForPOS(String word, String pos, int maxResults) {
		String[] ret = wordnet.getSynonyms(word, pos, maxResults);
		return ((ret == null)? new String[0] : ret);
	}

	public static String[] getAllHypernimsForPOS(String word, String pos) {
		String[] ret = wordnet.getAllHypernyms(word, pos);
		return ((ret == null)? new String[0] : ret);
	}

	public static String[] getHypernimsForPOS(String word, String pos) {
		String[] ret = wordnet.getHypernyms(word, pos);
		return ((ret == null)? new String[0] : ret);
	}

	public static String[] getAllHyponimsForPOS(String word, String pos) {
		String[] ret = wordnet.getAllHyponyms(word, pos);
		return ((ret == null)? new String[0] : ret);
	}

	public static String[] getHyponimsForPOS(String word, String pos) {
		String[] ret = wordnet.getHyponyms(word, pos);
		return ((ret == null)? new String[0] : ret);
	}

	public static String[] getAntonymsForPOS(String word, String pos) {
		String[] ret = wordnet.getAntonyms(word, pos);
		return ((ret == null)? new String[0] : ret);
	}

	public static String[] getAllAntonymsForPOS(String word, String pos) {
		String[] ret = wordnet.getAllAntonyms(word, pos);
		return ((ret == null)? new String[0] : ret);
	}

	public static String[] getCommonParents(String word1, String word2, String pos) {
		String[] ret = null;
		try {
			ret = wordnet.getCommonParents(word1, word2, pos);
		} catch(Exception e) {
		}
		return ((ret == null)? new String[0] : ret);
	}

	public static HashMap<String, String[]> getAllSynonyms(String word, int maxResults) {
		HashMap<String, String[]> ret = new HashMap<String, String[]>();
		String[] poss = wordnet.getPos(word);
		for (int j = 0; j < poss.length; j++) {
			System.out.println("\n\nSynonyms for " + word + " (pos: " + poss[j] + ")");
			String[] synonyms = wordnet.getAllSynonyms(word,poss[j],maxResults);
			ret.put(poss[j], synonyms);
			for (int i = 0; i < synonyms.length; i++) {
				System.out.println(synonyms[i]);
			}
		}
		return ret;
	}

	public static String[] getPOSForWord(String word) {
		return wordnet.getPos(word);
	}

	public static void main(String[] args) throws IOException {

		System.out.println();
		System.out.println(wordnet.getDistance("extraterrestrial", "stranger", "n"));
		String word = "run";
		System.out.println("\nFinding parts of speech for " + word + ".");
		String[] partsofspeech = WordNetInterface.getPOSForWord(word);
		for (int i = 0; i < partsofspeech.length; i++) {
			System.out.println(partsofspeech[i]);        	
		}


		// Demo finding a list of related words (synonyms)
		word = "beautiful";
		String[] poss = WordNetInterface.getPOSForWord(word);
		for (int j = 0; j < poss.length; j++) {
			System.out.println("\n\nSynonyms for " + word + " (pos: " + poss[j] + ")");
			String[] synonyms = WordNetInterface.getSynonymsForPOS(word, poss[j], 20);
			for (int i = 0; i < synonyms.length; i++) {
				System.out.println(synonyms[i]);
			}
			System.out.println("\nAntonyms for " + word + " (pos: " + poss[j] + ")");
			String[] antonyms = WordNetInterface.getAntonymsForPOS(word, poss[j]);
			for (int i = 0; i < antonyms.length; i++) {
				System.out.println(antonyms[i]);
			} 
			System.out.println("\nHypernyms for " + word + " (pos: " + poss[j] + ")");
			String[] hypernyms = WordNetInterface.getHypernimsForPOS(word, poss[j]);
			for (int i = 0; i < hypernyms.length; i++) {
				System.out.println(hypernyms[i]);
			} 
			System.out.println("\nHyponyms for " + word + " (pos: " + poss[j] + ")");
			String[] hyponyms = WordNetInterface.getHyponimsForPOS(word, poss[j]);
			for (int i = 0; i < hyponyms.length; i++) {
				System.out.println(hyponyms[i]);
			} 
		}
	}
}
