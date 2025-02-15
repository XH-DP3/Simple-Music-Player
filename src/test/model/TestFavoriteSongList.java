package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestFavoriteSongList {

    private FavoriteSongList fsl;
    private Song s1;
    private Song s2;
    private Song s3;

    @Before
    public void setup() {
        fsl = new FavoriteSongList();
        s1 = new Song("Payphone", "Maroon 5", "Pop", 231);
        s2 = new Song("Everybody Hurts", "Avril Lavigne", "Pop", 221);
        s3 = new Song("Innocence", "Avril Lavigne", "Pop", 233);
    }

    @Test
    public void testAddNotFavoriteSongWithIndex() {
        assertFalse(s1.isFavorite());
        assertFalse(fsl.addSong(0,s1));
    }

    @Test
    public void testAddFavoriteSongWithIndex() {
        s1.markedAsFavorite();
        s2.markedAsFavorite();
        assertTrue(fsl.addSong(s1));
        assertTrue(fsl.addSong(0, s2));
        assertEquals(s2, fsl.getSongList().get(0));
        assertEquals(s1, fsl.getSongList().get(1));
    }

    @Test
    public void testAddNotFavoriteSongWithNoIndex() {
        assertFalse(s1.isFavorite());
        assertFalse(fsl.addSong(s1));
    }

    @Test
    public void testAddFavoriteSongWithNoIndex() {
        s1.markedAsFavorite();
        s2.markedAsFavorite();
        assertTrue(fsl.addSong(s1));
        assertTrue(fsl.addSong(s2));
        assertEquals(s1, fsl.getSongList().get(0));
        assertEquals(s2, fsl.getSongList().get(1));
    }
}
