package model;

import java.util.LinkedList;
import java.util.List;

// Represent a song list that contains all available songs
public class SongList {

    private List<Song> songList;

    // EFFECTS: construct an empty list
    public SongList() {
        songList = new LinkedList<>();
    }

    // EFFECTS: return the size of the list
    public int getSize() {
        return songList.size();
    }

    // EFFECTS: reuturn this song list
    public List<Song> getSongList() {
        return songList;
    }

    // EFFECTS: return true if the title of the song is contained in the list
    public boolean isContained(String title) {
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return true if the index is valid.
    public boolean checkValidIndex(int index) {
        return (index < getSize() && index >= 0);
    }

    // EFFECTS: Return the song if found. Otherwise, return null.
    public Song findSongByTitle(String title) {
        for (Song s : songList) {
            if (s.getTitle().equals(title)) {
                return s;
            }
        }
        return null;
    }

    // EFFECTS: Return the song as a list if the author matches. Othewise, return
    // null.
    public List<Song> findSongByAuthor(String author) {
        List<Song> hasAuthor = new LinkedList<>();
        for (Song s : songList) {
            if (s.getAuthor().equals(author)) {
                hasAuthor.add(s);
            }
        }
        return hasAuthor;
    }

    // REQUIRES: (index >= 0 && index < getSize()) && (!songList.contains(mySong))
    // MODIFIES: this
    // EFFECT: add mySong at the specific index to the song list and return true
    public boolean addSong(int index, Song mySong) {
        if ((checkValidIndex(index)) && (!isContained(mySong.getTitle()))) {
            songList.add(index, mySong);
            return true;
        }
        return false;
    }

    // REQUIRES: !songList.contains(mySong)
    // MODIFIES: this
    // EFFECT: add mySong to the end of the list and return true;
    public boolean addSong(Song mySong) {
        if (!isContained(mySong.getTitle())) {
            return songList.add(mySong);
        }
        return false;
    }

    // REQUIRES: index >= 0 && index < getSize()
    // MODIFIES: this
    // EFFECTS: delete the song at the specific index from the music list and return
    // true
    public boolean deleteSong(int index) {
        if (checkValidIndex(index)) {
            songList.remove(index);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: delete the song object with the corresponding title from the list.
    // If the song title is not found, return false.
    public boolean deleteSong(String songTitle) {
        for (int i = 0; i < getSize(); i++) {
            if (songList.get(i).getTitle().equals(songTitle)) {
                return deleteSong(i);
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: reset the list
    public void reset() {
        songList = new LinkedList<>();
    }

    // REQUIRES: getSize() > 0
    // MODIFIES: this
    // EFFECTS: Sort the song from the lowest duration to the highest.
    public void sortByLowestDuration() {
        if (getSize() > 0) {
            for (int i = 0; i < getSize(); i++) {
                int minIndex = i;
                for (int j = i + 1; j < getSize(); j++) {
                    if (songList.get(minIndex).getDuration() > songList.get(j).getDuration()) {
                        minIndex = j;
                    }
                }
                Song temp = songList.get(minIndex);
                songList.set(minIndex, songList.get(i));
                songList.set(i, temp);
            }
        }
    }

    // REQUIRES: getSize() > 0
    // MODIFIES: this
    // EFFECTS: Sort the song from the highest duration to the lowest.
    public void sortByHighestDuration() {
        if (getSize() > 0) {
            for (int i = 0; i < getSize(); i++) {
                int maxIndex = i;
                for (int j = i + 1; j < getSize(); j++) {
                    if (songList.get(maxIndex).getDuration() < songList.get(j).getDuration()) {
                        maxIndex = j;
                    }
                }
                Song temp = songList.get(maxIndex);
                songList.set(maxIndex, songList.get(i));
                songList.set(i, temp);
            }
        }
    }
}