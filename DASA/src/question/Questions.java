package question;

import java.io.File;
import java.util.ArrayList;

import question.classifier.QuestionClassifier;

import common.Constants;
import common.FileInOut;

public class Questions {
	ArrayList<Question> questions;

	public Questions() {
		File questionFile = new File(Constants.QUESTIONS_FILE);
		ArrayList<String> answers_filenames = FileInOut.getFiles(new File(
				Constants.ANSWERS_FOLDERNAME));

		try {
			String content = FileInOut.readFile(questionFile);
			questions = new ArrayList<>();
			for (String question : content.split("\n"))
				questions.add(new Question(question));
			
			for (int i = 0; i < answers_filenames.size(); i++) {

				File tmp = new File(answers_filenames.get(0).split("_")[0]+"_"+(i+1)+".txt");
				String answers = FileInOut.readFile(tmp);
				
				for (String answer : answers.split("\n"))
					questions.get(i).answers.add(answer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void classifier() {
		QuestionClassifier qc = new QuestionClassifier();
		qc.train("question/question_5500.train");
		for (Question question : questions) {
			question.predictedAnswerClass = qc.test("NUM:other\t"
					+ question.contant);
		}
	}

	public void print() {
		for (Question question : questions) {
			System.out.println(question);
		}
	}

}
