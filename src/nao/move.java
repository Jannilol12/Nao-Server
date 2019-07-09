package nao;

import com.aldebaran.qi.helper.proxies.ALMotion;

public class move {
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

}
