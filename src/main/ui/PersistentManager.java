package ui;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.SongList;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represent a persistent manager that handles reloading and saving the data.
public class PersistentManager {
    private static final String MUSIC_LIBRARY_PATH = "data/musicLibrary.json";
    private static final String SONG_LIST_PATH = "data/mySongList.json";
    private static final String FAVORITE_LIST_PATH = "data/myFavoriteSongList.json";
    private SongListPanel songListPanel = new SongListPanel();
    private Scanner in;
    private MainMenu mainMenu;

    // consturct the persistent manager page with a reference to main menu and song
    // list panel
    public PersistentManager(MainMenu mainMenu, SongListPanel songListPanel) {
        this.mainMenu = mainMenu;
        this.songListPanel = songListPanel;
    }

    // EFFECTS: a helper method that will print message.
    private void printm(String message) {
        System.out.println(message);
    }

    // EFFECTS: a helper method that will invoke menu() in MainMenu
    private void menu() {
        mainMenu.menu();
    }

    // EFFECTS: a helper method that will return music library from SongListPanel
    private SongList getMusicLibrary() {
        return songListPanel.getMusicLibrary();
    }

    // EFFECTS: a helper method that will return song list from SongListPanel
    private SongList getSongList() {
        return songListPanel.getSongList();
    }

    // EFFECTS: a helper method that will return favorite list from SongListPanel
    private SongList getMyFavoriteList() {
        return songListPanel.getMyFavoriteList();
    }

    // EFFECTS: add every elements in prevList to currentList
    private void merge(SongList prevList, SongList currentList) {
        songListPanel.merge(prevList, currentList);
    }

    // EFFECTS: identify which attribute(s) the user want to save
    public void saveHelper() {
        try {
            printm("\nPlease select one of the following:\n");
            in = new Scanner(System.in);
            printm("1. Save music library.");
            printm("2. Save song list.");
            printm("3. Save favorite song list.");
            printm("4. Return to the menu.");
            printm("5. Finish saving.");
            int input = in.nextInt();
            mainMenu.checkValidInput(input, 1, 5);
            evaluateInputForSave(input);
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            saveHelper();
        }
    }

    // EFFECTS: identify the input from user for the attribute(s) they want to save
    private void evaluateInputForSave(int input) {
        if (input == 1) {
            writeMusicLibrary(MUSIC_LIBRARY_PATH);
        } else if (input == 2) {
            writeSongList(SONG_LIST_PATH);
        } else if (input == 3) {
            writeFavoriteSongList(FAVORITE_LIST_PATH);
        } else if (input == 4) {
            menu();
        } else {
            printm("\nProgram ends. Bye!\n");
            System.exit(1);
        }
    }

    // EFFECTS: write list to source
    public void write(String source, SongList list) throws IOException {
        JsonWriter writer = new JsonWriter(source);
        writer.open();
        writer.write(list);
        writer.close();
    }

    // EFFECTS: write music library to a json file
    private void writeMusicLibrary(String source) {
        try {
            write(source, getMusicLibrary());
            printm("\nYour music library is saved.");
            saveHelper();
        } catch (IOException e) {
            printm("\nYour music library cannot be saved.");
            saveHelper();
        }
    }

    // EFFECTS: write song list to a json file
    private void writeSongList(String source) {
        if (getSongList().getSize() == 0) {
            printm("\nYour song list has no songs to save.");
            saveHelper();
        } else {
            try {
                write(source, getSongList());
                printm("\nYour song list is saved.");
                saveHelper();
            } catch (IOException e) {
                printm("\nYour song list cannot be saved.");
                saveHelper();
            }
        }
    }

