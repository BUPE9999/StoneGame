package com.game.stone.test;

import com.game.stone.gui.ResultWindow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultWindowTest {

    ResultWindow resultWindow;

    @Test
    void testIsGameSolved(){
        assertFalse(resultWindow.isGameRestarted());
        resultWindow.setisGameRestarted(true);
        assertTrue(resultWindow.isGameRestarted());
    }

}