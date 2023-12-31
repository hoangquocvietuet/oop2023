package org.oop2023.utils;

import org.oop2023.utils.enums.Language;
import org.oop2023.utils.enums.WordClass;

import java.util.ArrayList;

public class Word extends DualLanguageStructure{
    private WordClass wordClass;
    private String pronunciation;
    private String description;
    private ArrayList<String> antonyms;
    private ArrayList<String> synonyms;
    private ArrayList<Sentence> sentences;

    /**
     * Default constructor.
     */
    public Word() {
        content = "";
        this.meaning = new ArrayList<>();
        language = Language.UNKNOWN;
        wordClass = WordClass.NONE;
        pronunciation = "";
        antonyms = new ArrayList<>();
        synonyms = new ArrayList<>();
        sentences = new ArrayList<>();
        description = "";
    }

    /**
     * Construct a word with content only.
     * @param content The content of the word
     */
    public Word(String content) {
        this.content = content;
        this.meaning = new ArrayList<>();
        this.language = Language.UNKNOWN;
        this.wordClass = WordClass.NONE;
        this.pronunciation = "";
        this.antonyms = new ArrayList<>();
        this.synonyms = new ArrayList<>();
        this.sentences = new ArrayList<>();
        this.description = "";
    }

    /**
     * Construct a word with content and meaning only.
     * @param content The content of the word
     * @param description The description of the word
     * @param language The language of the word, ENGLISH or VIETNAMESE
     */
    public Word(String content, String description, Language language) {
        this.content = content;
        this.meaning = new ArrayList<>();
        this.language = language;
        this.wordClass = WordClass.NONE;
        this.pronunciation = "";
        this.antonyms = new ArrayList<>();
        this.synonyms = new ArrayList<>();
        this.sentences = new ArrayList<>();
        this.description = description;
    }

    public Word(String content, HTMLObject description, Language language) {
        this.content = content;
        this.meaning = new ArrayList<>();
        this.language = language;
        this.wordClass = WordClass.NONE;
        this.pronunciation = "";
        this.antonyms = new ArrayList<>();
        this.synonyms = new ArrayList<>();
        this.sentences = new ArrayList<>();
        this.description = description.toString();
    }

    /**
     * Get the word class of the word.
     * @return The word's class, NOUN, VERB, ADJECTIVE, ...
     */
    public WordClass getWordClass() {
        return wordClass;
    }

    /**
     * Set the word class of the word.
     * @param wordClass NOUN, VERB, ADJECTIVE, ...
     */
    public void setWordClass(WordClass wordClass) {
        this.wordClass = wordClass;
    }

    /**
     * Get the pronunciation of the word as phonetic symbols.
     * @return A string.
     */
    public String getPronunciation() {
        return pronunciation;
    }

    /**
     * Set the pronunciation of the word as phonetic symbols.
     * @param pronunciation A string consists of phonetic symbols.
     */
    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    /**
     * Returns a list of the word's antonym.
     * @return An ArrayList
     */
    public ArrayList<String> getAntonyms() {
        return antonyms;
    }

    /**
     * Add a word to the antonyms list.
     * @param word The word
     */
    public void addAntonym(String word) {
        this.antonyms.add(word);
    }

    /**
     * Returns a list of the word's synonyms.
     * @return An ArrayList
     */
    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    /**
     * Add a word to the synonyms list.
     * @param word The word
     */
    public void addSynonym(String word) {
        this.synonyms.add(word);
    }

    /**
     * Returns a list of the word's related sentences.
     * @return An ArrayList
     */
    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    /**
     * Add a sentence to the sentences list.
     * @param s The sentence
     */
    public void addSentence(Sentence s) {
        this.sentences.add(s);
    }

    /**
     * Get the description of the word.
     * @return A string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the word.
     * @param description A string.
     */
    public void setDescription(HTMLObject description) {
        this.description = description.toString();
    }

    /**
     * Check if a word is valid (not null, no invalid characters).
     * @param word The word to be checked
     * @return true if the word is valid, false otherwise
     */
    public static boolean isInvalid(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        for (int i = 0; i < word.length(); i++) {
            if (CharMapMethods.encode(word.charAt(i)) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print the word.
     */
    public void debug() {
        System.out.println("Word: " + content);
        System.out.println("Meaning: " + meaning);
        System.out.println("Language: " + language);
        System.out.println("Word class: " + wordClass);
        System.out.println("Pronunciation: " + pronunciation);
        System.out.println("Antonyms: " + antonyms);
        System.out.println("Synonyms: " + synonyms);
        System.out.println("Sentences: " + sentences);
        System.out.println("Description:\n" + description);
    }
}
