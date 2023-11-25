package org.oop2023;

import static org.oop2023.services.database.DatabaseMethods.buildDictionaryCLI;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.oop2023.services.MerriamWebsterDictAPI.DictAPI;
import org.oop2023.services.database.DatabaseController;
import org.oop2023.services.database.DatabaseMethods;
import org.oop2023.services.textToSpeechAPI.Speaker;
import org.oop2023.utils.Dictionary;
import org.oop2023.utils.enums.Language;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import org.oop2023.services.translateAPI.Translator;

public class Utils {
    private static int apiCallCount = 0;
    public static final Dictionary dictionary = new Dictionary();
    public static final Translator translator = new Translator();
    public static final Speaker speaker = new Speaker();


    static void initialize() {
        // Initialize database and dictionary
        DatabaseController.start();
        dictionary.setLanguage(Language.ENGLISH);
        System.out.println("Building dictionary...");
        buildDictionaryCLI(dictionary);

        // Initialize translator

        // Initialize speaker
        speaker.initialize();


        DatabaseController.stop();
    }

    public static void speakWord(String word) {
        System.out.println("Speaking word: " + word);

        int thisApiCallNumber = ++ apiCallCount;

        Task<Void> apiCallTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    if (thisApiCallNumber != apiCallCount) {
                        return null;
                    }
                    String audioUrl = DictAPI.getAudioURL(word);
                    Media media = new Media(audioUrl);
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setOnReady(new Runnable() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                if (thisApiCallNumber == apiCallCount) {
                                    if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                                        mediaPlayer.stop();
                                    }
                                    mediaPlayer.seek(mediaPlayer.getStartTime());
                                    mediaPlayer.play();
                                }
                            });
                        }
                    });

                } catch(Exception e) {
                    System.out.println(e.getMessage());
                    speaker.speak(word);
                }
                return null;    
            }
        };

        new Thread(apiCallTask).start();
    }
}
