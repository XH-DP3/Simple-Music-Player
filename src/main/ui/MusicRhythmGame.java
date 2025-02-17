package ui;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Buttons;
import model.FavoriteSongList;
import model.Song;
import model.SongList;

// Represent a music rhythm game application with a general music library,
// my song list, my favorite song list, and buttons.
public class MusicRhythmGame {
    Scanner in;
    String username;
    SongList musicLibrary;
    SongList mySongList;
    FavoriteSongList myFavoriteList;
    Buttons buttons;

    // EFFECTS: intialize the fields and invoke the menu
    public MusicRhythmGame() {
        setup();
        printm("Wecome to the game!");
        println();
        printm("Please enter your username: ");
        println();
        username = in.nextLine();
        println();
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

    // EFFECTS: printing the menu// EFFECTS: showing the menu panel and receives an
    // input from user about the
    // next command. It will invoke the evaluateInputForMenu() and handle the
    // potential InputMismatchException().
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
    // thrown by menu
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
        } else {
            quit();
        }
    }

    // REQUIRES: user's song list is not empty
    // EFFECTS: start the game if the song title is found in either song list or
    // favorite song list. Otherwise, print an error message and reask for input.
    public void startHelper() {
        if (getSongList().getSize() == 0) {
            println();
            printm("Your music list is empty. Please add songs to your list first.");
            menu();
        } else {
            println();
            printSongInfo(getMusicLibrary());
            printm("Please enter the title of the song that you want to play: ");
            in.nextLine();
            String msg = in.nextLine();
            Song s = getSongList().findSongByTitle(msg);
            if (s != null) {
                start(s);
            } else {
                printm(msg + " is not found");
                startHelper();
            }
        }
    }

    // EFFECTS: starts the game and evaluate if the key press from user is correrct.
    // If the key press is correct, print an appropriate message and show the total
    // points the user received. Otherwise, print an error message.
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
            println();
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

    // EFFECTS: generating the next falling buttons that the user has to press
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

    // EFFECTS: finish the game and show the total point that the user received.
    // Then return to the menu
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

    // EFFECTS: return the music library
    public SongList getMusicLibrary() {
        return musicLibrary;
    }

    // EFFECTS: show the music library panel and ask for input from user.
    public void musicLibrary() {
        while (true) {
            try {
                in = new Scanner(System.in);
                println();
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

    // EFFECTS: evaluate the input from the user for music library. If the input is
    // valid, invoking the corresponding panel. Otherwise, throw new
    // InputMismatchException()
    public void evaluateInputForMusicLibrary(int input) throws InputMismatchException {
        if (input == 1) {
            addSongListHelper();
        } else if (input == 2) {
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
            println();
            printm(mySong.getTitle() + " is added to you song list!");
            getMusicLibrary();
        } else {
            println();
            printm("The song is already in you song list.");
            getMusicLibrary();
        }
    }

    // EFFECTS: if the title is found from the music library, then invoke
    // addSongToMSongList() method. Else throw new InputMismatchException(). This
    // method will also handle the potential SongAlreadyExistsException thrown by
    // addSongToMySongList().
    public void addSongListHelper() {
        while (true) {
            try {
                in = new Scanner(System.in);
                println();
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
                printm("Invalid input.");
                musicLibrary();
            }
        }
    }

    // EFFECTS: delete the song from the user's song list if the input title is
    // found and print a success message. Otherwise, print an error message and
    // return to the song list panel.
    public void deleteSongFromMySongList() {
        println();
        in.nextLine();
        printm("Please enter the title: ");
        String msg = in.nextLine();
        Song s = getSongList().findSongByTitle(msg);
        if (s != null) {
            getSongList().deleteSong(s.getTitle());
            println();
            printm(msg + " is deleted");
            songList();
        } else {
            println();
            printm(msg + " is not found");
            songList();
        }
    }

    // EFFECTS: invoke the song list panel and hanle the potential
    // InputMismatchException thrown by song list panel.
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

    // EFFECTS: show the song list panel. If the user enters a valid input, invoke
    // the evaluateInputForSongList() method. Otherwise, throw new
    // InputMismatchException()
    public void songList() {
        println();
        printm("Your song list has: ");
        println();
        printSongInfo(mySongList);
        printm("Please select one of the following by typing a valid integer");
        printm("1. Add song to your favorite list");
        printm("2. Remove song from your song list");
        printm("3. Return to the menu");
        int input = in.nextInt();
        if (input < 1 || input > 3) {
            throw new InputMismatchException();
        }
        evaluateInputForSongList(input);
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

    // EFFECTS: return the song list.
    public SongList getSongList() {
        return mySongList;
    }

    // EFFECTS: if the title is found from the song list, then invoke
    // addSongToMyFavoriteSongList() method. It will handle the
    // SongAlreadyExistsException and SongIsNotFavoriteException thrown by
    // addSongToMyFavoriteSongList() method. And for the latter, it will ask if the
    // user want to mark the song as favorite.
    public void addSongToMyFavoriteSongListHelper() {
        String title = in.nextLine();
        Song s = getMusicLibrary().findSongByTitle(title);
        while (true) {
            try {
                if (s != null && s.isFavorite()) {
                    addSongToMyFavoriteSongList(s);
                }
            } catch (InputMismatchException e) {
                printm("The song title is not found");
                favoriteList();
            }
        }
    }

    // EFFECTS: a helper method that invokes favorite list panel and handle the
    // potential InputMismatchException thrown by it.
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

    // EFFECTS: add song to my favorite song list and print a successful message.
    // Otherwise, determines if the song is already exists, or the song is not
    // favorite, and thrown the corresponding exception.
    public void addSongToMyFavoriteSongList(Song mySong) {
        if (myFavoriteList.addSong(mySong)) {
            printm(mySong.getTitle() + " is added to your favorite song list!");
        } else {
            if (!mySong.isFavorite()) {
                printm("The song is not marked as favorite. Marked as favorite? (yes/no)?");
                String msg = in.nextLine();
                if (msg.equalsIgnoreCase("Yes")) {
                    mySong.markedAsFavorite();
                    printm("Marked as favorite. Now try add the song again.");
                    favoriteList();
                } else {
                    printm("The song is already in you song list.");
                    favoriteList();
                }

            }
        }
    }

    // EFFECTS: show my favorite list panel
    public void favoriteList() throws InputMismatchException {
        println();
        printm("Your favorite song list has: ");
        printSongInfo(myFavoriteList);
        printm("Please select one of the following by typing a valid integer: ");
        printm("1. Remove an existing song from your favorite list by typing the title");
        printm("2. Return to the menu");
        int input = in.nextInt();
        if (input < 1 || input > 2) {
            throw new InputMismatchException();
        }
        evaluateInputForFavoriteList(input);
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
        println();
        printm("Please enter the title: ");
        in.nextLine();
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

    // EFFECTS: return the favorite song list.
    public FavoriteSongList getMyFavoriteSongList() {
        return myFavoriteList;
    }

    // EFFECTS: return the total points the player got
    public int getTotalPoint() {
        return buttons.getTotalPressedPoints();
    }

    // EFFECTS: if the list is not empty, printing the title, author, genre, and
    // duration of each song. Otherwise, printing a message that the list is empty
    // and return to the menu.
    public void printSongInfo(SongList list) {
        if (list.getSize() == 0) {
            printm("No songs in this list!");
            menu();
        } else {
            for (Song s : list.getSongs()) {
                printm("Song Title: " + s.getTitle());
                printm("    Author: " + s.getAuthor());
                printm("    Genre: " + s.getGenre());
                printm("    Duration: " + s.getDuration());
                println();
            }
        }
    }
}