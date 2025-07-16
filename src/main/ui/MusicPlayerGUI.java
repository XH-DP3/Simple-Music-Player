package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import model.Song;
import model.SongList;

// Represent a music player that can play song
public class MusicPlayerGUI {
    private Clip clip;
    private JFrame frame;
    private MainMenuGUI mainMenuGUI;
    private Map<JButton, Song> mapSongs;
    private ArrayList<JButton> songButtons;
    private JButton play;
    private JButton pause;
    private JButton reset;
    private JButton close;
    private JButton previous;
    private JButton menu;
    private SongList list;

    // MODIFIES: this
    // EFFECTS: construct a music player with a reference to the main menu and music
    // library.
    public MusicPlayerGUI(MainMenuGUI mainMenuGUI) {
        this.mainMenuGUI = mainMenuGUI;
        mapSongs = new HashMap<>();
        songButtons = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: displaying songs in your list and let user select a song to play
    public void playHelper(SongList list) {
        this.list = list;
        frame = new JFrame("Play Helper");
        JLabel label = new JLabel("Please click on the song you want to play");
        frame.add(label);
        generateSongButtons(frame, list, mapSongs);
        menu = new JButton("Return to the menu");
        frame.add(menu);
        layout(frame, list.getSize() + 2, 1);
        addSongButtonsActionListeners();
        addMenu();
    }

    // MODIFIES: this
    public void generateSongButtons(JFrame frame, SongList list, Map<JButton, Song> mapSongs) {
        for (Song s : list.getSongs()) {
            JButton button = new JButton(displaySong(s));
            mapSongs.put(button, s);
            songButtons.add(button);
            frame.add(button);
        }
    }

    // MODIFIES: this
    // EFFECTS: a helper method that will generate the layout
    public void layout(JFrame frame, int row, int col) {
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
    // EFFECTS: add an action listerner for each song
    private void addSongButtonsActionListeners() {
        for (JButton button : mapSongs.keySet()) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        frame.dispose();
                        playSongHelper(button);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }

    // MODIFIES: this
    // EFFECTS: return to the menu if user click on the menu button
    private void addMenu() {
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                mainMenuGUI.mainMenu();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: display the buttons which control the song.
    private void playSongHelper(JButton button) throws Exception {
        frame = new JFrame("Music Player");
        Song mySong = mapSongs.get(button);
        JLabel label = new JLabel(mySong.getTitle());
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(new File(mySong.getMelodyFilePath()))) {
            clip = AudioSystem.getClip();
            clip.open(stream);
            addPlayButtons();
            previous = new JButton("Return to the previous page");
            frame.add(label);
            frame.add(play);
            frame.add(pause);
            frame.add(reset);
            frame.add(close);
            frame.add(previous);
            layout(frame, 6, 1);
            addPlayActionListeners();
            addCloseAndPrevious();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: adding buttons when user is playing the music
    private void addPlayButtons() {
        play = new JButton("Play");
        pause = new JButton("Pause");
        reset = new JButton("Reset");
        close = new JButton("Close");
    }

    // MODIFIES: this
    // EFFECTS: adding action listeners for each button when user wants to play the
    // song
    private void addPlayActionListeners() {
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clip.start();
            }
        });
        pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clip.stop();
            }
        });
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clip.setMicrosecondPosition(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adding close music player and return previous page fuctionality/
    private void addCloseAndPrevious() {
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clip.close();
            }
        });
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clip.close();
                frame.dispose();
                playHelper(list);
            }
        });
    }

}
