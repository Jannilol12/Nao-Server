package nao.functions;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;
import nao.currentApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class events {
    private static ExecutorService executor;
    private static long footContact;
    private static long speechRecognition;
    private static long sonar;

    static {
        executor = Executors.newFixedThreadPool(3);
    }

    public static void startFootContactChanged() {
        try {
            footContact = currentApplication.getAlMemory().subscribeToEvent("footContact", new EventCallback<Float>() {
                @Override
                public void onEvent(Float variable) throws InterruptedException, CallError {
                    if (variable == 1.0) {
                        System.out.println("FROM BOTTOM");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopFootContactChanged(){
        try {
            currentApplication.getAlMemory().unsubscribeToEvent(footContact);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CallError callError) {
            callError.printStackTrace();
        }
    }


    public static void startSpeechRecognition() {
        try {
            currentApplication.getAlSpeechRecognition().subscribe("Test");
            speechRecognition =  currentApplication.getAlMemory().subscribeToEvent("WordRecognized", new EventCallback() {
                @Override
                public void onEvent(Object o) throws InterruptedException, CallError {
                    System.out.println(o.toString());
                    System.out.println(currentApplication.getAlMemory().getData("WordRecognized") + "" +  currentApplication.getAlMemory().getData("Test"));
                }
            });
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopSpeechRecognition(){
        try {
            currentApplication.getAlSpeechRecognition().unsubscribe("Test");
            currentApplication.getAlMemory().unsubscribeToEvent(speechRecognition);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startSonar(){

        //http://doc.aldebaran.com/2-1/family/nao_dcm/actuator_sensor_names.html#term-us-sensor-m
        //The results of the first echo detected on each receiver are in Value, the 9 following echoes are from Value1 to Value9.
        //
        //Value of 0 means an error. A value of Max Detection range means no echo.
        //
        //For example, if Value contains 0,40, Value1 1,2 and Value2 Max Detection range, the following values (3 to 9) will contain Max Detection range too.
        // It probably means you have the echo of the ground at 0,40m and another object at 1,2m. Left and Right sensors work the same way and allow you to locate objects.

        try {
            sonar = currentApplication.getAlMemory().subscribeToEvent("SonarLeftDetected", new EventCallback() {
                @Override
                public void onEvent(Object o) throws InterruptedException, CallError {
                    System.out.println("Sonar Left Detected");
                    String distanceLeft = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Left/Sensor/Value").toString();
                    String distanceRight = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Right/Sensor/Value").toString();
                    System.out.println(distanceLeft + "|" + distanceRight);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void stopSonar(){
        try {
            currentApplication.getAlMemory().unsubscribeToEvent(sonar);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CallError callError) {
            callError.printStackTrace();
        }
    }
}
