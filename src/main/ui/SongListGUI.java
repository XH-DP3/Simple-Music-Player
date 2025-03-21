package ui;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.Song;
import model.SongList;

// Represent the user's song list
public class SongListGUI {
    private SongList mySongList;
    private MainMenuGUI mainMenuGUI;
    private MusicPlayerGUI musicPlayerGUI;
    private JFrame frame;
    private JLabel label;
    private Map<JCheckBox, Song> mapSongs;
    private Map<String, JButton> buttons;

    // MODIFIES: this
    // EFFECTS: construct an empty song list with refernece to the previous page
    // (main menu and music library)
    public SongListGUI(MainMenuGUI mainMenuGUI) {
        mySongList = new SongList();
        buttons = new HashMap<>();
        this.mainMenuGUI = mainMenuGUI;
        musicPlayerGUI = new MusicPlayerGUI(mainMenuGUI, new MusicLibraryGUI(mainMenuGUI, this));
    }

    // MODIFIES: this
    // EFFECTS: a helper method that will generate the layout
    private void layout(JFrame frame, int row, int col, int width, int height) {
        frame.setLayout(new GridLayout(row, col));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: return a string representation to display the song
    private String displaySong(Song mySong) {
        return "Title: " + mySong.getTitle() + ", "
                + "   Author: " + mySong.getAuthor() + ", "
                + "   Duration: " + mySong.getDuration() + ", "
                + "   Is Favorite: " + mySong.isFavorite();
    }

    // MODIFIES: this
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

    // MODIFIES: this
    // EFFECTS: create the buttons for the song list panel
    private void createButtons() {
        JButton deletePage = new JButton("Delete song from your song list");
        JButton play = new JButton("Play song in your song list");
        JButton sort = new JButton("Sort your song list");
        frame.add(deletePage);
        frame.add(play);
        frame.add(sort);
        buttons.put("deletePage", deletePage);
        buttons.put("play", play);
        buttons.put("sort", sort);
    }

    // MODIFIES: this
    // EFFECTS: adding mySong from the music library to the song list
    public void addSongToSongList(Song mySong) {
        mySongList.addSong(mySong);
    }

    // EFFECTS: return the songlist
    public SongList getSongList() {
        return mySongList;
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
            createButtons();
            addSongListActionListeners();
            addPlay();
        }
        JButton menu = new JButton("Return to the menu");
        frame.add(menu);
        buttons.put("menu", menu);
        layout(frame, mySongList.getSize() + 6, 1, 1000, 700);
        addMenu();
    }

    // MODIFIES: this
    // EFFECTS: add action listener for the song list page and will invoke the
    // corresponding method to perform actions.
    private void addSongListActionListeners() {
        buttons.get("deletePage").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                deleteHelper();
            }
        });
        buttons.get("sort").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                sortHelper();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add an action listerner for play
    private void addPlay() {
        buttons.get("play").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                musicPlayerGUI.playHelper(mySongList);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add an action listener for menu button
    private void addMenu() {
        buttons.get("menu").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                mainMenu();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add an action listener for previous button
    private void addPrevious() {
        buttons.get("previous").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                songList();
            }
        });
    }

    // EFFECTS: return to the main menu
    private void mainMenu() {
        mainMenuGUI.mainMenu();
    }

    // MODIFIES: this
    // EFFECTS: display songs in the songlist as buttons and click on
    private void deleteHelper() {
        if (mySongList.getSize() == 0) {
            emptyList();
        }
        frame = new JFrame("Songs in your song list");
        label = new JLabel("Please select the songs you want to delete");
        frame.add(label);
        mapSongs = new HashMap<>();
        for (Song s : mySongList.getSongs()) {
            generateCheckBox(s);
        }
        JButton delete = new JButton("Delete");
        JButton previous = new JButton("Return to the previous page");
        JButton menu = new JButton("Return to the main menu");
        buttons.put("delete", delete);
        buttons.put("previous", previous);
        buttons.put("menu", menu);
        frame.add(delete);
        frame.add(previous);
        frame.add(menu);
        layout(frame, mySongList.getSize() + 4, 1, 1000, 700);
        addDeleteActionListeners();
        addPrevious();
        addMenu();
    }

    // MODIFIES: this
    // EFFECTS: add more action listener for each JButton object and will invoke the
    // corresponding method to perform actions.
    private void addDeleteActionListeners() {
        buttons.get("delete").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                delete();
            }
        });
    }

    // EFFECTS: display a message that the song list is empty.
    private void emptyList() {
        JOptionPane.showMessageDialog(frame, "Your song list has no songs!");
        songList();
    }

    // MODIFIES: this
    // EFFECTS: generating a checkbox for each song in the songlist
    private void generateCheckBox(Song mySong) {
        JCheckBox box = new JCheckBox(displaySong(mySong));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(box);
        frame.add(panel);
        mapSongs.put(box, mySong);
    }

    // MODIFIES: this
    // EFFECTS: delete the selected song from songlist
    private void delete() {
        Set<JCheckBox> boxes = mapSongs.keySet();
        for (JCheckBox b : boxes) {
            if (b.isSelected()) {
                Song s = mapSongs.get(b);
                mySongList.deleteSong(s.getTitle());
            }
            frame.dispose();
            songList();
        }
    }

    // MODIFIES: this
    // EFFECTS: provide options to user of how they like the songs to be sorted
    private void sortHelper() {
        frame = new JFrame("Sorting Helper");
        JButton fromLowest = new JButton("Sort the songs from the lowest duration");
        JButton fromHighest = new JButton("Sort the songs from the highest duration");
        JButton previous = new JButton("Return to the previous page");
        JButton menu = new JButton("Return to the menu");
        buttons.put("fromLowest", fromLowest);
        buttons.put("fromHighest", fromHighest);
        buttons.put("previous", previous);
        buttons.put("menu", menu);
        frame.add(fromLowest);
        frame.add(fromHighest);
        frame.add(previous);
        frame.add(menu);
        layout(frame, 4, 1, 500, 500);
        addSortingActionListeners();
        addMenu();
        addPrevious();
    }

    // MODIFIES: this
    // EFFECTS: invoke the corresponding methods to perform sorting action.
    private void addSortingActionListeners() {
        buttons.get("fromLowest").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mySongList.sortByLowestDuration();
                frame.dispose();
                songList();
            }
        });
        buttons.get("fromHighest").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mySongList.sortByHighestDuration();
                frame.dispose();
                songList();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: if merge is true, merge current song list with list. Otherwise, do
    // nothing
    public void merge(SongList list, boolean merge) {
        if (merge) {
            if (mySongList.getSize() > 0) {
                JOptionPane.showMessageDialog(null, "Your previous list and current list will be merged", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            for (Song s : list.getSongs()) {
                mySongList.addSong(s);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Your current list will be preserved", "Message",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
