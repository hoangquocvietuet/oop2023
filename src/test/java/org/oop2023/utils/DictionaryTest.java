package org.oop2023.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;

public class DictionaryTest {
    /**
     * Test add()
     */
    @Test
    public void testAdd() {
        Dictionary dict = new Dictionary();
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        assertTrue(dict.getDetails("hello").getMeaning().equals("xin chào"));
    }

    /**
     * Test delete()
     */
    @Test
    public void testDelete() {
        Dictionary dict = new Dictionary();
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        dict.delete(new Word("hello", "xin chào", Language.ENGLISH));
        assertTrue(dict.getDetails("hello") == null);
    }

    /**
     * Test change()
     */
    @Test
    public void testChange() {
        Dictionary dict = new Dictionary();
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        dict.change(new Word("hello", "xin chào", Language.ENGLISH), new Word("hello", "chào", Language.ENGLISH));
        assertTrue(dict.getDetails("hello").getMeaning().equals("chào"));
    }

    /**
     * Test getWordsList()
     */
    @Test
    public void testGetWordsList() {
        Dictionary dict = new Dictionary();
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        assertTrue(dict.getWordsList().get(0).equals("hello"));
    }

    /**
     * Test getAlike()
     */
    @Test
    public void testGetAlike() {
        Dictionary dict = new Dictionary();
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        dict.add(new Word("helicopter", "trực thăng", Language.ENGLISH));
        dict.add(new Word("hero", "anh hùng", Language.ENGLISH));
        dict.add(new Word("he", "anh ấy", Language.ENGLISH));
        assertFalse(!dict.getAlike("he").get(0).equals("he"));
        assertFalse(!dict.getAlike("he").get(1).equals("helicopter"));
        assertFalse(!dict.getAlike("he").get(2).equals("hello"));
        assertFalse(!dict.getAlike("he").get(3).equals("hero"));
    }

    /**
     * Test getDetails()
     */
    @Test
    public void testGetDetails() {
        Dictionary dict = new Dictionary();
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        assertTrue(dict.getDetails("hello").getMeaning().equals("xin chào"));
    }

    /**
     * Test getWords()
     */
    @Test
    public void testGetWords() {
        Dictionary dict = new Dictionary();
        dict.add(new Word("hello", "xin chào", Language.ENGLISH));
        assertTrue(dict.getWords().get(0).getMeaning().equals("xin chào"));
    }
}
