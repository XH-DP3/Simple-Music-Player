package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents the history of completed rhythm game sessions.
public class RhythmGameHistory implements Writable {
    private List<RhythmGameResult> results;

    // EFFECTS: constructs an empty rhythm game history.
    public RhythmGameHistory() {
        results = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a session result to history.
    public void addResult(RhythmGameResult result) {
        if (result != null) {
            results.add(result);
        }
    }

    // MODIFIES: this
    // EFFECTS: appends all results from another history.
    public void merge(RhythmGameHistory other) {
        if (other == null) {
            return;
        }
        for (RhythmGameResult result : other.results) {
            addResult(result);
        }
    }

    // MODIFIES: this
    // EFFECTS: clears all saved results.
    public void reset() {
        results = new ArrayList<>();
    }

    // EFFECTS: returns number of saved sessions.
    public int getSize() {
        return results.size();
    }

    // EFFECTS: returns all results.
    public List<RhythmGameResult> getResults() {
        return results;
    }

    // EFFECTS: returns the highest score in saved history.
    public int getHighestScore() {
        int highest = 0;
        for (RhythmGameResult result : results) {
            if (result.getScore() > highest) {
                highest = result.getScore();
            }
        }
        return highest;
    }

    // EFFECTS: returns history as JSON object.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Rhythm game history", resultsToJson());
        return json;
    }

    // EFFECTS: returns results in this history as JSON array.
    private JSONArray resultsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (RhythmGameResult result : results) {
            jsonArray.put(result.toJson());
        }
        return jsonArray;
    }
}
