package com.game.stone.test;

import com.game.stone.gui.LoginWindow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginWindowTest {

    LoginWindow loginWindow;
    @Test
    void testIsGameStarted(){
        assertFalse(loginWindow.isGameStarted());
        loginWindow.setGameStarted(true);
        assertTrue(loginWindow.isGameStarted());
    }
}