import preprocessor.DependencyNode;
import preprocessor.Preprocessor;
import question.Questions;


public class Main {
	public static void main(String[] args) {
        Questions questions = new Questions();
       // questions.classifier();
       questions.print();

        Preprocessor preprocessor = new Preprocessor();
        preprocessor.stanfordPreprocess(null);
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println();
        for (DependencyNode root :	preprocessor.compareWithHoleGraph(questions.questions.get(0).graph))
        {
        	for (DependencyNode node : preprocessor.BFS(root))
        	{
        		System.out.print(node.value.value() + " ");
        	}
        	System.out.println();
        }
	}
}
