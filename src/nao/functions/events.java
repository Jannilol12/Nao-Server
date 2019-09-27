package nao.functions;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;
import nao.currentApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class events {
    private static ExecutorService executor;
    private static Thread footContact;
    private static Thread speechRecognition;
    private static Thread sonar;

    static {
        executor = Executors.newFixedThreadPool(3);
    }

    public static void startFootContactChanged() {
        if (footContact != null) {
            return;
        }
        footContact = new Thread() {
            @Override
            public void run() {
                while (!this.isInterrupted()) {
                    try {
                        currentApplication.getAlMemory().subscribeToEvent("footContact", new EventCallback<Float>() {
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
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        footContact.start();
    }

    public static void stopFootContactChanged(){
        if(footContact == null){
            return;
        }
        footContact.interrupt();
        footContact = null;
    }


    public static void startSpeechRecognition() {
        try {
            currentApplication.getAlSpeechRecognition().subscribe("Test");
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (speechRecognition != null) {
            return;
        }
        speechRecognition = new Thread() {
            @Override
            public void run() {
                while (!this.isInterrupted()) {
                    try {
                        System.out.println(currentApplication.getAlMemory().getData("WordRecognized") + "" +  currentApplication.getAlMemory().getData("Test"));

                        currentApplication.getAlMemory().subscribeToEvent("WordRecognized", new EventCallback() {
                            @Override
                            public void onEvent(Object o) throws InterruptedException, CallError {
                                System.out.println(o.toString());
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        speechRecognition.start();
    }

    public static void stopSpeechRecognition(){
        try {
            currentApplication.getAlSpeechRecognition().unsubscribe("Test");
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(speechRecognition == null){
            return;
        }
        speechRecognition.interrupt();
        speechRecognition = null;
    }

    public static void startSonar(){

        //http://doc.aldebaran.com/2-1/family/nao_dcm/actuator_sensor_names.html#term-us-sensor-m
        //The results of the first echo detected on each receiver are in Value, the 9 following echoes are from Value1 to Value9.
        //
        //Value of 0 means an error. A value of Max Detection range means no echo.
        //
        //For example, if Value contains 0,40, Value1 1,2 and Value2 Max Detection range, the following values (3 to 9) will contain Max Detection range too.
        // It probably means you have the echo of the ground at 0,40m and another object at 1,2m. Left and Right sensors work the same way and allow you to locate objects.
        if (sonar != null) {
            return;
        }
        sonar = new Thread() {
            @Override
            public void run() {
                while (!this.isInterrupted()) {
                    try {
                        String distanceLeft = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Left/Sensor/Value").toString();
                        String distanceRight = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Right/Sensor/Value").toString();

                        currentApplication.getAlMemory().subscribeToEvent("SonarLeftDetected", new EventCallback() {
                            @Override
                            public void onEvent(Object o) throws InterruptedException, CallError {
                                System.out.println("Sonar Left Detected");
                            }
                        });
                        System.out.println(distanceLeft + "|" + distanceRight);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        sonar.start();
    }

    public static void stopSonar(){
        if(sonar == null){
            return;
        }
        sonar.interrupt();
        sonar = null;
    }
}
