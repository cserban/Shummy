import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import preprocessor.DependencyNode;
import preprocessor.Preprocessor;
import question.Question;
import question.Questions;

import common.ReadXMLFile;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		ReadXMLFile.load();
		Questions questions = new Questions();
		 questions.classifier();
		questions.print();

		Preprocessor preprocessor = new Preprocessor();
		preprocessor.stanfordPreprocess(null);
		System.out.println("CHOOSING CANDIDATES");
		System.out.println("CHOOSING CANDIDATES");
		System.out.println();

		PrintWriter writer = new PrintWriter("LOG.txt", "UTF-8");
		// questions.questions.size();
		for (int questionIndex = 0; questionIndex < questions.questions.size(); questionIndex++) {
			writer.print("\n");
			writer.print("\n");
			writer.print("\n");
			System.out.println("////////// " + questionIndex + " \\\\\\\\\\");
			writer.print("////////// " + questionIndex + " \\\\\\\\\\");
			ArrayList<DependencyNode> candidats = new ArrayList<>();
			// compar initial intrebarea cu subgraful
			if (questions.questions.get(questionIndex).predictedAnswerClass.equals("MISC"))
			{
				candidats = preprocessor.compareWithGraph(questions.questions.get(questionIndex).graph,	preprocessor.dependencyGraph.graph, 5, 1);
			}
			else
			{
				System.out.println(questions.questions.get(questionIndex).predictedAnswerClass);
			      if (preprocessor.dependencyGraph.ners.containsKey(questions.questions.get(questionIndex).predictedAnswerClass))
			      {
				System.out.println(preprocessor.dependencyGraph.ners.get(questions.questions.get(questionIndex).predictedAnswerClass).size());
				candidats = preprocessor.compareWithGraph(questions.questions.get(questionIndex).graph,
						preprocessor.dependencyGraph.ners.get(questions.questions.get(questionIndex).predictedAnswerClass), (preprocessor.dependencyGraph.ners.get(questions.questions.get(questionIndex).predictedAnswerClass).size() > 5) ? 5 : preprocessor.dependencyGraph.ners.get(questions.questions.get(questionIndex).predictedAnswerClass).size(),1);
			}
			      else
			      {
			    	  candidats = preprocessor.compareWithGraph(questions.questions.get(questionIndex).graph,	preprocessor.dependencyGraph.graph, 5, 1);
			      }
			}
			for (DependencyNode root : candidats) {
				for (DependencyNode node : preprocessor.BFS(root)) {
					System.out.print(node.value.value() + " ");
					writer.print(node.value.value() + " ");
				}
				System.out.println();
				writer.print("\n");
			}
			writer.print("\n");
			writer.print("CHOOSING answers");
			writer.print("\n");
			System.out.println("CHOOSING answers");
			System.out.println("CHOOSING answers");
			Double maxScore = 0.0;

			for (DependencyNode answere : questions.questions
					.get(questionIndex).answersGraph) {
				preprocessor.answersScore = 0.0;
				writer.print("\n");
				
				DependencyNode selectedGraph = preprocessor.compareWithGraph(answere, candidats, 1, 0).get(0);
				writer.print(preprocessor.answersScore +" ");
				System.out
						.println("FOr: "
								+ questions.questions.get(questionIndex).answers.get(questions.questions
										.get(questionIndex).answersGraph
										.indexOf(answere)));
				writer.print(questions.questions.get(questionIndex).answers.get(questions.questions
						.get(questionIndex).answersGraph
						.indexOf(answere)));
				
				writer.print(" (" );
				for (DependencyNode node : preprocessor.BFS(selectedGraph)) {
					System.out.print(node.value.value() + " ");
					writer.print(node.value.value() + " ");
				}
				writer.print(")");
				System.out.println("SCOR: " + preprocessor.answersScore);
				if (preprocessor.answersScore >= maxScore) {
					maxScore = preprocessor.answersScore;
					if (maxScore == 0.0)
					{
						questions.questions.get(questionIndex).choosenAnswere = 5;
					}
					else
					{
						questions.questions.get(questionIndex).choosenAnswere = questions.questions
								.get(questionIndex).answersGraph.indexOf(answere);
					}
					
				}
			}
			writer.print("\n");
		}
		
		System.out.println("//////////-----\\\\\\\\\\");
		System.out.println("//////////-----\\\\\\\\\\");
		writer.print("\n");
		writer.print("\n");
		writer.print("//////////-----\\\\\\\\\\");
		writer.print("//////////-----\\\\\\\\\\");
		writer.print("\n");
		writer.print("\n");
		for (Question question : questions.questions)
		{
			writer.print("\n");
			writer.print(question.contant);
			if (question.choosenAnswere == 5)
			{
				writer.print("--> None of the above");
				System.out.println ("--> None of the above");
			}
			else
			{
				writer.print("-->" + question.answers.get(question.choosenAnswere));
				System.out.println ("-->" + question.answers.get(question.choosenAnswere));
			}
			System.out.println();
			System.out.println (question.contant);
			
			System.out.println();
		}
		writer.close();
	}
	
}
