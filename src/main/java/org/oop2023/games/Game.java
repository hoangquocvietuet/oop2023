package org.oop2023.games;

import org.oop2023.utils.Dictionary;

public abstract class Game {
    protected int score;
    protected Dictionary dict;

    /**
     * Get the instruction of a game.
     * @return The instruction
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the dictionary of a game.
     * @return The dictionary
     */
    public Dictionary getDictionary() {
        return dict;
    }

    /**
     * Set up the dictionary of a game.
     * @param dict Dictionary
     */
    void loadDictionary(Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Launch a game.
     */
    public abstract void launch();

    /**
     * Game loop.
     */
    protected abstract boolean gameLoop();
}
