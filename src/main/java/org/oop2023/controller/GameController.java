package org.oop2023.controller;

import java.util.Collections;

import org.oop2023.Utils;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;

public class GameController extends SceneController {

    @FXML
    private ImageView playButton;

    @FXML
    private ImageView rule;

    @FXML
    private ImageView clock;

    @FXML
    private ImageView heart;

    @FXML
    private ImageView check;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    @FXML
    private TextField textField4;

    @FXML
    private TextField textField5;

    @FXML
    private TextField textField6;

    @FXML
    private TextField textField7;

    @FXML
    private TextField answerField;

    @FXML
    private Label timeLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label heartLabel;

    private String characters;

    private Timeline timeline;

    private int timeInSeconds = 60 * 5;

    private boolean timeExpired = false;

    private int score;

    private int numHeart = 3;
    
    private ParallelTransition heartAnimation;

    public static ParallelTransition createFallingHeartAnimation(ImageView heartImageView, double endX, double endY) {
        // drop
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), heartImageView);

        fallTransition.setFromX(0);
        fallTransition.setFromY(0);
        fallTransition.setToX(endX);
        fallTransition.setToY(endY);

        // fade
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1), heartImageView);
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);

        // merge
        ParallelTransition parallelTransition = new ParallelTransition(fallTransition, fadeOutTransition);

        fadeOutTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Đặt lại vị trí và hiển thị heart
                heartImageView.setTranslateX(0); 
                heartImageView.setTranslateY(0);
                heartImageView.setOpacity(1.0);
            }
        });

        return parallelTransition;
    }

    /**
     * Set visibility of all components.
     * 
     * @param visibility
     */
    void setVisibility(boolean visibility) {
        if (textField1 == null) {
            return;
        }
        textField1.setVisible(visibility);
        textField2.setVisible(visibility);
        textField3.setVisible(visibility);
        textField4.setVisible(visibility);
        textField5.setVisible(visibility);
        textField6.setVisible(visibility);
        textField7.setVisible(visibility);
        answerField.setVisible(visibility);
        clock.setVisible(visibility);
        check.setVisible(visibility);
        heart.setVisible(visibility);
        timeLabel.setVisible(visibility);
        scoreLabel.setVisible(visibility);
        heartLabel.setVisible(visibility);
    }

    private RotateTransition createRotateTransition(TextField textField, double angle, double durationSeconds) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(durationSeconds), textField);
        rotateTransition.setByAngle(angle);
        return rotateTransition;
    }

    /**
     * Set rotate animation for text field.
     * 
     * @param textField
     */
    @FXML
    public void setRotate(TextField textField) {
        textField.setAlignment(javafx.geometry.Pos.CENTER);
        textField.setTranslateX(textField.getWidth() / 2);
        textField.setTranslateY(textField.getHeight() / 2);
        RotateTransition phase0 = createRotateTransition(textField, -30, 1.0);
        RotateTransition phase1 = createRotateTransition(textField, 60, 1.0);
        RotateTransition phase2 = createRotateTransition(textField, -60, 1.0);

        phase0.setOnFinished(event -> phase1.play());
        phase1.setOnFinished(event -> phase2.play());
        phase2.setOnFinished(event -> phase1.play());
        phase0.play();
    };

    void setRotate() {
        setRotate(textField1);
        setRotate(textField2);
        setRotate(textField3);
        setRotate(textField4);
        setRotate(textField5);
        setRotate(textField6);
        setRotate(textField7);
    }

    @FXML
    void initialize() {
        setVisibility(false);
        numHeart = 3;
        score = 0;
        timeInSeconds = 60 * 5;
        heartAnimation = createFallingHeartAnimation(heart, 0, 150);

    }

    /**
     * Init game's candidates.
     * 
     * @param characters
     */
    void loadGame(String characters) {
        String text1 = "";
        text1 += characters.charAt(0);
        textField1.setText(text1);

        String text2 = "";
        text2 += characters.charAt(1);
        textField2.setText(text2);

        String text3 = "";
        text3 += characters.charAt(2);
        textField3.setText(text3);

        String text4 = "";
        text4 += characters.charAt(3);
        textField4.setText(text4);

        String text5 = "";
        text5 += characters.charAt(4);
        textField5.setText(text5);

        String text6 = "";
        text6 += characters.charAt(5);
        textField6.setText(text6);

        String text7 = "";
        text7 += characters.charAt(6);
        textField7.setText(text7);
    }

    /**
     * Handle home button clicked event: return Home.
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
     * Start game.
     * 
     * @param event
     */
    @FXML
    void playButtonOnMouseClicked(MouseEvent event) {
        playButton.setVisible(false);
        rule.setVisible(false);

        setRotate();
        setVisibility(true);
        characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // shuffle characters
        for (int i = 0; i < characters.length(); ++i) {
            int j = (int) (Math.random() * characters.length());
            char tmp = characters.charAt(i);
            characters = characters.substring(0, i) + characters.charAt(j) + characters.substring(i + 1);
            characters = characters.substring(0, j) + tmp + characters.substring(j + 1);
        }
        loadGame(characters);

        timeExpired = false;
        timeInSeconds = 60 * 5;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!timeExpired) {
                    updateTimer();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Set characters for game's candidates.
     * 
     * @param characters
     */
    public void setCharacters(String characters) {
        this.characters = characters;
    }

    /**
     * Stop game.
     */
    void stopGame() {
        Node anyNode = textField2;
        Scene scene = anyNode.getScene();
        Stage stage = (Stage) scene.getWindow();

        try {
            super.setGameResultScene(stage, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateTimer() {
        if (timeInSeconds > 0) {
            timeInSeconds--;
            int minutes = timeInSeconds / 60;
            int seconds = timeInSeconds % 60;
            String time = String.format("%02d:%02d", minutes, seconds);
            timeLabel.setText(time);
        } else {
            timeline.stop();
            timeExpired = true;
            timeLabel.setText("Hết thời gian!");
            stopGame();
        }
    }

    /**
     * Check answer.
     * 
     * @param answer
     * @return
     */
    boolean check(String answer) {
        answer = answer.toLowerCase();
        if (answer.indexOf(characters.charAt(3)) == -1) {
            return false;
        }
        if (Utils.dictionary.getDetails(answer) == null) {
            return false;
        }
        return true;
    }

    /**
     * Handle answer field on key pressed event.
     * 
     * @param event
     */
    @FXML
    void answerFieldOnKeyPressed(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            String answer = answerField.getText();
            if (answer.isEmpty()) {
                return;
            }
            if (check(answer)) {
                score++;
                scoreLabel.setText(String.valueOf(score));
                answerField.setText("");
            } else {
                System.out.println("Wrong answer");
                --numHeart;
                heartAnimation.play();
                if(numHeart == 0) {
                    stopGame();
                }
                heartLabel.setText(String.valueOf(numHeart));
            }
        }
    }
}
