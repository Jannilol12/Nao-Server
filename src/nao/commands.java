package nao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class commands {
    private ExecutorService executor;
    
    public commands(){
        executor = Executors.newFixedThreadPool(3);
    }

    public void goToPosture(String posture, float speed){
        try{
	        if (currentApplication.getAlRobotPosture() != null){
	            currentApplication.getAlRobotPosture().goToPosture(posture, speed);
	        }}
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getBatteryCharge(){
        try {
            if (currentApplication.getAlBattery() != null){
                return currentApplication.getAlBattery().getBatteryCharge();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public void reboot(){
        try {
            if (currentApplication.getAlSystem() != null){
                currentApplication.getAlSystem().reboot();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void shutdown(){
        try {
            if (currentApplication.getAlSystem() != null){
                currentApplication.getAlSystem().shutdown();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
