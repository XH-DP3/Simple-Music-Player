package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.RhythmGameHistory;
import model.RhythmGameResult;

// Represents a reader that reads rhythm game history from JSON data in file.
public class RhythmHistoryReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public RhythmHistoryReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads rhythm history from file and returns it;
    // throws IOException if an error occurs reading data from file.
    public RhythmGameHistory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHistory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it.
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses history object and returns it.
    private RhythmGameHistory parseHistory(JSONObject jsonObject) {
        RhythmGameHistory history = new RhythmGameHistory();
        addResults(history, jsonObject);
        return history;
    }

    // MODIFIES: history
    // EFFECTS: parses session results and adds them to history.
    private void addResults(RhythmGameHistory history, JSONObject jsonObject) {
        if (!jsonObject.has("Rhythm game history")) {
            return;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("Rhythm game history");
        for (Object json : jsonArray) {
            JSONObject nextResult = (JSONObject) json;
            addResult(history, nextResult);
        }
    }

    // MODIFIES: history
    // EFFECTS: parses one result JSON and adds it to history.
    private void addResult(RhythmGameHistory history, JSONObject jsonObject) {
        String timestamp = jsonObject.getString("Timestamp");
        String song = jsonObject.getString("Song");
        String difficulty = jsonObject.getString("Difficulty");
        int score = jsonObject.getInt("Score");
        int bestCombo = jsonObject.getInt("Best Combo");
        int misses = jsonObject.getInt("Misses");
        int perfect = jsonObject.getInt("Perfect");
        int good = jsonObject.getInt("Good");
        int ok = jsonObject.getInt("Ok");
        RhythmGameResult result = new RhythmGameResult(timestamp, song, difficulty, score, bestCombo, misses, perfect,
                good, ok);
        history.addResult(result);
    }
}
