package ui;

import java.io.IOException;

import javax.swing.JOptionPane;

import model.RhythmGameHistory;
import model.SongList;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.RhythmHistoryReader;
import persistence.RhythmHistoryWriter;

// Represent a class that deals with saving and reloading the lists
public class PersistentGUI {
    private JsonWriter writer;
    private JsonReader reader;
    private RhythmHistoryWriter rhythmWriter;
    private RhythmHistoryReader rhythmReader;
    private SongListGUI songListGUI;

    // MODIFIES: this
    // EFFECTS: construct a PersistentGUI object with a reference to SongListGUI
    public PersistentGUI(SongListGUI songListGUI) {
        this.songListGUI = songListGUI;
    }

    // MODIFIES: this
    // EFFECTS: write list to filepath
    public void writeToFile(String filePath, SongList list) {
        writer = new JsonWriter(filePath);
        try {
            writer.open();
            writer.write(list);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: reload list from filepath
    public SongList reloadFromFile(String filePath) {
        reader = new JsonReader(filePath);
        try {
            SongList mySongList = reader.read();
            return mySongList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: writes rhythm game history to filepath.
    public void writeRhythmHistoryToFile(String filePath, RhythmGameHistory history) {
        rhythmWriter = new RhythmHistoryWriter(filePath);
        try {
            rhythmWriter.open();
            rhythmWriter.write(history);
            rhythmWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: reloads rhythm game history from filepath.
    public RhythmGameHistory reloadRhythmHistoryFromFile(String filePath) {
        rhythmReader = new RhythmHistoryReader(filePath);
        try {
            RhythmGameHistory history = rhythmReader.read();
            return history;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // EFFECTS: if the current song list is not empty, ask if the user wants to
    // merge the current song list and the previous osng list
    public void askMerge(SongList list) {
        boolean merge = true;
        if (songListGUI.getSongList().getSize() > 0) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "Your current list is not empty. Would you like to merge your previous and current list?",
                    "Reload Options", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                merge = false;
            }
        }
        songListGUI.merge(list, merge);
    }
}
