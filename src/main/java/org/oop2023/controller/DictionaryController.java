package org.oop2023.controller;

import java.util.ArrayList;

import org.oop2023.Utils;
import org.oop2023.utils.Word;

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

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private ImageView span;

    @FXML
    private Button closeButton;

    @FXML
    private Button doneButton;

    @FXML
    private TextArea desField;
    
    @FXML
    private TextField wordField;

    void updateVisitable(boolean type) {
        searchField.setVisible(type);
        resultField.setVisible(type);
        suggestionListView.setVisible(type);
        microButton.setVisible(type);
        addButton.setVisible(type);
        editButton.setVisible(type);

        span.setVisible(!type);
        closeButton.setVisible(!type);
        doneButton.setVisible(!type);
        desField.setVisible(!type);
        wordField.setVisible(!type);
    }

    // private static ArrayList<String> allWords = Utils.dictionary.getWordsList();
    /**
     * Initialize the controller.
     */
    @FXML
    public void initialize() {
        resultField.setEditable(false);
        searchField.setPromptText("Enter a word to search.");
        resultField.setPromptText("Result will be shown here.");
        suggestionListView.getItems().addAll(Utils.allWords);
        suggestionListView.getSelectionModel().selectFirst();
        microButton.setVisible(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                microButton.setVisible(false);
                suggestionListView.getItems().clear();
                suggestionListView.getItems().addAll(Utils.allWords);
                suggestionListView.getSelectionModel().selectFirst();
            } else { 
                ArrayList<String> suggestions = Utils.dictionary.getAlike(newValue, (int)1e9);
                if (suggestions.size() == 0) {
                    return;
                }

                suggestionListView.getItems().clear();
                suggestionListView.getItems().addAll(suggestions);
                suggestionListView.getSelectionModel().selectFirst();
            }
        });

        wordField.textProperty().addListener((observable, oldValue, newValue) -> {
            wordField.setText(newValue.toLowerCase());
        });

        updateVisitable(true);
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
            // suggestionListView.setVisible(false);
            // resultField.setVisible(true);
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
            // suggestionListView.setVisible(false);
            // resultField.setVisible(true);
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
        if (event.getClickCount() == 1) {
            String text = suggestionListView.getSelectionModel().getSelectedItem();
            System.out.println(text);
            searchField.setText(text);
            search();
            // suggestionListView.setVisible(false);
            // resultField.setVisible(true);
        }
    }

    @FXML
    void suggestionListViewOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String text = suggestionListView.getSelectionModel().getSelectedItem();
            System.out.println(text);
            searchField.setText(text);
            search();
        }
    }
    
    /**
     * Stop edit or insert a word.
     * @param event
     */
    @FXML
    void closeButtonOnMouseClicked(MouseEvent event) {
        wordField.setText("");
        desField.setText("");
        updateVisitable(true);
    }

    /**
     * Done edit or insert a word.
     * @param event
     */
    @FXML
    void doneButtonOnMouseClicked(MouseEvent event) {
        String word = wordField.getText();
        String description = desField.getText();
        // Them vao tu dien, neu co -> replace, neu khong co -> them.
        Utils.dictionary.change(word, new Word(word, description, Utils.dictionary.getLanguage()));
        wordField.setText("");
        desField.setText("");
        updateVisitable(true);
        wordField.setEditable(true);
        resultField.setText(description);
        searchField.setText(word);
    }

    /**
     * add a new word to the dictionary.
     * @param event
     */
    @FXML
    void addButtonOnMouseClicked(MouseEvent event) {
        updateVisitable(false);
    }

    /**
     * edit a word from the dictionary.
     * @param event
     */
    @FXML
    void editButtonOnMouseClicked(MouseEvent event) {
        String word = searchField.getText();
        if (Utils.dictionary.getDetails(word) == null) {
            return;
        }
        wordField.setText(word);
        wordField.setEditable(false);
        desField.setText(Utils.dictionary.getDetails(word).getDescription());
        updateVisitable(false);
    }

    /**
     * update ditionary.
     */
    @FXML
    void resultFieldOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            resultField.setEditable(false);
            // Them vao day

            // allWords = Utils.dictionary.getWordsList();
        }
    }
}
