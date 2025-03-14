package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

// Represents the main menu with options that the user can select
public class MainMenuGUI extends JFrame {
    private JFrame frame;
    private JButton musicLibrary;
    private JButton songList;
    private JButton favoriteList;
    private JButton reload;
    private JButton quit;
    private SongListGUI songListGUI = new SongListGUI(this);
    private MusicLibraryGUI musicLibraryGUI = new MusicLibraryGUI(this, songListGUI);

    // EFFECTS: contruct the main menu panel by invoking it
    public MainMenuGUI() {
        mainMenu();
    }

    // EFFECTS: a helper method that will generate the layout
    private void layout(JFrame frame, int row, int col) {
        frame.setLayout(new GridLayout(row, col));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: display the main menu panel
    public void mainMenu() {
        frame = new JFrame("Main Menu");
        createButtons();
        layout(frame, 5, 1);
        addActionListeners();
    }

    // EFFECTS: create the buttons for the main menu panel
    private void createButtons() {
        musicLibrary = new JButton("Check music library");
        songList = new JButton("Check your song list");
        favoriteList = new JButton("Check your favorite song list");
        reload = new JButton("Reload your saved lists");
        quit = new JButton("Quit the program");
        frame.add(musicLibrary);
        frame.add(songList);
        frame.add(favoriteList);
        frame.add(reload);
        frame.add(quit);
    }

    // EFFECTS: add action listners for the main menu panel
    private void addActionListeners() {
        musicLibrary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                handleMusicLibraryClicked();
            }
        });
        songList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                handleSongListClicked();
            }
        });
        favoriteList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                handleFavoriteListClicked();
            }
        });
    }

    // EFFECTS: handle the case when music library button is clicked
    private void handleMusicLibraryClicked() {
        musicLibraryGUI.musicLibrary();
    }

    // EFFECTS: handle the case when song list button is clicked
    private void handleSongListClicked() {
        songListGUI.songList();
    }

    // EFFECTS: handle the case when favorite list button is clicked
    private void handleFavoriteListClicked() {
        // stub
    }

    // EFFECTS: handle the case when reload button is clicked
    private void handleReloadClicked() {
        // stub
    }

    // EFFECTS: handle the case when quit button is clicked
    private void handleQuitClicked() {
        // stub
    }
}
