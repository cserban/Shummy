package question;

import java.io.File;
import java.util.ArrayList;

import question.classifier.QuestionClassifier;

import common.Constants;
import common.FileInOut;

public class Questions {
	ArrayList<Question> questions;

	public Questions() {
		try {
			File in = new File(Constants.QUESTIONS_FILE);
			String content = FileInOut.readFile(in);
			for (String question : content.split("\n"))
				questions.add(new Question(question));

			ArrayList<String> corpus_filenames = FileInOut.getFiles(new File(
					Constants.ANSWERS_FOLDERNAME));
			for (int i = 0; i < corpus_filenames.size(); i++) {

				String answers = FileInOut.readFile(in);
				for (String answer : answers.split("\n"))
					questions.get(i).answers.add(answer);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void classifier()
	{
		QuestionClassifier qc = new QuestionClassifier();
		qc.train("question/question_5500.train");
		// can be done for as many questions as you like
		for (Question question : questions)
		{
			question.predictedAnswerClass = qc.test("NUM:other\t" + question.contant);
		}
	}
	
	public void print()
	{
		for (Question question : questions)
		{
			System.out.println(question);
		}
	}
	
}
