package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import model.EventLog;
import model.SongList;

// Represents the main menu with options that the user can select
public class MainMenuGUI extends JFrame {
    private static final String SONG_LIST_PATH = "data/mySongList.json";
    private static final Color PAGE_BG = new Color(236, 241, 248);
    private static final Color CARD_BG = new Color(255, 255, 255);
    private static final Color ACCENT = new Color(30, 104, 176);
    private static final Color BUTTON_TEXT = new Color(24, 34, 49);
    private static final Color SUBTLE_TEXT = new Color(97, 108, 124);
    private JFrame frame;
    private SongListGUI songListGUI;
    private MusicLibraryGUI musicLibraryGUI;
    private PersistentGUI persistentGUI;
    private Map<String, JButton> buttons;

    // MODIFIES: this
    // EFFECTS: contruct the main menu panel by invoking it
    public MainMenuGUI() {
        setGlobalTheme();
        songListGUI = new SongListGUI(this);
        musicLibraryGUI = new MusicLibraryGUI(this, songListGUI);
        persistentGUI = new PersistentGUI(songListGUI);
        buttons = new HashMap<>();
        mainMenu();
    }

    // MODIFIES: this
    // EFFECTS: a helper method that will generate the layout
    private void layout(JFrame frame, int row, int col) {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        if (frame.getWidth() == 0 || frame.getHeight() == 0) {
            frame.setSize(500, 200);
        }
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set global UI theme for all pages
    private void setGlobalTheme() {
        Font defaultFont = new Font("Dialog", Font.PLAIN, 15);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", new Font("Dialog", Font.BOLD, 14));
        UIManager.put("Label.foreground", new Color(45, 45, 45));
        UIManager.put("Button.background", new Color(242, 244, 255));
        UIManager.put("Button.foreground", BUTTON_TEXT);
        UIManager.put("Panel.background", PAGE_BG);
    }

    // MODIFIES: button
    // EFFECTS: style button with icon and consistent colors
    private void stylePrimaryButton(JButton button, Icon icon) {
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setFocusPainted(false);
        button.setBackground(ACCENT);
        button.setForeground(BUTTON_TEXT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIcon(icon);
        button.setIconTextGap(10);
        button.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
    }

    // EFFECTS: return a styled card panel
    private JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 219, 232)),
                BorderFactory.createEmptyBorder(20, 22, 20, 22)));
        return card;
    }

    // EFFECTS: add an action listerner for menu button and return to the main menu
    private void addMenu() {
        buttons.get("menu").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                mainMenu();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: display the main menu panel
    public void mainMenu() {
        frame = new JFrame("Main Menu");
        createButtons();
        layout(frame, 4, 1);
        addListsActionListeners();
        addPersistentActionListeners();
    }

    // MODIFIES: this
    // EFFECTS: create the buttons for the main menu panel
    private void createButtons() {
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(PAGE_BG);
        JButton musicLibrary = new JButton("Check music library");
        JButton songList = new JButton("Check your song list");
        JButton reload = new JButton("Reload your saved lists");
        JButton quit = new JButton("Quit the program");
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        content.setBackground(PAGE_BG);
        JPanel card = createCardPanel();
        JLabel heading = new JLabel("Simple Music Player", SwingConstants.CENTER);
        heading.setFont(new Font("Dialog", Font.BOLD, 28));
        heading.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        Icon musicLibraryIcon = UIManager.getIcon("FileView.directoryIcon");
        Icon songListIcon = UIManager.getIcon("FileView.fileIcon");
        Icon reloadIcon = UIManager.getIcon("FileChooser.upFolderIcon");
        Icon quitIcon = UIManager.getIcon("InternalFrame.closeIcon");
        stylePrimaryButton(musicLibrary, musicLibraryIcon);
        stylePrimaryButton(songList, songListIcon);
        stylePrimaryButton(reload, reloadIcon);
        stylePrimaryButton(quit, quitIcon);
        card.add(heading);
        card.add(Box.createVerticalStrut(18));
        card.add(musicLibrary);
        card.add(Box.createVerticalStrut(10));
        card.add(songList);
        card.add(Box.createVerticalStrut(10));
        card.add(reload);
        card.add(Box.createVerticalStrut(10));
        card.add(quit);
        buttons.put("musicLibrary", musicLibrary);
        buttons.put("songList", songList);
        buttons.put("reload", reload);
        buttons.put("quit", quit);
        content.add(card, BorderLayout.CENTER);
        frame.add(content, BorderLayout.CENTER);
        frame.setSize(640, 460);
    }

    // MODIFIES: this
    // EFFECTS: add action listners for the lists-related operations
    private void addListsActionListeners() {
        buttons.get("musicLibrary").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                musicLibraryGUI.musicLibrary();
            }
        });
        buttons.get("songList").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                songListGUI.songList();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adding action listeners for persistent-related operations
    private void addPersistentActionListeners() {
        buttons.get("quit").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                quitHelper();
            }
        });
        buttons.get("reload").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                reloadHelper();
            }
        });
    }

    // EFFECTS: provide options to save the progress when the user click on the quit
    // button.
    private void quitHelper() {
        int choice = JOptionPane.showConfirmDialog(null,
                "Would you like to save your progress?", "Quit Options", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION) {
            ConsolePrinter printer = new ConsolePrinter();
            printer.printLog(EventLog.getInstance());
            System.exit(1);
        } else {
            saveHelper();
        }
    }

    // MODIFIES: this
    // EFFECTS: display the save page and provide options of objects which can be
    // saved
    private void saveHelper() {
        frame = new JFrame("Save Helper");
        JButton saveSongList = new JButton("Save your song list");
        JButton menu = new JButton("Return to the menu");
        buttons.put("saveSongList", saveSongList);
        buttons.put("menu", menu);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(PAGE_BG);
        JPanel content = createCardPanel();
        stylePrimaryButton(saveSongList, UIManager.getIcon("FileView.floppyDriveIcon"));
        stylePrimaryButton(menu, UIManager.getIcon("FileChooser.homeFolderIcon"));
        content.add(saveSongList);
        content.add(Box.createVerticalStrut(10));
        content.add(menu);
        frame.add(content, BorderLayout.CENTER);
        layout(frame, 2, 1);
        addSaveActionListeners();
        addMenu();
    }

    // EFFECTS: add action listeners for saving and invoke writeToFile() method to
    // perform saving
    private void addSaveActionListeners() {
        buttons.get("saveSongList").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (songListGUI.getSongList().getSize() > 0) {
                    persistentGUI.writeToFile(SONG_LIST_PATH, songListGUI.getSongList());
                    JOptionPane.showMessageDialog(null, "Your song list is saved", "Message",
                            JOptionPane.INFORMATION_MESSAGE);
                    ConsolePrinter printer = new ConsolePrinter();
                    printer.printLog(EventLog.getInstance());
                    System.exit(1);
                } else {
                    JOptionPane.showMessageDialog(null, "Your song list is empty", "Message",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    mainMenu();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: display the reload page and provide options of objects which can be
    // reloaded
    private void reloadHelper() {
        frame = new JFrame("Reload Helper");
        JButton reloadSongList = new JButton("Reload your saved song list");
        JButton menu = new JButton("Return to the menu");
        buttons.put("reloadSongList", reloadSongList);
        buttons.put("menu", menu);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(PAGE_BG);
        JPanel content = createCardPanel();
        stylePrimaryButton(reloadSongList, UIManager.getIcon("FileChooser.upFolderIcon"));
        stylePrimaryButton(menu, UIManager.getIcon("FileChooser.homeFolderIcon"));
        content.add(reloadSongList);
        content.add(Box.createVerticalStrut(10));
        content.add(menu);
        frame.add(content, BorderLayout.CENTER);
        layout(frame, 2, 1);
        addReloadActionListeners();
        addMenu();
    }

    // MODIFIES: this
    // EFFECTS: add action listeners for reload
    private void addReloadActionListeners() {
        buttons.get("reloadSongList").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SongList mySongList = persistentGUI.reloadFromFile(SONG_LIST_PATH);
                persistentGUI.askMerge(mySongList);
                JOptionPane.showMessageDialog(null, "Reload is done", "Message", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                mainMenu();
            }
        });
    }
}
