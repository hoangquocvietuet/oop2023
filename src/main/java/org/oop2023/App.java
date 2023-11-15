package org.oop2023;

import java.io.IOException;

import org.oop2023.services.database.DatabaseController;
import org.oop2023.utils.Dictionary;
import org.oop2023.utils.enums.Language;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static final double SCREEN_WIDTH = 1080;
    public static final double SCREEN_HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML.MAIN_MENU));
            Parent mainMenuRoot = fxmlLoader.load();
            Scene mainMenuScene = new Scene(mainMenuRoot);

            primaryStage.setScene(mainMenuScene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Utils.initialize();

        launch(args);
    }
}