package question;

import java.io.File;
import java.util.ArrayList;

import preprocessor.Preprocessor;
import question.classifier.QuestionClassifier;

import common.Constants;
import common.FileInOut;

public class Questions {
	public ArrayList<Question> questions;

	public Questions() {
		//File questionFiles = new File(Constants.QUESTIONS_FILE);
		ArrayList<String> questionFilenames = FileInOut.getFiles(new File(Constants.QUESTIONS_FOLDERNAME));
		ArrayList<String> answersFilenames = FileInOut.getFiles(new File(Constants.ANSWERS_FOLDERNAME));

		try {
			questions = new ArrayList<>();
			
			for (String questionFilename : questionFilenames) {
				File tmp = new File(questionFilename);
				String question = FileInOut.readFile(tmp);
				questions.add(new Question(question));
			}

			
			for (String answerFilename : answersFilenames) {
				File tmp = new File(answerFilename);
				String answer = FileInOut.readFile(tmp);
				questions.get(Integer.parseInt(tmp.getName().split("_")[0])-1).answers.add(answer);
				
				Preprocessor processTmp = new Preprocessor();
				processTmp.stanfordPreprocess(answer);
				questions.get(Integer.parseInt(tmp.getName().split("_")[0])-1).answersGraph.add(processTmp.dependencyGraph.graph.get(0));
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//classifier();
	}

	public void classifier() {
		QuestionClassifier qc = new QuestionClassifier();
		for (Question question : questions) {
			question.predictedAnswerClass = qc.testQuestion(question.contant);
		}
	}

	public void print() {
		for (Question question : questions) {
			System.out.println(question);
		}
	}

}
