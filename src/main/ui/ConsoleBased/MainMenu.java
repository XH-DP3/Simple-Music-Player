package ui.ConsoleBased;

import java.util.InputMismatchException;
import java.util.Scanner;

// Represent the main menu the user will see once they start the program
public class MainMenu {
    private Scanner in;
    private RhythmGame rhythmGame;
    private SongListPanel songListPanel;
    private PersistentManager persistentManager;

    // start the program and invoke the menu.
    public MainMenu() {
        songListPanel = new SongListPanel(this);
        rhythmGame = new RhythmGame(this, songListPanel);
        persistentManager = new PersistentManager(this, songListPanel);
        printm("\nWelcome to the game!\n");
        menu();
    }

    // EFFECTS: A helper method that will print message
    public void printm(String message) {
        System.out.println(message);
    }

    // EFFECTS: A helper method that invokes startHelper() in RhythmGame
    private void startHelper() {
        rhythmGame.startHelper();
    }

    // EFFECTS: A helper method that invokes musicLibrary() in SongListPanel
    private void musicLibrary() {
        songListPanel.musicLibrary();
    }

    // EFFECTS: A helper method that invokes songList() in SongListPanel
    private void songList() {
        songListPanel.songList();
    }

    // EFFECTS: A helper method that invokes favoriteList() in SongListPanel
    private void favoriteList() {
        songListPanel.favoriteList();
    }

    // EFFECTS: A helper method that invokes saveHelper() in PersistentManager
    private void saveHelper() {
        persistentManager.saveHelper();
    }

    // EFFECTS: A helper method that invokes reloadHelper() in PersistentManager
    private void reloadHelper() {
        persistentManager.reloadHelper();
    }

    // EFFECTS: a helper method that will throw an InputMisMatchException if the
    // user input is not valid
    public void checkValidInput(int input, int lowerBound, int upperBound) {
        if (input < lowerBound || input > upperBound) {
            throw new InputMismatchException();
        }
    }

    // MODIFIES: this
    // EFFECTS: printing the menu// EFFECTS: showing the menu panel and receives an
    // input from user about the
    // next command. It will invoke the evaluateInputForMenu() and handle the
    // potential InputMismatchException().
    public void menu() throws InputMismatchException {
        try {
            in = new Scanner(System.in);
            printm("\nPlease select one of the following by entering an integer: \n");
            printm("1. Playing the game.");
            printm("2. Check music library.");
            printm("3. Check your song list.");
            printm("4. Check your favorite song list.");
            printm("5. Quit the program.");
            printm("6. Reload your saved lists.");
            int input = in.nextInt();
            checkValidInput(input, 1, 6);
            evaluateInputForMenu(input);
        } catch (InputMismatchException e) {
            printm("Invalid input. Please try again.");
            menu();
        }
    }

    // EFFECTS: it will evaluate input from the user in the menu panel and invoke
    // the corresponding panel
    private void evaluateInputForMenu(int input) {
        if (input == 1) {
            startHelper();
        } else if (input == 2) {
            musicLibrary();
        } else if (input == 3) {
            songList();
        } else if (input == 4) {
            favoriteList();
        } else if (input == 5) {
            quit();
        } else {
            reloadHelper();
        }
    }

    // MODIFIES: this
    // EFFECTS: ask the user if they want to save their music library, song list,
    // and favorite song list then quit the program
    public void quit() {
        printm("\nWould you like to save your progress (yes/no)?");
        String s = in.next();
        if (!s.equals("yes") && !s.equals("no")) {
            printm("Invalid input. Please try again.");
            quit();
        } else if (s.equals("no")) {
            printm("\nYour progress for this time is not saved.");
        } else {
            saveHelper();
        }
        printm("\nProgram ends. Bye!\n");
        System.exit(1);
    }
}