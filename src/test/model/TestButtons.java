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
        assertEquals("S", actualKeys[1]);
        assertEquals("D", actualKeys[2]);
        assertEquals("F", actualKeys[3]);
        assertEquals("J", actualKeys[4]);
        assertEquals("K", actualKeys[5]);
        assertEquals("L", actualKeys[6]);
        assertEquals(";", actualKeys[7]);
    }

    @Test
    public void testCheckWrongKeyPress() {
        buttons.setNextKeyPress("A");
        assertEquals("A", buttons.getNextFallingButton());
        assertFalse(buttons.checkKeyPress("Q"));
        assertEquals(0, buttons.getSinglePressedPoints());
    }

    @Test
    public void testCheckCorrectKeyPress() {
        buttons.setNextKeyPress("A");
        assertEquals("A", buttons.getNextFallingButton());
        assertTrue(buttons.checkKeyPress("A"));
        assertEquals(100, buttons.getSinglePressedPoints());
    }

    @Test
    public void testCheckMultipleKeyPress() {
        buttons.setNextKeyPress("A");
        assertEquals("A", buttons.getNextFallingButton());
        assertTrue(buttons.checkKeyPress("A"));
        buttons.setNextKeyPress("S");
        assertEquals("S", buttons.getNextFallingButton());
        assertTrue(buttons.checkKeyPress("S"));
        buttons.setNextKeyPress("D");
        assertEquals("D", buttons.getNextFallingButton());
        assertFalse(buttons.checkKeyPress("K"));
        assertEquals(200, buttons.getTotalPressedPoints());
    }
}
