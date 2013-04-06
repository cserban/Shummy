package preprocessor;

import java.util.ArrayList;

public class Sentence {
	String content;
	ArrayList<Word> words;

	public Sentence() {
		this.words = new ArrayList<>();
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
				System.out.println("Word not tagged: " + tagged_word);
			else {
				String str = tagged_word.split("_")[0];
				String tag = tagged_word.split("_")[1];
				this.words.add(new Word(str, tag));
			}
		}
	}
	
	@Override
	public String toString() {
		return this.content;
	}
}
