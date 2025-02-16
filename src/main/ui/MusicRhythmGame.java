package ui;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Buttons;
import model.FavoriteSongList;
import model.Song;
import model.SongAlreadyExistsException;
import model.SongIsNotFavoriteException;
import model.SongList;

//represent a music rhythm game application
public class MusicRhythmGame {
    Scanner in;
    String username;
    SongList musicLibrary;
    SongList mySongList;
    FavoriteSongList myFavoriteList;
    Buttons buttons;

    // EFFECTS: start the game
    public MusicRhythmGame() {
        setup();
        printm("Wecome to the game!");
        println();
        printm("Please enter your username: ");
        println();
        username = in.nextLine();
        printm("Hello " + username);
        menuHelper();
    }

    // Initialize the fields.
    public void setup() {
        in = new Scanner(System.in);
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

    // EFFECTS: a helper method that will print an empty line
    public void println() {
        System.out.println();
    }

    // EFFECTS: a helper method that will print message.
    public void printm(String message) {
        System.out.println(message);
    }

    // EFFECTS: printing the menu
    public void menu() throws InputMismatchException {
        in = new Scanner(System.in);
        println();
        printm("Please select one of the following by entering an integer: ");
        printm("1. Playing the game");
        printm("2. Check music library");
        printm("3. Check your song list");
        printm("4. Check your favorite song list");
        printm("5. Quit the program");
        int input = in.nextInt();
        if (input < 1 || input > 5) {
            throw new InputMismatchException();
        }
        evaluateInputForMenu(input);
    }

    // EFFECTS: it will call menu and handle the potential InputMismatchException
    public void menuHelper() {
        while (true) {
            try {
                menu();
                break;
            } catch (InputMismatchException e) {
                printm("Please enter a valid integer.");
                continue;
            }
        }
    }

    // EFFECTS: it will evaluate input and call the corresponding method
    public void evaluateInputForMenu(int input) {
        if (input == 1) {
            startHelper();
        } else if (input == 2) {
            musicLibrary();
        } else if (input == 3) {
            songList();
        } else if (input == 4) {
            favoriteList();
        } else {
            quit();
        }
    }

    // REQUIRES:
    // EFFECTS: start the game
    public void startHelper() {
        if (getSongList().getSize() == 0) {
            printm("Your music list is empty. Please add songs to your list first.");
            menu();
        }
        println();
        printSongInfo(getMusicLibrary());
        printm("Please enter the title of the song that you want to play: ");
        String msg = in.nextLine();
        Song s = getSongList().findSongByTitle(msg);
        if (s != null) {
            start(s);
        } else {
            printm(msg + " is not found");
            startHelper();
        }
    }

    public void start(Song mySong) {
        println();
        printm("Game is starting");
        printm("Playing " + mySong.getTitle());
        mySong.playSong();
        boolean isOver = false;
        while (!isOver) {
            int random = (int) (Math.random() * 8);
            generatingButtons(random);
            printm("(Type Q to quit the game)");
            printm("The next falling button is: " + buttons.getNextFallingButton());
            println();
            printm("Press keys: " + Arrays.toString(buttons.getFixedButtons()));
            String msg = in.nextLine();
            if (buttons.checkKeyPress(msg)) {
                printm("Good job! You got " + getTotalPoint());
            } else if (msg.equals("Q")) {
                isOver = true;
                end();
            } else {
                printm("Wrong key press!");
            }
        }
    }

    public void generatingButtons(int random) {
        if (random == 0) {
            buttons.setNextKeyPress("A");
        } else if (random == 1) {
            buttons.setNextKeyPress("S");
        } else if (random == 2) {
            buttons.setNextKeyPress("D");
        } else if (random == 4) {
            buttons.setNextKeyPress("F");
        } else if (random == 5) {
            buttons.setNextKeyPress("J");
        } else if (random == 6) {
            buttons.setNextKeyPress("K");
        } else if (random == 7) {
            buttons.setNextKeyPress("L");
        } else {
            buttons.setNextKeyPress(";");
        }
    }

    // EFFECTS: finish the game
    public void end() {
        println();
        printm("Game ends. Yout got " + getTotalPoint() + " points");
        menu();
    }

    // EFFECTS: quit the program
    public void quit() {
        println();
        printm("Program ends. Bye!");
        System.exit(1);
    }

    // EFFECTS: return the "available songs" object as a list
    public SongList getMusicLibrary() {
        return musicLibrary;
    }

    // EFFECTS: show the music library panel
    public void musicLibrary() {
        while (true) {
            try {
                in = new Scanner(System.in);
                printm("Below are currently available songs: ");
                printSongInfo(getMusicLibrary());
                printm("Please select one of the following by typing a valid integer:");
                printm("1. Add song to my song list.");
                printm("2. Return to the menu");
                int input = in.nextInt();
                evaluateInputForMusicLibrary(input);
            } catch (InputMismatchException e) {
                println();
                printm("Invalid input, please try again");
                continue;
            }
        }
    }

    public void evaluateInputForMusicLibrary(int input) throws InputMismatchException {
        if (input == 1) {
            addSongListHelper();
        } else if (input == 2) {
            menu();
        } else {
            throw new InputMismatchException();
        }
    }

    // EFFECTS: add song to the song list
    public void addSongToMySongList(Song mySong) throws SongAlreadyExistsException {
        if (mySongList.addSong(mySong)) {
            printm(mySong.getTitle() + " is added to you song list!");
            getMusicLibrary();
        } else {
            throw new SongAlreadyExistsException();
        }
    }

    // EFFECTS: if the title is found, then add the song to my song list.
    public void addSongListHelper() {
        while (true) {
            try {
                in = new Scanner(System.in);
                printm("Please enter the song title: ");
                String msg = in.nextLine();
                Song s = getMusicLibrary().findSongByTitle(msg);
                if (s != null) {
                    addSongToMySongList(s);
                    break;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                printm("Invalid input. Try again");
                continue;
            } catch (SongAlreadyExistsException e) {
                printm("The song is already in you song list.");
                continue;
            }
        }
    }

    public void deleteSongFromMySongList() {
        println();
        printm("Please enter the title: ");
        String msg = in.nextLine();
        Song s = getSongList().findSongByTitle(msg);
        if (s != null) {
            getSongList().deleteSong(s.getTitle());
            printm(msg + " is deleted");
            songList();
        } else {
            printm(msg + " is not found");
            songList();
        }
    }

    public void songListHelper() {
        while (true) {
            try {
                songList();
                break;
            } catch (InputMismatchException e) {
                printm("Please enter a valid integer.");
                continue;
            }
        }
    }

    // EFFECTS: show my song list panel
    public void songList() {
        println();
        printm("Your song list has: ");
        printSongInfo(mySongList);
        printm("Please select one of the following by typing a valid integer");
        printm("1. Add song to your favorite list");
        printm("2. Remove song from your song list");
        printm("3. Return to the menu");
        int input = in.nextInt();
        if (input < 1 || input > 3) {
            throw new InputMismatchException();
        }
        evaluateInputForFavoriteList(input);
    }

    public void evaluateInputForSongList(int input) {
        if (input == 1) {
            addSongToMyFavoriteSongListHelper();
        } else if (input == 2) {
            deleteSongFromMySongList();
        } else {
            throw new InputMismatchException();
        }
    }

    public SongList getSongList() {
        return mySongList;
    }

    public void addSongToMyFavoriteSongListHelper() {
        String title = in.nextLine();
        Song s = getMusicLibrary().findSongByTitle(title);
        while (true) {
            try {
                if (s != null && s.isFavorite()) {
                    addSongToMyFavoriteSongList(s);
                }
            } catch (SongAlreadyExistsException e) {
                printm("The song is already in you song list.");
            } catch (SongIsNotFavoriteException e) {
                printm("The song is not marked as favorite. Marked as favorite? (yes/no)?");
                String msg = in.nextLine();
                if (msg.equalsIgnoreCase("Yes")) {
                    s.markedAsFavorite();
                    printm("Marked as favorite. Now try add the song again.");
                }
            }
        }
    }

    public void favoriteSongListHelper(String title) {
        while (true) {
            try {
                favoriteList();
                break;
            } catch (InputMismatchException e) {
                printm("Please enter a valid integer.");
                continue;
            }
        }
    }

    public void addSongToMyFavoriteSongList(Song mySong) throws SongAlreadyExistsException, SongIsNotFavoriteException {
        if (mySongList.addSong(mySong)) {
            printm(mySong.getTitle() + " is added to your favorite song list!");
        } else {
            if (!mySong.isFavorite()) {
                throw new SongIsNotFavoriteException();
            } else {
                throw new SongAlreadyExistsException();
            }
        }
    }

    // EFFECTS: show my favorite list
    public void favoriteList() throws InputMismatchException {
        println();
        printm("Your favorite song list has: ");
        printSongInfo(mySongList);
        printm("Please select one of the following by typing a valid integer: ");
        printm("1. Remove an existing song from your favorite list by typing the title");
        printm("2. Return to the menu");
        int input = in.nextInt();
        if (input < 1 || input > 2) {
            throw new InputMismatchException();
        }
        evaluateInputForFavoriteList(input);
    }

    public void evaluateInputForFavoriteList(int input) {
        if (input == 1) {
            deleteSongFromMyFavoriteList();
        } else {
            menu();
        }
    }

    public void deleteSongFromMyFavoriteList() {
        println();
        printm("Please enter the title: ");
        String msg = in.nextLine();
        Song s = getMyFavoriteSongList().findSongByTitle(msg);
        if (s != null) {
            getMyFavoriteSongList().deleteSong(s.getTitle());
            printm(msg + " is deleted");
            favoriteList();
        } else {
            printm(msg + " is not found");
            favoriteList();
        }
    }

    public FavoriteSongList getMyFavoriteSongList() {
        return myFavoriteList;
    }

    // EFFECTS: return the total points the player got
    public int getTotalPoint() {
        return buttons.getTotalPressedPoints();
    }

    // EFFECTS: printing the information of the song
    public void printSongInfo(SongList list) {
        if (list.getSize() == 0) {
            printm("No songs in this list!");
            menu();
        } else {
            for (Song s : list.getSongList()) {
                printm("Song Title: " + s.getTitle());
                printm("    Author: " + s.getAuthor());
                printm("    Genre: " + s.getGenre());
                printm("    Duration: " + s.getDuration());
                println();
            }
        }
    }
}
