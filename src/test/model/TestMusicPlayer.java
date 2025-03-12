package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

public class TestMusicPlayer {
    private MusicPlayer player;

    @Test
    public void testFileNotFound() {
        try {
            player = new MusicPlayer("unknown");
        } catch (FileNotFoundException e) {
            // expected
        } catch (Exception e) {
            fail("Not expected.");
        }
    }

    @Test
    public void testPlay() {
        player.play();
        assertTrue(player.isPlaying());
        assertFalse(player.isOver());
    }

    @Test
    public void testPause() {
        player.play();
        assertTrue(player.isPlaying());
        assertFalse(player.isOver());
        player.pause();
        assertFalse(player.isPlaying());
        assertFalse(player.isOver());
    }
}
