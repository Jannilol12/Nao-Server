package nao;

import com.aldebaran.qi.CallError;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class led {
    private ExecutorService executor;

    public led() {
        executor = Executors.newFixedThreadPool(3);
    }

    public void setRGBled(String name,float red,float green,float blue,float fade){
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

    public void onled (String name){
        try{
            if(currentApplication.getAlLeds() != null){
                currentApplication.getAlLeds().on(name);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public void offled (String name){
        try{
            if(currentApplication.getAlLeds() != null){
                currentApplication.getAlLeds().off(name);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }

}
