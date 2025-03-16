package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

// Represents the main menu with options that the user can select
public class MainMenuGUI extends JFrame {
    private JFrame frame;
    private SongListGUI songListGUI;
    private MusicLibraryGUI musicLibraryGUI;
    private Map<String, JButton> buttons;

    // MODIFIES: this
    // EFFECTS: contruct the main menu panel by invoking it
    public MainMenuGUI() {
        musicLibraryGUI = new MusicLibraryGUI(this, songListGUI);
        songListGUI = new SongListGUI(this);
        buttons = new HashMap<>();
        mainMenu();
    }

    // MODIFIES: this
    // EFFECTS: a helper method that will generate the layout
    private void layout(JFrame frame, int row, int col) {
        frame.setLayout(new GridLayout(row, col));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: display the main menu panel
    public void mainMenu() {
        frame = new JFrame("Main Menu");
        createButtons();
        layout(frame, 5, 1);
        addListsActionListeners();
    }

    // MODIFIES: this
    // EFFECTS: create the buttons for the main menu panel
    private void createButtons() {
        JButton musicLibrary = new JButton("Check music library");
        JButton songList = new JButton("Check your song list");
        JButton favoriteList = new JButton("Check your favorite song list");
        JButton reload = new JButton("Reload your saved lists");
        JButton quit = new JButton("Quit the program");
        buttons.put("musicLibrary", musicLibrary);
        buttons.put("songList", songList);
        buttons.put("favoriteList", favoriteList);
        buttons.put("reload", reload);
        buttons.put("quit", quit);
        frame.add(musicLibrary);
        frame.add(songList);
        frame.add(favoriteList);
        frame.add(reload);
        frame.add(quit);
    }

    // MODIFIES: this
    // EFFECTS: add action listners for the main menu panel
    private void addListsActionListeners() {
        buttons.get("musicLibrary").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                handleMusicLibraryClicked();
            }
        });
        buttons.get("songList").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                handleSongListClicked();
            }
        });
        buttons.get("favoriteList").addActionListener(new ActionListener() {
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
