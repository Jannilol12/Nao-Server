package nao.moves;

import com.aldebaran.qi.helper.proxies.ALMotion;

import components.json.JSONArray;
import nao.functions.currentApplication;
import nao.functions.motors;

public class winken implements SendClassName{

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

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

    @Override
    public JSONArray getArgsRequest() {
        return null;
    }
}
