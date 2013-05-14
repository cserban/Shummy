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
				String path = tmp.getName().substring(0, tmp.getName().lastIndexOf("."));
				questions.add(new Question(question, path));
			}

			
			for (String answerFilename : answersFilenames) {
				File tmp = new File(answerFilename);
				String answer = FileInOut.readFile(tmp);

				Question q = getQuestionByPath(tmp.getName().substring(0, tmp.getName().lastIndexOf("_")));
				q.answers.add(answer);
				
				Preprocessor processTmp = new Preprocessor();
				processTmp.stanfordPreprocess(answer);
				q.answersGraph.add(processTmp.dependencyGraph.graph.get(0));
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		classifier();
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

	public Question getQuestionByPath(String path) {
		for (Question question : questions) {
			if (question.path.equals(path))
				return question;
		}
		System.err.println("No question found for path " + path);
		return null;
	}
	
}
