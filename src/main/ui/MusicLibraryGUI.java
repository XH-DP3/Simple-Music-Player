package ui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.Song;
import model.SongList;

// Represent music library with default built in songs
public class MusicLibraryGUI extends JFrame {
    private Song s1 = new Song("Payphone", "Maroon 5", "Pop", 231);
    private Song s2 = new Song("Everybody Hurts", "Avril Lavigne", "Pop", 221);
    private Song s3 = new Song("Innocence", "Avril Lavigne", "Pop", 233);
    private Song s4 = new Song("Whataya Want from Me", "Adam Lambert", "Pop", 227);
    private Song s5 = new Song("Like I Do", "J.Tajor", "R&B", 149);
    private SongList musicLibrary;
    private JButton add;
    private JButton delete;
    private JButton mainMenu;
    private JButton previousPage;
    private JFrame frame;
    private JLabel label;
    private MainMenuGUI mainMenuGUI;
    private SongListGUI songListGUI;
    private ArrayList<JButton> songButtons;
    private Map<JButton, Song> mapSongs;

    // MODIFIES: this
    // EFFECTS: consturct the music library with default songs
    public MusicLibraryGUI(MainMenuGUI mainMenuGUI, SongListGUI songListGUI) {
        this.mainMenuGUI = mainMenuGUI;
        musicLibrary = new SongList();
        songButtons = new ArrayList<>();
        mapSongs = new HashMap<>();
        this.songListGUI = songListGUI;
        addDefaultSongs();
    }

    // EFFECTS: add the default songs to the music library
    private void addDefaultSongs() {
        musicLibrary.addSong(s1);
        musicLibrary.addSong(s2);
        musicLibrary.addSong(s3);
        musicLibrary.addSong(s4);
        musicLibrary.addSong(s5);
        s1.setImageFilePath("data/Maroon_5_Payphone_cover.png");
        s2.setImageFilePath("data/Cover_Everybody Hurts.png");
        s3.setImageFilePath("data/Cover_Innocence.jpeg");
        s4.setImageFilePath("data/Cover_Whataya Want from Me.jpeg");
        s5.setImageFilePath("data/Cover_Like I Do.jpg");
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

    // MODIFIES: this
    // EFFECTS: print the information of the song toghet
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

    // MODIFIES: this
    // EFFECTS: invoke the music library page
    public void musicLibrary() {
        frame = new JFrame("Music Library");
        for (Song mySong : musicLibrary.getSongs()) {
            printSongInfo(mySong);
        }
        add = new JButton("Add song to your song list");
        delete = new JButton("Delete song from your song list");
        mainMenu = new JButton("Return to the main menu");
        frame.add(add);
        frame.add(delete);
        frame.add(mainMenu);
        layout(frame, 10, 1);
        addActionListeners();
    }

    // MODIFIES: this
    // EFFECTS: add action listener for each JButton object and will invoke the
    // corresponding method to perform actions.
    private void addActionListeners() {
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                addHelper();
            }
        });
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                mainMenuGUI.mainMenu();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: display the available songs in the music library
    private void addHelper() {
        frame = new JFrame("Songs in Music Library");
        generateJButtonForSongs(musicLibrary);
        addActionListenersForAdd();
    }

    // EFFECTS: generating JButton for song and return the JButton
    private void generateJButtonForSongs(SongList musicLibrary) {
        label = new JLabel("Click on the button to add song to your song list");
        frame.add(label);
        for (Song s : musicLibrary.getSongs()) {
            JButton button = new JButton(displaySong(s));
            mapSongs.put(button, s);
            songButtons.add(button);
            frame.add(button);
        }
        previousPage = new JButton("Return to the previous page");
        frame.add(previousPage);
        layout(frame, musicLibrary.getSize() + 2, 1);
    }

    // MODIFIES: this
    // EFFECTS: add action listner for each JButton object of adding.
    private void addActionListenersForAdd() {
        for (JButton button : songButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addSongToSongList(button);
                }
            });
        }
        previousPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                musicLibrary();
            }
        });
    }

    // EFFECTS: add song to the song list
    private void addSongToSongList(JButton button) {
        Song mySong = mapSongs.get(button);
        songListGUI.addSongToSongList(mySong);
        label.setText(mySong.getTitle() + " is added to your list");
        frame.add(label);
        frame.revalidate();
        frame.repaint();
    }
}
