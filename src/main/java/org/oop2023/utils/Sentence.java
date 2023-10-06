package org.oop2023.utils;

import org.oop2023.utils.enums.Language;

import java.util.ArrayList;

public class Sentence extends DualLanguageStructure{
    /**
     * Default constructor
     */
    public Sentence() {
        content = "";
        meaning = new ArrayList<>();
        language = Language.UNKNOWN;
    }

    /**
     * Construct a sentence with given content.
     * @param content Given content of the sentence
     */
    public Sentence(String content) {
        this.content = content;
        this.meaning = new ArrayList<>();
        this.language = Language.UNKNOWN;
    }

    /**
     * Construct a sentence with given content and meaning.
     * @param content Given content of the sentence
     * @param meaning Given meaning of the sentence
     * @param language The language of the sentence, ENGLISH or VIETNAMESE
     */
    public Sentence(String content, String meaning, Language language) {
        this.content = content;
        new ArrayList<>();
        this.addMeaning(meaning);
        this.language = language;
    }

    /**
     * Test client.
     */
    public static void main(String[] args) {
        Sentence s = new Sentence("I am speaking.", "Tôi đang nói.", Language.ENGLISH);
        System.out.println(s.getLanguage());
    }
}
