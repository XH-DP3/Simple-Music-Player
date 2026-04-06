package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.WindowConstants;

import model.Song;
import model.SongList;

// Represent the user's song list
public class SongListGUI {
    private static final Color PAGE_BG = new Color(236, 241, 248);
    private static final Color CARD_BG = new Color(255, 255, 255);
    private static final Color ACCENT = new Color(30, 104, 176);
    private static final Color BUTTON_TEXT = new Color(24, 34, 49);
    private static final Color SUBTLE_TEXT = new Color(97, 108, 124);
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
        musicPlayerGUI = new MusicPlayerGUI(mainMenuGUI);
    }

    // MODIFIES: this
    // EFFECTS: a helper method that will generate the layout
    private void layout(JFrame frame, int row, int col, int width, int height) {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: button
    // EFFECTS: style action button with icon and color
    private void styleActionButton(JButton button, Icon icon) {
        button.setFocusPainted(false);
        button.setBackground(ACCENT);
        button.setForeground(BUTTON_TEXT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIcon(icon);
        button.setIconTextGap(10);
        button.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
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
        JPanel panel = new JPanel(new BorderLayout(16, 8));
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));
        JLabel songLabel = new JLabel(displaySong(mySong));
        ImageIcon originalIcon = new ImageIcon(mySong.getImageFilePath());
        Image resizedImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        panel.add(songLabel, BorderLayout.CENTER);
        panel.add(imageLabel, BorderLayout.EAST);
        frame.add(panel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: create the buttons for the song list panel
    private void createButtons(JPanel content) {
        JButton deletePage = new JButton("Delete song from your song list");
        JButton play = new JButton("Play song in your song list");
        JButton sort = new JButton("Sort your song list");
        styleActionButton(deletePage, UIManager.getIcon("OptionPane.warningIcon"));
        styleActionButton(play, UIManager.getIcon("OptionPane.informationIcon"));
        styleActionButton(sort, UIManager.getIcon("FileChooser.detailsViewIcon"));
        content.add(deletePage);
        content.add(play);
        content.add(sort);
        buttons.put("deletePage", deletePage);
        buttons.put("play", play);
        buttons.put("sort", sort);
    }

    // MODIFIES: this
    // EFFECTS: adding mySong from the music library to the song list
    public boolean addSongToSongList(Song mySong) {
        return mySongList.addSong(mySong);
    }

    // EFFECTS: return the songlist
    public SongList getSongList() {
        return mySongList;
    }

    // MODIFIES: this
    // EFFECTS: invoke the song list page
    public void songList() {
        frame = new JFrame("Your Song List");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(PAGE_BG);
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PAGE_BG);
        header.setBorder(new EmptyBorder(14, 16, 10, 16));
        JLabel title = new JLabel("Your Song List");
        title.setFont(new Font("Dialog", Font.BOLD, 28));
        title.setIcon(UIManager.getIcon("FileView.fileIcon"));
        title.setIconTextGap(10);
        JLabel subtitle = new JLabel("Play, sort, and manage your selected songs");
        subtitle.setForeground(SUBTLE_TEXT);
        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        content.setBackground(PAGE_BG);
        if (mySongList.getSize() == 0) {
            label = new JLabel("Your song list is empty");
            label.setFont(new Font("Dialog", Font.BOLD, 16));
            label.setForeground(SUBTLE_TEXT);
            content.add(label);
        } else {
            for (Song s : mySongList.getSongs()) {
                JPanel panel = new JPanel(new BorderLayout(16, 8));
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(209, 219, 232)),
                        new EmptyBorder(10, 12, 10, 12)));
                panel.setBackground(CARD_BG);
                JLabel songLabel = new JLabel(displaySong(s));
                songLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
                ImageIcon originalIcon = new ImageIcon(s.getImageFilePath());
                Image resizedImage = originalIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                JLabel imageLabel = new JLabel(resizedIcon);
                panel.add(songLabel, BorderLayout.CENTER);
                panel.add(imageLabel, BorderLayout.EAST);
                panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
                content.add(panel);
                content.add(Box.createVerticalStrut(8));
            }
        }
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        controls.setBackground(PAGE_BG);
        if (mySongList.getSize() > 0) {
            createButtons(controls);
            addSongListActionListeners();
            addPlay();
        }
        JButton menu = new JButton("Return to the menu");
        styleActionButton(menu, UIManager.getIcon("FileChooser.homeFolderIcon"));
        controls.add(menu);
        buttons.put("menu", menu);
        frame.getContentPane().removeAll();
        frame.add(header, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);
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
            return;
        }
        frame = new JFrame("Songs in your song list");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(PAGE_BG);
        label = new JLabel("Please select the songs you want to delete");
        label.setBorder(new EmptyBorder(10, 10, 10, 10));
        label.setFont(new Font("Dialog", Font.BOLD, 16));
        label.setForeground(SUBTLE_TEXT);
        mapSongs = new HashMap<>();
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(PAGE_BG);
        content.add(label);
        for (Song s : mySongList.getSongs()) {
            generateCheckBox(content, s);
        }
        JButton delete = new JButton("Delete");
        JButton previous = new JButton("Return to the previous page");
        JButton menu = new JButton("Return to the main menu");
        buttons.put("delete", delete);
        buttons.put("previous", previous);
        buttons.put("menu", menu);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        controls.setBackground(PAGE_BG);
        styleActionButton(delete, UIManager.getIcon("OptionPane.errorIcon"));
        styleActionButton(previous, UIManager.getIcon("FileChooser.upFolderIcon"));
        styleActionButton(menu, UIManager.getIcon("FileChooser.homeFolderIcon"));
        controls.add(delete);
        controls.add(previous);
        controls.add(menu);
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);
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
    private void generateCheckBox(JPanel content, Song mySong) {
        JCheckBox box = new JCheckBox(displaySong(mySong));
        box.setBackground(CARD_BG);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 219, 232)),
                new EmptyBorder(8, 10, 8, 10)));
        panel.add(box);
        panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        content.add(panel);
        content.add(Box.createVerticalStrut(8));
        mapSongs.put(box, mySong);
    }

    // MODIFIES: this
    // EFFECTS: delete the selected song from songlist
    private void delete() {
        boolean hasSelectedSong = false;
        Set<JCheckBox> boxes = mapSongs.keySet();
        for (JCheckBox b : boxes) {
            if (b.isSelected()) {
                Song s = mapSongs.get(b);
                mySongList.deleteSong(s.getTitle());
                hasSelectedSong = true;
            }
        }
        if (!hasSelectedSong) {
            JOptionPane.showMessageDialog(frame, "No songs selected.");
        }
        frame.dispose();
        songList();
    }

    // MODIFIES: this
    // EFFECTS: provide options to user of how they like the songs to be sorted
    private void sortHelper() {
        frame = new JFrame("Sorting Helper");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(PAGE_BG);
        JButton fromLowest = new JButton("Sort the songs from the lowest duration");
        JButton fromHighest = new JButton("Sort the songs from the highest duration");
        JButton previous = new JButton("Return to the previous page");
        JButton menu = new JButton("Return to the menu");
        buttons.put("fromLowest", fromLowest);
        buttons.put("fromHighest", fromHighest);
        buttons.put("previous", previous);
        buttons.put("menu", menu);
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(12, 12, 12, 12));
        content.setBackground(PAGE_BG);
        JLabel title = new JLabel("Sorting Options", SwingConstants.CENTER);
        title.setFont(new Font("Dialog", Font.BOLD, 22));
        title.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0, 0, 14, 0));
        styleActionButton(fromLowest, UIManager.getIcon("FileChooser.listViewIcon"));
        styleActionButton(fromHighest, UIManager.getIcon("FileChooser.detailsViewIcon"));
        styleActionButton(previous, UIManager.getIcon("FileChooser.upFolderIcon"));
        styleActionButton(menu, UIManager.getIcon("FileChooser.homeFolderIcon"));
        content.add(title);
        content.add(fromLowest);
        content.add(Box.createVerticalStrut(8));
        content.add(fromHighest);
        content.add(Box.createVerticalStrut(8));
        content.add(previous);
        content.add(Box.createVerticalStrut(8));
        content.add(menu);
        frame.add(content, BorderLayout.CENTER);
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
