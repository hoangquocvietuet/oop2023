package org.oop2023.services.database;

import org.oop2023.utils.Dictionary;
import org.oop2023.utils.HTMLObject;
import org.oop2023.utils.Word;
import org.oop2023.utils.enums.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseMethods {
    /**
     * Get all words in the database.
     */
    public static ArrayList<String> getWords() throws RuntimeException {
        Statement statement = DatabaseController.getStatement();
        try {
            ResultSet res = statement.executeQuery("SELECT word FROM av");
            ArrayList<String> words = new ArrayList<>();
            while (res.next()) {
                words.add(res.getString("word"));
            }
            return words;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the information of a word.
     */
    public static ArrayList<String> getWordInfo(String word) throws RuntimeException {
        Statement statement = DatabaseController.getStatement();
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM av WHERE word = '" + word + "'");
            ArrayList<String> wordInfo = new ArrayList<>();
            while (res.next()) {
                wordInfo.add(res.getString("word"));
                wordInfo.add(res.getString("html"));
                wordInfo.add(res.getString("pronounce"));
                wordInfo.add(res.getString("description"));
            }
            return wordInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Build a dictionary from the database.
     */
    public static Dictionary buildDictionary(Dictionary dictionary) throws RuntimeException {
        Statement statement = DatabaseController.getStatement();
        try {
            ResultSet res = statement.executeQuery(
                    "SELECT word, html, description FROM av");
            while (res.next()) {
                String word = res.getString("word");
                String html = res.getString("html");
                String mean = res.getString("description");
//                dictionary.add(new Word(word, mean, Language.ENGLISH));
                dictionary.add(new Word(word, new HTMLObject(html), Language.ENGLISH));
            }
            System.out.println("Words has been added to the dictionary.");
            return dictionary;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Build a dictionary from the database (CLI version).
     */
    public static Dictionary buildDictionaryCLI(Dictionary dictionary) throws RuntimeException {
        Statement statement = DatabaseController.getStatement();
        try {
            ResultSet res = statement.executeQuery(
                    "SELECT word, html, description FROM av");
            while (res.next()) {
                String word = res.getString("word");
                String html = res.getString("html");
                String mean = res.getString("description");
                // dictionary.add(new Word(word, mean, Language.ENGLISH));
               dictionary.add(new Word(word, new HTMLObject(html), Language.ENGLISH));
            }
            System.out.println("Words has been added to the dictionary.");
            return dictionary;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
