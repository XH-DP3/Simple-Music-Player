package ui;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.Song;
import model.SongList;

// Represent the user's song list
public class SongListGUI {
    private SongList mySongList;
    private MainMenuGUI mainMenuGUI;
    private MusicLibraryGUI musicLibraryGUI;

    // MODIFIES: this
    // EFFECTS: construct an empty song list with refernece to the previous page (main menu and music library)
    public SongListGUI(MainMenuGUI mainMenuGUI, MusicLibraryGUI musicLibraryGUI) {
        mySongList = new SongList();
        this.mainMenuGUI= mainMenuGUI;
        this.musicLibraryGUI = musicLibraryGUI;
    }

    // EFFECTS: a helper method that will generate the layout
    private void layout(JFrame frame, int row, int col) {
        // stub
    }

    // EFFECTS: return a string representation to display the song  
    private String displaySong(Song mySong) {
        // stub
        return "";
    }

    // EFFECTS: create the buttons for the song list panel
    private void createButtons() {
        // stub
    }


    // MODIFIES: this
    // EFFECTS: adding mySong from the music library to the song list
    public void addSong(Song mySong) {
        // stub
    }
    
    // MODIFIES: this
    // EFFECTS: invoke the song list page
    public void songList() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: add action listener for each JButton object and will invoke the corresponding method to perform actions. 
    private void addActionListeners() {
        // stub
    }
}
