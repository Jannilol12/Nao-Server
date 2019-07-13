package nao.functions;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.*;

public class currentApplication {
    private static Application application;
    private static ALMotion alMotion;
    private static ALLeds alLeds;
    private static ALBattery alBattery;
    private static ALLauncher alLauncher;
    private static ALRobotPosture alRobotPosture;
    private static ALSystem alSystem;

    public synchronized static void load(String ip, int port){
    	new Thread(() -> {
	        String[] args = new String[0];
	        application = new Application(args, "tcp://" + ip + ":" + port);
	        application.start();

            try {
                alMotion = new ALMotion(currentApplication.getApplication().session());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                alLeds = new ALLeds(currentApplication.getApplication().session());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                alBattery = new ALBattery(currentApplication.getApplication().session());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                alLauncher = new ALLauncher(currentApplication.getApplication().session());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                alRobotPosture = new ALRobotPosture(currentApplication.getApplication().session());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                alSystem = new ALSystem(currentApplication.getApplication().session());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public static Application getApplication(){
        return application;
    }

    public static ALMotion getAlMotion(){ return alMotion;}

    public static ALLeds getAlLeds(){ return alLeds;}

    public static ALBattery getAlBattery() {return alBattery;}

    public static ALLauncher getAlLauncher() {return alLauncher;}

    public static ALRobotPosture getAlRobotPosture() {return alRobotPosture;}

    public static ALSystem getAlSystem(){return alSystem;}
}
