package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.concurrent.Executors;


/**
 * AudioPlayer from the robot, for playing music and audio files
 */
public class audioPlayer {

    //the maximum of threads the nao could use
    static {
        Executors.newFixedThreadPool(3);
    }

    //------------------------- PLAY, PAUSE, STOP ---------------------

    /**
     * Play a file
     * @param id the id of the file which is running, you get it when loading the file
     */
    public static void playPlayer(int id) {
        try {
            currentApplication.getAlAudioPlayer().play(id);
        } catch (InterruptedException | CallError err){
            err.printStackTrace();
        }

    }

    /**
     * Play a file in a continuous loop
     * @param id the id of the file which is running, you get it when loading the file
     */
    public static void playinLoop(int id) {
        try {
            currentApplication.getAlAudioPlayer().playInLoop(id);
        } catch (InterruptedException | CallError err){
            err.printStackTrace();
        }
    }

    /**
     * stop the audioPlayer
     */
    public static void stopPlayer() throws InterruptedException, CallError {
        currentApplication.getAlAudioPlayer().stopAll();
    }

    /**
     * Pause the audioPlayer
     * @param id the id of the file which is running, you get it when loading the file
     */
    public static void pausePlayer(int id) throws CallError, InterruptedException {
        currentApplication.getAlAudioPlayer().pause(id);
    }

    //----------------------- POSITION ----------------------------------------------


    /**
     * Get the current position of the file, which is actually played
     * @param id the id of the file which is running, you get it when loading the file
     * @return return the current position, in seconds
     */
    public static float getcurrentPosition(int id) throws CallError, InterruptedException {
        return currentApplication.getAlAudioPlayer().getCurrentPosition(id);
    }

    /**
     * jump to the position of the file acutally played
     * @param id the id of the file which is running, you get it when loading the file
     * @param position jump to the position, in seconds, of the file acutally played
     */
    public static void goToPosition(int id, float position) throws CallError, InterruptedException {
        currentApplication.getAlAudioPlayer().goTo(id,position);
    }

    //------------------------ VOLUME -------------------------------------------------

    /**
     * Set the volume of the AudioPlayer, not of the robot. This is only in the internet browser possible
     * @param volume set the volume of the audioPlayer, between 0 and 1
     */
    public static void setMasterVolume(float volume) throws CallError, InterruptedException {
        currentApplication.getAlAudioPlayer().setMasterVolume(volume);
    }


    /**
     * get the Volume of the AudioPlayer
     * @return get the Volume of the AudioPlayer, between 0 and 1
     */
    public static float getMasterVolume() throws CallError, InterruptedException {
        return currentApplication.getAlAudioPlayer().getMasterVolume();
    }

    //--------------------------- FILE ----------------------------------------------

    /**
     * Load a file into the audioPlayer, to actually play it
     * @param filename get the path of the file, which shall be loaded
     * @return the id of the file, used for other methods, like {@link #playPlayer}
     */
    public static int loadFile(String filename) throws InterruptedException, CallError {
        return currentApplication.getAlAudioPlayer().loadFile(filename);
    }

    /**
     * Get the length of the file
     * @param id the id of the file which is running, you get it when loading the file
     * @return Get the length of the file, in seconds
     */
    public static float getFileLengthInSec(int id) throws InterruptedException, CallError {
        return currentApplication.getAlAudioPlayer().getFileLength(id);
    }

    /**
     * unload all files
     */
    public static void unloadAllFiles() throws CallError, InterruptedException {
        currentApplication.getAlAudioPlayer().unloadAllFiles();
    }
}
