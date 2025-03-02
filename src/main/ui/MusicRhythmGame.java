package ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Buttons;
import model.FavoriteSongList;
import model.Song;
import model.SongList;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represent a music rhythm game application with a general music library,
// my song list, my favorite song list, and buttons.
public class MusicRhythmGame {
    private static final String MUSIC_LIBRARY_PATH = "data/musicLibrary.json";
    private static final String SONG_LIST_PATH = "data/mySongList.json";
    private static final String FAVORITE_LIST_PATH = "data/myFavoriteSongList.json";
    private Scanner in;
    private SongList musicLibrary;
    private SongList mySongList;
    private SongList myFavoriteList;
    private Buttons buttons;

    // MODIFIES: this
    // EFFECTS: Intialize the fields and invoke the menu
    public MusicRhythmGame() {
        setup();
        printm("\nWelcome to the game!\n");
        menu();
    }

    // EFFECTS: return the music library
    public SongList getMusicLibrary() {
        return musicLibrary;
    }

    // EFFECTS: return the song list.
    public SongList getSongList() {
        return mySongList;
    }

    // EFFECTS: return the favorite song list.
    public SongList getMyFavoriteSongList() {
        return myFavoriteList;
    }

    // EFFECTS: return the total points the player got
    public int getTotalPoint() {
        return buttons.getTotalPressedPoints();
    }

    // MODIFIES: this
    // EFFECTS: Initialize the fields.
    public void setup() {
        musicLibrary = new SongList();
        mySongList = new SongList();
        myFavoriteList = new FavoriteSongList();
        buttons = new Buttons();
        Song s1 = new Song("Payphone", "Maroon 5", "Pop", 231);
        Song s2 = new Song("Everybody Hurts", "Avril Lavigne", "Pop", 221);
        Song s3 = new Song("Innocence", "Avril Lavigne", "Pop", 233);
        Song s4 = new Song("Whataya Want from Me", "Adam Lambert", "Pop", 227);
        Song s5 = new Song("Like I Do", "J.Tajor", "R&B", 149);
        musicLibrary.addSong(s1);
        musicLibrary.addSong(s2);
        musicLibrary.addSong(s3);
        musicLibrary.addSong(s4);
        musicLibrary.addSong(s5);
    }

    // EFFECTS: a helper method that will print message.
    public void printm(String message) {
        System.out.println(message);
    }

    // EFFECTS: a helper method that will throw an InputMisMatchException if the
    // user input is not valid
    public void checkValidInput(int input, int lowerBound, int upperBound) {
        if (input < lowerBound || input > upperBound) {
            throw new InputMismatchException();
        }
    }

    // MODIFIES: this
    // EFFECTS: printing the menu// EFFECTS: showing the menu panel and receives an
    // input from user about the
    // next command. It will invoke the evaluateInputForMenu() and handle the
    // potential InputMismatchException().
    public void menu() throws InputMismatchException {
        try {
            in = new Scanner(System.in);
            printm("\nPlease select one of the following by entering an integer: \n");
            printm("1. Playing the game.");
            printm("2. Check music library.");
            printm("3. Check your song list.");
            printm("4. Check your favorite song list.");
            printm("5. Quit the program.");
            printm("6. Reload your saved lists.");
            int input = in.nextInt();
            checkValidInput(input, 1, 6);
            evaluateInputForMenu(input);
        } catch (InputMismatchException e) {
            printm("Invalid input. Please try again.");
            menu();
        }
    }

    // EFFECTS: it will evaluate input from the user in the menu panel and invoke
    // the corresponding panel
    public void evaluateInputForMenu(int input) {
        if (input == 1) {
            startHelper();
        } else if (input == 2) {
            musicLibrary();
        } else if (input == 3) {
            songList();
        } else if (input == 4) {
            favoriteList();
        } else if (input == 5) {
            quit();
        } else {
            reloadHelper();
        }
    }

    // REQUIRES: user's song list is not empty
    // MODIFIES: this
    // EFFECTS: start the game if the song title is found in either song list or
    // favorite song list. Otherwise, print an error message and reask for input.
    public void startHelper() {
        if (getSongList().getSize() == 0) {
            printm("\nYour music list is empty. Please add songs to your list first.");
            menu();
        } else {
            in = new Scanner(System.in);
            printm("Below are all songs in the music library: \n");
            printSongInfo(getMusicLibrary());
            printm("\nPlease enter the title of the song that you want to play: \n");
            String msg = in.nextLine();
            Song s = getSongList().findSongByTitle(msg);
            if (s != null) {
                start(s);
            } else {
                printm("\n" + msg + " is not found.");
                startHelper();
            }
        }
    }

