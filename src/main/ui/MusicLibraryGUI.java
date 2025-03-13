package ui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.Song;
import model.SongList;

// Represent music library with default built in songs
public class MusicLibraryGUI extends JFrame{
    private JButton add;
    private JButton delete;
    private JButton mainMenu;
    private JButton previousPage;
    private SongList musicLibrary;
    private Song s1 = new Song("Payphone", "Maroon 5", "Pop", 231);
    private Song s2 = new Song("Everybody Hurts", "Avril Lavigne", "Pop", 221);
    private Song s3 = new Song("Innocence", "Avril Lavigne", "Pop", 233);
    private Song s4 = new Song("Whataya Want from Me", "Adam Lambert", "Pop", 227);
    private Song s5 = new Song("Like I Do", "J.Tajor", "R&B", 149);
    private MainMenuGUI mainMenuGUI;
    private ArrayList<JButton> songButtons = new ArrayList<>();
    private JFrame frame;

    // MODIFIES: this
    // EFFECTS: consturct the music library with default songs
    public MusicLibraryGUI(MainMenuGUI mainMenuGUI) {
        this.mainMenuGUI = mainMenuGUI;
        musicLibrary = new SongList();
        musicLibrary.addSong(s1);
        musicLibrary.addSong(s2);
        musicLibrary.addSong(s3);
        musicLibrary.addSong(s4);
        musicLibrary.addSong(s5);
    }

    // EFFECTS: a helper method that will generate the layout
    private void layout(JFrame frame, int row, int col) {
        frame.setLayout(new GridLayout(row, col));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
    }

    // EFFECTS: generating JButton for song and return the JButton
    private JButton generateJButtonForSong(Song mySong) {
        JButton button = new JButton(displaySong(mySong));
        songButtons.add(button);
        return button;
    }

    // EFFECTS: return a string representation to display the song  
    private String displaySong(Song mySong) {
        return "Title: " + mySong.getTitle() + 
                "   Author: " + mySong.getAuthor() + 
                "   Genre: " + mySong.getGenre() +
                "   Duration: " + mySong.getDuration();
    }

    // MODIFIES: this
    // EFFECTS: print the information of the song toghet
    private void printSongInfo(Song mySong, String imagePath) {
        JPanel panel = new JPanel();
        JLabel songLabel = new JLabel(displaySong(mySong));
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        panel.setLayout(new GridLayout(1,2));
        panel.add(songLabel);
        panel.add(imageLabel);
        frame.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: invoke the music library page
    public void musicLibrary() {
        frame = new JFrame("Music Library");
        printSongInfo(s1, "data/Maroon_5_Payphone_cover.png");
        printSongInfo(s2, "data/Cover_Everybody Hurts.png");
        printSongInfo(s3, "data/Cover_Innocence.jpeg");
        printSongInfo(s4, "data/Cover_Whataya Want from Me.jpeg");
        printSongInfo(s5, "data/Cover_Like I Do.jpg");
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
    // EFFECTS: add action listener for each JButton object and will invoke the corresponding method to perform actions. 
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
        for (Song s : musicLibrary.getSongs()) {
            frame.add(generateJButtonForSong(s));
        }
        previousPage = new JButton("Return to the previous page");
        frame.add(previousPage);
        layout(frame, musicLibrary.getSongs().size()+1, 1);
        addActionListenersForAdd();
    }

    // MODIFIES: this
    // EFFECTS: add action listner for each JButton object of adding.
    private void addActionListenersForAdd() {
        for (JButton button : songButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
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
}

