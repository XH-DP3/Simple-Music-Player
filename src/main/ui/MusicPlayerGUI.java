package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Song;
import model.SongList;

public class MusicPlayerGUI {
    private Clip clip;
    private JFrame frame;
    private MusicLibraryGUI musicLibraryGUI;
    private MainMenuGUI mainMenuGUI;
    private Map<JButton, Song> mapSongs;
    private JButton play;
    private JButton pause;
    private JButton reset;
    private JButton close;
    private JButton previous;
    private JButton menu;
    private SongList list;

    public MusicPlayerGUI(MainMenuGUI mainMenuGUI, MusicLibraryGUI musicLibraryGUI) {
        this.mainMenuGUI = mainMenuGUI;
        this.musicLibraryGUI = musicLibraryGUI;
        mapSongs = new HashMap<>();
    }

    public void playHelper(SongList list) {
        this.list = list;
        frame = new JFrame("Play Helper");
        JLabel label = new JLabel("Please click on the song you want to play");
        frame.add(label);
        musicLibraryGUI.generateSongButtons(frame, list, mapSongs);
        menu = new JButton("Return to the menu");
        frame.add(menu);
        musicLibraryGUI.layout(frame, list.getSize() + 2, 1);
        addSongButtonsActionListeners();
        addMenu();
    }

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

    private void addMenu() {
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                mainMenuGUI.mainMenu();
            }
        });
    }

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
            musicLibraryGUI.layout(frame, 6, 1);
            addPlayActionListeners();
            addCloseAndPrevious();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPlayButtons() {
        play = new JButton("Play");
        pause = new JButton("Pause");
        reset = new JButton("Reset");
        close = new JButton("Close");
    }

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