    // EFFECTS: starts the game and evaluate if the key press from user is correct.
    // If the key press is correct, print an appropriate message and show the total
    // points the user received. Otherwise, print an error message.
    public void start(Song mySong) {
        printm("\nGame is starting.");
        printm("Playing " + mySong.getTitle());
        mySong.playSong();
        boolean isOver = false;
        while (!isOver) {
            int random = (int) (Math.random() * 8);
            generatingButtons(random);
            printm("\n(Type Q to quit the game)\n");
            printm("\nThe next falling button is: " + buttons.getNextFallingButton());
            printm("\nPress keys: " + Arrays.toString(buttons.getFixedButtons()));
            String msg = in.nextLine();
            if (buttons.checkKeyPress(msg)) {
                printm("\nGood job! You got " + getTotalPoint() + "\n");
            } else if (msg.equals("Q")) {
                isOver = true;
                end();
            } else {
                printm("\nWrong key press!\n");
            }
        }
    }

    // EFFECTS: generating the next falling buttons that the user has to press
    public void generatingButtons(int random) {
        if (random == 0) {
            buttons.setNextKeyPress("A");
        } else if (random == 1) {
            buttons.setNextKeyPress("S");
        } else if (random == 2) {
            buttons.setNextKeyPress("D");
        } else if (random == 3) {
            buttons.setNextKeyPress("F");
        } else if (random == 4) {
            buttons.setNextKeyPress("J");
        } else if (random == 5) {
            buttons.setNextKeyPress("K");
        } else if (random == 6) {
            buttons.setNextKeyPress("L");
        } else {
            buttons.setNextKeyPress(";");
        }
    }

    // EFFECTS: finish the game and show the total point that the user received.
    // Then return to the menu
    public void end() {
        printm("\nGame ends. You got " + getTotalPoint() + " points.");
        menu();
    }

