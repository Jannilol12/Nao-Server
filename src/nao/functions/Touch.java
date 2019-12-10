package nao.functions;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import nao.currentApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Touch {
    private static ExecutorService executor;
    static {
        executor = Executors.newFixedThreadPool(3);
    }

    private static long TouchChanged;
    private static long RightBumperPressed;
    private static long LeftBumperPressed;
    private static long FrontTactilTouched;
    private static long MiddleTactilTouched;
    private static long RearTactilTouched;
    private static long HandRightBackTouched;
    private static long HandRightLeftTouched;
    private static long HandRightRightTouched;
    private static long HandLeftBackTouched;
    private static long HandLeftLeftTouched;
    private static long HandLeftRightTouched;

    public static void startRightBumperPressed() throws Exception {
        RightBumperPressed = currentApplication.getAlMemory().subscribeToEvent("RightBumperPressed", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopRightBumperPressed() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(RightBumperPressed);
    }

    public static void startLeftBumperPressed() throws Exception {
        LeftBumperPressed = currentApplication.getAlMemory().subscribeToEvent("LeftBumperPressed", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopLeftBumperPressed() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(LeftBumperPressed);
    }

    public static void startFrontTactilTouched() throws Exception {
        FrontTactilTouched = currentApplication.getAlMemory().subscribeToEvent("FrontTactilTouched", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopFrontTactilTouched() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(FrontTactilTouched);
    }

    public static void startMiddleTactilTouched() throws Exception {
        MiddleTactilTouched = currentApplication.getAlMemory().subscribeToEvent("MiddleTactilTouched", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopMiddleTactilTouched() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(MiddleTactilTouched);
    }

    public static void startRearTactilTouched() throws Exception {
        RearTactilTouched = currentApplication.getAlMemory().subscribeToEvent("RearTactilTouched", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopRearTactilTouched() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(RearTactilTouched);
    }

    public static void startHandRightBackTouched() throws Exception {
        HandRightBackTouched = currentApplication.getAlMemory().subscribeToEvent("HandRightBackTouched", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopHandRightBackTouched() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(HandRightBackTouched);
    }

    public static void startHandRightLeftTouched() throws Exception {
        HandRightLeftTouched = currentApplication.getAlMemory().subscribeToEvent("HandRightLeftTouched", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopHandRightLeftTouched() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(HandRightLeftTouched);
    }

    public static void startHandRightRightTouched() throws Exception {
        HandRightRightTouched = currentApplication.getAlMemory().subscribeToEvent("HandRightRightTouched", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopHandRightRightTouched() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(HandRightRightTouched);
    }

    public static void startHandLeftBackTouched() throws Exception {
        HandLeftBackTouched = currentApplication.getAlMemory().subscribeToEvent("HandLeftBackTouched", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopHandLeftBackTouched() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(HandLeftBackTouched);
    }

    public static void startHandLeftLeftTouched() throws Exception {
        HandLeftLeftTouched = currentApplication.getAlMemory().subscribeToEvent("HandLeftLeftTouched", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopHandLeftLeftTouched() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(HandLeftLeftTouched);
    }

    public static void startHandLeftRightTouched() throws Exception {
        LeftBumperPressed = currentApplication.getAlMemory().subscribeToEvent("HandLeftRightTouched", new EventCallback<Float>() {
            @Override
            public void onEvent(Float o) throws InterruptedException, CallError {
                if(o > 1.0){

                }
            }
        });
    }

    public static void stopHandLeftRightTouched() throws CallError, InterruptedException {
        currentApplication.getAlMemory().unsubscribeToEvent(HandLeftRightTouched);
    }
}
