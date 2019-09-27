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
                        currentApplication.getAlMemory().subscribeToEvent("WordRecognized", new EventCallback() {
                            @Override
                            public void onEvent(Object o) throws InterruptedException, CallError {
                                System.out.println(o.toString());
                                currentApplication.getAlMemory().getData("WordRecognized");
                                currentApplication.getAlMemory().getData("Test");

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
}
