package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

// Represent test class for FavoriteSongList class
public class TestFavoriteSongList {

    private FavoriteSongList fsl;
    private Song s1;
    private Song s2;

    @Before
    public void setup() {
        fsl = new FavoriteSongList();
        s1 = new Song("Payphone", "Maroon 5", "Pop", 231);
        s2 = new Song("Everybody Hurts", "Avril Lavigne", "Pop", 221);
    }

    @Test
    public void testAddNotFavoriteSong() {
        assertFalse(s1.isFavorite());
        assertFalse(fsl.addSong(s1));
    }

    @Test
    public void testAddRepeatdSong() {
        s1.markedAsFavorite();
        assertTrue(fsl.addSong(s1));
        assertFalse(fsl.addSong(s1));
        assertEquals(1, fsl.getSize());
    }

    @Test
    public void testAddFavoriteSong() {
        s1.markedAsFavorite();
        s2.markedAsFavorite();
        assertTrue(fsl.addSong(s1));
        assertTrue(fsl.addSong(s2));
        assertEquals(s1, fsl.getSongs().get(0));
        assertEquals(s2, fsl.getSongs().get(1));
    }

    @Test
    public void testDeleteFoundSong() {
        s1.markedAsFavorite();
        assertTrue(fsl.addSong(s1));
        assertTrue(fsl.deleteSong(s1.getTitle()));
    }

    @Test
    public void testDeleteNotFoundSong() {
        s1.markedAsFavorite();
        assertTrue(fsl.addSong(s1));
        assertFalse(fsl.deleteSong("ABC"));
    }

}
