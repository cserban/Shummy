package preprocessor;

public class WordWithTag{
	public String word;
	public String postag;
	public String ner;
	public int poz;
	
	/**
	 * @brief constructs a tagged word
	 * @param word - string value of the word
	 * @param postag - tag for word
	 * @param ner - ner for word
	 * @param poz - position in sentence
	 */
	public WordWithTag(String word, String postag, String ner, int poz){
		this.word = word;
		this.postag = postag;
		this.ner = ner;
		this.poz = poz;
	}
	
	@Override
	public String toString(){
		return this.word + " " + this.postag + " " + this.ner + " " + this.poz;
	}
}
