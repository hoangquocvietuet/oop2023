package org.oop2023.games;

import org.oop2023.utils.Dictionary;
import org.oop2023.utils.Trie;
import org.oop2023.utils.Word;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChainGameCmd extends Game {
    private static final String HIGH_SCORE_PATH = "src/main/resources/org/oop2023/games_resources/chain_game_cmd/high_score.txt";
    public static final String INSTRUCTION_PATH = "src/main/resources/org/oop2023/games_resources/chain_game_cmd/instruction.txt";
    public static final int PAGE_SIZE = 3;
    private static final int MAX_TRIALS_COUNT = 5000000;
    private static final int VICTORY_SCORE = 1000000;
    private Scanner sc;
    private int highScore;

    /**
     * Constructor.
     */
    public ChainGameCmd() {
        this.score = 0;
        sc = new Scanner(System.in);
        loadHighScore();
    }

    /**
     * Clear screen.
     */
    public void nextPage() {
        for (int i = 0; i < PAGE_SIZE; i ++) System.out.println();
    }

    /**
     * Get input integer.
     * @return input integer
     */
    private int getInputInt() {
        String choiceRaw = sc.next();
        int choice = 0;
        try {
            choice = Integer.parseInt(choiceRaw);
        } catch (NumberFormatException e) {
            return -1;
        }
        return choice;
    }

    /**
     * Load high score.
     */
    public void loadHighScore() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader(HIGH_SCORE_PATH));
            String line;
            try {
                line = reader.readLine();
                highScore = Integer.parseInt(line);
            } catch (java.io.IOException e) {
                System.out.println("Error loading high score!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("High score text not found!");
        }
    }

    /**
     * Record high score.
     */
    private void recordHighScore(int score) {
        highScore = score;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new java.io.FileWriter(HIGH_SCORE_PATH));
            writer.write(Integer.toString(highScore));
            writer.close();
        } catch (java.io.IOException e) {
            System.out.println("Error recording high score!");
        }
    }

    /**
     * Launch a game.
     */
    @Override
    public void launch() {
        System.out.println("Launching Chain Game...");
        nextPage();
        while(gameLoop()) {
            // Do nothing
        }
    }

    /**
     * Game loop.
     * @return true if continue, false if exit
     */
    @Override
    protected boolean gameLoop() {
        nextPage();
        int choice = menu();
        switch (choice) {
            case 0:
                return !exit();
            case 1:
                play();
                return true;
            case 2:
                help();
                return true;
            case 3:
                highScore();
                return true;
            default:
                return true;
        }
    }

    private int menu() {
        System.out.println("CHAIN GAME");
        System.out.println("[1] Play");
        System.out.println("[2] Help");
        System.out.println("[3] High Score");
        System.out.println("[0] Exit");
        System.out.print("> ");
        int choice = getInputInt();
        switch (choice) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                System.out.println("Invalid choice!");
                return -1;
        }
    }

    /**
     * Play game.
     */
    private void play() {
        String word = "";
        String lastWord = "";
        score = 0;
        Trie<Boolean> trie = new Trie<>();
        while (true) {
            nextPage();
            System.out.println("Your score: " + score);
            if (!lastWord.isEmpty()) {
                System.out.println("Computer's last word: " + lastWord);
            }
            System.out.println("Please type a word: ");
            System.out.print("> ");
            word = sc.next();
            if (word.equals("/ff")) {
                System.out.println("You forfeit!");
                System.out.println("Game over!");
                System.out.println("Your score: " + score);
                if (score > highScore) {
                    System.out.println("New high score!");
                    recordHighScore(score);
                }
                break;
            }
            if (word.equals("/help")) {
                help();
                continue;
            }
            if (dict.getDetails(word) == null) {
                System.out.println("Word does not exist in the database!");
                continue;
            }
            if (lastWord != "" && lastWord.charAt(lastWord.length() - 1) != word.charAt(0)) {
                System.out.println("First character does not match!");
                continue;
            }
            if (trie.find(word) != null) {
                System.out.println("Word already exist!");
                continue;
            }
            trie.insert(word, true);
            score += word.length();
            lastWord = word;
            int trialsCount = 0;
            System.out.println("Computer is thinking...");
            boolean computerFails = false;
            while (trie.find(word) != null) {
                trialsCount++;
                if (trialsCount > MAX_TRIALS_COUNT) {
                    score += VICTORY_SCORE;
                    System.out.println("Computer gives up!");
                    System.out.println("Game over!");
                    System.out.println("Your score: " + score);
                    if (score > highScore) {
                        System.out.println("New high score!");
                        recordHighScore(score);
                    }
                    computerFails = true;
                    break;
                }
                ArrayList<String> possibleWords = dict.getAlike(lastWord.substring(lastWord.length() - 1), Integer.MAX_VALUE);
                int randomPosition = (int) (Math.random() * possibleWords.size());
                word = possibleWords.get(randomPosition);
            }
            if (computerFails) break;
            System.out.println("Computer's word is: " + word);
            trie.insert(word, true);
            lastWord = word;
        }
        System.out.println("[Any symbol] Continue");
        System.out.print("> ");
        String choice = sc.next();
    }

    /**
     * Show instructions.
     */
    private void help() {
        nextPage();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader(INSTRUCTION_PATH));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (java.io.IOException e) {
                System.out.println("Error loading instructions!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Instruction text not found!");
        }
        System.out.println("[Any symbol] Continue");
        System.out.print("> ");
        String choice = sc.next();
    }

    /**
     * Show high score.
     */
    private void highScore() {
        nextPage();
        System.out.println("High Score: " + highScore);
        System.out.println("[1]: Reset");
        System.out.println("[Any symbol] Continue");
        System.out.print("> ");
        String choice = sc.next();
        if (choice.equals("1")) {
            recordHighScore(0);
            System.out.println("High Score reset!");
        }
    }

    /**
     * Exit game.
     */
    private boolean exit() {
        nextPage();
        System.out.println("Exit game?");
        System.out.println("[1] Yes");
        System.out.println("[0] No");
        System.out.print("> ");
        int choice = getInputInt();
        if (choice == 1) {
            System.out.println("Goodbye!");
            return true;
        }
        return false;
    }

    /**
     * Test client.
     * @param args
     */
    public static void main(String[] args) {
        ChainGameCmd game = new ChainGameCmd();
        Dictionary dict = new Dictionary();
        dict.add(new Word("hello"));
        dict.add(new Word("ok"));
        dict.add(new Word("know"));
        dict.add(new Word("will"));
        dict.add(new Word("learn"));
        dict.add(new Word("nine"));
        dict.add(new Word("entertainment"));
        dict.add(new Word("time"));
        dict.add(new Word("external"));
        game.loadDictionary(dict);
        game.launch();
    }
}
