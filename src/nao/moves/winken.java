package nao.moves;

import com.aldebaran.qi.helper.proxies.ALMotion;

import components.json.JSONArray;
import nao.currentApplication;
import nao.functions.motors;

/**
 * a class, just started and never finished
 */
public class winken implements SendClassName{

    /**
     * @return getting the name of the class
     */
    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    /**
     *
     * @param args what the class need to start, see {@link say} where a String is needed.
     */
    @Override
    public void start(JSONArray args) {
        try{
            resetMotion();
            float speed = 0.1f;
//            p.setAngles(motors.RShoulderRoll.name, Math.toRadians(0), speed);


        }catch(Exception err){
            err.printStackTrace();
        }
    }

    /**
     * Resetting al motors from the robot to one position...ATTENTION the robot may fall
     * @throws Exception
     */
    public void resetMotion() throws Exception {
        ALMotion p = currentApplication.getAlMotion();
        for(motors nMotors : motors.values()) {
            p.setAngles(nMotors.name, 0f, 1f);
        }
        p.waitUntilMoveIsFinished();
        Thread.sleep(1000);
    }

    @Override
    public void stop() {
    }

    /**
     *
     * @return this class need nothing, so null....for example {@link say} need a String
     */
    @Override
    public JSONArray getArgsRequest() {
        return null;
    }
}
