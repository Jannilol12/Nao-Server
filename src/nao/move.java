package nao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.aldebaran.qi.helper.proxies.ALMotion;

public class move {
    private ExecutorService executor;

    public move() {
    	executor = Executors.newFixedThreadPool(1);
    }
    
    public void moveinfinity(float forward_backward, float right_left){
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

    public void steps(float foward_backward, float right_left ){
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

    public void stop(){
        try{
            if(currentApplication.getAlMotion() != null) {
                currentApplication.getAlMotion().stopMove();
                currentApplication.getAlMotion().stopWalk();
            }
        }
        catch(Exception err){}
    }
    
    public void rotate(int Degrees){
//        stop();
        executor.execute(() -> {
            try{
                if(currentApplication.getAlMotion() != null) {
                   currentApplication.getAlMotion().moveTo(0f, 0f, (float) Math.toRadians(Degrees));
                }
            }catch (Exception err){}
        });
    }
    
    public void test(){
        stop();
        new Thread(() -> {
            try{
                Thread.sleep(2_000);
                if(currentApplication.getAlMotion() != null) {
                    currentApplication.getAlMotion().setAngles(motors.LShoulderPitch.name, -1.0f, 1.0f);
                }
                Thread.sleep(2_000);
            }catch (Exception err){}
        }).start();
    }
    
    public void motors(String motor, float angle, float speed){
//        stop();
    	executor.execute(() -> {
            try{
                if(currentApplication.getAlMotion() != null) {
                    currentApplication.getAlMotion().setAngles(motor, angle, speed);
                }
            }catch (Exception err){}
        });
    }
    
    public float getAngle(String motor){
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

    public void wakeup(){
        try{
            if(currentApplication.getAlMotion() != null) {
                currentApplication.getAlMotion().wakeUp();
            }
        } catch (Exception err){
            err.printStackTrace();
        }
    }

    public void handopenclose(String Left_Right, String O_C){
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