    // MODIFIES: this
    // EFFECTS: ask the user if they want to save their music library, song list,
    // and favorite song list then quit the program
    public void quit() {
        printm("\nWould you like to save your progress (yes/no)?");
        String s = in.next();
        if (!s.equals("yes") && !s.equals("no")) {
            printm("Invalid input. Please try again.");
            quit();
        } else if (s.equals("no")) {
            printm("\nYour progress for this time is not saved.");
        } else {
            saveHelper();
        }
        printm("\nProgram ends. Bye!\n");
        System.exit(1);
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
            checkValidInput(input, 1, 5);
            evaluateInputForSave(input);
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            saveHelper();
        }
    }

    // EFFECTS: identify the input from user for the attribute(s) they want to save
    public void evaluateInputForSave(int input) {
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
    public void writeMusicLibrary(String source) {
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
    public void writeSongList(String source) {
        if (mySongList.getSize() == 0) {
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
    public void writeFavoriteSongList(String source) {
        if (myFavoriteList.getSize() == 0) {
            printm("\nYour favorite song list has no songs to save.");
            saveHelper();
        } else {
            try {
                write(source, getMyFavoriteSongList());
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
            checkValidInput(input, 1, 5);
            evaluateInputForReload(input);
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            reloadHelper();
        }
    }

    // EFFECTS: evaluate user's input for reload and invoke the corresponding
    // methods
    public void evaluateInputForReload(int input) {
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
    public void writeEmptyLists() {
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
    public SongList reload(String source) throws IOException {
        JsonReader reader = new JsonReader(source);
        return reader.read();
    }

    // EFFECTS: add every elements in prevList to currentList
    public void merge(SongList prevList, SongList currentList) {
        for (Song s : prevList.getSongs()) {
            currentList.addSong(s);
        }
    }

    // EFFECTS: evaluate if list is empty.
    public boolean isEmpty(SongList list) {
        return list.getSize() == 0;
    }

    // EFFECTS: reload the music library
    public void reloadMusicLibrary(String source) {
        try {
            SongList myPrevMusicLibrary = reload(source);
            in = new Scanner(System.in);
            if (getMusicLibrary().getSize() > 0) {
                printm("\nYour current music library is not empty.");
                printm("\nWould you like to merge your previous and your current music library (yes/no)?");
                String s = in.nextLine();
                if (s.equals("yes")) {
                    merge(myPrevMusicLibrary, musicLibrary);
                }
                printm("\nYour music library has been reloaded and merged.");
                reloadHelper();
            } else {
                musicLibrary = myPrevMusicLibrary;
                printm("\nYour music library has been reloaded.");
                reloadHelper();
            }
        } catch (IOException e) {
            printm("\nYour muisc library cannot be read.");
            reloadHelper();
        }
    }

    // EFFECTS: reload the user's song list
    public void reloadSongList(String source) {
        try {
            SongList myPrevSongList = reload(source);
            in = new Scanner(System.in);
            if (mySongList.getSize() > 0) {
                printm("\nYour current song list is not empty.");
                printm("\nWould you like to merge your previous saved song list and your current song list (yes/no)?");
                String s = in.nextLine();
                if (s.equals("yes")) {
                    merge(myPrevSongList, mySongList);
                }
                printm("\nYour song list has been reloaded and merged.");
                reloadHelper();
            } else {
                mySongList = myPrevSongList;
                printm("\nYour song list has been reloaded.");
                reloadHelper();
            }
        } catch (IOException e) {
            printm("\nYour song list cannot be read.");
            reloadHelper();
        }
    }

    // EFFECTS: reload the user's favorite song list
    public void reloadFavoriteSongList(String source) {
        try {
            SongList myPrevFavoriteList = reload(source);
            in = new Scanner(System.in);
            if (myFavoriteList.getSize() > 0) {
                printm("\nYour current favorite song list is not empty.");
                printm("\nWould you like to merge your previous and current favorite song list (yes/no)?");
                String s = in.nextLine();
                if (s.equals("yes")) {
                    merge(myPrevFavoriteList, myFavoriteList);
                }
                printm("\nYour favorite song list has been reloaded and merged.");
                reloadHelper();
            } else {
                myFavoriteList = myPrevFavoriteList;
                printm("\nYour favorite song list has been reloaded.");
                reloadHelper();
            }
        } catch (IOException e) {
            printm("\nYour favorite song list cannot be read.");
            reloadHelper();
        }
    }

    // MODIFIES: this
    // EFFECTS: show the music library panel and ask for input from user.
    public void musicLibrary() {
        try {
            in = new Scanner(System.in);
            printm("\nBelow are currently available songs: \n");
            printSongInfo(getMusicLibrary());
            printm("Please select one of the following by typing a valid integer:");
            printm("1. Add song to my song list.");
            printm("2. Add new song to the music library.");
            printm("3. Return to the menu.");
            int input = in.nextInt();
            checkValidInput(input, 1, 3);
            evaluateInputForMusicLibrary(input);
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            musicLibrary();
        }
    }

    // EFFECTS: evaluate the input from the user for music library. If the input is
    // valid, invoking the corresponding panel. Otherwise, throw new
    // InputMismatchException()
    public void evaluateInputForMusicLibrary(int input) throws InputMismatchException {
        if (input == 1) {
            addSongListHelper();
        } else if (input == 2) {
            addNewSongToMusicLibrary();
        } else if (input == 3) {
            menu();
        } else {
            throw new InputMismatchException();
        }
    }

    // EFFECTS: add song to the song list. If the song is added sucessfully, print a
    // success message and return to the music library panel. Otherwise, throw new
    // SongAlreadyExistsException()
    public void addSongToMySongList(Song mySong) {
        if (mySongList.addSong(mySong)) {
            printm("\n" + mySong.getTitle() + " is added to you song list!");
            musicLibrary();
        } else {
            printm("\nThe song is already in you song list.");
            musicLibrary();
        }
    }

    // MODIFIES: this
    // EFFECTS: add "customize" song from user to the music library.
    public void addNewSongToMusicLibrary() {
        try {
            in = new Scanner(System.in);
            printm("\nPlease type the title of the song: \n");
            String title = in.nextLine();
            printm("Please type the name of the author: ");
            String author = in.nextLine();
            printm("Please type the genre of the song: ");
            String genre = in.nextLine();
            printm("Please type the duration of the song: ");
            int duration = in.nextInt();
            musicLibrary.addSong(new Song(title, author, genre, duration));
            printm("\n" + title + " is added to the music library!");
            musicLibrary();
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            musicLibrary();
        }
    }

    // MODIFIES: this
    // EFFECTS: if the title is found from the music library, then invoke
    // addSongToMSongList() method. Else throw new InputMismatchException(). This
    // method will also handle the potential SongAlreadyExistsException thrown by
    // addSongToMySongList().
    public void addSongListHelper() {
        try {
            in = new Scanner(System.in);
            printm("\nPlease enter the song title: ");
            String msg = in.nextLine();
            Song s = getMusicLibrary().findSongByTitle(msg);
            if (s != null) {
                addSongToMySongList(s);
            } else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            printm("Invalid input.");
            musicLibrary();
        }
    }

    // MODIFIES: this
    // EFFECTS: delete the song from the user's song list if the input title is
    // found and print a success message. Otherwise, print an error message and
    // return to the song list panel.
    public void deleteSongFromMySongList() {
        in = new Scanner(System.in);
        printm("\nPlease enter the title: ");
        String msg = in.nextLine();
        Song s = getSongList().findSongByTitle(msg);
        if (s != null) {
            getSongList().deleteSong(s.getTitle());
            printm("\n" + msg + " is deleted.");
            songList();
        } else {
            printm("\n" + msg + " is not found.");
            songList();
        }
    }

    // EFFECTS: show the song list panel. If the user enters a valid input, invoke
    // the evaluateInputForSongList() method. Otherwise, throw new
    // InputMismatchException()
    public void songList() {
        try {
            printm("\nYour song list has: \n");
            printSongInfo(mySongList);
            printm("Please select one of the following by typing a valid integer\n");
            printm("1. Add song to your favorite list.");
            printm("2. Remove song from your song list.");
            printm("3. Return to the menu.");
            int input = in.nextInt();
            checkValidInput(input, 1, 3);
            evaluateInputForSongList(input);
        } catch (InputMismatchException e) {
            printm("\nPlease enter a valid integer.");
            songList();
        }
    }

    // EFFECTS: evaluate the input from user for the song list panel. If the input
    // is valid, invoke the corresponding methods. Otherwise, throw new
    // InputMismatchException()
    public void evaluateInputForSongList(int input) {
        if (input == 1) {
            addSongToMyFavoriteSongListHelper();
        } else if (input == 2) {
            deleteSongFromMySongList();
        } else if (input == 3) {
            menu();
        } else {
            throw new InputMismatchException();
        }
    }

    // EFFECTS: if the title is found from the song list, then invoke
    // addSongToMyFavoriteSongList() method. It will handle the
    // SongAlreadyExistsException and SongIsNotFavoriteException thrown by
    // addSongToMyFavoriteSongList() method. And for the latter, it will ask if the
    // user want to mark the song as favorite.
    public void addSongToMyFavoriteSongListHelper() {
        in = new Scanner(System.in);
        printm("\nPlease type the title of the song that you want to add: ");
        String title = in.nextLine();
        Song s = getSongList().findSongByTitle(title);
        try {
            if (s != null) {
                addSongToMyFavoriteSongList(s);
            }
        } catch (InputMismatchException e) {
            printm("\nThe song title is not found.");
            songList();
        }
    }

    // EFFECTS: add song to my favorite song list and print a successful message.
    // Otherwise, determines if the song is already exists, or the song is not
    // favorite, and thrown the corresponding exception.
    public void addSongToMyFavoriteSongList(Song mySong) {
        if (myFavoriteList.addSong(mySong)) {
            printm("\n" + mySong.getTitle() + " is added to your favorite song list!");
            favoriteList();
        } else {
            if (!mySong.isFavorite()) {
                printm("\nThe song is not marked as favorite. Marked as favorite? (yes/no)?");
                String msg = in.nextLine();
                if (msg.equalsIgnoreCase("Yes")) {
                    mySong.markedAsFavorite();
                    printm("\nMarked as favorite. Now try add the song again.");
                    songList();
                } else {
                    printm("\nThe song is already in you song list.");
                    favoriteList();
                }

            }
        }
    }

    // EFFECTS: show my favorite list panel
    public void favoriteList() throws InputMismatchException {
        try {
            printm("\nYour favorite song list has: \n");
            printSongInfo(myFavoriteList);
            printm("\nPlease select one of the following by typing a valid integer: \n");
            printm("1. Remove an existing song from your favorite list by typing the title.");
            printm("2. Return to the menu.");
            int input = in.nextInt();
            checkValidInput(input, 1, 2);
            evaluateInputForFavoriteList(input);
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            favoriteList();
        }
    }

    // EFFECTS: evaluate input from user for the favorite list panel. If the input
    // is valid, invoke the corresponding method.
    public void evaluateInputForFavoriteList(int input) {
        if (input == 1) {
            deleteSongFromMyFavoriteList();
        } else {
            menu();
        }
    }

    // EFFECTS: delete the song from user's favorite list if the song is found.
    // Otherwise, return to the favorite list panel.
    public void deleteSongFromMyFavoriteList() {
        printm("\nPlease enter the title: ");
        in.nextLine();
        String msg = in.nextLine();
        Song s = getMyFavoriteSongList().findSongByTitle(msg);
        if (s != null) {
            getMyFavoriteSongList().deleteSong(s.getTitle());
            printm("\n" + msg + " is deleted.");
            favoriteList();
        } else {
            printm("\n" + msg + " is not found.");
            favoriteList();
        }
    }

    // EFFECTS: if the list is not empty, printing the title, author, genre, and
    // duration of each song. Otherwise, printing a message that the list is empty
    // and return to the menu.
    public void printSongInfo(SongList list) {
        if (list.getSize() == 0) {
            printm("\nNo songs in this list!");
            menu();
        } else {
            for (Song s : list.getSongs()) {
                printm("Song Title: " + s.getTitle());
                printm("    Author: " + s.getAuthor());
                printm("    Genre: " + s.getGenre());
                printm("    Duration: " + s.getDuration() + "\n");
            }
        }
    }
}