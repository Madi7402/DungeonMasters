package view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Manages the playback of sound effects and background music in the game.
 * Allows for adjusting volume, muting, and stopping playback.
 *
 * @author Jonathan Abrams, Martha Emerson, Madison Pope
 */
public class SoundManager {
    /**
     * The MediaPlayer responsible for playing audio in the SoundManager.
     */
    private MediaPlayer myMediaPlayer;
    /**
     * The volume level for sound effects in the SoundManager.
     * The default value is 0.5 (medium volume).
     */
    private double mySoundVolume = 0.5; // sound volume (defaults to medium)
    /**
     * The volume level for background music in the SoundManager.
     * The default value is 0.5 (medium volume).
     */
    private double myMusicVolume = 0.5; // music volume (defaults to medium)

    /**
     * Constructs a SoundManager instance.
     * Loads media files for sound effects and background music.
     */
    public SoundManager() {
        // TODO - write code for loading media files
        // music for levels, battles, different menus, etc.
        // sound effects for potions, pits, health status, level completion, etc.
    }

    /**
     * Plays a sound effect with the specified file name.
     *
     * @param soundFileName The file name of the sound effect.
     */
    public void playSound(String soundFileName) {
        Media sound = new Media(getClass().getResource(soundFileName).toString());
        MediaPlayer soundPlayer = new MediaPlayer(sound);
        soundPlayer.setVolume(mySoundVolume);
        soundPlayer.play();
    }

    /**
     * Sets the volume for sound effects.
     *
     * @param volume The volume level (0.0 to 1.0).
     */
    public void setMySoundVolume(double volume) {
        this.mySoundVolume = volume;
    }

    /**
     * Gets the current volume level for sound effects.
     *
     * @return The volume level (0.0 to 1.0).
     */
    public double getMySoundVolume() {
        return mySoundVolume;
    }

    /**
     * Stops playing the current sound effect.
     */
    public void stopSound() {
        // TO DO - figure out how to stop sound (when muted, or when...)
    }

    /**
     * Plays background music with the specified file name.
     *
     * @param musicFileName The file name of the background music.
     */
    public void playMusic(String musicFileName) {
        Media music = new Media(getClass().getResource(musicFileName).toString());
        myMediaPlayer = new MediaPlayer(music);
        myMediaPlayer.setVolume(myMusicVolume);
        myMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Background music can repeat forever
        myMediaPlayer.play();
    }

    /**
     * Stops playing the current background music.
     */
    public void stopMusic() {
        if (myMediaPlayer != null) {
            myMediaPlayer.stop();
        }
    }

    /**
     * Sets the volume for background music.
     *
     * @param volume The volume level (0.0 to 1.0).
     */
    public void setMyMusicVolume(double volume) {
        this.myMusicVolume = volume;
        if (myMediaPlayer != null) {
            myMediaPlayer.setVolume(myMusicVolume);
        }
    }

    /**
     * Gets the current volume level for background music.
     *
     * @return The volume level (0.0 to 1.0).
     */
    public double getMyMusicVolume() {
        return myMusicVolume;
    }

    /**
     * Toggles mute status for both sound effects and background music.
     * If currently muted, this method will unmute and reset the volume to medium.
     * If not muted, this method will mute both sound effects and background music.
     */
    public void toggleMute() {
        if (mySoundVolume > 0.0 || myMusicVolume > 0.0) {
            mySoundVolume = 0.0;
            myMusicVolume = 0.0;
        } else {
            mySoundVolume = 0.5; // volume gets reset to medium
            myMusicVolume = 0.5; // sound gets reset to medium
        }

        if (myMediaPlayer != null) {
            myMediaPlayer.setVolume(myMusicVolume);
        }
    }

    /**
     * Stops all currently playing sounds, both sound effects and background music.
     */
    public void stopAllSounds() {
        stopSound();
        stopMusic();
    }

    /**
     * Stops all currently playing sounds, both sound effects and background music,
     * and cleans up resources.
     */
    public void cleanup() {
        stopAllSounds();
        stopMusic();
    }
}