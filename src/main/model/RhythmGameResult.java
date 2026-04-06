package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents one completed rhythm game session.
public class RhythmGameResult implements Writable {
    private String timestamp;
    private String songTitle;
    private String difficulty;
    private int score;
    private int bestCombo;
    private int misses;
    private int perfectCount;
    private int goodCount;
    private int okCount;

    // EFFECTS: constructs a rhythm game result with all summary data.
    public RhythmGameResult(String timestamp, String songTitle, String difficulty, int score, int bestCombo, int misses,
            int perfectCount, int goodCount, int okCount) {
        this.timestamp = timestamp;
        this.songTitle = songTitle;
        this.difficulty = difficulty;
        this.score = score;
        this.bestCombo = bestCombo;
        this.misses = misses;
        this.perfectCount = perfectCount;
        this.goodCount = goodCount;
        this.okCount = okCount;
    }

    // EFFECTS: returns session score.
    public int getScore() {
        return score;
    }

    // EFFECTS: returns this result as JSON object.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Timestamp", timestamp);
        json.put("Song", songTitle);
        json.put("Difficulty", difficulty);
        json.put("Score", score);
        json.put("Best Combo", bestCombo);
        json.put("Misses", misses);
        json.put("Perfect", perfectCount);
        json.put("Good", goodCount);
        json.put("Ok", okCount);
        return json;
    }
}
