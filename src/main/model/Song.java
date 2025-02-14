package model;

import java.io.*;
import java.util.*;

// Represents a song track with a title, author name, genre, duration, lyrics, playing status, and favorite status.
public class Song {
    private String title;
    private String author;
    private String genre;
    private int duration;
    private List<String> lyrics;
    private boolean isPlaying;
    private boolean isFavorite;

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
}