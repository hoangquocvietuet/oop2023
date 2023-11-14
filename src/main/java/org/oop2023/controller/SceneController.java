package org.oop2023.controller;

import org.oop2023.utils.Dictionary;

import java.util.ArrayList;

import org.oop2023.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SceneController {

    private final Dictionary dictionary;

    SceneController() {
        dictionary = new Dictionary();
    }

    public ArrayList<String> getAlikeWord(String word) {
        return dictionary.getAlike(word, 10);
    }

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
}