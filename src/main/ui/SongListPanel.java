package ui;

import java.io.IOException;
import java.util.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.FavoriteSongList;
import model.Song;
import model.SongList;

// Represents the songlist panel page
public class SongListPanel {
    private Scanner in;
    private SongList musicLibrary;
    private SongList mySongList;
    private SongList myFavoriteList;
    private SongList availableList;
    private Song currentPlayingSong;
    private MainMenu mainMenu;

    private Song s1 = new Song("Payphone", "Maroon 5", "Pop", 231);
    private Song s2 = new Song("Everybody Hurts", "Avril Lavigne", "Pop", 221);
    private Song s3 = new Song("Innocence", "Avril Lavigne", "Pop", 233);
    private Song s4 = new Song("Whataya Want from Me", "Adam Lambert", "Pop", 227);
    private Song s5 = new Song("Like I Do", "J.Tajor", "R&B", 149);
    private String path1 = "data/21 Payphone.wav";
    private String path2 = "data/08 Everybody Hurts.wav";
    private String path3 = "data/08 Innocence.wav";
    private String path4 = "data/03 Whataya Want from Me.wav";
    private String path5 = "data/01 Like I Do (with sunkis).wav";

    // EFFECTS: construct a SongListPanel with the given main menu
    public SongListPanel(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        musicLibrary = new SongList();
        mySongList = new SongList();
        myFavoriteList = new FavoriteSongList();
        availableList = new SongList();
        musicLibrary.addSong(s1);
        musicLibrary.addSong(s2);
        musicLibrary.addSong(s3);
        musicLibrary.addSong(s4);
        musicLibrary.addSong(s5);
        availableList.addSong(s1);
        availableList.addSong(s2);
        availableList.addSong(s3);
        availableList.addSong(s4);
        availableList.addSong(s5);
    }

