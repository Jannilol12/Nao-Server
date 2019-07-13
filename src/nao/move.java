package nao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class move {
    private static ExecutorService executor;
    
    static {
    	executor = Executors.newFixedThreadPool(1);
    }
    
    public static void moveinfinity(float forward_backward, float right_left){
        stop();
        executor.execute(() -> {
            try {
                if(currentApplication.getAlMotion() != null) {
                    currentApplication.getAlMotion().move(forward_backward, right_left, (float) Math.toRadians(0));
                }
            }
            catch (Exception err){
                err.printStackTrace();
            }
        });
    }

    public static void steps(float foward_backward, float right_left ){
        stop();
        executor.execute(() -> {
            try {
                if(currentApplication.getAlMotion() != null) {
                    currentApplication.getAlMotion().moveTo(foward_backward / 30, right_left / 30, (float) Math.toRadians(0));
                }
            }
            catch (Exception err){
                err.printStackTrace();
            }
        });
    }

    public static void stop(){
        try{
            if(currentApplication.getAlMotion() != null) {
                currentApplication.getAlMotion().stopMove();
                currentApplication.getAlMotion().stopWalk();
            }
        }
        catch(Exception err){}
    }
    
    public static void rotate(int Degrees){
//        stop();
        executor.execute(() -> {
            try{
                if(currentApplication.getAlMotion() != null) {
                   currentApplication.getAlMotion().moveTo(0f, 0f, (float) Math.toRadians(Degrees));
                }
            }catch (Exception err){}
        });
    }
    
    public static void motors(String motor, float angle, float speed){
//        stop();
    	executor.execute(() -> {
            try{
                if(currentApplication.getAlMotion() != null) {
                    currentApplication.getAlMotion().setAngles(motor, angle, speed);
                }
            }catch (Exception err){}
        });
    }
    
    public static float getAngle(String motor){
        try {
            if(currentApplication.getAlMotion() != null) {
                List<Float> list = currentApplication.getAlMotion().getAngles(motor, true);
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void wakeup(){
        try{
            if(currentApplication.getAlMotion() != null) {
                currentApplication.getAlMotion().wakeUp();
            }
        } catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void handopenclose(String Left_Right, String O_C){
        try{
            if(currentApplication.getAlMotion() != null) {
                if (O_C == "O") {
                    currentApplication.getAlMotion().openHand(Left_Right);
                } else if (O_C == "C") {
                    currentApplication.getAlMotion().closeHand(Left_Right);
                }
            }
        }catch(Exception err){
            err.printStackTrace();
        }
    }
}
