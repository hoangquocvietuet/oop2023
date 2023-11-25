package org.oop2023.controller;

import javafx.util.Duration;
import java.util.List;

import org.oop2023.services.database.DatabaseController;
import org.oop2023.services.database.DatabaseMethods;
import org.oop2023.utils.Question;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.EventHandler;

import org.oop2023.Utils;

public class PracticeController extends SceneController {

    @FXML
    private TextField answer1Field;

    @FXML
    private TextField answer2Field;

    @FXML
    private TextField answer3Field;

    @FXML
    private TextField questionField;

    @FXML
    private RadioButton optionA;

    @FXML
    private RadioButton optionB;

    @FXML
    private RadioButton optionC;

    @FXML
    private Button finishButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label timerLabel;

    @FXML
    private ImageView startButton;

    @FXML
    private ImageView readyImage;

    private ToggleGroup answerGroup;

    private Timeline timeline;

    private int timeInSeconds = 60 * 5;

    private boolean timeExpired = false;

    private int currentQuestion = 0;

    private final int totalQuestions = 10;

    private int currentScore = 0;

    private List<Question> listQuestions;


    ScaleTransition scaleIn;
    ScaleTransition scaleOut;

    /**
     * Set visibility for all components in practice scene.
     * 
     * @param visibility
     */
    void setVisibility(boolean visibility) {
        answer1Field.setVisible(visibility);
        answer2Field.setVisible(visibility);
        answer3Field.setVisible(visibility);
        optionA.setVisible(visibility);
        optionB.setVisible(visibility);
        optionC.setVisible(visibility);
        questionField.setVisible(visibility);
        timerLabel.setVisible(visibility);
        nextButton.setVisible(visibility);
        finishButton.setVisible(visibility);
    }

    /**
     * Initialize practice scene.
     */
    @FXML
    void initialize() {
        currentScore = 0;
        DatabaseController.start();
        listQuestions = DatabaseMethods.getRandomQuestions(Question.QUESTIONS_COUNT);
        DatabaseController.stop();  

        answerGroup = new ToggleGroup();
        optionA.setToggleGroup(answerGroup);
        optionB.setToggleGroup(answerGroup);
        optionC.setToggleGroup(answerGroup);

        questionField.setText(listQuestions.get(currentQuestion).getQuestion());
        answer1Field.setText(listQuestions.get(currentQuestion).getChoices().get(0));
        answer2Field.setText(listQuestions.get(currentQuestion).getChoices().get(1));
        answer3Field.setText(listQuestions.get(currentQuestion).getChoices().get(2));

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

        scaleIn = new ScaleTransition(Duration.millis(500), readyImage);
        scaleIn.setFromX(1);
        scaleIn.setToX(1.2);
        scaleIn.setFromY(1);
        scaleIn.setToY(1.2);

        scaleOut = new ScaleTransition(Duration.millis(500), readyImage);
        scaleOut.setFromX(1.2);
        scaleOut.setToX(1);
        scaleOut.setFromY(1.2);
        scaleOut.setToY(1);

        scaleIn.setOnFinished(event -> scaleOut.play());

        scaleOut.setOnFinished(event -> scaleIn.play());

        scaleIn.play();

        setVisibility(false);
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

    void setNextQuestion() {
        if (currentQuestion == totalQuestions - 1) {
            finishButton.setVisible(true);
            nextButton.setVisible(false);
        } else {
            currentQuestion++;
            questionField.setText(listQuestions.get(currentQuestion).getQuestion());
            answer1Field.setText(listQuestions.get(currentQuestion).getChoices().get(0));
            answer2Field.setText(listQuestions.get(currentQuestion).getChoices().get(1));
            answer3Field.setText(listQuestions.get(currentQuestion).getChoices().get(2));
        }
    }

    /**
     * Handle next question button clicked event.
     * 
     * @param event
     */
    @FXML
    void nextQuestionOnClicked(MouseEvent event) {
        System.out.println("Next question clicked");
        setNextQuestion();
        answerGroup.selectToggle(null);
    }

    /**
     * Handle finish button clicked event.
     * 
     * @param event
     */
    @FXML
    void checkButtonOnMouseClicked(MouseEvent event) {
        timeline.stop();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận nộp bài");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn nộp bài không?");

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    System.out.println("Score: " + currentScore);
                    super.setPracticeResultScene(stage, currentScore);
                } catch (Exception e) {
                    System.out.println(e);
                }
                // Nếu nộp bài code tiếp vào đây
            } else {
                // Nếu không nộp bài code tiếp vào đây
                timeline.play();
            }
        });
    }

    /**
     * Update timer.
     */
    @FXML
    void updateTimer() {
        if (timeInSeconds > 0) {
            timeInSeconds--;
            int minutes = timeInSeconds / 60;
            int seconds = timeInSeconds % 60;
            String time = String.format("%02d:%02d", minutes, seconds);
            timerLabel.setText(time);
        } else {
            timeline.stop();
            timeExpired = true;
            timerLabel.setText("Hết thời gian!");
        }
    }

    /**
     * Start practice.
     */
    void startPractice() {
        timeline.play();
    }

    /**
     * Handle start button clicked event.
     * 
     * @param event
     */
    @FXML
    void startButtonOnMouseClicked(MouseEvent event) {
        System.out.println("Start button clicked");

        setVisibility(true);
        startButton.setVisible(false);
        readyImage.setVisible(false);

        scaleIn.stop();
        scaleOut.stop();

        startPractice();
    }

    /**
     * Handle option A clicked event.
     * 
     * @param event
     */
    @FXML
    void optionAOnMouseClicked(MouseEvent event) {
        if (listQuestions.get(currentQuestion).getKey() == 0) {
            currentScore++;
        }
    }

    /**
     * Handle option B clicked event.
     * 
     * @param event
     */
    @FXML
    void optionBOnMouseClicked(MouseEvent event) {
        if (listQuestions.get(currentQuestion).getKey() == 1) {
            currentScore++;
        }
    }

    /**
     * Handle option C clicked event.
     * 
     * @param event
     */
    @FXML
    void optionCOnMouseClicked(MouseEvent event) {
        if (listQuestions.get(currentQuestion).getKey() == 2) {
            currentScore++;
        }
    }
}
