package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class motionRecording {
    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(3);
    }

    private static List<String> motor = new ArrayList<>();

    private static void startMotionRecording(){
        try {
            currentApplication.getAlMotionRecorder();
            currentApplication.getAlMotionRecorder().startInteractiveRecording(motor,99,true,2 );
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Object stopMotionRecording(){
        try {
            return currentApplication.getAlMotionRecorder().stopAndGetRecording();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
