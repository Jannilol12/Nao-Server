package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * I created this class, but never worked with it, because I didn't get where I can use the Object receiving when stopping the Recording
 */
public class motionRecording {
    //the maximum of threads the nao could use
    static {
        Executors.newFixedThreadPool(3);
    }

    private static List<String> motor = new ArrayList<>();

    /**
     * start the Recording
     */
    private static void startMotionRecording(){
        try {
            currentApplication.getAlMotionRecorder().startInteractiveRecording(motor,99,true,2 );
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * stop the Recording
     * @return getting the recorded Motion as an Object, have fun with it!
     */
    private static Object stopMotionRecording(){
        try {
            return currentApplication.getAlMotionRecorder().stopAndGetRecording();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
        return null;
    }

}
