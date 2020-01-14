package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Remote-control for the robot
 */
public class move {
    private static ExecutorService executor;

    //the maximum of threads the nao could use
    static {
    	executor = Executors.newFixedThreadPool(1);
    }

    /**
     * move until someone stops him
     * @param forward_backward + or - for moving forward or backward
     * @param right_left + or - for moving right or left
     */
    public static void moveInfinity(float forward_backward, float right_left){
        //stop every movement
        stop();
        executor.execute(() -> {
            try {
                currentApplication.getAlMotion().move(forward_backward, right_left, (float) Math.toRadians(0));
            }
            catch (Exception err){
                err.printStackTrace();
            }
        });
    }

    /**
     * move only one amount of steps
     * @param forward_backward how many steps he should make.../30 is try and error that he is making one step,+ or - for moving forward or backward
     * @param right_left how many steps he should make.../30 is try and error that he is making one step,+ or - for moving right or left
     */
    public static void steps(float forward_backward, float right_left ){
        stop();
        executor.execute(() -> {
            try {
                currentApplication.getAlMotion().moveTo(forward_backward / 30, right_left / 30, (float) Math.toRadians(0));
            }
            catch (Exception err){
                err.printStackTrace();
            }
        });
    }

    /**
     * stop every movement
     */
    public static void stop(){
        try{
            currentApplication.getAlMotion().stopMove();
            currentApplication.getAlMotion().stopWalk();
        }
        catch(Exception err){
            err.printStackTrace();
        }
    }

    /**
     * Let the robot rotate
     * @param Degrees as far the robot should turn around, in degrees
     */
    public static void rotate(int Degrees){
        executor.execute(() -> {
            try{
                   currentApplication.getAlMotion().moveTo(0f, 0f, (float) Math.toRadians(Degrees));
            }catch (Exception err){
                err.printStackTrace();
            }
        });
    }

    /**
     * Move a motor of the robot
     * @param motor the name of the motor which shell be moved
     * @param angle as far the motor shell be rotated, in degrees
     * @param speed how fast he shell turn his motors
     */
    public static void motors(String motor, float angle, float speed){
    	executor.execute(() -> {
            try {
                currentApplication.getAlMotion().setAngles(motor, angle, speed);
            } catch (CallError | InterruptedException callError) {
                callError.printStackTrace();
            }
        });
    }

    /**
     * get the Angle of a motor
     * @param motor name of motor you want the angle
     * @return the angle in Radians
     */
    public static float getAngle(String motor){
        try {
            List<Float> list = currentApplication.getAlMotion().getAngles(motor, true);
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * let the robot stand up
     */
    public static void wakeup(){
        try{
            currentApplication.getAlMotion().wakeUp();
        } catch (Exception err){
            err.printStackTrace();
        }
    }

    /**
     * let the robot open or close his hand
     * @param Left_Right LHand or RHand for the left or right hand, the name of the motor
     * @param O_C O for Open, C for close
     */
    public static void handOpenClose(String Left_Right, String O_C){
        try{
            if (O_C.equals("O")) {
                currentApplication.getAlMotion().openHand(Left_Right);
            } else if (O_C.equals("C")) {
                currentApplication.getAlMotion().closeHand(Left_Right);
            }
        }catch(Exception err){
            err.printStackTrace();
        }
    }
}
