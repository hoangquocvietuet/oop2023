package org.oop2023.utils;

import org.oop2023.utils.enums.Language;

import java.util.ArrayList;

public abstract class DualLanguageStructure {
    protected String content;
    protected ArrayList<String> meaning;
    protected Language language;

    /**
     * Set up the conntent of a dual language structure.
     * @param content String
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the content of a dual language structure.
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * Get the language of a dual language structure.
     * @return The content
     */
    public ArrayList<String> getMeaning() {
        return meaning;
    }

    /**
     * Add a meaning to the meaning list of a dual language structure.
     * @param meaning String
     */
    public void addMeaning(String meaning) {
        this.meaning.add(meaning);
    }

    /**
     * Set up the language of a dual language structure.
     * @param lang LANGUAGE_ENGLISH or LANGUAGE_VIETNAMESE
     */
    public void setLang(Language lang) {
        this.language = lang;
    }

    /**
     * Get the language of a dual language structure.
     * @return The content
     */
    public Language getLanguage() {
        return language;
    }
}
