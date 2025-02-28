package persistence;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;

import model.FavoriteSongList;
import model.SongList;

// Represents a reader that reads song list from JSON data stored in file
// The methods are referenced from the below link, with several adjustments according to my own project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
public class JsonReader {
    
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        // stub
    }

    // EFFECTS: reads song list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SongList read() throws IOException{
        return null;
        // stub
    }

      // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return "";
        // stub
    }

    // EFFECTS: parses song list from JSON object and returns it
    private SongList parseSongList(JSONObject jsonObject) {
        return null;
        // stub
    }

    // MODIFIES: mySongList
    // EFFECTS: parses songs from JSON object and adds them to song list
    private void addSongs(SongList mySongList, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: myFavoriteSongList
    // EFFECTS: parses songs from JSON object and adds them to song list
    private void addSongs(FavoriteSongList myFavoriteSongList, JSONObject jsonObject) {
        // stub
    }
}
