package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import model.RhythmGameHistory;

// Represents a writer that writes rhythm game history JSON to file.
public class RhythmHistoryWriter {
    private static final int TAB = 4;
    private String destination;
    private PrintWriter writer;

    // EFFECTS: constructs writer to write to destination file
    public RhythmHistoryWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if destination can't be opened.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of rhythm history to file.
    public void write(RhythmGameHistory history) {
        JSONObject json = history.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer.
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file.
    private void saveToFile(String json) {
        writer.print(json);
    }
}
