package nao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.aldebaran.qi.helper.proxies.ALMotion;

public class move {
    private ExecutorService executor;

    public move() {
    	executor = Executors.newFixedThreadPool(10);
    }
    
    public void moveinfinity(float forward_backward, float right_left){
        stop();
        executor.execute(() -> {
            try {
                ALMotion move = new ALMotion(currentApplication.getApplication().session());
                move.move(forward_backward, right_left, (float) Math.toRadians(0));
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
                ALMotion move = new ALMotion(currentApplication.getApplication().session());
                move.moveTo(foward_backward / 30, right_left / 30, (float) Math.toRadians(0));
            }
            catch (Exception err){
                err.printStackTrace();
            }
        });
    }

    public void stop(){
        try{
            ALMotion move = new ALMotion(currentApplication.getApplication().session());
            move.stopMove();
            move.stopWalk();
        }
        catch(Exception err){}
    }
    
    public void rotate(int Degrees){
//        stop();
        executor.execute(() -> {
            try{
                ALMotion move = new ALMotion(currentApplication.getApplication().session());
                move.moveTo(0f,0f, (float)Math.toRadians(Degrees));
            }catch (Exception err){}
        });
    }
    
    public void test(){
        stop();
        new Thread(() -> {
            try{
                Thread.sleep(2_000);
                ALMotion p = new ALMotion(currentApplication.getApplication().session());
                p.setAngles(motors.LShoulderPitch.name, -1.0f, 1.0f);
                Thread.sleep(2_000);
            }catch (Exception err){}
        }).start();
    }
    
    public void motors(String motor, float angle, float speed){
//        stop();
    	executor.execute(() -> {
            try{
                ALMotion p = new ALMotion(currentApplication.getApplication().session());
                p.setAngles(motor, angle, speed);
            }catch (Exception err){}
        });
    }
    
    public float getAngle(String motor){
        try {
            ALMotion p = new ALMotion(currentApplication.getApplication().session());
            List<Float> list = p.getAngles(motor, true);
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void wakeup(){
        try{
            ALMotion p = new ALMotion(currentApplication.getApplication().session());
            p.wakeUp();
        } catch (Exception err){
            err.printStackTrace();
        }
    }
}
