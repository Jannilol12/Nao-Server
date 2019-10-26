package nao;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.*;
import nao.functions.ReactToEvents;

public class currentApplication {
    private static Application application;
    private static ALMotion alMotion;
    private static ALLeds alLeds;
    private static ALBattery alBattery;
    private static ALLauncher alLauncher;
    private static ALRobotPosture alRobotPosture;
    private static ALSystem alSystem;
    private static ALTextToSpeech alTextToSpeech;
    private static ALMemory alMemory;
    private static ALAudioPlayer alAudioPlayer;
    private static ALSpeechRecognition alSpeechRecognition;
    private static ALMotionRecorder alMotionRecorder;
    private static ALFaceDetection alFaceDetection;
    private static ReactToEvents react;



    public synchronized static void load(String ip, int port){
    	new Thread(() -> {
	        String[] args = new String[0];
	        application = new Application(args, "tcp://" + ip + ":" + port);
	        application.start();
	        react = new ReactToEvents();

	        try{
	            alMotionRecorder = new ALMotionRecorder(currentApplication.getApplication().session());
            }catch(Exception e){
	            e.printStackTrace();
            }

            try{
                alAudioPlayer = new ALAudioPlayer(currentApplication.getApplication().session());
            }catch (Exception e){
                e.printStackTrace();
            }

	        try{
	            alMemory = new ALMemory(currentApplication.getApplication().session());
            }catch (Exception e){
	            e.printStackTrace();
            }

	        try{
                alTextToSpeech = new ALTextToSpeech(currentApplication.getApplication().session());
                alTextToSpeech.setLanguage("English");
            }catch(Exception e){
	            e.printStackTrace();
            }

            try {
                alMotion = new ALMotion(currentApplication.getApplication().session());
                alMotion.setFallManagerEnabled(true);
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

            try {
                alSpeechRecognition = new ALSpeechRecognition(currentApplication.getApplication().session());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                alFaceDetection = new ALFaceDetection(currentApplication.getApplication().session());
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

    public static ALTextToSpeech getAlTextToSpeech(){ return alTextToSpeech;}

    public static ALMemory getAlMemory(){ return alMemory;}

    public static ALSpeechRecognition getAlSpeechRecognition(){return alSpeechRecognition;}

    public static ALAudioPlayer getAlAudioPlayer(){ return alAudioPlayer;}

    public static ALMotionRecorder getAlMotionRecorder(){ return alMotionRecorder;}

    public static ALFaceDetection getAlFaceDetection(){ return alFaceDetection;}

    public static ReactToEvents getReact(){ return react;}
}
