package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class audioPlayer {
    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(3);
    }

    //        fileName – Absolute path of the file
    //        volume – Volume of the sound file [0.0 - 1.0]
    //        pan – Stereo panorama requested (-1.0 : left / 1.0 : right / 0.0 : center)

    public static boolean isRunning(int id){
        try {
            return currentApplication.getAlAudioPlayer().isRunning(id);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //------------------------- PLAY, PAUSE, STOP ---------------------

    public static void playPlayer(int id){
        try {
            currentApplication.getAlAudioPlayer().play(id);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void playinLoop(int id){
        try {
            currentApplication.getAlAudioPlayer().playInLoop(id);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void playSine(int frequencec, int gain, int pan, float duration){
        try {
            currentApplication.getAlAudioPlayer().playSine(frequencec, gain, pan, duration);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stopPlayer(){
        try {
            currentApplication.getAlAudioPlayer().stopAll();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pausePlayer(int id){
        try {
            currentApplication.getAlAudioPlayer().pause(id);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //----------------------- POSITION ----------------------------------------------

    public static float getcurrentPosition(int id){
        try {
            return currentApplication.getAlAudioPlayer().getCurrentPosition(id);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (float) -2;
    }

    public static void goToPosition(int id, float position){
        try {
            currentApplication.getAlAudioPlayer().goTo(id,position);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //------------------------ VOLUME -------------------------------------------------

    public static void setMasterVolume(float volume){
        try {
            currentApplication.getAlAudioPlayer().setMasterVolume(volume);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static float getMasterVolume(){
        try {
            return currentApplication.getAlAudioPlayer().getMasterVolume();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (float)-2;
    }

    //--------------------------- FILE ----------------------------------------------

    public static int loadFile(String filename){
        try {
            return currentApplication.getAlAudioPlayer().loadFile(filename);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<String> getFileList(){
        try {
            return currentApplication.getAlAudioPlayer().getLoadedFilesNames();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static float getFileLengthInSec(int id){
        try {
            return currentApplication.getAlAudioPlayer().getFileLength(id);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (float)-2;
    }

    public static void unloadAllFiles(){
        try {
            currentApplication.getAlAudioPlayer().unloadAllFiles();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
