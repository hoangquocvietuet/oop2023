package org.oop2023.consoleApp;

import org.oop2023.database.DatabaseController;
import org.oop2023.games.ChainGameCmd;
import org.oop2023.utils.Dictionary;
import org.oop2023.utils.Word;
import org.oop2023.utils.enums.Language;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import static org.oop2023.database.DatabaseHelpers.buildDictionaryCLI;

public class App {
//    public static final String DICTIONARY_PATH = "resources/WordList.txt";
    public static final int PAGE_SIZE = 3;
    public static final int LOOKALIKE_LIMIT = 10;
    private Dictionary dictionary;
    private ChainGameCmd chainGameCmd;
    private Scanner sc;

    /**
     * Constructor
     */
    public App() {
        dictionary = new Dictionary();
        chainGameCmd = new ChainGameCmd();
        sc = new Scanner(System.in);
    }

    /**
     * Get dictionary.
     */
    public Dictionary getDictionary() {
        return dictionary;
    }

    /**
     * Clear screen.
     */
    private void nextPage() {
        for (int i = 0; i < PAGE_SIZE; i ++) System.out.println();
    }

    /**
     * Get input integer.
     * @return
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
     * Load dictionary.
     * This method is still hard-coded due to the lack of database.
     */
    private void loadDictionary() {
        dictionary.setLanguage(Language.ENGLISH);
        buildDictionaryCLI(dictionary);
    }

    /**
     * Launch app.
     */
    public void launch() {
        nextPage();
        DatabaseController.start();
        loadDictionary();
        while (appLoop()) {
            //do nothing
        }
        DatabaseController.stop();
    }

    /**
     * App loop
     * @return true if app should continue, false otherwise
     */
    private boolean appLoop() {
        nextPage();
        System.out.println("DICTIONARY APP - CONSOLE MODE");
        System.out.println("[1] Look up Words");
        System.out.println("[2] Search Words Lookalike");
        System.out.println("[3] Game");
        System.out.println("[4] Show All Words");
        System.out.println("[5] Dictionary Tools");
        System.out.println("[0] Exit");
        System.out.print("> ");
        int input = getInputInt();
        switch (input) {
            case 1:
                lookUp();
                return true;
            case 2:
                search();
                return true;
            case 3:
                chainGameCmd.loadDictionary(dictionary);
                chainGameCmd.launch();
                return true;
            case 4:
                showAll();
                return true;
            case 5:
                dictionaryTools();
                return true;
            case 0:
                return !exit();
            default:
                System.out.println("Invalid input!");
                return true;
        }
    }

