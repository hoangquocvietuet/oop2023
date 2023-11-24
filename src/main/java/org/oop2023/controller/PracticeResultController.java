package org.oop2023.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;

public class PracticeResultController extends SceneController {

    @FXML
    private ImageView reloadButton;

    @FXML
    private TextField scoreField;

    ScaleTransition scaleIn;
    ScaleTransition scaleOut;

    @FXML
    void initialize() {
        String score = "Your score: 100";
        scoreField.setText(score);

        scaleIn = new ScaleTransition(Duration.millis(500), reloadButton);
        scaleIn.setFromX(1);
        scaleIn.setToX(1.2);
        scaleIn.setFromY(1);
        scaleIn.setToY(1.2);

        scaleOut = new ScaleTransition(Duration.millis(500), reloadButton);
        scaleOut.setFromX(1.2);
        scaleOut.setToX(1);
        scaleOut.setFromY(1.2);
        scaleOut.setToY(1);

        scaleIn.setOnFinished(event -> scaleOut.play());

        scaleOut.setOnFinished(event -> scaleIn.play());

        scaleIn.play();

    }

    /**
     * Home button on mouse clicked: return Home.
     * 
     * @param event
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
     * Reload button on mouse clicked: reload Practice.
     * 
     * @param event
     */
    @FXML
    void reloadButtonOnMouseClicked(MouseEvent event) {
        System.out.println("Reload clicked");
        try {
            scaleIn.stop();
            scaleOut.stop();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            super.setPracticeScene(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
