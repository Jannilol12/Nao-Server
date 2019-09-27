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
}
