package com.game.stone.test;

import com.game.stone.gui.GameWindow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameWindowTest {

    GameWindow gameWindow;

    @Test
    void testIsGameSolved(){
        assertFalse(gameWindow.isGameSolved());
        gameWindow.setGameIsSolved(true);
        assertTrue(gameWindow.isGameSolved());
    }
}