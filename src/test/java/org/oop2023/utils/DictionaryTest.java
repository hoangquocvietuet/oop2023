package org.oop2023.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.oop2023.utils.enums.Language;

public class DictionaryTest {
    /**
     * Test add()
     */
    @Test
    public void testAdd1() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        assertTrue(dict.getDetails("hello").getMeaning().equals("xin chào"));
    }

    /**
     * Test add() with duplicate word
     */
    @Test
    public void testAdd2() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        dict.add(new Word("hello", "chào", Language.ENGLISH));
        assertFalse(dict.getDetails("hello").getMeaning().equals("chào"));
        assertFalse(dict.getWordsList().size() > 1);
    }

    /**
     * Test add() with invalid word
     */
    @Test
    public void testAdd3() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello?", "xin chào?", Language.ENGLISH));
        assertFalse(!dict.getWordsList().isEmpty());
    }

    /**
     * Test add() with a word that has language mismatch
     */
    @Test
    public void testAdd4() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.setLanguage(Language.ENGLISH);
        dict.add(new Word("xin chào", "hello", Language.VIETNAMESE));
        assertFalse(!dict.getWordsList().isEmpty());
    }

    /**
     * Test delete()
     */
    @Test
    public void testDelete1() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        dict.delete("hello");
        assertTrue(dict.getDetails("hello") == null);
    }

    /**
     * Test delete() with a word that is not in the dictionary
     */
    @Test
    public void testDelete2() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        try {
            dict.delete("hello");
        } catch (Exception e) {
            assertFalse(true);
        }
        assertTrue(true);
    }

    /**
     * Test change()
     */
    @Test
    public void testChange1() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        dict.change("hello", new Word("hello", "chào", Language.ENGLISH));
        assertTrue(dict.getDetails("hello").getMeaning().equals("chào"));
    }

    /**
     * Test change() with a word that is not in the dictionary
     */
    @Test
    public void testChange2() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        try {
            dict.change("hello", new Word("hello", "chào", Language.ENGLISH));
        } catch (Exception e) {
            assertFalse(true);
        }
        assertTrue(true);
    }

    /**
     * Test getWordsList()
     */
    @Test
    public void testGetWordsList() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        assertTrue(dict.getWordsList().get(0).equals("hello"));
    }

    /**
     * Test getAlike()
     */
    @Test
    public void testGetAlike1() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        dict.add(new Word("helicopter", "trực thăng", Language.ENGLISH));
        dict.add(new Word("hero", "anh hùng", Language.ENGLISH));
        dict.add(new Word("he", "anh ấy", Language.ENGLISH));
        assertFalse(!dict.getAlike("he", 10).get(0).equals("he"));
        assertFalse(!dict.getAlike("he", 10).get(1).equals("helicopter"));
        assertFalse(!dict.getAlike("he", 10).get(2).equals("hello"));
        assertFalse(!dict.getAlike("he", 10).get(3).equals("hero"));
    }

    /**
     * Test getAlike() with limited number of queries
     */
    @Test
    public void testGetAlike2() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        dict.add(new Word("helicopter", "trực thăng", Language.ENGLISH));
        dict.add(new Word("hero", "anh hùng", Language.ENGLISH));
        dict.add(new Word("he", "anh ấy", Language.ENGLISH));
        assertFalse(dict.getAlike("he", 2).size() > 2);
        assertFalse(!dict.getAlike("he", 2).get(0).equals("he"));
        assertFalse(!dict.getAlike("he", 2).get(1).equals("helicopter"));
    }

    /**
     * Test getDetails()
     */
    @Test
    public void testGetDetails() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        assertTrue(dict.getDetails("hello").getMeaning().equals("xin chào"));
    }

    /**
     * Test getWords()
     */
    @Test
    public void testGetWords() {
        Dictionary dict = new Dictionary(Language.ENGLISH);
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        assertTrue(dict.getWords().get(0).getMeaning().equals("xin chào"));
    }
}
