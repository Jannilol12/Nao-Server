package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class autoLifeOfRobot {
    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(3);
    }

    public static void setLife(String name){
        //solitary, interactive , disabled , safeguard
        try {
            currentApplication.getAlAutonomousLife().setState(name);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static String getLife(){
        try {
            return currentApplication.getAlAutonomousLife().getState();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
        return "Self-destruction";
    }

    public static void setRobotOffsetFromFloor(float height){
        try {
            currentApplication.getAlAutonomousLife().setRobotOffsetFromFloor(height);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static float getRobotOffsetFromFloor(){
        try {
            return currentApplication.getAlAutonomousLife().getRobotOffsetFromFloor();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
        return (float) -1;
    }

    //----------------- AL Autonomous Moves ---------------------

    public static void setExpressiveListeningEnabled(boolean bol){
        try {
            currentApplication.getAlAutonomousMoves().setExpressiveListeningEnabled(bol);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static boolean getExpressiveListeningEnabled(){
        try {
            return currentApplication.getAlAutonomousMoves().getExpressiveListeningEnabled();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
        return false;
    }

    public static void setBackgroundStrategy(String strategy){
        //none or backToNeutral
        try {
            currentApplication.getAlAutonomousMoves().setBackgroundStrategy(strategy);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    public static String getBackgroundStrategy(){
        try {
            return currentApplication.getAlAutonomousMoves().getBackgroundStrategy();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
        return "Self-destruction";
    }
}
