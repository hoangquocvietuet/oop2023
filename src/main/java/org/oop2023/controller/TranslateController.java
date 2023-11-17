package org.oop2023.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TranslateController extends SceneController {

    @FXML
    private ImageView Home;

    @FXML
    private Button micro1;

    @FXML
    private Button micro2;

    @FXML
    private Button speaker1;

    @FXML
    private Button speaker2;

    @FXML
    private Button swapButton;

    @FXML
    private TextArea textField1;

    @FXML
    private TextArea textField2;

    @FXML
    public void initialize() {
        textField1.setPromptText("English");
        textField2.setPromptText("Vietnamese");
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

    @FXML
    void micro1OnMouseClicked(MouseEvent event) {
        System.out.println("Microphone 1 clicked");
    }

    @FXML
    void micro2OnMouseClicked(MouseEvent event) {
        System.out.println("Microphone 2 clicked");
    }

    @FXML
    void speaker1OnMouseClicked(MouseEvent event) {
        System.out.println("Speaker 1 clicked");
    }

    @FXML
    void speaker2OnMouseClicked(MouseEvent event) {
        System.out.println("Speaker 2 clicked");
    }

    @FXML
    void swapButtonOnMouseClicked(MouseEvent event) {
        System.out.println("Swap button clicked");
    }

    void translate(String text) {
        textField2.setText(text);
    }

    @FXML
    void textField1OnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String text = textField1.getText();
            translate(text);
        }
    }
}
