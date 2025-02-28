package model;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import persistence.JsonReader;

public class TestJsonReader {
    private JsonReader reader;

    @Test
    public void testReadInvalidFile() {
        reader = new JsonReader("./data/my\\0illegal:fileName.json");
        try {
            SongList mySonglist = reader.read();
            fail ("The above line should throw FileNotFoundException");
        } catch (IOException e) {
            // expect;
        }
    }

    @Test
    public void testReadEmptyFile() {
        reader = new JsonReader("data/myTestEmptyFile.json");
        try {
            SongList mySongList = reader.read();
            assertEquals(mySongList.getSize(), 0);
        } catch (IOException e) {
            fail ("IOException is not expected!");
        }
    }

    @Test
    public void testReadFile() {
        reader = new JsonReader("data/myTestFile.json");
        try {
            SongList mySongList = reader.read();
            assertEquals(mySongList.getSize(), 4);
            assertEquals("Paypone", mySongList.getSongs().get(0).getTitle());
            assertEquals("Everybody Hurts", mySongList.getSongs().get(1).getTitle());
        } catch (IOException e) {
            fail ("IOException is not expected!");
        }
    }
}
