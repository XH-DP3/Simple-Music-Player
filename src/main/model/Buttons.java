package model;

// Represents the buttons that the user needs to press the key
public class Buttons {

    // EFFECTS: construct an object with 8 fixed buttons and no points.
    public Buttons() {

    }

    // EFFECTS: return the keys/buttons that the user needs to press
    public String[] getFixedButtons() {
        // stub
        return null;
    }

    // EFFECTS: return the next key/button that the user have to press
    public String getNextFallingButton() {
        // stub
        return null;
    }

    // EFFECTS: return the points that the user got after this current key press.
    public int getSinglePressedPoints() {
        // stub
        return 0;
    }

    // EFFECTS: return the total points that the user got
    public int getTotalPressedPoints() {
        // stub
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: Checks if the user's key press matches a valid falling button.
    // If the key press is correct, updates points and returns true.
    // Otherwise, returns false. 100 points are rewarded for each correct key press
    public boolean checkKeyPress(String keyPress) {
        // stub
        return false;
    }

    // MODIFIES: this
    // EFFECTS: generate the next key press
    public void generateNextKeyPress() {
        // stub
    }
}
