package persistence;

import java.io.FileNotFoundException;

import model.SongList;

// Represent a writer that writes JSON representation of songlist to file
// for now, the methods are referenced from the below link, with several adjustments according to my own project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
public class JsonWriter {

    // EFFECTS: construct a writer to write to destination file
    public JsonWriter(String destination) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    public void open() throws FileNotFoundException{
        // stub
    }

    // MODIFES: this
    // EFFECTS: writes JSON representation of songlist to file
    public void write(SongList mySongList) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        // stub
    }

}