    // constructs the song list panel with several "built in" songs.
    public SongListPanel() {
        musicLibrary = new SongList();
        mySongList = new SongList();
        myFavoriteList = new FavoriteSongList();
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

    // EFFECTS: a helper method that will invoke the menu
    private void menu() {
        mainMenu.menu();
    }

    // EFFECTS: return the music library
    public SongList getMusicLibrary() {
        return musicLibrary;
    }

    // EFFECTS: return the song list.
    public SongList getSongList() {
        return mySongList;
    }

    // EFFECTS: return the favorite list.
    public SongList getMyFavoriteList() {
        return myFavoriteList;
    }

    // EFFECTS: return the total points the player got
    public int getTotalPoints() {
        return getCurrentPlayingSong().getTotalPoints();
    }

    // EFFECTS: return the song object that the user is playing
    public Song getCurrentPlayingSong() {
        return currentPlayingSong;
    }

    // MODIFIES: this
    // EFFECTS: set song as the current playing song
    public void setCurrentPlayingSong(Song song) {
        currentPlayingSong = song;
        currentPlayingSong.playSong();
    }

    // EFFECTS: a helper method that will print message.
    private void printm(String message) {
        System.out.println(message);
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
                printm("    Duration: " + s.getDuration());
                printm("    Record: " + s.getRecord());
                printm("    Playing times: " + s.getPlayingTimes() + "\n");
            }
        }
    }

    // If the music library is not empty, print the general info for each song in
    // the music library
    public void printSongInfoForMusicLibrary() {
        if (getMusicLibrary().getSize() == 0) {
            printm("\nNo songs in this list!");
            menu();
        } else {
            for (Song s : getMusicLibrary().getSongs()) {
                printm("Song Title: " + s.getTitle());
                printm("    Author: " + s.getAuthor());
                printm("    Genre: " + s.getGenre());
                printm("    Duration: " + s.getDuration() + "\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: show the music library panel and ask for input from user.
    public void musicLibrary() {
        try {
            in = new Scanner(System.in);
            printm("\nBelow are currently available songs: \n");
            printSongInfoForMusicLibrary();
            printm("Please select one of the following by typing a valid integer:");
            printm("1. Add song to my song list.");
            printm("2. Add new song to the music library.");
            printm("3. Play the music");
            printm("4. Return to the menu.");
            int input = in.nextInt();
            mainMenu.checkValidInput(input, 1, 4);
            evaluateInputForMusicLibrary(input);
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            musicLibrary();
        }
    }

    // EFFECTS: evaluate the input from the user for music library. If the input is
    // valid, invoking the corresponding panel. Otherwise, throw new
    // InputMismatchException()
    private void evaluateInputForMusicLibrary(int input) {
        if (input == 1) {
            addSongListHelper();
        } else if (input == 2) {
            addNewSongToMusicLibrary();
        } else if (input == 3) {
            playHepler();
        } else {
            menu();
        }
    }

    // MODIFIES: this
    // EFFECTS: ask the user which song they want to play
    public void playHepler() {
        try {
            in = new Scanner(System.in);
            printm("\nBelow are the available songs to play: \n");
            printSongInfo(availableList);
            printm("\nPlease enter the song title of the song you want to play: ");
            String title = in.nextLine();
            Song s = availableList.findSongByTitle(title);
            if (s == null) {
                printm("The song is not found");
                menu();
            }
            findAvailableSong(s);
        } catch (LineUnavailableException e) {
            printm(e.getMessage());
            menu();
        } catch (UnsupportedAudioFileException e) {
            printm(e.getMessage());
            menu();
        } catch (IOException e) {
            printm(e.getMessage());
            menu();
        }
    }

    // MODIFIES: this
    // EFFECTS: create a music player object and will play the corresponding song
    private void findAvailableSong(Song s) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        if (s.equals(s1)) {
            new MusicPlayer(path1, mainMenu, this);
        } else if (s.equals(s2)) {
            new MusicPlayer(path2, mainMenu, this);
        } else if (s.equals(s3)) {
            new MusicPlayer(path3, mainMenu, this);
        } else if (s.equals(s4)) {
            new MusicPlayer(path4, mainMenu, this);
        } else if (s.equals(s5)) {
            new MusicPlayer(path5, mainMenu, this);
        }
    }

    // EFFECTS: add song to the song list. If the song is added sucessfully, print a
    // success message and return to the music library panel. Otherwise, throw new
    // SongAlreadyExistsException()
    private void addSongToMySongList(Song mySong) {
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
    private void addNewSongToMusicLibrary() {
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
    private void addSongListHelper() {
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
    private void deleteSongFromMySongList() {
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
            in = new Scanner(System.in);
            printm("\nYour song list has: \n");
            printSongInfo(mySongList);
            printm("Please select one of the following by typing a valid integer\n");
            printm("1. Add song to your favorite list.");
            printm("2. Remove song from your song list.");
            printm("3. Play the music");
            printm("4. Return to the menu.");
            int input = in.nextInt();
            mainMenu.checkValidInput(input, 1, 4);
            evaluateInputForSongList(input);
        } catch (InputMismatchException e) {
            printm("\nPlease enter a valid integer.");
            songList();
        }
    }

    // EFFECTS: evaluate the input from user for the song list panel. If the input
    // is valid, invoke the corresponding methods. Otherwise, throw new
    // InputMismatchException()
    private void evaluateInputForSongList(int input) {
        if (input == 1) {
            addSongToMyFavoriteSongListHelper();
        } else if (input == 2) {
            deleteSongFromMySongList();
        } else if (input == 3) {
            playHepler();
        } else {
            menu();
        }
    }

    // EFFECTS: if the title is found from the song list, then invoke
    // addSongToMyFavoriteSongList() method. It will handle the
    // SongAlreadyExistsException and SongIsNotFavoriteException thrown by
    // addSongToMyFavoriteSongList() method. And for the latter, it will ask if the
    // user want to mark the song as favorite.
    private void addSongToMyFavoriteSongListHelper() {
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
    private void addSongToMyFavoriteSongList(Song mySong) {
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
            printm("2. Play the music");
            printm("3. Return to the menu.");
            int input = in.nextInt();
            mainMenu.checkValidInput(input, 1, 3);
            evaluateInputForFavoriteList(input);
        } catch (InputMismatchException e) {
            printm("\nInvalid input. Please try again.");
            favoriteList();
        }
    }

    // EFFECTS: evaluate input from user for the favorite list panel. If the input
    // is valid, invoke the corresponding method.
    private void evaluateInputForFavoriteList(int input) {
        if (input == 1) {
            deleteSongFromMyFavoriteList();
        } else if (input == 2) {
            playHepler();
        } else {
            menu();
        }
    }

    // EFFECTS: delete the song from user's favorite list if the song is found.
    // Otherwise, return to the favorite list panel.
    private void deleteSongFromMyFavoriteList() {
        printm("\nPlease enter the title: ");
        in.nextLine();
        String msg = in.nextLine();
        Song s = getMyFavoriteList().findSongByTitle(msg);
        if (s != null) {
            getMyFavoriteList().deleteSong(s.getTitle());
            printm("\n" + msg + " is deleted.");
            favoriteList();
        } else {
            printm("\n" + msg + " is not found.");
            favoriteList();
        }
    }

    // EFFECTS: add every elements in prevList to currentList
    public void merge(SongList prevList, SongList currentList) {
        for (Song s : prevList.getSongs()) {
            currentList.addSong(s);
        }
    }

}
