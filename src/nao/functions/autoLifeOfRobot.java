package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.concurrent.Executors;

/**
 * setting the autonomous life strategy of the nao
 */
public class autoLifeOfRobot {

    //the maximum of threads the nao could use
    static {
        Executors.newFixedThreadPool(3);
    }

    /**
     * setting a life mode
     * @param name either solitary, interactive , disabled or safeguard
     * @throws CallError error
     * @throws InterruptedException error
     */
    public static void setLife(String name) throws CallError, InterruptedException {
        currentApplication.getAlAutonomousLife().setState(name);
    }

    /**
     * setting how high, the nao is standing, for example on the floor or on the table, that he knows if he have to look up or down
     * @param height in meters, how high he is standing
     * @throws CallError error
     * @throws InterruptedException error
     */
    public static void setRobotOffsetFromFloor(float height) throws CallError, InterruptedException {
        currentApplication.getAlAutonomousLife().setRobotOffsetFromFloor(height);
    }

    /**
     * Switch the Expressive Listening on or off, don't know what it really does, but hey, here it is!
     * @param bol on or off
     * @throws CallError error
     * @throws InterruptedException error
     */
    public static void setExpressiveListeningEnabled(boolean bol) throws CallError, InterruptedException {
        currentApplication.getAlAutonomousMoves().setExpressiveListeningEnabled(bol);
    }

    /**
     * Setting the background strategy of the nao
     * @param strategy either none or backToNeutral
     * @throws CallError error
     * @throws InterruptedException error
     */
    public static void setBackgroundStrategy(String strategy) throws CallError, InterruptedException {
        currentApplication.getAlAutonomousMoves().setBackgroundStrategy(strategy);
    }

}
