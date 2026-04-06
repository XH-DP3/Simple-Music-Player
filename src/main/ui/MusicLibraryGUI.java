package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.WindowConstants;

import model.Song;
import model.SongList;

// Represent music library with default built in songs
public class MusicLibraryGUI extends JFrame {
    private SongList musicLibrary;
    private JFrame frame;
    private JLabel label;
    private MainMenuGUI mainMenuGUI;
    private SongListGUI songListGUI;
    private MusicPlayerGUI musicPlayerGUI;
    private ArrayList<JButton> songButtons;
    private Map<JButton, Song> mapSongs;
    private Map<String, JButton> buttons;
    private JPanel contentPanel;

    // MODIFIES: this
    // EFFECTS: consturct the music library with default songs
    public MusicLibraryGUI(MainMenuGUI mainMenuGUI, SongListGUI songListGUI) {
        this.mainMenuGUI = mainMenuGUI;
        this.songListGUI = songListGUI;
        musicPlayerGUI = new MusicPlayerGUI(mainMenuGUI);
        musicLibrary = new SongList();
        songButtons = new ArrayList<>();
        mapSongs = new HashMap<>();
        buttons = new HashMap<>();
        addDefaultSongs();
    }

    // MODIFIES: this
    // EFFECTS: add the default songs to the music library
    private void addDefaultSongs() {
        Song s1 = new Song("Payphone", "Maroon 5", "Pop", 231);
        Song s2 = new Song("Everybody Hurts", "Avril Lavigne", "Pop", 221);
        Song s3 = new Song("Innocence", "Avril Lavigne", "Pop", 233);
        Song s4 = new Song("Whataya Want from Me", "Adam Lambert", "Pop", 227);
        Song s5 = new Song("Like I Do", "J.Tajor", "R&B", 149);
        musicLibrary.addDefaultSong(s1);
        musicLibrary.addDefaultSong(s2);
        musicLibrary.addDefaultSong(s3);
        musicLibrary.addDefaultSong(s4);
        musicLibrary.addDefaultSong(s5);
        setFilePath(s1, s2, s3, s4, s5);
    }

    // MODIFIES: this
    // EFFECTS: Set the default melody and image filepath.
    private void setFilePath(Song s1, Song s2, Song s3, Song s4, Song s5) {
        s1.setImageFilePath("data/Maroon_5_Payphone_cover.png");
        s2.setImageFilePath("data/Cover_Everybody Hurts.png");
        s3.setImageFilePath("data/Cover_Innocence.jpeg");
        s4.setImageFilePath("data/Cover_Whataya Want from Me.jpeg");
        s5.setImageFilePath("data/Cover_Like I Do.jpg");
        s1.setMelodyFilePath("data/21 Payphone.wav");
        s2.setMelodyFilePath("data/08 Everybody Hurts.wav");
        s3.setMelodyFilePath("data/08 Innocence.wav");
        s4.setMelodyFilePath("data/03 Whataya Want from Me.wav");
        s5.setMelodyFilePath("data/01 Like I Do (with sunkis).wav");
    }

    // MODIFIES: this
    // EFFECTS: a helper method that will generate the layout
    public void layout(JFrame frame, int row, int col) {
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
        JPanel panel = new JPanel(new BorderLayout(16, 8));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(250, 251, 255));
        JLabel songLabel = new JLabel(displaySong(mySong));
        ImageIcon originalIcon = new ImageIcon(mySong.getImageFilePath());
        Image resizedImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        panel.add(songLabel, BorderLayout.CENTER);
        panel.add(imageLabel, BorderLayout.EAST);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        contentPanel.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: invoke the music library page
    public void musicLibrary() {
        frame = new JFrame("Music Library");
        frame.setLayout(new BorderLayout());
        contentPanel = new JPanel();
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        for (Song mySong : musicLibrary.getSongs()) {
            printSongInfo(mySong);
        }
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        JButton add = new JButton("Add song to your song list");
        JButton play = new JButton("Play song");
        JButton mainMenu = new JButton("Return to the main menu");
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        controls.add(add);
        controls.add(play);
        controls.add(mainMenu);
        buttons.put("add", add);
        buttons.put("play", play);
        buttons.put("mainMenu", mainMenu);
        frame.add(controls, BorderLayout.SOUTH);
        layout(frame, 10, 1);
        addActionListeners();
        menu();
    }

    // MODIFIES: this
    // EFFECTS: add action listener for each JButton object and will invoke the
    // corresponding method to perform actions.
    private void addActionListeners() {
        buttons.get("add").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                addHelper();
            }
        });
        buttons.get("play").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                musicPlayerGUI.playHelper(musicLibrary);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: return to the main menu
    public void menu() {
        buttons.get("mainMenu").addActionListener(new ActionListener() {
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
        addActionListeners();
        menu();
    }

    // MODIFIES: this
    // EFFECTS: generating JButton for song and return the JButton
    private void generateJButtonForSongs(SongList musicLibrary) {
        label = new JLabel("Click on the button to add song to your song list");
        label.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.setLayout(new BorderLayout());
        JPanel songPanel = new JPanel();
        songPanel.setLayout(new javax.swing.BoxLayout(songPanel, javax.swing.BoxLayout.Y_AXIS));
        songPanel.add(label);
        generateSongButtons(songPanel, musicLibrary, mapSongs);
        frame.add(new JScrollPane(songPanel), BorderLayout.CENTER);
        JButton previousPage = new JButton("Return to the previous page");
        JButton mainMenu = new JButton("Return to the main menu");
        buttons.put("previousPage", previousPage);
        buttons.put("mainMenu", mainMenu);
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        footer.add(previousPage);
        footer.add(mainMenu);
        frame.add(footer, BorderLayout.SOUTH);
        layout(frame, musicLibrary.getSize() + 3, 1);
    }

    // MODIFIES: this
    public void generateSongButtons(JPanel songPanel, SongList list, Map<JButton, Song> mapSongs) {
        for (Song s : list.getSongs()) {
            JButton button = new JButton(displaySong(s));
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            mapSongs.put(button, s);
            songButtons.add(button);
            songPanel.add(button);
        }
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
        buttons.get("previousPage").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                musicLibrary();
            }
        });
    }

    // MODIFIES: this
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
