package model;

// Represents the buttons that are fixed on the screen, and the user will press the key once the falling buttons reach each fixed button.
public class FixedButtons{

    // EFFECTS: construct an object with 8 fixed buttons.
    public FixedButtons() {

    }

    // EFFECTS: return the keys that the user needs to press
    public String[] getKeys() {
        // stub
        return null;
    }

    // EFFECTS: return the points that the user got after this current key press.
    public int getSinglePressedPoints() {
        // stub
        return 0;
    }

    // MODIFIES: this
    // EFFECTS:Checks if the user's key press matches a valid falling button.
    //          If the key press is correct, updates points and returns true.
    //          Otherwise, returns false.
    public boolean checkKeyPress(String keyPress) {
        // stub
        return false;
    }
}
