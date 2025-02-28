package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Song;
import model.SongList;

// Represents a reader that reads song list from JSON data stored in file
// The methods are referenced from the below link, with several adjustments according to my own project
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads song list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SongList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSongList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses song list from JSON object and returns it
    private SongList parseSongList(JSONObject jsonObject) {
        SongList mySongList = new SongList();
        addSongs(mySongList, jsonObject);
        return mySongList;
    }

    // MODIFIES: mySongList
    // EFFECTS: parses songs from JSON object and adds them to song list
    private void addSongs(SongList mySongList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Your song list: ");
        for (Object json : jsonArray) {
            JSONObject nextSong = (JSONObject) json;
            addSong(mySongList, nextSong);
        }
    }

    // MODIFIES: mySongList
    // EFFECTS: parses songs from JSON object and adds them to song list
    private void addSong(SongList mySongList, JSONObject jsonObject) {
        String title = jsonObject.getString("Title: ");
        String author = jsonObject.getString("Author: ");
        String genre = jsonObject.getString("Genre: ");
        int duration = jsonObject.getInt("Duration: ");
        Song mySong = new Song(title, author, genre, duration);
        mySongList.addSong(mySong);
    }
}
