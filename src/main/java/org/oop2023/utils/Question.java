package org.oop2023.utils;

import org.oop2023.services.database.DatabaseController;
import org.oop2023.services.database.DatabaseMethods;

import java.util.ArrayList;
import java.util.List;

public class Question {
    public static final int QUESTIONS_COUNT = 200;
    private final String question;
    private final List<String> choices;
    private final int key;

    /**
     * Default constructor.
     */
    public Question(String question, List<String> choices, int key) {
        this.question = question;
        this.choices = choices;
        this.key = key;
    }

    /**
     * Get the key of the question.
     * @return The key
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get the choices of the question.
     * @return The choices
     */
    public List<String> getChoices() {
        return choices;
    }

    /**
     * Get the key of the question.
     * @return The key
     */
    public int getKey() {
        return key;
    }

    /**
     * Get a random question saved in the database.
     */
    public static Question getRandomQuestion() throws RuntimeException {
        return DatabaseMethods.getRandomQuestion();
    }

    /**
     * Test client.
     */
    public static void main(String[] args) {
        DatabaseController.start();
        Question question = Question.getRandomQuestion();
        System.out.println(question.getQuestion());
        for (String choice : question.getChoices()) {
            System.out.println(choice);
        }
        System.out.println(question.getKey());
        DatabaseController.stop();
    }
}
