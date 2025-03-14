package model;

import java.io.*;
import java.util.*;

import org.json.JSONObject;

import persistence.Writable;

// Represents a song track with a title, author name, genre, duration, lyrics, playing status, and favorite status.
public class Song implements Writable {
    private String title;
    private String author;
    private String genre;
    private int duration;
    private List<String> lyrics;
    private boolean isPlaying;
    private boolean isFavorite;
    private int record;
    private int totalPoints;
    private int playingTimes;
    private boolean finish;
    private String melodyFilePath;
    private String imageFilePath;

    // REQUIRES: duration > 0
    // MODIFIES: this
    // EFFECTS: Construct a song track with the given title, author name, genre,
    // with a empty lyrics list.
    public Song(String title, String author, String genre, int duration) {
        this.title = title;
        this.author = author;
        setGenre(genre);
        setDuration(duration);
        lyrics = new ArrayList<>();
    }

    // EFFECTS: return the title of this song
    public String getTitle() {
        return title;
    }

    // EFFECTS: return the author of this song
    public String getAuthor() {
        return author;
    }

    // EFFECTS: reuturn the genre of this song
    public String getGenre() {
        return genre;
    }

    // MODIFIES: this
    // EFFECTS: update the genre of this song
    public void setGenre(String genre) {
        this.genre = genre;
    }

    // EFFECTS: return the duration of this song.
    public int getDuration() {
        return duration;
    }

    // REQUIRES: duration > 0
    // MODIFIES: this
    // EFFECTS: setting the duration of the song
    public void setDuration(int duration) {
        if (duration > 0) {
            this.duration = duration;
        }
    }

    // MODIFIES: this
    // EFFECTS: reading the lyrics of this song. If the lyrics have been
    // successfully read, return true. Otherwise, return false.
    public void readLyrics(File lyricsFile) throws FileNotFoundException, IOException {
        if (!lyricsFile.exists()) {
            throw new FileNotFoundException();
        }
        BufferedReader in = new BufferedReader(new FileReader(lyricsFile));
        String s;
        while ((s = in.readLine()) != null) {
            lyrics.add(s);
        }
        in.close();
    }

    // EFFECTS: return the lyrics as a list. If the list is empty, return null.
    public List<String> getLyrics() {
        return lyrics;
    }

    // EFFECTS: play this song
    public void playSong() {
        playingTimes++;
        isPlaying = true;
    }

    // EFFECTS: pause this song
    public void pauseSong() {
        isPlaying = false;
    }

    // EFFECTS; if the song is playing, return true. Otherwise, return false.
    public boolean getPlayingStatus() {
        return isPlaying;
    }

    // EFFECTS: marked this song as favorite.
    public void markedAsFavorite() {
        isFavorite = true;
    }

    // EFFECTS: return if my song is marked as favorite
    public boolean isFavorite() {
        return isFavorite;
    }

    // EFFECTS: return the points that the user received of this song
    public int getTotalPoints() {
        return totalPoints;
    }

    // EFFECTS: update the the total points that the user received
    public void updateTotalPoints(int points) {
        int i = getTotalPoints();
        if ((i += points) <= 0) {
            totalPoints = 0;
        } else {
            totalPoints += points;
        }
    }

    // EFFECTS: reset total points
    public void resetTotalPoints() {
        totalPoints = 0;
    }

    // EFFECTS: return user's record (i.e., the highest point that the user used to
    // get)
    public int getRecord() {
        return record;
    }

    // EFFECTS: updates the user's record.
    public void updateRecord(int record) {
        this.record = record;
    }

    // EFFECTS: return true if the song is finished (totalPoints >= 1000)
    public boolean isFinished() {
        return finish;
    }

    // MODIFIES: this
    // EFFECTS: set finish status
    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    // EFFECTS: return the playing times of this song
    public int getPlayingTimes() {
        return playingTimes;
    }

    // EFFECTS: updating the playing times of the song
    public void updatePlayingTimes(int playingTimes) {
        this.playingTimes = playingTimes;
    }

    // EFFECTS: set the melody filepath of this song
    public void setMelodyFilePath(String filePath) {
        melodyFilePath = filePath;
    }

    // EFFECTS: return the melodyFilePath
    public String getMelodyFilePath() {
        return melodyFilePath;
    }

    // EFFECTS: set the image filepath of this song
    public void setImageFilePath(String filePath) {
        imageFilePath = filePath;
    }

    // EFFECTS: return the imageFilePath
    public String getImageFilePath() {
        return imageFilePath;
    }

    // EFFECTS: return this song as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Title: ", getTitle());
        json.put("Author: ", getAuthor());
        json.put("Genre: ", getGenre());
        json.put("Duration: ", getDuration());
        json.put("Record: ", getRecord());
        json.put("Playing times: ", getPlayingTimes());
        return json;
    }

    // EFFECTS: return true if o has the same title as this
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Song s = (Song)o;
        return s.getTitle().equals(this.getTitle());
    }

    // EFFECTS: used to comparae if the title is true, return the hashcode of title
    @Override
    public int hashCode() {
        return title.hashCode();
    }
}