package question;

import java.util.ArrayList;

import preprocessor.DependencyNode;
import preprocessor.Preprocessor;

public class Question {
	public ArrayList<String> answers;
	public ArrayList<DependencyNode> answersGraph;
	public String contant;
	public String predictedAnswerClass;
	public DependencyNode graph;
	public int choosenAnswere;

	int id;
	static int count = 0;
	String path = "";
	
	public Question(String contant, String path) {
		this.contant = contant;
		answers = new ArrayList<>();
		answersGraph = new ArrayList<>();
		id = ++count;
		Preprocessor process = new Preprocessor();
		process.stanfordPreprocess(contant);
		graph = process.dependencyGraph.graph.get(0);
		this.path = path;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n" + id + " " + this.contant + "\n");
		sb.append("Class: " + this.predictedAnswerClass + "\n");
		for (String answer : this.answers) {
			sb.append("Answer: " + (this.answers.indexOf(answer)+1) + " -> " + answer
					+"\n");
		}
		return sb.toString();
	}
}
