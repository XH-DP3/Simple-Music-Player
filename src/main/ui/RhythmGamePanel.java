package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import model.RhythmGameHistory;
import model.RhythmGameResult;
import model.Song;

// Represents a rhythm game panel with falling notes synchronized to song playback.
public class RhythmGamePanel extends JPanel {
    private static final String[] LANE_KEYS = { "D", "F", "J", "K" };
    private static final int FRAME_DELAY_MS = 16;
    private static final int NOTE_SIZE = 34;
    private static final int TARGET_LINE_BOTTOM_OFFSET = 90;
    private static final int BASE_PERFECT_WINDOW_MS = 70;
    private static final int BASE_GOOD_WINDOW_MS = 115;
    private static final int BASE_OK_WINDOW_MS = 155;
    private static final int BASE_MISS_WINDOW_MS = 190;
    private static final int BASE_MAX_HIT_WINDOW_MS = 165;
    private static final int LANE_FLASH_TICKS = 8;
    private static final double PIXELS_PER_MS = 0.32;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Color BACKGROUND = new Color(23, 31, 46);
    private static final Color LANE_DARK = new Color(33, 43, 61);
    private static final Color LANE_LIGHT = new Color(40, 51, 72);
    private static final Color LANE_FLASH = new Color(130, 207, 255, 150);
    private static final Color HIT_ZONE_FLASH = new Color(255, 211, 122, 165);
    private static final Color HIT_LINE = new Color(108, 200, 255);
    private static final Color NOTE_COLOR = new Color(255, 181, 88);
    private static final Color TEXT = new Color(236, 244, 255);
    private static final String SPACE_HINT = "Tip: Press SPACE to pause/resume.";

    private final List<BeatNote> beatNotes;
    private final Timer timer;
    private final RhythmGameHistory history;
    private final int[] laneFlashTicks;
    private Song loadedSong;
    private Clip clip;
    private boolean running;
    private boolean paused;
    private int score;
    private int combo;
    private int bestCombo;
    private int misses;
    private int perfectCount;
    private int goodCount;
    private int okCount;
    private String judgement;
    private int judgementTicks;
    private String statusMessage;
    private Difficulty difficulty;
    private boolean sessionSavedForCurrentRound;
    private String loadedDifficultyName;

    // EFFECTS: constructs a panel and configures key bindings and timer.
    public RhythmGamePanel(RhythmGameHistory history) {
        beatNotes = new ArrayList<>();
        timer = new Timer(FRAME_DELAY_MS, this::onFrame);
        this.history = history;
        laneFlashTicks = new int[LANE_KEYS.length];
        setBackground(BACKGROUND);
        setFocusable(true);
        judgement = "";
        statusMessage = "Select a song and press Start.";
        difficulty = Difficulty.NORMAL;
        loadedDifficultyName = null;
        configureKeyBindings();
    }

    // MODIFIES: this
    // EFFECTS: sets rhythm game difficulty.
    public void setDifficulty(String difficultyName) {
        if ("Easy".equalsIgnoreCase(difficultyName)) {
            difficulty = Difficulty.EASY;
        } else if ("Hard".equalsIgnoreCase(difficultyName)) {
            difficulty = Difficulty.HARD;
        } else {
            difficulty = Difficulty.NORMAL;
        }
    }

    // EFFECTS: returns current difficulty label.
    public String getDifficultyName() {
        return difficulty.label;
    }

    // EFFECTS: returns difficulty used for current loaded beatmap.
    public String getLoadedDifficultyName() {
        return loadedDifficultyName;
    }

