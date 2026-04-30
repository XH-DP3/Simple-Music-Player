# Interactive Music Player with Rhythm Game

## Feel the music, Feel the Rhythm

A simple music player that allows users to play and enjoy built-in songs. A lightweight and intuitive music player designed for an easy listening experience.

- Provides a clean interface for selecting and playing tracks.
- Displays basic information such as song title and artist.
- Supports pause, resume, and stop controls for smooth playback.

## User Stories

- As a user, I want to be able to upload an arbitrary number of songs to my song list and my favorite song list
- As a user, I want to be able to see my song list, and my favorite song like
- As a user, I want to be able to view my scores after ending the game
- As a user, I want to be able to see the feedback of each key presses
- As a user,  when I select the quit option from the game menu,  I want to be reminded to save my progress and song lists and have the option to do so or not
- As a user, I want to have the option to reload my previous progress and song lists and resume exactly where they left off when I quit the game.


# Instructions for End User

- You can "add an X to Y" by clicking the "Check music library" button to access the available songs. Then, click on the "Add song to your song list" button, then click on the button of the song you like to add a song to your song list.
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by first adding several songs to your song list and going to your song list by clicking the "Check your song list" button. Then click on "Sort your song list" to perform either sorting the songs from the lowest duration to the highest duration or sorting the songs from the highest duration to the lowest duration.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by first adding several songs to your song list. Then, you can delete the added songs from your song list by clicking the "Delete song from your song list.” You can then select the song by clicking the checkbox and delete the song by clicking "Delete.”
- You can locate my visual component by accessing the music library and the song list, where the visual component is represented by the album cover of each song.
- You can save the state of my application by clicking on the "Quit the program" button in the main menu. There will be a window pop out and ask you whether you would like to save your progress. If you click "yes,” then you can select the list you wish to save.
- You can reload the state of my application by restarting the program and clicking on the "Reload your saved lists" button in the main menu. You can select the song list you wish to reload. If your current song list is not empty, then a window will pop out and ask you whether you wish to merge your previous saved song list and your current song list. If yes, then your two song lists will be merged, and otherwise, your current song list will be preserved.
- You can play the music by going to either the music library or one of your song lists and clicking the "play song" button. You can control the state of the song by clicking the buttons presented in the music player panel after selecting the song.

## Image Citation
- https://en.wikipedia.org/wiki/Payphone_(song)
- https://avrillavigne.fandom.com/wiki/Everybody_Hurts
- https://open.spotify.com/track/5OuP08bgU2H7ZeDQQFQ6q8
- https://open.spotify.com/album/57arTya5WF8bnNtKSENgRJ
- https://www.deezer.com/us/album/530941612


## Phase 4: Task 2
- Here is a representative sample of the events that occur when the program runs:

Mon Mar 24 15:52:28 PDT 2025
Default action: Payphone is added to music library

Mon Mar 24 15:52:28 PDT 2025
Default action: Everybody Hurts is added to music library

Mon Mar 24 15:52:28 PDT 2025
Default action: Innocence is added to music library

Mon Mar 24 15:52:28 PDT 2025
Default action: Whataya Want from Me is added to music library

Mon Mar 24 15:52:28 PDT 2025
Default action: Like I Do is added to music library

Mon Mar 24 15:52:32 PDT 2025
Payphone is added to song list.

Mon Mar 24 15:52:33 PDT 2025
Everybody Hurts is added to song list.

Mon Mar 24 15:52:33 PDT 2025
Whataya Want from Me is added to song list.

Mon Mar 24 15:52:37 PDT 2025
Song list is sorted by the lowest duration.

Mon Mar 24 15:52:39 PDT 2025
Song list is sorted by the highest duration.

Mon Mar 24 15:52:43 PDT 2025
Whataya Want from Me is deleted from song list.

Mon Mar 24 15:52:43 PDT 2025
Everybody Hurts is deleted from song list.


## Phase 4: Task 3
Suppose I have more time to work on the project. In that case, I will make SongListGUI to be a subclass of MusicLibraryGUI to avoid duplicate methods because these two classes have very similar behaviours, which include adding songs, playing songs, and potentially some other functionalities. So, having this structure will follow a good design principle. In addition to this, I will make a separate class that deals with layout and format when displaying songs in each panel. For now, this is done by some methods implemented in each class with similar code, so extracting them and putting them into a separate class could make each class more cohesive.

On the other hand, I may probably make some methods to be static to make sure they can be invoked outside of the class and without instantiating an instance of that type. For example, the main menu panel will be used in almost every page to let the user return to the very beginning page. But because of this, I have a reference of MainMenuGUI in some classes, which increases the level of coupling. When I was working on the project, I also realized that, a small adjustment of a class can make other classes that are associated with it or depend on it to crash. So, the current implementation may not be the best choice. Although I have already separated a single classes into several classes to maintain cohesion, I still need to find a way to minimize coupling.# CPSC-210-Software-Construction-Project

## Updates After Course Deadline

This repository continued to receive improvements after the original CPSC 210 submission deadline.

- Added a dedicated rhythm game page (`RhythmGameGUI` + `RhythmGamePanel`) without breaking core music-player features.
- Added difficulty options with different lane counts and note density tuning.
- Added keyboard pause/resume support using `SPACE`.
- Added visual key-press feedback (lane flash effect).
- Added rhythm-game session history persistence (`save`/`reload`) and historical highest-score display.
- Improved UI layout and status messaging across the menu and game pages.

## How To Run This Program

### Prerequisites

- Java 11 or newer (`javac` and `java` available in terminal).

### Compile

From the project root, run:

```bash
javac -cp lib/json-20240303.jar -d bin $(find src/main -name '*.java')
```

### Start the App

From the project root, run:

```bash
java -cp bin:lib/json-20240303.jar ui.Main
```

Notes:
- On Windows Command Prompt, replace `:` with `;` in the classpath:

```bat
java -cp bin;lib/json-20240303.jar ui.Main
```
- Audio/image assets are loaded from the `data/` folder, so run commands from the project root directory.
