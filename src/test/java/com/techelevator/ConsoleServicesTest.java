package com.techelevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ConsoleServicesTest {
    /* Start citation
     * Adapted from AI response: Google Gemini
     * Date: 10/5/24
     * Conversation link: https://g.co/gemini/share/525468b64609
     */

    //Input Validation Tests
    @Test
    public void testValidNumber() {
        assertTrue(ConsoleServices.isValidNumber("123.45"));
        assertTrue(ConsoleServices.isValidNumber("-123.45"));
        assertTrue(ConsoleServices.isValidNumber("0"));
        assertTrue(ConsoleServices.isValidNumber("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));

        assertFalse(ConsoleServices.isValidNumber(""));
        assertFalse(ConsoleServices.isValidNumber("cat"));
    }
    @Test void testValidPosWholeNumber() {
        assertTrue(ConsoleServices.isValidPosWholeNumber("123"));
        assertTrue(ConsoleServices.isValidPosWholeNumber("0"));

        assertFalse(ConsoleServices.isValidPosWholeNumber("-10"));
        assertFalse(ConsoleServices.isValidPosWholeNumber("1.0"));
        assertFalse(ConsoleServices.isValidPosWholeNumber(""));
        assertFalse(ConsoleServices.isValidPosWholeNumber("cat"));
    }

    @Test
    public void testValidPosWholeDollar() {
        assertTrue(ConsoleServices.isValidPosWholeNumber("123"));
        assertTrue(ConsoleServices.isValidPosWholeNumber("0"));
        assertTrue(ConsoleServices.isValidPosWholeDollar("123.00"));

        assertFalse(ConsoleServices.isValidPosWholeDollar("-10"));
        assertFalse(ConsoleServices.isValidPosWholeNumber("1.50"));
        assertFalse(ConsoleServices.isValidPosWholeNumber(""));
        assertFalse(ConsoleServices.isValidPosWholeNumber("cat"));
    }

    @Test
    public void testValidMenuOption() {
        assertTrue(ConsoleServices.isValidMenuOption(1, 10, 5));
        assertTrue(ConsoleServices.isValidMenuOption(1, 10, 1));
        assertTrue(ConsoleServices.isValidMenuOption(1, 10, 10));
        assertTrue(ConsoleServices.isValidMenuOption(-5, -1, -2));

        assertFalse(ConsoleServices.isValidMenuOption(1, 10, 0));
        assertFalse(ConsoleServices.isValidMenuOption(1, 10, 11));
        assertFalse(ConsoleServices.isValidMenuOption(1, 10, 100));
        assertFalse(ConsoleServices.isValidMenuOption(-5, -1, -6));
    }

    @Test
    public void testValidQuantity() {
        assertTrue(ConsoleServices.isValidQuantity(1, 10));
        assertTrue(ConsoleServices.isValidQuantity(5, 10));
        assertTrue(ConsoleServices.isValidQuantity(10, 10));

        assertFalse(ConsoleServices.isValidQuantity(0, 10));
        assertFalse(ConsoleServices.isValidQuantity(-1, 10));
        assertFalse(ConsoleServices.isValidQuantity(11, 10));
    }

    /*
    * End citation
    */

}