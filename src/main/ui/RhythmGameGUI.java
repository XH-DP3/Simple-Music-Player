package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import model.RhythmGameHistory;
import model.Song;
import model.SongList;

// Represents the rhythm game page and handles track selection + controls.
public class RhythmGameGUI {
    private static final Color PAGE_BG = new Color(236, 241, 248);
    private static final Color ACCENT = new Color(30, 104, 176);
    private static final Color BUTTON_TEXT = new Color(24, 34, 49);
    private static final Color SUBTLE_TEXT = new Color(97, 108, 124);
    private static final Color STATUS_OK = new Color(35, 120, 70);
    private static final Color STATUS_ERROR = new Color(168, 52, 52);

    private final MainMenuGUI mainMenuGUI;
    private final MusicLibraryGUI musicLibraryGUI;
    private final RhythmGameHistory history;
    private final Map<String, Song> songsByTitle;
    private JFrame frame;
    private RhythmGamePanel rhythmPanel;
    private JButton startButton;
    private JButton restartButton;
    private JButton pauseButton;
    private JButton menuButton;
    private JComboBox<String> songSelector;
    private JComboBox<String> difficultySelector;
    private JLabel statusLabel;

    // EFFECTS: constructs a rhythm game GUI with access to menu and music library.
    public RhythmGameGUI(MainMenuGUI mainMenuGUI, MusicLibraryGUI musicLibraryGUI, RhythmGameHistory history) {
        this.mainMenuGUI = mainMenuGUI;
        this.musicLibraryGUI = musicLibraryGUI;
        this.history = history;
        songsByTitle = new LinkedHashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: opens rhythm game page.
    public void rhythmGame() {
        frame = new JFrame("Rhythm Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(PAGE_BG);
        rhythmPanel = new RhythmGamePanel(history);
        refreshSongOptions();
        frame.add(buildHeader(), BorderLayout.NORTH);
        frame.add(rhythmPanel, BorderLayout.CENTER);
        frame.add(buildControls(), BorderLayout.SOUTH);
        frame.setSize(980, 760);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        loadSelectedSong(false);
        rhythmPanel.requestFocusInWindow();
    }

    // MODIFIES: this
    // EFFECTS: refreshes song selector options from music library.
    private void refreshSongOptions() {
        songsByTitle.clear();
        SongList list = musicLibraryGUI.getMusicLibrary();
        if (list != null) {
            for (Song song : list.getSongs()) {
                songsByTitle.put(song.getTitle(), song);
            }
        }
        String[] titles = songsByTitle.keySet().toArray(new String[0]);
        songSelector = new JComboBox<>(titles);
        songSelector.setPreferredSize(new java.awt.Dimension(260, 30));
        songSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSelectedSong(false);
            }
        });
        String[] difficulties = new String[] { "Easy", "Normal", "Hard" };
        difficultySelector = new JComboBox<>(difficulties);
        difficultySelector.setSelectedItem("Normal");
        difficultySelector.setPreferredSize(new java.awt.Dimension(130, 30));
        difficultySelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applySelectedDifficulty();
                loadSelectedSong(false);
            }
        });
    }

    // EFFECTS: builds top header with title, selector, and instructions.
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PAGE_BG);
        header.setBorder(BorderFactory.createEmptyBorder(14, 16, 10, 16));
        JLabel title = new JLabel("Rhythm Game");
        title.setFont(new Font("Dialog", Font.BOLD, 28));
        title.setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
        title.setIconTextGap(10);
        JLabel subtitle = new JLabel("Hit keys at the blue line. Press SPACE to pause/resume.");
        subtitle.setForeground(SUBTLE_TEXT);
        JPanel chooserRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        chooserRow.setBackground(PAGE_BG);
        JLabel songLabel = new JLabel("Song:");
        chooserRow.add(songLabel);
        chooserRow.add(songSelector);
        JLabel difficultyLabel = new JLabel("Difficulty:");
        chooserRow.add(difficultyLabel);
        chooserRow.add(difficultySelector);
        JPanel textArea = new JPanel();
        textArea.setLayout(new javax.swing.BoxLayout(textArea, javax.swing.BoxLayout.Y_AXIS));
        textArea.setBackground(PAGE_BG);
        textArea.add(title);
        textArea.add(subtitle);
        textArea.add(javax.swing.Box.createVerticalStrut(8));
        textArea.add(chooserRow);
        header.add(textArea, BorderLayout.CENTER);
        return header;
    }

    // EFFECTS: builds start/restart/menu controls with status line.
    private JPanel buildControls() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(PAGE_BG);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        controls.setBackground(PAGE_BG);
        startButton = new JButton("Start");
        restartButton = new JButton("Restart");
        pauseButton = new JButton("Pause");
        menuButton = new JButton("Return to menu");
        styleControlButton(startButton, UIManager.getIcon("FileChooser.newFolderIcon"));
        styleControlButton(restartButton, UIManager.getIcon("FileChooser.upFolderIcon"));
        styleControlButton(pauseButton, UIManager.getIcon("OptionPane.warningIcon"));
        styleControlButton(menuButton, UIManager.getIcon("FileChooser.homeFolderIcon"));
        controls.add(startButton);
        controls.add(restartButton);
        controls.add(pauseButton);
        controls.add(menuButton);
        statusLabel = new JLabel(" ");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 8, 16));
        statusLabel.setForeground(SUBTLE_TEXT);
        root.add(controls, BorderLayout.NORTH);
        root.add(statusLabel, BorderLayout.SOUTH);
        addControlActionListeners();
        return root;
    }

    // MODIFIES: button
    // EFFECTS: applies consistent style for rhythm controls.
    private void styleControlButton(JButton button, Icon icon) {
        button.setFocusPainted(false);
        button.setBackground(ACCENT);
        button.setForeground(BUTTON_TEXT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIcon(icon);
        button.setIconTextGap(10);
        button.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
    }

    // MODIFIES: this
    // EFFECTS: wires actions for start/restart/menu buttons.
    private void addControlActionListeners() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applySelectedDifficulty();
                if (loadSelectedSong(false) && rhythmPanel.startGame()) {
                    pauseButton.setText("Pause");
                    setStatus(rhythmPanel.getStatusMessage(), STATUS_OK);
                    rhythmPanel.requestFocusInWindow();
                } else {
                    setStatus(rhythmPanel.getStatusMessage(), STATUS_ERROR);
                }
            }
        });
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applySelectedDifficulty();
                if (loadSelectedSong(false) && rhythmPanel.restartGame()) {
                    pauseButton.setText("Pause");
                    setStatus(rhythmPanel.getStatusMessage(), STATUS_OK);
                    rhythmPanel.requestFocusInWindow();
                } else {
                    setStatus(rhythmPanel.getStatusMessage(), STATUS_ERROR);
                }
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rhythmPanel.isPaused()) {
                    if (rhythmPanel.resumeGame()) {
                        pauseButton.setText("Pause");
                        setStatus(rhythmPanel.getStatusMessage(), STATUS_OK);
                    }
                } else {
                    if (rhythmPanel.pauseGame()) {
                        pauseButton.setText("Resume");
                        setStatus(rhythmPanel.getStatusMessage(), STATUS_OK);
                    }
                }
                rhythmPanel.requestFocusInWindow();
            }
        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rhythmPanel.shutdown();
                frame.dispose();
                mainMenuGUI.mainMenu();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: loads currently selected song into panel.
    private boolean loadSelectedSong(boolean silent) {
        Song song = getSelectedSong();
        if (song == null) {
            setStatus("No songs found in the music library.", STATUS_ERROR);
            return false;
        }
        applySelectedDifficulty();
        Song loaded = rhythmPanel.getLoadedSong();
        String selectedDifficulty = getSelectedDifficulty();
        String loadedDifficulty = rhythmPanel.getLoadedDifficultyName();
        if (loaded != null && loaded.equals(song) && selectedDifficulty.equals(loadedDifficulty)) {
            if (!silent) {
                setStatus("Loaded: " + song.getTitle() + " (" + selectedDifficulty + ")", STATUS_OK);
            }
            return true;
        }
        boolean ok = rhythmPanel.loadSong(song);
        if (ok) {
            pauseButton.setText("Pause");
            if (!silent) {
                setStatus(rhythmPanel.getStatusMessage() + " (" + selectedDifficulty + ")", STATUS_OK);
            }
        } else {
            setStatus(rhythmPanel.getStatusMessage(), STATUS_ERROR);
        }
        return ok;
    }

    // EFFECTS: returns currently selected song from selector.
    private Song getSelectedSong() {
        if (songSelector == null || songSelector.getSelectedItem() == null) {
            return null;
        }
        String title = songSelector.getSelectedItem().toString();
        return songsByTitle.get(title);
    }

    // EFFECTS: returns selected difficulty label.
    private String getSelectedDifficulty() {
        if (difficultySelector == null || difficultySelector.getSelectedItem() == null) {
            return "Normal";
        }
        return difficultySelector.getSelectedItem().toString();
    }

    // MODIFIES: this
    // EFFECTS: applies selected difficulty to rhythm panel.
    private void applySelectedDifficulty() {
        if (rhythmPanel != null) {
            rhythmPanel.setDifficulty(getSelectedDifficulty());
        }
    }

    // MODIFIES: this
    // EFFECTS: updates status message color and text.
    private void setStatus(String text, Color color) {
        statusLabel.setText(text);
        statusLabel.setForeground(color);
    }
}
