package preprocessor;

import java.util.ArrayList;

public class Sentence {
	public String content;
	public ArrayList<Word> words;

	public Sentence() {
		words = new ArrayList<Word>();
	}
	/*
	 * Example:
	 * The_DT Fulton_NNP County_NNP Grand_NNP Jury_NNP said_VBD Friday_NNP an_DT investigation_NN  ._. 
	 */
	public Sentence(String tagged_content) {
		this.words = new ArrayList<>();
		String[] tagged_words = tagged_content.split(" ");
		for (String tagged_word : tagged_words) {
			if (tagged_word.split("_").length != 2)
				;//System.out.println(tagged_word + " has no tag.");
			else {
				String str = tagged_word.split("_")[0];
				String tag = tagged_word.split("_")[1];
				addWord(new Word(str, tag));
			}
		}
	}

	public void addSynonims() {
		for (Word w : words) {
			w.addSynonims();
		}
	}

	public void addWord(Word word) {
		this.words.add(word);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Word word : this.words) {
			sb.append(word.str + " ");
		}
		return sb.toString();
	}
}
