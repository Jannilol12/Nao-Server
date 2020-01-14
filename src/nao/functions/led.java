package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.concurrent.Executors;

/**
 * control all the led from the robot
 */
public class led {

    //the maximum of threads the nao could use
    static {
        Executors.newFixedThreadPool(3);
    }

    /**
     * switch the Led on and set its RGB
     * @param name the name of the LED
     * @param red between 0 and 255
     * @param green between 0 and 255
     * @param blue between 0 and 255
     * @param fade time from the start until the led has his entire new rgb
     */
    public static void setRGBled(String name,float red,float green,float blue,float fade){
        try {
            currentApplication.getAlLeds().fadeRGB(name, red, green, blue, fade);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * Switch an LED on
     * @param name the name of the LED
     */
    public static void onled (String name){
        try{
            currentApplication.getAlLeds().on(name);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    /**
     * Switch an LED off
     * @param name the name of the LED
     */
    public static void offled (String name){
        try{
            currentApplication.getAlLeds().off(name);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    /**
     * let the LEDs of the Eyes blink in random colours
     * @param duration how long the eyes should make this, in seconds
     */
    public static void randomEyes(float duration){
        try{
            currentApplication.getAlLeds().randomEyes(duration);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    /**
     * Let the eyes rotate with a specific colour
     * @param rgb which colour
     * @param timeforroation how long it will rotate, in seconds
     * @param totalduration how many rotations
     */
    public static void rotateEyes(int rgb, float timeforroation, float totalduration){
        try{
            currentApplication.getAlLeds().rotateEyes(rgb,timeforroation,totalduration);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

}
