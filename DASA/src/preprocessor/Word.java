package preprocessor;

public class Word {
	public String str;
	public String tag;
	
	public Word() {
		super();
	}

	public Word(String str) {
		super();
		this.str = str;
	}

	public Word(String str, String tag) {
		super();
		this.str = str;
		this.tag = tag;
	};
	
	@Override
	public String toString() {
		return "( " + str + ", " + tag + " )";
	}
	
}
