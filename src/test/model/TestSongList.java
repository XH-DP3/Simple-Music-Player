package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

// Represents test class for SongList class
public class TestSongList {

    private SongList list;
    private Song s1;
    private Song s2;
    private Song s3;

    @Before
    public void setup() {
        list = new SongList();
        s1 = new Song("Payphone", "Maroon 5", "Pop", 231);
        s2 = new Song("Everybody Hurts", "Avril Lavigne", "Pop", 221);
        s3 = new Song("Innocence", "Avril Lavigne", "Pop", 233);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, list.getSize());
    }

    @Test
    public void testFindSongByTitleNotFound() {
        list.addSong(s1);
        list.addSong(s2);
        list.addSong(s3);
        String title = "Best Song Ever";
        assertNull(list.findSongByTitle(title));
    }

    @Test
    public void testFindSongByTitleFound() {
        list.addSong(s1);
        list.addSong(s2);
        list.addSong(s3);
        String title = s1.getTitle();
        assertEquals(s1.getTitle(), list.findSongByTitle(title).getTitle());
    }

    @Test
    public void testFindByAuthorNotFound() {
        String author = "One Direction";
        assertEquals(0, list.findSongByAuthor(author).size());
    }

    @Test
    public void testFindByAuthorOneSongFound() {
        list.addSong(s1);
        list.addSong(s2);
        list.addSong(s3);
        String author = "Maroon 5";
        List<Song> l = list.findSongByAuthor(author);
        assertEquals(1, l.size());
        assertEquals(s1, l.get(0));
    }

    @Test
    public void testFindByAuthorMultipleSongsFound() {
        list.addSong(s1);
        list.addSong(s2);
        list.addSong(s3);
        String author = "Avril Lavigne";
        List<Song> l = list.findSongByAuthor(author);
        assertEquals(2, l.size());
        assertEquals(s2, l.get(0));
        assertEquals(s3, l.get(1));
    }

    @Test
    public void testAddOneSongWithNoIndex() {
        assertTrue(list.addSong(s1));
        List<Song> l = list.getSongs();
        assertEquals(1, l.size());
        assertEquals(s1, l.get(0));
    }

    @Test
    public void testAddMultipleSongsWithNoIndex() {
        assertTrue(list.addSong(s1));
        assertTrue(list.addSong(s2));
        assertTrue(list.addSong(s3));
        List<Song> l = list.getSongs();
        assertEquals(3, l.size());
        assertEquals(s1, l.get(0));
        assertEquals(s2, l.get(1));
        assertEquals(s3, l.get(2));
    }

    @Test
    public void testAddExtraSongWithIndex() {
        list.addSong(s1);
        list.addSong(s2);
        list.addSong(s3);
        Song s4 = new Song("Whataya Want from me", "Adam Lambert", "Pop", 227);
        assertTrue(list.addSong(1, s4));
        assertEquals(s4, list.getSongs().get(1));
    }

    @Test
    public void testAddSongWithInvalidInput() {
        assertFalse(list.addSong(-1, s1));
        assertFalse(list.addSong(10, s1));
        assertEquals(0, list.getSize());
        assertTrue(list.addSong(s1));
        assertFalse(list.addSong(s1));
        assertFalse(list.addSong(0, s1));

    }

    @Test
    public void testDeleteSongWithAnIndex() {
        assertTrue(list.addSong(s1));
        assertTrue(list.addSong(s2));
        assertTrue(list.addSong(s3));
        assertTrue(list.deleteSong(0));
        assertEquals(2, list.getSize());
        assertFalse(list.getSongs().contains(s1));
    }

    @Test
    public void testDeleteSongWithExistedSongName() {
        assertTrue(list.addSong(s1));
        assertTrue(list.addSong(s2));
        assertTrue(list.addSong(s3));
        assertTrue(list.deleteSong(s1.getTitle()));
        assertEquals(2, list.getSize());
        assertFalse(list.getSongs().contains(s1));
    }

    @Test
    public void testDeleteSongWithNonExistedSongName() {
        assertTrue(list.addSong(s1));
        assertTrue(list.addSong(s2));
        assertTrue(list.addSong(s3));
        assertFalse(list.deleteSong("Unknown Song"));
        assertEquals(3, list.getSize());
    }

    @Test
    public void testDeleteSongWithInvalidIndex() {
        assertTrue(list.addSong(s1));
        assertFalse(list.deleteSong(-1));
        assertFalse(list.deleteSong(10));
        assertEquals(1, list.getSize());
        ;
    }

    @Test
    public void testReset() {
        assertTrue(list.addSong(s1));
        assertTrue(list.addSong(s2));
        assertTrue(list.addSong(s3));
        assertEquals(3, list.getSize());
        list.reset();
        assertEquals(0, list.getSize());
    }

    @Test
    public void testSortByLowestDuration() {
        assertTrue(list.addSong(s1));
        assertTrue(list.addSong(s2));
        assertTrue(list.addSong(s3));
        list.sortByLowestDuration();
        List<Song> sortedList = list.getSongs();
        assertEquals(s2, sortedList.get(0));
        assertEquals(s1, sortedList.get(1));
        assertEquals(s3, sortedList.get(2));
    }

    @Test
    public void testSortByLowestDurationWithInvalidSize() {
        list.sortByLowestDuration();
        assertEquals(0, list.getSize());
    }

    @Test
    public void testSortByhighestDuration() {
        assertTrue(list.addSong(s1));
        assertTrue(list.addSong(s2));
        assertTrue(list.addSong(s3));
        list.sortByHighestDuration();
        List<Song> sortedList = list.getSongs();
        assertEquals(s3, sortedList.get(0));
        assertEquals(s1, sortedList.get(1));
        assertEquals(s2, sortedList.get(2));
    }

    @Test
    public void testSortByHighestDurationWithInvalidSize() {
        list.sortByHighestDuration();
        assertEquals(0, list.getSize());
    }

    @Test
    public void testAddDefualtSong() {
        assertTrue(list.addDefaultSong(s1));
        assertFalse(list.addDefaultSong(s1));
    }
}
