package nao;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALFaceDetection;
import com.aldebaran.qi.helper.proxies.ALFaceCharacteristics;
import com.aldebaran.qi.helper.proxies.ALLeds;
import com.aldebaran.qi.helper.proxies.ALSonar;

public class Test {

		public static Application application;
		
		public static void main(String[] args) {
			application = new Application(args, "tcp://127.0.0.1:9559"); //9559
			application.start();
			
			new Thread(() -> {
				try {
					resetMotion();
					ALMotion p = new ALMotion(application.session());
                    ALLeds led = new ALLeds(application.session());
                    ALSonar s = new ALSonar(application.session());

                    

                    led.on("FaceLedRight0");
                    System.out.println("Hallo");

				} catch(Exception err) {
					err.printStackTrace();
				}
			}).start();
		}
		public static void resetMotion() throws Exception {
			ALMotion p = new ALMotion(application.session());
			for(motors nMotors : motors.values()) {
				p.setAngles(nMotors.name, 0f, 1f);
			}
			p.waitUntilMoveIsFinished();
			Thread.sleep(1000);
		}
		
}
