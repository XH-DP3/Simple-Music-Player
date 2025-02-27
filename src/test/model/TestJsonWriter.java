package model;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import persistence.JsonWriter;

public class TestJsonWriter {
    private JsonWriter writer;

    @Test
    public void testOpenWithANotFoundFile() {
        try {
            writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("The test should not pass!");
        } catch (FileNotFoundException e) {
            // expected exception
        }
    }

    @Test
    public void testOpenWithAFoundFile() {
        try {
            writer = new JsonWriter("./data/myTestFile.json");
            writer.open();
        } catch (FileNotFoundException e) {
            fail ("FileNotFoundException is not expected!");
        }
    }

    @Test
    public void testWriteEmptySongList() {
        try {
            writer = new JsonWriter("./data/myTestFile.json");
            writer.open();
            SongList mySongList = new SongList();
            assertEquals(0, mySongList.getSize());
            writer.write(mySongList);
            writer.close();
        } catch (IOException e) {
            fail ("IOException is not expected!");
        }
    }

    @Test
    public void testWriteSongList() {
        try {
            writer = new JsonWriter("./data/myTestFile.json");
            writer.open();
            SongList mySongList = new SongList();
            Song s1 = new Song("Payphone", "Maroon 5", "Pop", 231);
            Song s2 = new Song("Everybody Hurts", "Avril Lavigne", "Pop", 221);
            mySongList.addSong(s1);
            mySongList.addSong(s2);
            writer.write(mySongList);
            writer.close();
        } catch (IOException e) {
            fail ("IOException is not expected!");
        }
    }
}
