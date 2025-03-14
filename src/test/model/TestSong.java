package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.io.*;

// Represents test class for Song class
public class TestSong {
    private Song mySong;

    @Before
    public void setup() {
        mySong = new Song("Patience", "Take That", "Pop", 202);
    }

    @Test
    public void testConstructor() {
        assertEquals("Patience", mySong.getTitle());
        assertEquals("Take That", mySong.getAuthor());
        assertEquals("Pop", mySong.getGenre());
        assertEquals(202, mySong.getDuration());
    }

    @Test
    public void testSetDifferentGenre() {
        assertEquals("Pop", mySong.getGenre());
        mySong.setGenre("Classic");
        assertEquals("Classic", mySong.getGenre());
    }

    @Test
    public void testSetSameGenre() {
        assertEquals("Pop", mySong.getGenre());
        mySong.setGenre("Pop");
        assertEquals("Pop", mySong.getGenre());
    }

    @Test
    public void testSetInvalidDuration() {
        assertEquals(202, mySong.getDuration());
        mySong.setDuration(-10);
        assertEquals(202, mySong.getDuration());
    }

    @Test
    public void testSetDuration() {
        assertEquals(202, mySong.getDuration());
        mySong.setDuration(60);
        assertEquals(60, mySong.getDuration());
    }

    @Test
    public void testReadEmptyLyricsFile() {
        File f = new File("src/test/model/TestReadEmptyLyrics.txt");
        try {
            mySong.readLyrics(f);
            assertEquals(0, mySong.getLyrics().size());
        } catch (FileNotFoundException e) {
            fail("Got FileNotFoundException when we shouldn't have!");
        } catch (IOException e) {
            fail("Got IOException when we shouldn't have!");
        }
    }

    @Test
    public void testReadLyricsFile() {
        File f = new File("src/test/model/Unknown.txt");
        try {
            mySong.readLyrics(f);
            fail("Got no exception when we should have FileNotFoundException");
        } catch (FileNotFoundException e) {
            // expecting FileNotFoundException to be catught
        } catch (IOException e) {
            fail("Got IOException when we shoudn't have!");
        }
    }

    @Test
    public void testReadLyricsWhenFileIsNotFound() {
        File f = new File("src/test/model/TestReadLyrics.txt");
        try {
            mySong.readLyrics(f);
            assertEquals(6, mySong.getLyrics().size());
        } catch (FileNotFoundException e) {
            fail("Got FileNotFoundException when we shouldn't have!");
        } catch (IOException e) {
            fail("Got IOException when we shouldn't have!");
        }
    }

    @Test
    public void testPlaySong() {
        assertFalse(mySong.getPlayingStatus());
        mySong.playSong();
        assertTrue(mySong.getPlayingStatus());
    }

    @Test
    public void testPauseSong() {
        mySong.playSong();
        assertTrue(mySong.getPlayingStatus());
        mySong.pauseSong();
        assertFalse(mySong.getPlayingStatus());
    }

    @Test
    public void testMarkedAsFavorite() {
        mySong.markedAsFavorite();
        assertTrue(mySong.isFavorite());
    }

    @Test
    public void testPointsRelatedMethods() {
        assertEquals(0, mySong.getTotalPoints());
        mySong.updateTotalPoints(200);
        assertEquals(200, mySong.getTotalPoints());
        mySong.setFinish(true);
        assertTrue(mySong.isFinished());
        mySong.updateRecord(200);
        assertEquals(200, mySong.getRecord());
        mySong.resetTotalPoints();
        assertEquals(0, mySong.getTotalPoints());
    }

    @Test
    public void testUpdateTotalPoints() {
        mySong.updateTotalPoints(-100);
        assertEquals(0, mySong.getTotalPoints());
    }

    @Test
    public void testEqualityofSongs() {
        assertTrue(mySong.equals(mySong));
        assertFalse(mySong.equals(new Song("Payphone", "Maroon 5", "Pop", 231)));
        assertFalse(mySong.equals(null));
        assertFalse(mySong.equals(new Object()));
        mySong.hashCode();
    }

    @Test
    public void testSetMelodyFilePath() {
        mySong.setMelodyFilePath("ATestFilePath");
        assertEquals("ATestFilePath", mySong.getMelodyFilePath());
    }

    @Test
    public void testSetImageFilePath() {
        mySong.setImageFilePath("A file path");
        assertEquals("A file path", mySong.getImageFilePath());
    }
}
