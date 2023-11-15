package org.oop2023.controller;

import org.oop2023.FXML;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenuController extends SceneController {
    @FXML
    public void laptopOnMouseClicked(MouseEvent event) {
        System.out.println("Laptop clicked");
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            super.setHomeScene(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
