package nao.functions;

import com.aldebaran.qi.CallError;
import components.json.JSONArray;
import nao.currentApplication;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Recorder {
    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(3);
    }

    public static void startAudioRecording(){
        String fileName = "Audio: " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        File file = new File(new File("./").getParentFile(), "files/" + fileName );
        file.getParentFile().mkdirs();
        String path = file.getAbsolutePath();
        try {
            currentApplication.getAlAudioRecorder().startMicrophonesRecording(path,"wav", 16000, Arrays.asList(0,0,1,0).toArray());
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static void stopAudioRecording(){
        try {
            currentApplication.getAlAudioRecorder().stopMicrophonesRecording();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static void setCameraStats(){
        try {
            currentApplication.getAlPhotoCapture().setColorSpace(0);
            currentApplication.getAlPhotoCapture().setPictureFormat("png");
            currentApplication.getAlPhotoCapture().setResolution(2);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static void takePicture(){
        File file = new File(new File("./").getParentFile(), "files/");
        file.getParentFile().mkdirs();
        String path = file.getAbsolutePath();
        String fileName = "Picture: " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        try {
            currentApplication.getAlPhotoCapture().takePicture(path,fileName,false);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static void setVideoStats(){
        try {
            currentApplication.getAlVideoRecorder().setResolution(2);
            currentApplication.getAlVideoRecorder().setVideoFormat("MJPG");
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static void startVideoRecording(){
        File file = new File(new File("./").getParentFile(), "files/");
        file.getParentFile().mkdirs();
        String path = file.getAbsolutePath();
        String fileName = "Video: " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        try {
            currentApplication.getAlVideoRecorder().startRecording(path,fileName,true);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static void stopVideoRecording(){
        try {
            currentApplication.getAlVideoRecorder().stopRecording();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static void getVideo(){
        try {
            currentApplication.getAlVideoDevice().subscribeCamera("VideoDevice", 0,2,11,5);
            Object image = currentApplication.getAlVideoDevice().getDirectRawImageRemote("VideoDevice");
            int[] array = (int[]) image;

            FileOutputStream stream = new FileOutputStream(new File("./test.txt"));
            stream.write(array[0]);
            stream.write('\n');
            stream.write(array[1]);
            stream.write('\n');

            stream.close();
        } catch (CallError | InterruptedException | FileNotFoundException callError) {
            callError.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
