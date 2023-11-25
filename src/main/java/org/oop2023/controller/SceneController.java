package org.oop2023.controller;

import org.oop2023.utils.Dictionary;

import java.util.ArrayList;

import org.oop2023.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    public void setScene(Stage stage, String fxmlPath) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    public void setMainMenuScene(Stage stage) throws Exception {
        setScene(stage, FXML.MAIN_MENU);
    }

    public void setHomeScene(Stage stage) throws Exception {
        setScene(stage, FXML.HOME);
    }

    public void setDictionaryScene(Stage stage) throws Exception {
        setScene(stage, FXML.DICTIONARY);
    }

    public void setTranslateScene(Stage stage) throws Exception {
        setScene(stage, FXML.TRANSLATE);
    }

    public void setPracticeScene(Stage stage) throws Exception {
        setScene(stage, FXML.PRACTICE);
    }

    public void setPracticeResultScene(Stage stage) throws Exception {
        System.out.println("setPracticeResultScene");
        setScene(stage, FXML.PRACTICE_RESULT);
    }

    public void setGameScene(Stage stage) throws Exception {
        setScene(stage, FXML.GAME);
    }

    public void setGameResultScene(Stage stage) throws Exception {
        setScene(stage, FXML.GAME_RESULT);
    }

    public void setGameResultScene(Stage stage, int score) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML.GAME_RESULT));
        stage.setScene(new Scene(fxmlLoader.load()));
        GameResultController gameResultController = fxmlLoader.<GameResultController>getController();
        gameResultController.setScore(score);
        stage.show();
    }
}