    // EFFECTS: write favorite song list to a json file
    private void writeFavoriteSongList(String source) {
        if (getMyFavoriteList().getSize() == 0) {
            printm("\nYour favorite song list has no songs to save.");
            saveHelper();
        } else {
            try {
                write(source, getMyFavoriteList());
                printm("\nYour favorite song list is saved.");
                saveHelper();
            } catch (IOException e) {
                printm("\nYour favorite song list cannot be saved.");
                saveHelper();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: identify which attributes the user want to reload
    public void reloadHelper() {
        try {
            in = new Scanner(System.in);
            printm("\nPlease select one of the following: \n");
            printm("1. Reloading music library.");
            printm("2. Reloading your song list.");
            printm("3. Reloading your favorite song list.");
            printm("4. Delete previous saved lists.");
            printm("5. Return to the menu.");
            int input = in.nextInt();
            mainMenu.checkValidInput(input, 1, 5);
            evaluateInputForReload(input);
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            reloadHelper();
        }
    }

    // EFFECTS: evaluate user's input for reload and invoke the corresponding
    // methods
    private void evaluateInputForReload(int input) {
        if (input == 1) {
            reloadMusicLibrary(MUSIC_LIBRARY_PATH);
        } else if (input == 2) {
            reloadSongList(SONG_LIST_PATH);
        } else if (input == 3) {
            reloadFavoriteSongList(FAVORITE_LIST_PATH);
        } else if (input == 4) {
            writeEmptyLists();
        } else {
            menu();
        }
    }

    // EFFECTS: delete the previous saved progress by writing empty content to the
    // files
    private void writeEmptyLists() {
        try {
            write(MUSIC_LIBRARY_PATH, new SongList());
            write(SONG_LIST_PATH, new SongList());
            write(FAVORITE_LIST_PATH, new SongList());
            printm("\nYour previous progress has been deleted.");
            reloadHelper();
        } catch (IOException e) {
            printm("\nThis request cannot be done.");
            reloadHelper();
        }
    }

    // EFFECTS: read from source and return the content as SongList
    private SongList reload(String source) throws IOException {
        JsonReader reader = new JsonReader(source);
        return reader.read();
    }

    // EFFECTS: reload the music library
    private void reloadMusicLibrary(String source) {
        try {
            SongList myPrevMusicLibrary = reload(source);
            in = new Scanner(System.in);
            if (getMusicLibrary().getSize() > 0) {
                printm("\nYour current music library is not empty.");
                printm("\nWould you like to merge your previous and your current music library (yes/no)?");
                String s = in.nextLine();
                if (s.equals("no")) {
                    printm("\nYour current music library is preserved.");
                    reloadHelper();
                }
            } else {
                merge(myPrevMusicLibrary, getMusicLibrary());
                printm("\nYour music library has been reloaded.");
                reloadHelper();
            }
        } catch (IOException e) {
            printm("\nYour muisc library cannot be read.");
            reloadHelper();
        }
    }

    // EFFECTS: reload the user's song list
    private void reloadSongList(String source) {
        try {
            SongList myPrevSongList = reload(source);
            in = new Scanner(System.in);
            if (getSongList().getSize() > 0) {
                printm("\nYour current song list is not empty.");
                printm("\nWould you like to merge your previous saved song list and your current song list (yes/no)?");
                String s = in.nextLine();
                if (s.equals("no")) {
                    printm("\nYour current song list is preserved.");
                    reloadHelper();
                }
            } else {
                merge(myPrevSongList, getSongList());
                printm("\nYour song list has been reloaded.");
                reloadHelper();
            }
        } catch (IOException e) {
            printm("\nYour song list cannot be read.");
            reloadHelper();
        }
    }

    // EFFECTS: reload the user's favorite song list
    private void reloadFavoriteSongList(String source) {
        try {
            SongList myPrevFavoriteList = reload(source);
            in = new Scanner(System.in);
            if (getMyFavoriteList().getSize() > 0) {
                printm("\nYour current favorite song list is not empty.");
                printm("\nWould you like to merge your previous and current favorite song list (yes/no)?");
                String s = in.nextLine();
                if (s.equals("no")) {
                    printm("\nYour current favorite list is preserved.");
                    reloadHelper();
                }
            } else {
                merge(myPrevFavoriteList, getMyFavoriteList());
                printm("\nYour favorite song list has been reloaded.");
                reloadHelper();
            }
        } catch (IOException e) {
            printm("\nYour favorite song list cannot be read.");
            reloadHelper();
        }
    }
}
