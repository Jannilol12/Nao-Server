package nao;

import com.aldebaran.qi.helper.proxies.ALMotion;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class move {
    private Executor executor = Executors.newFixedThreadPool(10);

    public void moveinfinity(float forward_backward, float right_left){
        stop();
        new Thread(() -> {
            try {
                ALMotion move = new ALMotion(currentApplication.getApplication().session());
                move.move(forward_backward, right_left, (float) Math.toRadians(0));
            }
            catch (Exception err){
                err.printStackTrace();
            }
        }).start();
    }

    public void steps(float foward_backward, float right_left ){
        stop();
        new Thread(() -> {
            try {
                ALMotion move = new ALMotion(currentApplication.getApplication().session());
                move.moveTo(foward_backward / 30, right_left / 30, (float) Math.toRadians(0));
            }
            catch (Exception err){
                err.printStackTrace();
            }
        }).start();
    }

    public void stop(){
        try{
            ALMotion move = new ALMotion(currentApplication.getApplication().session());
            move.stopMove();
            move.stopWalk();
        }
        catch(Exception err){

        }
    }
    public void rotate(int Degrees){
        stop();
        new Thread(() -> {
            try{
                ALMotion move = new ALMotion(currentApplication.getApplication().session());
                move.moveTo(0f,0f, (float)Math.toRadians(Degrees));
            }
            catch (Exception err){

            }
        }).start();
    }
    public void test(){
        stop();
        new Thread(() -> {
            try{
                Thread.sleep(2_000);
                ALMotion p = new ALMotion(currentApplication.getApplication().session());
                p.setAngles(motors.LShoulderPitch.name, -1.0f, 1.0f);
                Thread.sleep(2_000);
            }
            catch (Exception err){

            }
        }).start();
    }
    public void motors(String motor, float angle, float speed){
        stop();
        new Thread(() -> {
            try{
                ALMotion p = new ALMotion(currentApplication.getApplication().session());
                p.setAngles(motor, angle, speed);
            }
            catch (Exception err){

            }
        }).start();
    }
    public float getangle(String motor){
        try {
            ALMotion p = new ALMotion(currentApplication.getApplication().session());
            List<Float> list = p.getAngles(motor, true);
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
