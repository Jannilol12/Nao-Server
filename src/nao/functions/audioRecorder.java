package nao.functions;

import com.aldebaran.qi.CallError;
import components.json.JSONArray;
import nao.currentApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class audioRecorder {
    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(3);
    }

    public static void startRecording(String filename){
        File file = new File(new File("./").getParentFile(), "files/" + filename);
        file.getParentFile().mkdirs();
        String path = file.getAbsolutePath();
        try {
            currentApplication.getAlAudioRecorder().startMicrophonesRecording(path,"wav", 16000, Arrays.asList(0,0,1,0).toArray());
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static void stopRecording(){
        try {
            currentApplication.getAlAudioRecorder().stopMicrophonesRecording();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }
}
