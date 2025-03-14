package ui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.Song;
import model.SongList;

// Represent the user's song list
public class SongListGUI {
    private SongList mySongList;
    private MainMenuGUI mainMenuGUI;
    private MusicLibraryGUI musicLibraryGUI;
    private JFrame frame;
    private JLabel label;
    private JButton menu;

    // MODIFIES: this
    // EFFECTS: construct an empty song list with refernece to the previous page
    // (main menu and music library)
    public SongListGUI(MainMenuGUI mainMenuGUI) {
        mySongList = new SongList();
        this.mainMenuGUI = mainMenuGUI;
    }

    // EFFECTS: a helper method that will generate the layout
    private void layout(JFrame frame, int row, int col) {
        frame.setLayout(new GridLayout(row, col));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: return a string representation to display the song
    private String displaySong(Song mySong) {
        return "Title: " + mySong.getTitle()
                + "   Author: " + mySong.getAuthor()
                + "   Genre: " + mySong.getGenre()
                + "   Duration: " + mySong.getDuration();
    }

    // EFFECTS: print song info
    private void printSongInfo(Song mySong) {
        JPanel panel = new JPanel();
        JLabel songLabel = new JLabel(displaySong(mySong));
        ImageIcon originalIcon = new ImageIcon(mySong.getImageFilePath());
        Image resizedImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        panel.setLayout(new GridLayout(1, 2));
        panel.add(songLabel);
        panel.add(imageLabel);
        frame.add(panel);
    }

    // EFFECTS: create the buttons for the song list panel
    private void createButtons() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adding mySong from the music library to the song list
    public void addSongToSongList(Song mySong) {
        mySongList.addSong(mySong);
    }

    // MODIFIES: this
    // EFFECTS: invoke the song list page
    public void songList() {
        frame = new JFrame("Your Song List");
        if (mySongList.getSize() == 0) {
            label = new JLabel("Your song list is empty");
            frame.add(label);
        } else {
            for (Song s : mySongList.getSongs()) {
                printSongInfo(s);
            }
        }
        menu = new JButton("Return to the menu");
        frame.add(menu);
        layout(frame, mySongList.getSize() + 2, 1);
        addActionListeners();
    }

    // MODIFIES: this
    // EFFECTS: add action listener for each JButton object and will invoke the
    // corresponding method to perform actions.
    private void addActionListeners() {
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                mainMenu();
            }
        });
    }

    // EFFECTS: return to the main menu
    private void mainMenu() {
        mainMenuGUI.mainMenu();
    }
}
