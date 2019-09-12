package nao.functions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

public class commands {
    private static ExecutorService executor;
    
    static {
        executor = Executors.newFixedThreadPool(3);
    }

    public static void goToPosture(String posture, float speed){
    	executor.execute(() -> {
	        try{
	        	if (currentApplication.getAlRobotPosture() != null) {
		        	synchronized (currentApplication.getAlRobotPosture()) {
		        		move.stop();
		        		stopPosture();
				        currentApplication.getAlRobotPosture().goToPosture(posture, speed);
					}
	        	}
		    }
	        catch (Exception e){
	            e.printStackTrace();
	        }
    	});
    }
    
    public static void stopPosture() {
		try {
			if (currentApplication.getAlRobotPosture() != null)
				currentApplication.getAlRobotPosture().stopMove();
		} catch (CallError | InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public static void setMaxTryNumber(int maxTry) {
    	try {
			if (currentApplication.getAlRobotPosture() != null)
				currentApplication.getAlRobotPosture().setMaxTryNumber(maxTry);
		} catch (CallError | InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public static int getBatteryCharge(){
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

    public static void reboot(){
        try {
            if (currentApplication.getAlSystem() != null){
                currentApplication.getAlSystem().reboot();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void shutdown(){
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
