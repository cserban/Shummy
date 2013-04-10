package question;

import java.util.ArrayList;

public class Question {
	public ArrayList<String> answers;
	String contant;
	String predictedAnswerClass;

	int id;
	static int count = 0;

	public Question(String contant) {
		this.contant = contant;
		answers = new ArrayList<>();
		
		id = ++count;
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
