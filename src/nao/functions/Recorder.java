package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;

/**
 * make an audio or video recording or take a picture
 */
public class Recorder {
    //the maximum of threads the nao could use
    static {
        Executors.newFixedThreadPool(3);
    }

    /**
     * start the Recording of an Audio
     */
    public static void startAudioRecording(){
        //make a new File with a DateFormat, so you can easily read when this record was made...and also as a .wmv, otherwise it won't have an specification
        String fileName = "Audio " + new SimpleDateFormat("dd-MM-yyyy___HH-mm").format(Calendar.getInstance().getTime()) + ".wav";
        File file = new File(new File("./").getParentFile(), "files/" + fileName );
        String path = file.getAbsolutePath();
        //Configure the Channels which are needed
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0); //left
        list.add(0); //right
        list.add(1); //front
        list.add(0); //rear
        try {
            /*
            four channels 48000Hz in OGG.
            four channels 48000Hz in WAV uncompressed.
            one channels (front, rear, left or right), 16000Hz, in OGG.
            one channels (front, rear, left or right), 16000Hz, in WAV.
             */
             currentApplication.getAlAudioRecorder().startMicrophonesRecording(path,"wav", 16000, list);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * stop the Recording, file is automatically saved
     */
    public static void stopAudioRecording(){
        try {
            currentApplication.getAlAudioRecorder().stopMicrophonesRecording();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * upset the camera
     */
    public static void setCameraStats(){
        try {
            currentApplication.getAlPhotoCapture().setColorSpace(0); //0 = kYuvColorSpace, 13 = kBGRColorSpace
            currentApplication.getAlPhotoCapture().setPictureFormat("png"); //bmp,dib,jpeg,jpg,jpe,png,pbm,pgm,ppm,sr,ras,tiff,tif
            currentApplication.getAlPhotoCapture().setResolution(2); //0 = kQQVGA, 1 = kQVGA, 2 = kVGA
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * take a picture
     */
    public static void takePicture(){
        //make a new File with a DateFormat, so you can easily read when this record was made
        File file = new File(new File("./").getParentFile(), "files/");
        String path = file.getAbsolutePath();
        String fileName = "Picture " + new SimpleDateFormat("dd-MM-yyyy___HH-mm").format(Calendar.getInstance().getTime());
        try {
            currentApplication.getAlPhotoCapture().takePicture(path,fileName,false);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * start Video Recording
     */
    public static void startVideoRecording(){
        //make a new File with a DateFormat, so you can easily read when this record was made
        File file = new File(new File("./").getParentFile(), "files/");
        String path = file.getAbsolutePath();
        String fileName = "Video " + new SimpleDateFormat("dd-MM-yyyy___HH-mm").format(Calendar.getInstance().getTime());
        try {
            currentApplication.getAlVideoRecorder().startRecording(path,fileName,true);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * stop Video Recording, File is automatically saved
     */
    public static void stopVideoRecording(){
        try {
            currentApplication.getAlVideoRecorder().stopRecording();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

}
