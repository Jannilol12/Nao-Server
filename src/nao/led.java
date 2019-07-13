package nao;

import com.aldebaran.qi.CallError;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class led {
    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(3);
    }

    public static void setRGBled(String name,float red,float green,float blue,float fade){
        try {
            if(currentApplication.getAlLeds() != null) {
                currentApplication.getAlLeds().fadeRGB(name, red, green, blue, fade);
            }
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void onled (String name){
        try{
            if(currentApplication.getAlLeds() != null){
                currentApplication.getAlLeds().on(name);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void offled (String name){
        try{
            if(currentApplication.getAlLeds() != null){
                currentApplication.getAlLeds().off(name);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void randomEyes(float duration){
        try{
            if(currentApplication.getAlLeds() != null){
                currentApplication.getAlLeds().randomEyes(duration);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void rotateEyes(int rgb, float timeforroation, float totalduration){
        try{
            if(currentApplication.getAlLeds() != null){
                currentApplication.getAlLeds().rotateEyes(rgb,timeforroation,totalduration);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }

}