    /**
     * Look up words.
     */
    private void lookUp() {
        nextPage();
        System.out.println("LOOK UP WORDS");
        System.out.println("Enter a word to look up:");
        System.out.print("> ");
        String word = sc.next();
        Word w = dictionary.getDetails(word);
        if (w == null) {
            System.out.println("This word does not exist in the dictionary!");
        } else {
            System.out.println(w.getMeaning());
        }
        System.out.println("[1] Look up another");
        System.out.println("[0] Back");
        System.out.print("> ");
        int input = getInputInt();
        switch (input) {
            case 1:
                lookUp();
                return;
            case 0:
                return;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    /**
     * Search words lookalike.
     */
    private void search() {
        nextPage();
        System.out.println("SEARCH WORDS LOOKALIKE");
        System.out.println("Enter a word to search:");
        System.out.print("> ");
        String word = sc.next();
            System.out.println("Words lookalike:");
            int displayLimit = LOOKALIKE_LIMIT;
            while (true) {
                for (String lookalike : dictionary.getAlike(word, displayLimit)) {
                    System.out.println(lookalike);
                }
                System.out.println("[1] Load more");
                System.out.println("[2] Lookup");
                System.out.println("[3] Search another");
                System.out.println("[0] Back");
                int choice = getInputInt();
                switch (choice) {
                    case 1:
                        displayLimit += LOOKALIKE_LIMIT;
                        nextPage();
                        break;
                    case 2:
                        lookUp();
                        return;
                    case 3:
                        search();
                        return;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid input!");
                        break;
            }

        }
    }

    /**
     * Show all words.
     */
    private void showAll() {
        nextPage();
        System.out.println("SHOW ALL WORDS");
        System.out.println("Words in dictionary:");
        for (String word : dictionary.getWordsList()) {
            System.out.println(word);
        }
        System.out.println("[0] Back");
        int choice = getInputInt();
        if (choice == 0) {
            return;
        } else {
            System.out.println("Invalid input!");
        }
    }

    /**
     * Dictionary tools.
     */
    private void dictionaryTools() {
        nextPage();
        System.out.println("DICTIONARY TOOLS");
        System.out.println("[1] Add Word");
        System.out.println("[2] Remove Word");
        System.out.println("[3] Edit Word");
        System.out.println("[4] Export Dictionary");
        System.out.println("[0] Back");
        System.out.print("> ");
        int choice = getInputInt();
        switch (choice) {
            case 1:
                add();
                return;
            case 2:
                delete();
                return;
            case 3:
                change();
                return;
            case 4:
                export();
                return;
            case 0:
                return;
            default:
                System.out.println("Invalid input!");
                return;
        }
    }

    /**
     * Add a word into the dictionary.
     */
    private void add() {
        nextPage();
        System.out.println("ADD WORD");
        System.out.println("Enter a word to add into the dictionary:");
        System.out.print("> ");
        String word = sc.next();
        sc.nextLine();
        if (dictionary.getDetails(word) != null) {
            System.out.println("This word already exists in the dictionary!");
            return;
        }
        System.out.println("Enter word meaning:");
        System.out.print("> ");
        String meaning = sc.nextLine();
        dictionary.add(new Word(word, meaning, Language.ENGLISH));
        System.out.println("Word added!");
        System.out.println("[1] Add another");
        System.out.println("[0] Back");
        System.out.print("> ");
        int input = getInputInt();
        switch (input) {
            case 1:
                add();
                return;
            case 0:
                return;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    /**
     * Delete a word in the dictionary.
     */
    private void delete() {
        nextPage();
        System.out.println("REMOVE WORD");
        System.out.println("Enter a word to remove from the dictionary:");
        System.out.print("> ");
        String word = sc.next();
        if (dictionary.getDetails(word) == null) {
            System.out.println("This word does not exist in the dictionary!");
            return;
        }
        dictionary.delete(word);
        System.out.println("Word removed!");
        System.out.println("[1] Remove another");
        System.out.println("[0] Back");
        System.out.print("> ");
        int input = getInputInt();
        switch (input) {
            case 1:
                delete();
                return;
            case 0:
                return;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    /**
     * Edit a word in the dictionary.
     */
    private void change() {
        nextPage();
        System.out.println("EDIT WORD");
        System.out.println("Enter a word to edit in the dictionary:");
        System.out.print("> ");
        String word = sc.next();
        sc.nextLine();
        if (dictionary.getDetails(word) == null) {
            System.out.println("This word does not exist in the dictionary!");
            return;
        }
        System.out.println("Enter new word meaning:");
        System.out.print("> ");
        String meaning = sc.nextLine();
        dictionary.change(word, new Word(word, meaning, Language.ENGLISH));
        System.out.println("Word edited!");
        System.out.println("[1] Edit another");
        System.out.println("[0] Back");
        System.out.print("> ");
        int input = getInputInt();
        switch (input) {
            case 1:
                change();
                return;
            case 0:
                return;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    /**
     * Export dictionary.
     */
    private void export() {
        nextPage();
        System.out.println("EXPORT DICTIONARY");
        System.out.println("Enter destination directory:");
        System.out.print("> ");
        String dir = sc.next();

        ArrayList<Word> words = dictionary.getWords();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new java.io.FileWriter(dir));
            for (Word word : words) {
                writer.write(word.getContent());
                writer.write("(" + word.getWordClass() + ")");
                writer.newLine();
                for (String meaning : word.getMeaning()) {
                    writer.write("|" + meaning);
                    writer.newLine();
                }
            }
            writer.close();
        } catch (java.io.IOException e) {
            System.out.println("Error exporting dictionary!");
        }
        System.out.println("Dictionary exported!");
        System.out.println("[0] Back");
        System.out.print("> ");
        int input = getInputInt();
        if (input == 0) {
            return;
        } else {
            System.out.println("Invalid input!");
        }
    }

    /**
     * Exit app.
     */
    private boolean exit() {
        nextPage();
        System.out.println("Are you sure you want to exit?");
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
     * @param args command line arguments
     */
    public static void main(String[] args) {
        App app = new App();
        app.launch();
    }
}
