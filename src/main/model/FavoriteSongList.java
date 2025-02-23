package model;

// Represents a list contains songs that are marked as favorite
public class FavoriteSongList extends SongList {

    // Construct an empty list of my favorite song
    public FavoriteSongList() {
        super();
    }

    // REQUIRES: !favoriteSongs.contains(mySong) && mySong.isFavorite()
    // MODIFIES: this
    // EFFECTS: add mySong to the end of the favorite list and return true;
    @Override
    public boolean addSong(Song mySong) {
        if (mySong.isFavorite() && !super.getSongs().contains(mySong)) {
            super.addSong(mySong);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: delete the song if the title is found and return true. Otherwise,
    // return false.
    @Override
    public boolean deleteSong(String title) {
        for (int i = 0; i < super.getSongs().size(); i++) {
            if (super.getSongs().get(i).getTitle().equals(title)) {
                super.deleteSong(i);
                return true;
            }
        }
        return false;
    }
}
