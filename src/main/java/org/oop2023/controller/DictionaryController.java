package org.oop2023.controller;

import java.util.ArrayList;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DictionaryController extends SceneController {
    @FXML
    private ImageView home;

    @FXML
    private TextField searchField;

    @FXML
    private TextArea resultField;

    @FXML
    private ListView<String> suggestionListView;

    @FXML
    private Button microButton;

    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize() {
        searchField.setPromptText("Enter a word to search.");
        resultField.setPromptText("Result will be shown here.");
        setResult("Hello world!");

        suggestionListView.setVisible(false);
        resultField.setVisible(false);

        microButton.setVisible(false);
    };

    /**
     * home button on mouse clicked: return Home.
     */
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

    /**
     * looking for the word in the dictionary.
     */
    void search() {
        microButton.setVisible(true);
        String text = searchField.getText();
        System.out.println(text);
    }

    /**
     * update the suggestion list view.
     * 
     * @param event
     */
    @FXML
    void searchFieldOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            search();
            suggestionListView.setVisible(false);
            resultField.setVisible(true);
        } else {
            String word = searchField.getText();
            ArrayList<String> suggestions = super.getAlikeWord(word);
            if (word.length() == 0 || suggestions.size() == 0) {
                microButton.setVisible(false);
                suggestionListView.setVisible(false);
                resultField.setVisible(false);
                return;
            }

            suggestionListView.getItems().clear();
            suggestionListView.setPrefHeight(suggestions.size() * 24);
            suggestionListView.getItems().addAll(suggestions);
            suggestionListView.setVisible(true);
            resultField.setVisible(false);
        }
    }

    /**
     * search the word in the dictionary when search button is clicked.
     * 
     * @param event
     */
    @FXML
    void searchButtonOnMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 1) {
            search();
            suggestionListView.setVisible(false);
            resultField.setVisible(true);
        }
    }

    /**
     * set the result field.
     * 
     * @param text
     */
    @FXML
    void setResult(String text) {
        resultField.setText(text);
    }

    /**
     * audio for the word.
     * 
     * @param event
     */
    @FXML
    void microOnMouseClicked(MouseEvent event) {

    }

}
