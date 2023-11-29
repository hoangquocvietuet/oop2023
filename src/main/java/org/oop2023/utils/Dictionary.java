package org.oop2023.utils;

import org.oop2023.utils.enums.Language;

import java.util.ArrayList;

public class Dictionary {
    private Trie<Word> wordsTrie;
    private Language language;

    /**
     * Default constructor.
     */
    public Dictionary() {
        wordsTrie = new Trie<>();
        language = Language.UNKNOWN;
    }

    public Dictionary(Language language) {
        this.wordsTrie = new Trie<>();
        this.language = language;
    }

    /**
     * Set up the language of the dictionary.
     * @param language The language of the dictionary, ENGLISH or VIETNAMESE
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Get the language of the dictionary.
     * @return The language of the dictionary
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Get full content of the dictionary
     * @return  The dictionary
     */
    public Trie<Word> getDictionary() {
        return wordsTrie;
    }

    /**
     * Add a word to the dictionary.
     * @param word The word to be added
     */
    public boolean add(Word word) {
        if (Word.isInvalid(word.getContent())) {
            return false;
        }
        if (word.getLanguage() != language) {
            return false;
        }
        if (wordsTrie.find(word.getContent()) != null) {
            return false;
        }
        wordsTrie.insert(word.getContent(), word);
        return true;
    }

    /**
     * Remove a word from the dictionary.
     * @param word The word to be removed
     * @return true if the word is removed, false otherwise
     */
    public boolean delete(String word) {
        if (wordsTrie.find(word) == null) {
            return false;
        }
        wordsTrie.delete(word);
        return true;
    }

    /**
     * Remove a word from the dictionary.
     * @param word The word to be removed
     * @return true if the word is removed, false otherwise
     */
    public boolean delete(Word word) {
        if (wordsTrie.find(word.getContent()) == null) {
            return false;
        }
        wordsTrie.delete(word.getContent());
        return true;
    }

    /**
     * Change a word in the dictionary.
     * @param word The word to be changed
     * @param newWord The new word
     */
    public void change(String word, Word newWord) {
        this.delete(word);
        this.add(newWord);
    }

    /**
     * Show all words available in the dictionary.
     * @return The list of words
     */
    public ArrayList<String> getWordsList() {
        return wordsTrie.getAllKeys();
    }

    /**
     * Get a list of word that are alike to the given word.
     * @param word The given word
     * @return The list of alike words
     */
    public ArrayList<String> getAlike(String word, int maxSize) {
        return wordsTrie.getChildKeys(word, maxSize);
    }

    /**
     * Get details of a word.
     * @param word The given word
     * @return The word
     */
    public Word getDetails(String word) {
        return wordsTrie.find(word);
    }

    /**
     * Get all the words in the dictionary.
     * @return The list of words
     */
    public ArrayList<Word> getWords() {
        return wordsTrie.getAllValues();
    }

    /**
     * Test client.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào?", Language.ENGLISH));
        dict.add(new Word("helloworld", "thế giới", Language.ENGLISH));
        dict.add(new Word("helloworldd", "thế giới", Language.ENGLISH));
    }
}
