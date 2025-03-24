package model;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represent a song list that contains all available songs
public class SongList implements Writable {

    private List<Song> songs;

    // EFFECTS: construct an empty list
    public SongList() {
        songs = new LinkedList<>();
    }

    // EFFECTS: return the size of the list
    public int getSize() {
        return songs.size();
    }

    // EFFECTS: reuturn this song list
    public List<Song> getSongs() {
        return songs;
    }

    // EFFECTS: return true if the title of the song is contained in the list
    public boolean isContained(String title) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getTitle().equals(title)) {
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
        for (Song s : songs) {
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
        for (Song s : songs) {
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
            songs.add(index, mySong);
            return true;
        }
        return false;
    }

    // REQUIRES: !songList.contains(mySong)
    // MODIFIES: this
    // EFFECT: add mySong to the end of the list and return true;
    public boolean addSong(Song mySong) {
        if (!isContained(mySong.getTitle())) {
            EventLog.getInstance().logEvent(new Event(mySong.getTitle() + " is added to song list."));
            return songs.add(mySong);
        }
        return false;
    }

    // MODIFIES: this
    // EFFECT: add mySong to the end of the list and return true;
    public boolean addDefaultSong(Song mySong) {
        if (!isContained(mySong.getTitle())) {
            EventLog.getInstance()
                    .logEvent(new Event("Default action: " + mySong.getTitle() + " is added to music library"));
            return songs.add(mySong);
        }
        return false;
    }

    // REQUIRES: index >= 0 && index < getSize()
    // MODIFIES: this
    // EFFECTS: delete the song at the specific index from the music list and return
    // true
    public boolean deleteSong(int index) {
        if (checkValidIndex(index)) {
            Song mySong = songs.get(index);
            songs.remove(index);
            EventLog.getInstance().logEvent(new Event(mySong.getTitle() + " is deleted from song list."));
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: delete the song object with the corresponding title from the list.
    // If the song title is not found, return false.
    public boolean deleteSong(String songTitle) {
        for (int i = 0; i < getSize(); i++) {
            if (songs.get(i).getTitle().equals(songTitle)) {
                return deleteSong(i);
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: reset the list
    public void reset() {
        songs = new LinkedList<>();
    }

    // REQUIRES: getSize() > 0
    // MODIFIES: this
    // EFFECTS: Sort the song from the lowest duration to the highest.
    public void sortByLowestDuration() {
        if (getSize() > 0) {
            for (int i = 0; i < getSize(); i++) {
                int minIndex = i;
                for (int j = i + 1; j < getSize(); j++) {
                    if (songs.get(minIndex).getDuration() > songs.get(j).getDuration()) {
                        minIndex = j;
                    }
                }
                Song temp = songs.get(minIndex);
                songs.set(minIndex, songs.get(i));
                songs.set(i, temp);
            }
        }
        EventLog.getInstance().logEvent(new Event("Song list is sorted by the lowest duration."));
    }

    // REQUIRES: getSize() > 0
    // MODIFIES: this
    // EFFECTS: Sort the song from the highest duration to the lowest.
    public void sortByHighestDuration() {
        if (getSize() > 0) {
            for (int i = 0; i < getSize(); i++) {
                int maxIndex = i;
                for (int j = i + 1; j < getSize(); j++) {
                    if (songs.get(maxIndex).getDuration() < songs.get(j).getDuration()) {
                        maxIndex = j;
                    }
                }
                Song temp = songs.get(maxIndex);
                songs.set(maxIndex, songs.get(i));
                songs.set(i, temp);
            }
        }
        EventLog.getInstance().logEvent(new Event("Song list is sorted by the highest duration."));
    }

    // EFFECTS: returns a JSONObject containing all songs in the song list
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Your song list: ", songsToJson());
        return json;
    }

    // EFFECTS: returns songs in this song list as a JSON array
    private JSONArray songsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Song s : songs) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}