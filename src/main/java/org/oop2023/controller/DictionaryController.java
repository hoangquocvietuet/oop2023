package org.oop2023.controller;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    public void initialize() {
        searchField.setPromptText("Enter a word to search.");
        resultField.setPromptText("Result will be shown here.");
        setResult("Hello world!");
    };

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

    void search() {
        String text = searchField.getText();
        System.out.println(text);
    }

    @FXML
    void searchFieldOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            search();
        }
    }

    @FXML
    void searchButtonOnMouseClicked(MouseEvent event) {
        if(event.getClickCount() == 1) {
            search();
        }
    }

    @FXML
    void setResult(String text) {
        resultField.setText(text);
    }
}
