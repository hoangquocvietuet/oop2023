package org.oop2023.utils;

import org.oop2023.utils.enums.Language;

public abstract class DualLanguageStructure {
    protected String content;
    protected String meaning;
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
     * Set up the meaning of a dual language structure.
     * @param meaning String
     */
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    /**
     * Get the language of a dual language structure.
     * @return The content
     */
    public String getMeaning() {
        return meaning;
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
