package org.oop2023.controller;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController extends SceneController {
    private RotateTransition rotateTransition;

    @FXML
    private ImageView earthImageView;

    private RotateTransition createRotateTransition(ImageView imageView, double angle, double durationSeconds) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(durationSeconds), imageView);
        rotateTransition.setByAngle(angle);
        return rotateTransition;
    }

    @FXML
    public void initialize() {
        RotateTransition phase1 = createRotateTransition(earthImageView, 180, 5.0);
        RotateTransition phase2 = createRotateTransition(earthImageView, 180, 5.0);

        phase1.setOnFinished(event -> phase2.play());
        phase1.setOnFinished(event -> phase1.play());
        phase1.play();
    };

    @FXML
    void startRotation() {
        // Start rotation
        rotateTransition.play();
    }

    @FXML
    void stopRotation() {
        // Stop rotation
        rotateTransition.pause();
    }

    @FXML
    void dictionaryOnMouseClicked(MouseEvent event) {
        System.out.println("Dictionary clicked");
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            super.setDictionaryScene(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void translateOnMouseClicked(MouseEvent event) {
        System.out.println("Translate clicked");
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            super.setTranslateScene(stage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @FXML
    void practiceOnMouseClicked(MouseEvent event) {
        System.out.println("Practice clicked");
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            super.setPracticeScene(stage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @FXML
    void gameOnMouseClicked(MouseEvent event) {
        System.out.println("Game clicked");
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            super.setGameScene(stage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}
