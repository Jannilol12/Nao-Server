package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class behavior {
    private static ExecutorService executor;
    static {
        executor = Executors.newFixedThreadPool(3);
    }

    public static void loadBehavior(String path){
        try {
            currentApplication.getAlBehaviorManager().installBehavior(path);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void removeBehavior(String name){
        try {
            currentApplication.getAlBehaviorManager().removeBehavior(name);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void runBehavior(String name){
        try {
            currentApplication.getAlBehaviorManager().runBehavior(name);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stopBehavior(){
        try {
            currentApplication.getAlBehaviorManager().stopAllBehaviors();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
