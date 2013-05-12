import preprocessor.Preprocessor;
import question.Questions;


public class Main {
	public static void main(String[] args) {
        Questions questions = new Questions();
       // questions.classifier();
       // questions.print();

        Preprocessor preprocessor = new Preprocessor();
        preprocessor.stanfordPreprocess(null);
	}
}
