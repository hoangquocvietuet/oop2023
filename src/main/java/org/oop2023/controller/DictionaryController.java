package org.oop2023.controller;

import java.util.ArrayList;

import org.oop2023.Utils;

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
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() == 0) {
                suggestionListView.setVisible(false);
                resultField.setVisible(false);
            } else {
                ArrayList<String> suggestions = Utils.dictionary.getAlike(newValue, 10);
                if(suggestions.size() == 0) {
                    suggestionListView.setVisible(false);
                    resultField.setVisible(false);
                    return;
                }

                suggestionListView.getItems().clear();
                suggestionListView.setPrefHeight(suggestions.size() * 24);
                suggestionListView.getItems().addAll(suggestions);
                suggestionListView.setVisible(true);
                suggestionListView.getSelectionModel().selectFirst();
                resultField.setVisible(false);
            }
        });
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
        if (Utils.dictionary.getDetails(text) == null) {
            setResult("Word not found. Please check your typing.");
            return;
        }
        String description = Utils.dictionary.getDetails(text).getDescription();
        setResult(description);
        // System.out.println(text);
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
        }
        if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP) {
            suggestionListView.requestFocus();
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
        Utils.speakWord(searchField.getText());
    }

    @FXML
    void suggestionListViewOnMouseClicked(MouseEvent event) {
        System.out.println("Suggestion clicked");
        if(event.getClickCount() == 1) {
            String text = suggestionListView.getSelectionModel().getSelectedItem();
            System.out.println(text);
            searchField.setText(text);
            search();
            suggestionListView.setVisible(false);
            resultField.setVisible(true);
        }
    }

    @FXML
    void suggestionListViewOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String text = suggestionListView.getSelectionModel().getSelectedItem();
            System.out.println(text);
            searchField.setText(text);
            search();
            suggestionListView.setVisible(false);
            resultField.setVisible(true);
        }
    }
}
