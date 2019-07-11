package nao;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALLeds;
import com.aldebaran.qi.helper.proxies.ALMotion;

public class currentApplication {
    private static Application application;
    private static ALMotion alMotion;
    private static ALLeds alLeds;

    public synchronized static void load(String ip, int port){
    	new Thread(() -> {
	        String[] args = new String[0];
	        application = new Application(args, "tcp://" + ip + ":" + port);
	        application.start();
            try {
                alMotion = new ALMotion(currentApplication.getApplication().session());
                alLeds = new ALLeds(currentApplication.getApplication().session());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public static Application getApplication(){
        return application;
    }

    public static ALMotion getAlMotion(){ return alMotion;}

    public static ALLeds getAlLeds(){ return alLeds;    }
}
