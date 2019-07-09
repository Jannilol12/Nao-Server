package nao;

import com.aldebaran.qi.Application;

public class currentApplication {
    private static Application application;

    public synchronized static void load(String ip, int port){
    	new Thread(() -> {
	        String[] args = new String[0];
	        application = new Application(args, "tcp://" + ip + ":" + port);
	        application.start();
    	}).start();
    }

    public static Application getApplication(){
        return application;
    }
}
