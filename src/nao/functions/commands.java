package nao.functions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

/**
 * System functions and posture of the robot
 */
public class commands {
    private static ExecutorService executor;

    //the maximum of threads the nao could use
    static {
        executor = Executors.newFixedThreadPool(3);
    }

    /**
     * Go into a posture
     * @param posture in which posture the nao shell go
     * @param speed with which speed the nao should do this
     */
    public static void goToPosture(String posture, float speed){
    	executor.execute(() -> {
	        try{
	        	if (currentApplication.getAlRobotPosture() != null) {
		        	synchronized (currentApplication.getAlRobotPosture()) {
		        	    //stop every possible movement and start going into a posture
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

    /**
     * stop going into a posture
     */
    public static void stopPosture() {
		try {
			if (currentApplication.getAlRobotPosture() != null)
				currentApplication.getAlRobotPosture().stopMove();
		} catch (CallError | InterruptedException e) {
			e.printStackTrace();
		}
    }

    /**
     * how many tries the robot has to go into a posture
     * @param maxTry how many tries the robot has....in {@link nao.Main} 5 are given
     */
    public static void setMaxTryNumber(int maxTry) {
//    	try {
//                currentApplication.getAlRobotPosture().setMaxTryNumber(maxTry);
//		} catch (CallError | InterruptedException e) {
//			e.printStackTrace();
//		}
    }

    /**
     * get the battery level
     * @return between 0 and 100
     */
    public static int getBatteryCharge(){
        try {
                return currentApplication.getAlBattery().getBatteryCharge();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * reboot the system
     */
    public static void reboot(){
        try {
                currentApplication.getAlSystem().reboot();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * shutdown the system
     */
    public static void shutdown(){
        try {
                currentApplication.getAlSystem().shutdown();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
