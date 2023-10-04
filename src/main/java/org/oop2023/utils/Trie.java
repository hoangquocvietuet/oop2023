package org.oop2023.utils;

class TrieNode<E> {
    public static final int CHARACTERS_SIZE = 128;
    private E value;
    private TrieNode<E>[] children;

    /**
     * Default constructor
     */
    public TrieNode() {
        children = new TrieNode[CHARACTERS_SIZE];
    }

    /**
     * Get the child node of a character.
     * @param c The character
     * @return The child node
     */
    public TrieNode<E> getChild(char c) {
        return children[c];
    }

    /**
     * Add a child node to a character.
     * @param c The character
     * @param node The child node
     */
    public void addChild(char c, TrieNode<E> node) {
        children[c] = node;
    }

    /**
     * Remove the child node of a character.
     * @param c The character
     */
    public void removeChild(char c) {
        children[c] = null;
    }

    /**
     * Get the value of the node.
     * @return The value
     */
    public E getValue() {
        return value;
    }

    /**
     * Set the value of the node.
     * @param value The value
     */
    public void setValue(E value) {
        this.value = value;
    }

    /**
     * Get the children of the node.
     * @return The children
     */
    public TrieNode<E>[] getChildren() {
        return children;
    }
}

public class Trie<E> {
    private TrieNode<E> root;

    /**
     * Default constructor
     */
    public Trie() {
        root = new TrieNode<E>();
    }

    /**
     * Insert a key-value pair into the trie.
     * @param key The key
     * @param value The value
     */
    public void insert(String key, E value) {
        TrieNode<E> current = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            TrieNode<E> child = current.getChild(c);
            if (child == null) {
                child = new TrieNode<E>();
                current.addChild(c, child);
            }
            current = child;
        }
        current.setValue(value);
    }


    /**
     * Find the value of a key.
     * @param key The key
     * @return The value
     */
    public E find(String key) {
        TrieNode<E> current = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            TrieNode<E> child = current.getChild(c);
            if (child == null) {
                return null;
            }
            current = child;
        }
        return current.getValue();
    }

    /**
     * Delete a key-value pair from the trie.
     * @param key The key
     */
    public void delete(String key) {
        delete(root, key, 0);
    }

    /**
     * Delete a key-value pair from the trie.
     * @param current The current node
     * @param key The key
     * @param index The index of the key
     * @return True if the key-value pair is deleted, false otherwise
     */
    private boolean delete(TrieNode<E> current, String key, int index) {
        if (index == key.length()) {
            if (current.getValue() == null) {
                return false;
            }
            current.setValue(null);
            return current.getChildren().length == 0;
        }
        char c = key.charAt(index);
        TrieNode<E> child = current.getChild(c);
        if (child == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(child, key, index + 1) && child.getValue() == null;
        if (shouldDeleteCurrentNode) {
            current.removeChild(c);
            return current.getChildren().length == 0;
        }
        return false;
    }

    /**
     * Test client.
     */
    public static void main(String[] args) {
        Trie<Integer> trie = new Trie<Integer>();
        trie.insert("abc", 1);
        trie.insert("abcd", 2);
        trie.insert("abgl", 3);
        trie.insert("cdf", 4);
        trie.insert("lmn", 5);
        System.out.println(trie.find("abc"));
        System.out.println(trie.find("abcd"));
        System.out.println(trie.find("abgl"));
        System.out.println(trie.find("cdf"));
        System.out.println(trie.find("lmn"));
        System.out.println(trie.find("a"));
        System.out.println(trie.find("ab"));
        System.out.println(trie.find("lmno"));
        trie.delete("abc");
        System.out.println(trie.find("abc"));
        System.out.println(trie.find("abcd"));
        System.out.println(trie.find("abgl"));
        System.out.println(trie.find("cdf"));
        System.out.println(trie.find("lmn"));
        System.out.println(trie.find("a"));
        System.out.println(trie.find("ab"));
        System.out.println(trie.find("lmno"));
    }
}
