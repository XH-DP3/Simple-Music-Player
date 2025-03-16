package ui;

import java.io.IOException;

import javax.swing.JOptionPane;

import model.SongList;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represent a class that deals with saving and reloading the lists
public class PersistentGUI {
    private JsonWriter writer;
    private JsonReader reader;
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