    // MODIFIES: this
    // EFFECTS: loads a song and generates song-specific beat notes.
    public boolean loadSong(Song song) {
        if (song == null || song.getMelodyFilePath() == null) {
            statusMessage = "Selected song has no audio file.";
            return false;
        }
        finalizeSessionIfNeeded();
        stopGame();
        closeClip();
        loadedSong = song;
        beatNotes.clear();
        beatNotes.addAll(buildBeatmap(song));
        resetRoundStats();
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(new File(song.getMelodyFilePath()))) {
            clip = AudioSystem.getClip();
            clip.open(stream);
            loadedDifficultyName = difficulty.label;
            statusMessage = "Loaded: " + song.getTitle();
            repaint();
            return true;
        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            statusMessage = "Audio format is not supported for " + song.getTitle() + ".";
        } catch (Exception e) {
            statusMessage = "Failed to load audio for " + song.getTitle() + ".";
        }
        loadedSong = null;
        loadedDifficultyName = null;
        clip = null;
        repaint();
        return false;
    }

    // EFFECTS: returns the currently loaded song, or null.
    public Song getLoadedSong() {
        return loadedSong;
    }

    // EFFECTS: returns latest panel status text.
    public String getStatusMessage() {
        return statusMessage;
    }

    // MODIFIES: this
    // EFFECTS: starts game loop and audio playback.
    public boolean startGame() {
        if (clip == null) {
            statusMessage = "Load a song first.";
            return false;
        }
        if (running && paused) {
            paused = false;
            timer.start();
            clip.start();
            statusMessage = "Resumed: " + loadedSong.getTitle();
            return true;
        }
        if (!running) {
            running = true;
            paused = false;
            timer.start();
            clip.start();
            statusMessage = "Now playing: " + loadedSong.getTitle();
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: restarts current loaded song and resets score stats.
    public boolean restartGame() {
        if (clip == null) {
            statusMessage = "Load a song first.";
            return false;
        }
        finalizeSessionIfNeeded();
        for (BeatNote note : beatNotes) {
            note.judged = false;
        }
        resetRoundStats();
        clip.stop();
        clip.setMicrosecondPosition(0);
        running = true;
        paused = false;
        timer.start();
        clip.start();
        statusMessage = "Restarted: " + loadedSong.getTitle();
        repaint();
        return true;
    }

    // MODIFIES: this
    // EFFECTS: pauses game and music while keeping current run state.
    public boolean pauseGame() {
        if (!running || paused || clip == null) {
            return false;
        }
        paused = true;
        timer.stop();
        clip.stop();
        statusMessage = "Paused: " + loadedSong.getTitle();
        repaint();
        return true;
    }

    // MODIFIES: this
    // EFFECTS: resumes a paused game.
    public boolean resumeGame() {
        if (!running || !paused || clip == null) {
            return false;
        }
        paused = false;
        timer.start();
        clip.start();
        statusMessage = "Resumed: " + loadedSong.getTitle();
        repaint();
        return true;
    }

    // EFFECTS: returns true if currently paused.
    public boolean isPaused() {
        return paused;
    }

    // MODIFIES: this
    // EFFECTS: pauses game loop and audio.
    public void stopGame() {
        running = false;
        paused = false;
        timer.stop();
        if (clip != null) {
            clip.stop();
        }
    }

    // MODIFIES: this
    // EFFECTS: closes panel resources when leaving page.
    public void shutdown() {
        finalizeSessionIfNeeded();
        stopGame();
        closeClip();
    }

    // MODIFIES: this
    // EFFECTS: updates game state every frame.
    private void onFrame(ActionEvent e) {
        if (!running) {
            repaint();
            return;
        }
        long currentMs = getCurrentAudioMs();
        markMisses(currentMs);
        if (judgementTicks > 0) {
            judgementTicks--;
        }
        tickLaneFlashes();
        if (clip != null && !clip.isRunning() && currentMs >= getTrackLengthMs() - 40) {
            finalizeSessionIfNeeded();
            running = false;
            paused = false;
            timer.stop();
            statusMessage = "Track finished. Press Restart to play again.";
        }
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: marks overdue notes as misses.
    private void markMisses(long currentMs) {
        for (BeatNote note : beatNotes) {
            if (!note.judged && currentMs - note.timeMs > missWindowMs()) {
                note.judged = true;
                combo = 0;
                misses++;
                showJudgement("Miss");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes one lane key press and scores by timing.
    private void handleLanePress(int lane) {
        if (!running || paused) {
            return;
        }
        if (lane >= activeLaneCount()) {
            return;
        }
        flashLane(lane);
        long currentMs = getCurrentAudioMs();
        BeatNote target = findNearestLaneNote(lane, currentMs);
        if (target == null) {
            combo = 0;
            misses++;
            showJudgement("Miss");
            repaint();
            return;
        }
        int distance = (int)Math.abs(currentMs - target.timeMs);
        scoreHit(target, distance);
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: fades lane-flash feedback each frame.
    private void tickLaneFlashes() {
        for (int i = 0; i < laneFlashTicks.length; i++) {
            if (laneFlashTicks[i] > 0) {
                laneFlashTicks[i]--;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: triggers visual flash on one lane when key is pressed.
    private void flashLane(int lane) {
        if (lane >= 0 && lane < laneFlashTicks.length) {
            laneFlashTicks[lane] = LANE_FLASH_TICKS;
        }
    }

    // EFFECTS: finds nearest note in lane within hit window.
    private BeatNote findNearestLaneNote(int lane, long currentMs) {
        BeatNote best = null;
        long bestDistance = Long.MAX_VALUE;
        for (BeatNote note : beatNotes) {
            if (note.judged || note.lane != lane) {
                continue;
            }
            long distance = Math.abs(currentMs - note.timeMs);
            if (distance <= maxHitWindowMs() && distance < bestDistance) {
                best = note;
                bestDistance = distance;
            }
        }
        return best;
    }

    // MODIFIES: this
    // EFFECTS: updates judgement and score for one hit.
    private void scoreHit(BeatNote note, int distance) {
        note.judged = true;
        if (distance <= perfectWindowMs()) {
            score += 300;
            combo++;
            perfectCount++;
            showJudgement("Perfect");
        } else if (distance <= goodWindowMs()) {
            score += 180;
            combo++;
            goodCount++;
            showJudgement("Good");
        } else if (distance <= okWindowMs()) {
            score += 90;
            combo++;
            okCount++;
            showJudgement("Ok");
        } else {
            combo = 0;
            misses++;
            showJudgement("Miss");
        }
        if (combo > bestCombo) {
            bestCombo = combo;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets temporary feedback label.
    private void showJudgement(String text) {
        judgement = text;
        judgementTicks = 26;
    }

    // EFFECTS: returns y coordinate of the hit line.
    private int getHitLineY() {
        return Math.max(130, getHeight() - TARGET_LINE_BOTTOM_OFFSET);
    }

    // EFFECTS: returns current clip time in milliseconds.
    private long getCurrentAudioMs() {
        if (clip == null) {
            return 0;
        }
        return clip.getMicrosecondPosition() / 1000;
    }

    // EFFECTS: returns total clip length in milliseconds.
    private long getTrackLengthMs() {
        if (clip == null) {
            return 0;
        }
        return clip.getMicrosecondLength() / 1000;
    }

    // MODIFIES: this
    // EFFECTS: resets score, combo, misses, and temporary judgement.
    private void resetRoundStats() {
        score = 0;
        combo = 0;
        bestCombo = 0;
        misses = 0;
        perfectCount = 0;
        goodCount = 0;
        okCount = 0;
        judgement = "";
        judgementTicks = 0;
        sessionSavedForCurrentRound = false;
    }

    // MODIFIES: this
    // EFFECTS: saves a finished run to history once per round.
    private void finalizeSessionIfNeeded() {
        if (sessionSavedForCurrentRound || loadedSong == null) {
            return;
        }
        int totalActions = perfectCount + goodCount + okCount + misses;
        if (totalActions <= 0) {
            return;
        }
        RhythmGameResult result = new RhythmGameResult(
                LocalDateTime.now().format(TIME_FORMAT),
                loadedSong.getTitle(),
                difficulty.label,
                score,
                bestCombo,
                misses,
                perfectCount,
                goodCount,
                okCount);
        history.addResult(result);
        sessionSavedForCurrentRound = true;
    }

    // MODIFIES: this
    // EFFECTS: closes audio clip resources.
    private void closeClip() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }

    // EFFECTS: creates beatmap from song-specific BPM and lane patterns.
    private List<BeatNote> buildBeatmap(Song song) {
        List<BeatNote> result = new ArrayList<>();
        BeatmapProfile profile = profileFor(song.getTitle());
        long durationMs = Math.max(22000L, song.getDuration() * 1000L);
        long tailBufferMs = 1000L;
        long stepDurationMs = Math.max(85L, Math.round((60000.0 / profile.bpm) / 4.0));
        int noteIndex = 0;
        int laneCount = activeLaneCount();
        for (int step = 0; ; step++) {
            long timeMs = profile.startOffsetMs + step * stepDurationMs;
            if (timeMs > durationMs - tailBufferMs) {
                break;
            }
            int section = sectionForTime(timeMs, durationMs);
            int rhythmOffset = sectionValue(profile.sectionRhythmOffsets, section);
            int rhythmIndex = Math.floorMod(step + rhythmOffset, profile.rhythmPattern.length);
            int rhythmBit = profile.rhythmPattern[rhythmIndex];
            if (rhythmBit == 0 || !shouldUseStep(step, rhythmBit)) {
                continue;
            }
            int laneOffset = sectionValue(profile.sectionLaneOffsets, section);
            int laneIndex = Math.floorMod(noteIndex + laneOffset, profile.lanePattern.length);
            int rawLane = profile.lanePattern[laneIndex];
            int lane = rawLane % laneCount;
            result.add(new BeatNote(lane, timeMs));
            noteIndex++;
        }
        return result;
    }

    // EFFECTS: returns section id (0-3) based on where current time is in track.
    private int sectionForTime(long timeMs, long durationMs) {
        if (durationMs <= 0) {
            return 0;
        }
        double progress = (double) timeMs / (double) durationMs;
        if (progress < 0.2) {
            return 0;
        } else if (progress < 0.48) {
            return 1;
        } else if (progress < 0.76) {
            return 2;
        }
        return 3;
    }

    // EFFECTS: returns section-specific value from an array.
    private int sectionValue(int[] values, int section) {
        if (values == null || values.length == 0) {
            return 0;
        }
        int index = Math.floorMod(section, values.length);
        return values[index];
    }

    // EFFECTS: returns number of active lanes based on difficulty.
    private int activeLaneCount() {
        return difficulty.laneCount;
    }

    // EFFECTS: returns true if a note on this rhythm step should appear for difficulty.
    private boolean shouldUseStep(int step, int rhythmBit) {
        if (difficulty == Difficulty.EASY) {
            // Keep strong beats and selected off-beats to reduce density.
            return step % 4 == 0 || (step % 8 == 6 && rhythmBit == 1);
        } else if (difficulty == Difficulty.NORMAL) {
            return step % 2 == 0 || (step % 8 == 6 && rhythmBit == 1);
        }
        return step % 2 == 0 || (step % 4 == 3 && rhythmBit == 1);
    }

    // EFFECTS: returns current max hit window in ms.
    private int maxHitWindowMs() {
        return (int)Math.round(BASE_MAX_HIT_WINDOW_MS * difficulty.windowMultiplier);
    }

    // EFFECTS: returns current perfect hit window in ms.
    private int perfectWindowMs() {
        return (int)Math.round(BASE_PERFECT_WINDOW_MS * difficulty.windowMultiplier);
    }

    // EFFECTS: returns current good hit window in ms.
    private int goodWindowMs() {
        return (int)Math.round(BASE_GOOD_WINDOW_MS * difficulty.windowMultiplier);
    }

    // EFFECTS: returns current ok hit window in ms.
    private int okWindowMs() {
        return (int)Math.round(BASE_OK_WINDOW_MS * difficulty.windowMultiplier);
    }

    // EFFECTS: returns miss judgement window in ms.
    private int missWindowMs() {
        return (int)Math.round(BASE_MISS_WINDOW_MS * difficulty.windowMultiplier);
    }

    // EFFECTS: returns rhythm profile tuned for each built-in track.
    private BeatmapProfile profileFor(String title) {
        String lowerTitle = title.toLowerCase();
        if (lowerTitle.contains("payphone")) {
            return new BeatmapProfile(
                    110.0,
                    1300L,
                    new int[] { 0, 1, 2, 3, 2, 1 },
                    new int[] { 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0 },
                    new int[] { 0, 1, 2, 1 },
                    new int[] { 0, 0, 1, 2 });
        } else if (lowerTitle.contains("everybody hurts")) {
            return new BeatmapProfile(
                    82.0,
                    1650L,
                    new int[] { 0, 1, 2, 1, 3, 1 },
                    new int[] { 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0 },
                    new int[] { 0, 1, 1, 2 },
                    new int[] { 0, 1, 1, 2 });
        } else if (lowerTitle.contains("innocence")) {
            return new BeatmapProfile(
                    133.0,
                    1180L,
                    new int[] { 0, 2, 1, 3, 2, 0, 1, 3 },
                    new int[] { 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1 },
                    new int[] { 0, 2, 1, 3 },
                    new int[] { 0, 1, 0, 2 });
        } else if (lowerTitle.contains("whataya")) {
            return new BeatmapProfile(
                    122.0,
                    1220L,
                    new int[] { 1, 2, 1, 3, 0, 2, 3, 1 },
                    new int[] { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0 },
                    new int[] { 0, 1, 2, 0 },
                    new int[] { 0, 0, 1, 1 });
        } else if (lowerTitle.contains("like i do")) {
            return new BeatmapProfile(
                    100.0,
                    1020L,
                    new int[] { 0, 1, 0, 2, 3, 2, 1, 3 },
                    new int[] { 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1 },
                    new int[] { 0, 1, 0, 2 },
                    new int[] { 0, 1, 0, 2 });
        }
        return new BeatmapProfile(
                108.0,
                1250L,
                new int[] { 0, 1, 2, 3, 2, 1 },
                new int[] { 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1 },
                new int[] { 0, 1, 2, 1 },
                new int[] { 0, 1, 1, 2 });
    }

    // MODIFIES: this
    // EFFECTS: installs key actions for all lanes.
    private void configureKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        for (int i = 0; i < LANE_KEYS.length; i++) {
            String actionName = "lane" + i;
            int lane = i;
            inputMap.put(KeyStroke.getKeyStroke("pressed " + LANE_KEYS[i]), actionName);
            actionMap.put(actionName, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleLanePress(lane);
                }
            });
        }
        inputMap.put(KeyStroke.getKeyStroke("pressed SPACE"), "togglePause");
        actionMap.put("togglePause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!running) {
                    return;
                }
                if (paused) {
                    resumeGame();
                } else {
                    pauseGame();
                }
            }
        });
    }

    // EFFECTS: paints lanes, notes, hit line, and HUD.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        long currentMs = getCurrentAudioMs();
        drawLanes(g2);
        drawHitLine(g2);
        drawNotes(g2, currentMs);
        drawHud(g2);
        g2.dispose();
    }

    // EFFECTS: draws gameplay lanes and key labels.
    private void drawLanes(Graphics2D g2) {
        int laneCount = activeLaneCount();
        int laneWidth = Math.max(1, getWidth() / laneCount);
        for (int i = 0; i < laneCount; i++) {
            g2.setColor((i % 2 == 0) ? LANE_DARK : LANE_LIGHT);
            g2.fillRect(i * laneWidth, 0, laneWidth, getHeight());
            drawLaneFlash(g2, i, laneWidth);
            g2.setColor(new Color(59, 74, 102));
            g2.drawLine(i * laneWidth, 0, i * laneWidth, getHeight());
            g2.setColor(TEXT);
            g2.setFont(new Font("Dialog", Font.BOLD, 16));
            g2.drawString(LANE_KEYS[i], i * laneWidth + laneWidth / 2 - 5, getHeight() - 24);
        }
    }

    // EFFECTS: draws temporary flash overlay for pressed lane.
    private void drawLaneFlash(Graphics2D g2, int lane, int laneWidth) {
        if (laneFlashTicks[lane] <= 0) {
            return;
        }
        int laneX = lane * laneWidth;
        int flashAlpha = Math.min(220, 70 + laneFlashTicks[lane] * 16);
        g2.setColor(new Color(LANE_FLASH.getRed(), LANE_FLASH.getGreen(), LANE_FLASH.getBlue(), flashAlpha));
        g2.fillRect(laneX, 0, laneWidth, getHeight());
        int zoneTop = getHitLineY() - 58;
        int zoneHeight = 90;
        int zoneX = laneX + 8;
        int zoneWidth = Math.max(8, laneWidth - 16);
        int zoneAlpha = Math.min(230, 85 + laneFlashTicks[lane] * 14);
        g2.setColor(new Color(
                HIT_ZONE_FLASH.getRed(),
                HIT_ZONE_FLASH.getGreen(),
                HIT_ZONE_FLASH.getBlue(),
                zoneAlpha));
        g2.fillRoundRect(zoneX, zoneTop, zoneWidth, zoneHeight, 18, 18);
    }

    // EFFECTS: draws hit line near bottom.
    private void drawHitLine(Graphics2D g2) {
        int y = getHitLineY();
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(HIT_LINE);
        g2.drawLine(0, y, getWidth(), y);
    }

    // EFFECTS: draws active notes based on clip time.
    private void drawNotes(Graphics2D g2, long currentMs) {
        int laneCount = activeLaneCount();
        int laneWidth = Math.max(1, getWidth() / laneCount);
        int hitLineY = getHitLineY();
        for (BeatNote note : beatNotes) {
            if (note.judged) {
                continue;
            }
            if (note.lane >= laneCount) {
                continue;
            }
            double yDouble = hitLineY - (note.timeMs - currentMs) * PIXELS_PER_MS;
            int y = (int) yDouble;
            if (y < -NOTE_SIZE || y > getHeight() + NOTE_SIZE) {
                continue;
            }
            int x = note.lane * laneWidth + (laneWidth - NOTE_SIZE) / 2;
            g2.setColor(NOTE_COLOR);
            g2.fillRoundRect(x, y, NOTE_SIZE, NOTE_SIZE, 12, 12);
            g2.setColor(new Color(135, 90, 34));
            g2.drawRoundRect(x, y, NOTE_SIZE, NOTE_SIZE, 12, 12);
        }
    }

    // EFFECTS: draws score, combo, misses, historical high score, and current status.
    private void drawHud(Graphics2D g2) {
        g2.setColor(TEXT);
        g2.setFont(new Font("Dialog", Font.BOLD, 18));
        g2.drawString("Score: " + score, 14, 24);
        g2.drawString("Combo: " + combo, 14, 48);
        g2.drawString("Best Combo: " + bestCombo, 14, 72);
        g2.drawString("Misses: " + misses, 14, 96);
        g2.drawString("Historical High Score: " + history.getHighestScore(), 14, 120);
        String songText = (loadedSong == null) ? "Song: none" : "Song: " + loadedSong.getTitle();
        g2.drawString(songText, 14, 144);
        g2.drawString("Keys: " + keyDisplayForDifficulty(), 14, 168);
        g2.drawString("Difficulty: " + difficulty.label, 14, 192);
        g2.setFont(new Font("Dialog", Font.PLAIN, 14));
        g2.drawString(statusMessage, 14, 212);
        g2.drawString(SPACE_HINT, 14, 232);
        if (!running && loadedSong != null) {
            g2.setFont(new Font("Dialog", Font.BOLD, 24));
            g2.drawString("Press Start to begin", getWidth() / 2 - 120, getHeight() / 2 - 20);
        }
        if (paused) {
            g2.setFont(new Font("Dialog", Font.BOLD, 24));
            g2.setColor(new Color(255, 212, 94));
            g2.drawString("Paused", getWidth() / 2 - 44, getHeight() / 2 - 50);
        }
        if (judgementTicks > 0) {
            g2.setFont(new Font("Dialog", Font.BOLD, 28));
            g2.setColor(judgementColor());
            g2.drawString(judgement, getWidth() / 2 - 56, getHitLineY() - 30);
        }
    }

    // EFFECTS: returns active key labels based on difficulty.
    private String keyDisplayForDifficulty() {
        StringBuilder builder = new StringBuilder();
        int laneCount = activeLaneCount();
        for (int i = 0; i < laneCount; i++) {
            builder.append(LANE_KEYS[i]);
            if (i < laneCount - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    // EFFECTS: returns color for judgement feedback text.
    private Color judgementColor() {
        if ("Perfect".equals(judgement)) {
            return new Color(100, 255, 160);
        } else if ("Good".equals(judgement)) {
            return new Color(122, 200, 255);
        } else if ("Ok".equals(judgement)) {
            return new Color(255, 218, 101);
        }
        return new Color(255, 120, 120);
    }

    // Represents one beat note in rhythm gameplay.
    private static class BeatNote {
        private final int lane;
        private final long timeMs;
        private boolean judged;

        // EFFECTS: constructs a note with lane and target hit time.
        BeatNote(int lane, long timeMs) {
            this.lane = lane;
            this.timeMs = timeMs;
            judged = false;
        }
    }

    // Represents beatmap configuration for one song.
    private static class BeatmapProfile {
        private final double bpm;
        private final long startOffsetMs;
        private final int[] lanePattern;
        private final int[] rhythmPattern;
        private final int[] sectionLaneOffsets;
        private final int[] sectionRhythmOffsets;

        // EFFECTS: constructs a profile with tempo, start offset and note patterns.
        BeatmapProfile(double bpm, long startOffsetMs, int[] lanePattern, int[] rhythmPattern,
                int[] sectionLaneOffsets, int[] sectionRhythmOffsets) {
            this.bpm = bpm;
            this.startOffsetMs = startOffsetMs;
            this.lanePattern = lanePattern;
            this.rhythmPattern = rhythmPattern;
            this.sectionLaneOffsets = sectionLaneOffsets;
            this.sectionRhythmOffsets = sectionRhythmOffsets;
        }
    }

    // Represents user-selectable difficulty in rhythm mode.
    private enum Difficulty {
        EASY("Easy", 1.35, 2),
        NORMAL("Normal", 1.2, 3),
        HARD("Hard", 1.05, 4);

        private final String label;
        private final double windowMultiplier;
        private final int laneCount;

        // EFFECTS: constructs difficulty metadata.
        Difficulty(String label, double windowMultiplier, int laneCount) {
            this.label = label;
            this.windowMultiplier = windowMultiplier;
            this.laneCount = laneCount;
        }
    }
}
