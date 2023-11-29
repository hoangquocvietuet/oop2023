package org.oop2023.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.oop2023.Utils;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
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
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import org.oop2023.utils.Dictionary;
import org.oop2023.utils.Word;

public class GameController extends SceneController {

    @FXML
    private ImageView playButton;

    @FXML
    private ImageView rule;

    @FXML
    private ImageView clock;

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

    private String characters;

    private Timeline timeline;

    private int timeInSeconds = 60 * 5;

    private boolean timeExpired = false;

    private int score;

    private Dictionary used;

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
        timeLabel.setVisible(visibility);
        scoreLabel.setVisible(visibility);
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

        used = new Dictionary();
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

        characters = "ACDEGHILMNORSTU";

        List<Character> consonants = new ArrayList<>();
        List<Character> vowels = new ArrayList<>();
        for (int i = 0; i < characters.length(); ++i) {
            char c = characters.charAt(i);
            if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                vowels.add(c);
            } else {
                consonants.add(c);
            }
        }
        Collections.shuffle(consonants);
        Collections.shuffle(vowels);
        List<Character> gameChars = new ArrayList<>();

        gameChars.add(consonants.get(0));
        gameChars.add(consonants.get(1));
        gameChars.add(consonants.get(2));
        gameChars.add(consonants.get(3));
        gameChars.add(vowels.get(0));
        gameChars.add(vowels.get(1));
        if (Math.random() < 0.5) {
            gameChars.add(consonants.get(4));
        } else {
            gameChars.add(vowels.get(3));
        }
        Collections.shuffle(gameChars);
        characters = "";
        for (int i = 0; i < 7; ++i) {
            characters += gameChars.get(i);
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
        answer = answer.toUpperCase();
        if (answer.indexOf(characters.charAt(3)) == -1) {
            return false;
        }
        if (Utils.dictionary.getDetails(answer) == null) {
            return false;
        }
        for (int i = 0; i < answer.length(); ++i) {
            if (characters.indexOf(answer.charAt(i)) == -1) {
                return false;
            }
        }
        if (used.getDetails(answer) != null) {
            return false;
        }
        used.add(new Word(answer));
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
                stopGame();
            }
        }
    }
}
