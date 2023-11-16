package org.oop2023.controller;

import java.time.LocalTime;

import org.oop2023.Utils;

import javafx.application.Platform;
import javafx.concurrent.Task;
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
    private static int apiCallCount = 0;

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
        textField1.textProperty().addListener((observable, oldValue, newValue) -> {
            textField1.setText(newValue);
        });
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

    void translate() {
        String text = textField1.getText();
        System.out.println("translate " + text);
        int thisApiCallNumber = ++ apiCallCount;

        Task<Void> apiCallTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Gọi API ở đây và lưu kết quả vào biến
                // Giả sử API mất 5 giây để trả lời
                String resultText = Utils.translator.EtoV(text);
    
                // Cập nhật giao diện người dùng với kết quả từ API
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // Chỉ cập nhật giao diện nếu đây là lần gọi API cuối cùng
                        if (thisApiCallNumber == apiCallCount) {
                            // Giả sử textField là một trường văn bản trong giao diện người dùng của bạn
                            textField2.setText(resultText);
                        }
                    }
                });
    
                return null;
            }
        };

        new Thread(apiCallTask).start();
    }   

    @FXML 
    void textField1OnKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            translate();
        }
    }
}
