package ui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

// Represents a music player that will play the melody of the music
public class MusicPlayer {
    private Clip clip;
    private boolean isPlaying;
    private boolean isOver;
    private String filePath;
    private MainMenu mainMenu;
    private SongListPanel songListPanel;

    // MODIFIES: this
    // EFFECTS: construct a music player object with a filepath
    public MusicPlayer(String filePath, MainMenu mainMenu, SongListPanel songListPanel)
            throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        this.mainMenu = mainMenu;
        this.filePath = filePath;
        this.songListPanel = songListPanel;
        playHelper();
    }

    // EFFECTS: A helper method that print message
    public void printm(String message) {
        System.out.println(message);
    }

    // EFFECTS: A method that will ask user for operations including play, pause, reset, and quit.
    public void playHelper() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        try (Scanner in = new Scanner(System.in);
                AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filePath))) {
            int input = -1;
            clip = AudioSystem.getClip();
            clip.open(stream);
            while (input != 4) {
                printm("\nPlease select one of the following: ");
                printm("\n1. Play");
                printm("2. Pause");
                printm("3. Reset");
                printm("4. Quit");
                input = in.nextInt();
                mainMenu.checkValidInput(input, 1, 4);
                evaluateInputForPlay(input);
            }
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            playHelper();
        }
    }

    // EFFECTS: a method that will evaluate the input from user regarding the operations of playing the song
    public void evaluateInputForPlay(int input) throws LineUnavailableException, IOException {
        if (input == 1) {
            play();
        } else if (input == 2) {
            pause();
        } else if (input == 3) {
            reset();
        } else {
            close();
        }
    }

    // EFFECTS: play the music
    public void play() throws LineUnavailableException, IOException {
        printm("\nYour song is playing");
        clip.start();
    }

    // EFFECTS: pause the music
    public void pause() {
        clip.stop();
    }

    // EFFECTS: reset the music to the beginning
    public void reset() {
        clip.setMicrosecondPosition(0);
    }

    // EFFECTS: close the clip
    public void close() {
        clip.close();
        songListPanel.playHepler();
    }

    // EFFECTS: return true if the music is over
    public boolean isOver() {
        return isOver;
    }

    // EFFECTS: return true if the music is playing
    public boolean isPlaying() {
        return isPlaying;
    }
}
