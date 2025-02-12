package model;

import java.util.List;

public class SongList {

    // EFFECTS: construct an empty list
    public SongList() {
        // stub
    }

    // EFFECTS: return the size of the list
    public int getSize() {
        // stub
        return 0;
    }

    // EFFECTS: reuturn this song list
    public List<String> getSongList() {
        // stub
        return null;
    }

    // EFFECTS: Return the song if found. Otherwise, return null.
    public Song findSongByTitle(String title) {
        // stub
        return null;
    }

    // EFFECTS: Return the song as a list if the author matches. Othewise, return null.
    public List<Song> findSongByAuthor(String author) {
        // stub
        return null;
    }

    // REQUIRES: (index >= 0 && index < list.size()) && (!songList.contains(mySong))
    // MODIFIES: this
    // EFFECT: add mySong at the specific index to the music list and return true
    public boolean addMusic(Song mySong, int index) {
        // stub
        return false;
    }

    // REQUIRES: !songList.contains(mySong)
    // MODIFIES: this
    // EFFECT: add mySong to the end of the list and return true;
    public boolean addMusic(Song mySong) {
        return true;
    }

    // REQUIRES: index >= 0 && index < list.size()
    // MODIFIES: this
    // EFFECTS: delete the song at the specific index from the music list and return true
    public boolean deleteMusic(int index) {
        // stub
        return false;
    }

    // REQUIRES: songList.contains(mySong)
    // MODIFIES: this
    // EFFECTS: delete the mySong from the list.
    public boolean deleteMusic(Song mySong) {
        // stub
        return false;
    }

    // MODIFIES: this
    // EFFECTS: reset the list
    public void reset() {
        // stub
    }

    // REQUIRES: getSize() > 0
    // MODIFIES: this
    // EFFECTS: If startLowest is true, then sort the song from the lowest duration to the highest. 
    //          Otherwise, sort the song from the highest duration to the lowest.
    public void sortByDuration(boolean startLowest) {
        // stub
    }
}
