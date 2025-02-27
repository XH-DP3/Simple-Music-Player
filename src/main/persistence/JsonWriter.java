package persistence;

import java.io.*;

import org.json.JSONObject;

import model.SongList;

// Represent a writer that writes JSON representation of songlist to file
// The methods are referenced from the below link, with several adjustments according to my own project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
public class JsonWriter {
    private static final int TAB = 4;
    String destination;
    PrintWriter writer;

    // EFFECTS: construct a writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFES: this
    // EFFECTS: writes JSON representation of songlist to file
    public void write(SongList mySongList) {
        JSONObject json = mySongList.toJSON();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
