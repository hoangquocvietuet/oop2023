package org.oop2023;

import java.io.IOException;
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
        launch(args);
    }
}