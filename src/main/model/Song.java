package model;

import java.io.File;
import java.util.List;

// Represents a song track with a title, author name, genre, duration, lyrics.
public class Song {

    // EFFECTS: Construct a song track with the given title, author name, genre, length.
    public Song(String title, String author, String genre, int duration) {
        // stub
    }

    // EFFECTS: return the title of this song
    public String getTitle() {
        // stub
        return "";
    }

    // EFFECTS: return the author of this song
    public String getAuthor() {
        // stub
        return "";
    }

    // EFFECTS: reuturn the genre of this song
    public String getGenre() {
        // stub
        return "";
    }

    // MODIFIES: this
    // EFFECTS: update the genre of this song
    public void setGenre(String genre) {
        // stub
    }

    // EFFECTS: return the duration of this song.
    public int getDuration() {
        // stub
        return 0;
    }

    // REQUIRES: duration > 0
    // MODIFIES: this
    // EFFECTS: setting the duration of the song
    public void setDuration(int duration) {
        //stub
    }

    // EFFECTS: returning the summmary information about this song
    public String getInfo() {
        // stub
        return "";
    }

    // REQUIRES: lyricsFile exists
    // MODIFIES: this
    // EFFECTS: reading the lyrics of this song. If the lyrics have been successfully read, return true. Otherwise, return false.
    public boolean readLyrics(File lyricsFile) {
        // stub
        return false;
    }

    // EFFECTS: return the lyrics as a list. If the list is empty, return null.
    public List<String> getLyrics() {
        // stub
        return null;
    }

    // EFFECTS: play this song
    public void playSong() {
        // stub
    }

    // EFFECTS: pause this song
    public void pauseSong() {
        // stub
    }

    // EFFECTS: marked this song as favorite. 
    public void markedAsFavorite() {
        // stub
    }
}
