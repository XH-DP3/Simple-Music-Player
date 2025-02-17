package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list contains songs that are marked as favorite
public class FavoriteSongList extends SongList {

    private List<Song> songs;

    // Construct an empty list of my favorite song
    public FavoriteSongList() {
        super();
        songs = new ArrayList<>();
    }

    // REQUIRES: !favoriteSongs.contains(mySong) && mySong.isFavorite()
    // MODIFIES: this
    // EFFECTS: add mySong to the end of the favorite list and return true;
    @Override
    public boolean addSong(Song mySong) {
        if (mySong.isFavorite() && !songs.contains(mySong)) {
            songs.add(mySong);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: delete the song if the title is found and return true. Otherwise,
    // return false.
    @Override
    public boolean deleteSong(String title) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getTitle().equals(title)) {
                songs.remove(i);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return the list of my favorite songs
    @Override
    public List<Song> getSongs() {
        return songs;
    }

    // EFFECTS: return the size of my favorite list.
    @Override
    public int getSize() {
        return songs.size();
    }
}
