package org.oop2023.controller;

import javafx.util.Duration;

import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PracticeController extends SceneController {

    @FXML
    private TextField answer1Field;

    @FXML
    private TextField answer2Field;

    @FXML
    private TextField answer3Field;

    @FXML
    private TextField answer4Field;

    @FXML
    private TextField questionField;

    @FXML
    private RadioButton optionA;

    @FXML
    private RadioButton optionB;

    @FXML
    private RadioButton optionC;

    @FXML
    private RadioButton optionD;

    private ToggleGroup answerGroup;

    @FXML
    void initialize() {
        answerGroup = new ToggleGroup();
        optionA.setToggleGroup(answerGroup);
        optionB.setToggleGroup(answerGroup);
        optionC.setToggleGroup(answerGroup);
        optionD.setToggleGroup(answerGroup);

        questionField.setText("How are you?");
        answer1Field.setText("I'm fine.");
        answer2Field.setText("I'm good.");
        answer3Field.setText("I'm bad.");
        answer4Field.setText("I'm sad.");
    }

    @FXML
    void homeOnMouseClicked(MouseEvent event) {
        System.out.println("Home clicked");
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            super.setHomeScene(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void setNextQuestion(String question, List<String> answer) {
        assert(answer.size() == 4);
        questionField.setText(question);
        answer1Field.setText(answer.get(0));
        answer2Field.setText(answer.get(1));
        answer3Field.setText(answer.get(2));
        answer4Field.setText(answer.get(3));
    }

    @FXML 
    void nextQuestionOnClicked(MouseEvent event) {
        System.out.println("Next question clicked");
        setNextQuestion("How are you?", List.of("I'm fine.", "I'm good.", "I'm bad.", "I'm sad."));
        answerGroup.selectToggle(null);
    }
}
