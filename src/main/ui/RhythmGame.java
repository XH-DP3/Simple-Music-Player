package ui;

import java.util.Arrays;
import java.util.Scanner;

import model.Buttons;
import model.Song;
import model.SongList;

// Represent the game part
public class RhythmGame {
    private SongListPanel songListPanel = new SongListPanel();
    private Scanner in;
    private Buttons buttons = new Buttons();
    private MainMenu mainMenu;

    // EFFECTS: construct a RhythmGame with the given mainMenu and songListPanel
    public RhythmGame(MainMenu mainMenu, SongListPanel songListPanel) {
        this.songListPanel = songListPanel;
        this.mainMenu = mainMenu;
    }

    // EFFECTS: a helper method that will print message.
    private void printm(String message) {
        System.out.println(message);
    }

    // A helper method that will call getSongList() in SongListPanel and return the
    // list
    private SongList getSongList() {
        return songListPanel.getSongList();
    }

    // A helper method that will call printSongInfo in SongListPanel and print list.
    private void printSongInfo(SongList list) {
        songListPanel.printSongInfo(list);
    }

    // A helper method that will return the currentPlayingSong
    private Song getCurrentPlayingSong() {
        return songListPanel.getCurrentPlayingSong();
    }

    // A helper method that will call setCurrentPlayingSong in SongListPanel
    private void setCurrentPlayingSong(Song mySong) {
        songListPanel.setCurrentPlayingSong(mySong);
    }

    // A helper method that will return the total point of the current song
    private int getTotalPoints() {
        return songListPanel.getTotalPoints();
    }

    // A helper method that will invoke the menu
    private void menu() {
        mainMenu.menu();
    }

    // REQUIRES: user's song list is not empty
    // MODIFIES: this
    // EFFECTS: start the game if the song title is found in either song list or
    // favorite song list. Otherwise, print an error message and reask for input.
    public void startHelper() {
        if (songListPanel.getSongList().getSize() == 0) {
            printm("\nYour music list is empty. Please add songs to your list first.");
            menu();
        } else {
            in = new Scanner(System.in);
            printm("Below are all songs in your song list: \n");
            printSongInfo(getSongList());
            printm("\nPlease enter the title of the song that you want to play: \n");
            String msg = in.nextLine();
            Song s = getSongList().findSongByTitle(msg);
            if (s != null) {
                start(s);
            } else {
                printm("\n" + msg + " is not found.");
                startHelper();
            }
        }
    }

    // EFFECTS: starts the game and evaluate if the key press from user is correct.
    // If the key press is correct, print an appropriate message and show the total
    // points the user received. Otherwise, print an error message.
    private void start(Song mySong) {
        printm("\nGame is starting.");
        setCurrentPlayingSong(mySong);
        printm("Playing " + mySong.getTitle());
        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 8);
            generatingButtons(random);
            printm("\n(Type Q to quit the game)\n");
            printm("\nThe next falling button is: " + buttons.getNextFallingButton());
            printm("\nPress keys: " + Arrays.toString(buttons.getFixedButtons()));
            String msg = in.nextLine();
            if (buttons.checkKeyPress(msg)) {
                mySong.updateTotalPoints(100);
                printm("\nGood job! You have got " + getTotalPoints() + " points\n");
            } else if (msg.equals("Q")) {
                end();
            } else {
                mySong.updateTotalPoints(-50);
                printm("\nWrong key press!\n");
                printm("\nYou now have " + getTotalPoints() + " points\n");
            }
        }
        end();
    }

    // EFFECTS: generating the next falling buttons that the user has to press
    private void generatingButtons(int random) {
        if (random == 0) {
            buttons.setNextKeyPress("A");
        } else if (random == 1) {
            buttons.setNextKeyPress("S");
        } else if (random == 2) {
            buttons.setNextKeyPress("D");
        } else if (random == 3) {
            buttons.setNextKeyPress("F");
        } else if (random == 4) {
            buttons.setNextKeyPress("J");
        } else if (random == 5) {
            buttons.setNextKeyPress("K");
        } else if (random == 6) {
            buttons.setNextKeyPress("L");
        } else {
            buttons.setNextKeyPress(";");
        }
    }

    // EFFECTS: finish the game and show the total point that the user received.
    // Then return to the menu
    private void end() {
        getCurrentPlayingSong().setFinish(true);
        if (getTotalPoints() > getCurrentPlayingSong().getRecord()) {
            getCurrentPlayingSong().updateRecord(getTotalPoints());
        }
        printm("\nGame ends. You got " + getTotalPoints() + " points for this time.");
        printm("\nYour record is " + getCurrentPlayingSong().getRecord());
        getCurrentPlayingSong().resetTotalPoints();
        menu();
    }
}
