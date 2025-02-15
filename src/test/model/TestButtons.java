package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestButtons {

    private Buttons buttons;

    @Before
    public void setup() {
        buttons = new Buttons();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, buttons.getSinglePressedPoints());
        assertEquals(8, buttons.getFixedButtons().length);
        String[] actualKeys = buttons.getFixedButtons();
        assertEquals("A", actualKeys[0]);
        assertEquals("B", actualKeys[1]);
        assertEquals("C", actualKeys[2]);
        assertEquals("D", actualKeys[3]);
        assertEquals("J", actualKeys[4]);
        assertEquals("K", actualKeys[5]);
        assertEquals("L", actualKeys[6]);
        assertEquals(";", actualKeys[7]);
    }

    @Test
    public void testCheckWrongKeyPress() {
        buttons.generateNextKeyPress();
        assertFalse(buttons.checkKeyPress("Q"));
        assertEquals(0, buttons.getSinglePressedPoints());
    }

    @Test
    public void testCheckCorrectKeyPress() {
        buttons.generateNextKeyPress();
        assertTrue(buttons.checkKeyPress(buttons.getNextFallingButton()));
        assertEquals(0, buttons.getSinglePressedPoints());
    }

    @Test
    public void testCheckMultipleKeyPress() {
        buttons.generateNextKeyPress();
        assertTrue(buttons.checkKeyPress(buttons.getNextFallingButton()));
        buttons.generateNextKeyPress();
        assertTrue(buttons.checkKeyPress(buttons.getNextFallingButton()));
        buttons.generateNextKeyPress();
        assertFalse(buttons.checkKeyPress("P"));
        assertEquals(200, buttons.getSinglePressedPoints());
    }
}
