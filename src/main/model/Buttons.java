package model;

// Represents the buttons that the user needs to press the key
public class Buttons {

    private static final String[] FIXED_BUTTONS = { "A", "S", "D", "F", "J", "K", "L", ";" };
    private int singlePressPoints;
    private int totalPoints;
    private String nextKeyPress;

    // EFFECTS: construct an object with 8 fixed buttons, no points, and no next key
    // press.
    public Buttons() {
        singlePressPoints = 0;
        totalPoints = 0;
        nextKeyPress = null;
    }

    // EFFECTS: return the keys/buttons that the user needs to press
    public String[] getFixedButtons() {
        return FIXED_BUTTONS;
    }

    // EFFECTS: return the next key/button that the user have to press
    public String getNextFallingButton() {
        return nextKeyPress;
    }

    // EFFECTS: return the points that the user got after this current key press.
    public int getSinglePressedPoints() {
        return singlePressPoints;
    }

    // EFFECTS: return the total points that the user got
    public int getTotalPressedPoints() {
        return totalPoints;
    }

    // MODIFIES: this
    // EFFECTS: Checks if the user's key press matches a valid falling button.
    // If the key press is correct, updates points and returns true.
    // Otherwise, returns false. 100 points are rewarded for each correct key press
    public boolean checkKeyPress(String userKeyPress) {
        if (userKeyPress.equals(nextKeyPress)) {
            singlePressPoints = 100;
            totalPoints += 100;
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: setting up the next key press
    public void setNextKeyPress(String nextKeyPress) {
        this.nextKeyPress = nextKeyPress;
    }
}
