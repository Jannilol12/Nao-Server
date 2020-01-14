package nao.functions;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import components.json.JSONArray;
import components.json.JSONReader;
import nao.currentApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * start and stop all events like speech recognition, face detection etc.
 */
public class events {
    private static long footContact;
    private static long speechRecognition;
    private static ArrayList<String> vocabulary = new ArrayList<>();
    private static long sonar;
    private static long faceDetection;
    private static long barcodeReader;
    private static long landMark;
    private static Thread laserThread;

    static {
        Executors.newFixedThreadPool(3);
    }

    /**
     * says if the nao is touching a ground or flying in the air
     */
    public static void startFootContactChanged() {
        Object footContactKey = null;
        Object leftFootContact = null;
        Object rightFootContact = null;
        Object leftFootTotalWeight = null;
        Object rightFootTotalWeight = null;
        try {
            footContactKey = currentApplication.getAlMemory().getData("footContact");
            leftFootContact = currentApplication.getAlMemory().getData("leftFootContact");
            rightFootContact = currentApplication.getAlMemory().getData("rightFootContact");
            leftFootTotalWeight = currentApplication.getAlMemory().getData("leftFootTotalWeight");
            rightFootTotalWeight = currentApplication.getAlMemory().getData("rightFootTotalWeight");
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
        System.out.println("FootContact Keys from ALMEMORY: ");
        System.out.println("footContact: " + footContactKey);
        System.out.println("leftFootContact: " + leftFootContact);
        System.out.println("rightFootContact: " + rightFootContact);
        System.out.println("leftFootTotalWeight: " + leftFootTotalWeight);
        System.out.println("rightFootTotalWeight: " + rightFootTotalWeight);
        System.out.println("------------------------------");
        // footContact: 1.0f if one foot touched the ground
        // leftFootContact: 1.0f if the left foot touched the ground
        // rightFootContact: 1.0f if the right foot touched the ground
        // leftFootTotalWeight: the total weight on the left foot in kilograms
        // rightFootTotalWeight: the total weight on the right foot in kilograms


        //how to react to an event
        try {
            footContact  = currentApplication.getAlMemory().subscribeToEvent("footContactChanged", new EventCallback<Boolean>() {
                @Override
                public void onEvent(Boolean variable) throws InterruptedException, CallError {
                    //if true, the nao is standing on the ground, if false, he is flying in the air
                    if (variable) {
                        System.out.println("SubscribeToEevent Raised:");
                        System.out.println("At least one of the feet touched the ground.");
                    }
                    if(!variable){
                        System.out.println("SubscribeToEevent Raised:");
                        System.out.println("Both feet from the ground");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * stop the footContact event
     */
    public static void stopFootContactChanged(){
        try {
            currentApplication.getAlMemory().unsubscribeToEvent(footContact);
        } catch (InterruptedException | CallError e) {
            e.printStackTrace();
        }
    }

    /**
     * add a vocabulary for the speech recognition
     * @param voc the voc which shell be added
     */
    public static void addVocabulary(String voc){
        vocabulary.add(voc);
        //write the voc in a file
        writeVocabulary();
    }

    /**
     * delete a vocabulary
     * @param voc the voc which shell be deleted
     */
    public static void delVocabulary(String voc){
        vocabulary.remove(voc);
        //write the voc in a file
        writeVocabulary();
    }

    /**
     * get the vocabulary from the robot
     * @return the vocabulary as a list
     */
    public static List<String> getVocabulary(){ return vocabulary;}

    /**
     * write the vocabulary in a file, because the system isn't remembering his vocabulary unlike the faces
     */
    public static void writeVocabulary(){
        //save the voc as a JSON, so it can be easily read
        JSONArray array = new JSONArray(vocabulary);
        try{
            File file = new File(new File("./").getParentFile(), "setup/" + "vocabulary");
            file.getParentFile().mkdirs();
            //append false, so every time the file will be overwritten
            FileWriter fileWriter = new FileWriter(file, false);
            //write the JSON as a JSONString
            fileWriter.write(array.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read the vocabulary from the file
     */
    public static void loadVocabulary(){
        File file = new File(new File("./").getParentFile(), "setup/" + "vocabulary");
        JSONArray array = (JSONArray) JSONReader.read(file);
        //if there is no vocabulary, just quit the function
        if (array == null)
            return;
        ArrayList<String> sList = new ArrayList<>();
        //for every voc in the JSON, add it to the list
        for (Object obj : array.toObjectList()) {
            sList.add(obj.toString());
        }
        //set the list as the new vocabulary
        vocabulary = sList;
    }

    /**
     * load the vocabulary and start the speech recognition
     */
    public static void startSpeechRecognition() {
        try {
            //load the voc
            currentApplication.getAlSpeechRecognition().setWordListAsVocabulary(vocabulary);

            currentApplication.getAlSpeechRecognition().subscribe("SpeechRecognition");
            speechRecognition =  currentApplication.getAlMemory().subscribeToEvent("WordRecognized", new EventCallback<ArrayList>() {
                @Override
                public void onEvent(ArrayList o) throws InterruptedException, CallError {
                    System.out.println("SubscribeToEevent Raised:");
                    System.out.println(o.toString());
                    //o[0] is the actual word, and o[1] is how likely the word said is the actual word (between 0 and 1 as a float)
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * stop the speech Recognition
     */
    public static void stopSpeechRecognition(){
        try {
            currentApplication.getAlSpeechRecognition().unsubscribe("SpeechRecognition");
            currentApplication.getAlMemory().unsubscribeToEvent(speechRecognition);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * start the Sonar, but it won't update often!
     */
    public static void startSonar() {
        //http://doc.aldebaran.com/2-1/family/nao_dcm/actuator_sensor_names.html#term-us-sensor-m
        //The results of the first echo detected on each receiver are in Value, the 9 following echoes are from Value1 to Value9.
        //
        //Value of 0 means an error. A value of Max Detection range means no echo.
        //
        //For example, if Value contains 0,40, Value1 1,2 and Value2 Max Detection range, the following values (3 to 9) will contain Max Detection range too.
        // It probably means you have the echo of the ground at 0,40m and another object at 1,2m. Left and Right sensors work the same way and allow you to locate objects.
        try {
            currentApplication.getAlSonar().subscribe("ALSonar");
            sonar = currentApplication.getAlMemory().subscribeToEvent("SonarLeftDetected", new EventCallback<Float>() {
                @Override
                public void onEvent(Float o) throws InterruptedException, CallError {
                    System.out.println("SubscribeToEevent Raised:");
                    System.out.println("Float raised: " + o);
                    //just getting some information from the Memory, don't know if it's useful
                    String distanceLeft = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Left/Sensor/Value").toString();
                    String distanceRight = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Right/Sensor/Value").toString();
                    System.out.println("Distance Left: " + distanceLeft + "|" + "Distance Right: " + distanceRight);
                    System.out.println("---------------------------------");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * stop the sonar
     */
    public static void stopSonar(){
        try {
            currentApplication.getAlMemory().unsubscribeToEvent(sonar);
            currentApplication.getAlSonar().unsubscribe("ALSonar");
        } catch (InterruptedException | CallError e) {
            e.printStackTrace();
        }
    }

    /**
     * learn a new face
     * @param name the name of the face which shell be learned
     */
    public static void learnFace(String name){
        try {
            currentApplication.getAlFaceDetection().learnFace(name);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * delete a face from the system
     * @param name the name of the face which shell be deleted
     */
    public static void deleteFace(String name){
        try {
            currentApplication.getAlFaceDetection().forgetPerson(name);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * get all learned faces
     * @return the faces are saved as a List
     */
    public static List<String> getLearnedFaced(){
        try {
            return (List<String>) currentApplication.getAlFaceDetection().getLearnedFacesList();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
        return null;
    }

    /**
     * start the FaceDetection
     */
    public static void startFaceDectection(){
        try {
            currentApplication.getAlFaceDetection().subscribe("FaceDetection");
            faceDetection = currentApplication.getAlMemory().subscribeToEvent("FaceDetected", new EventCallback<ArrayList>() {
                @Override
                public void onEvent(ArrayList FaceDetected) throws InterruptedException, CallError {

                    //I named this Arrays like them on the website from Aldebaran except ArrayInFaceDetected!

                    ArrayList<ArrayList> ArrayInFaceDetected = new ArrayList<>();
                    ArrayInFaceDetected = (ArrayList<ArrayList>) FaceDetected.get(1);
                    ArrayList<ArrayList> FaceInfo = new ArrayList<>();
                    FaceInfo = (ArrayList<ArrayList>) ArrayInFaceDetected.get(0);
                    ArrayList<String> ExtraInfo = new ArrayList<>();
                    ExtraInfo = (ArrayList<String>) FaceInfo.get(1);
                    String nameB = ExtraInfo.get(2);

                    System.out.println(nameB);

                    if(nameB.equalsIgnoreCase("Jannik")){
                        System.out.println("Hello Jannik");
                    }else if(nameB.equalsIgnoreCase("Nick")){
                        behavior.runBehavior("nickmove-828ed6/behavior_1");
                    }else{
                        System.out.println("Nothing detected!");
                    }
                    System.out.println("-------------------------------------");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * stop the FaceDetection
     */
    public static void stopFaceDetection(){
        try {
            currentApplication.getAlFaceDetection().unsubscribe("FaceDetection");
            currentApplication.getAlMemory().unsubscribeToEvent(faceDetection);
        } catch (InterruptedException | CallError e) {
            e.printStackTrace();
        }
    }

    /**
     * start the FaceTracking, didn't really recognized if the robot really is tracking a face or not
     */
    public static void startFaceTracking(){
        try {
            currentApplication.getAlFaceDetection().enableTracking(true);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * stop the FaceTracking
     */
    public static void stopFaceTracking(){
        try {
            currentApplication.getAlFaceDetection().enableTracking(false);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * start the QR-Code Reader
     */
    public static void startBarcodeReader(){
        try {
            barcodeReader = currentApplication.getAlMemory().subscribeToEvent("BarcodeReader/BarcodeDetected", new EventCallback<ArrayList>() {
                @Override
                public void onEvent(ArrayList o) {
                    System.out.println("SubscribeToEevent Raised: ");
                    System.out.println(o);
                    System.out.println("-------------------------------------");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * stop the QR-Reader
     */
    public static void stopBarcodeReader(){
        try {
            currentApplication.getAlBarcodeReader().unsubscribe("BarcodeReader");
            currentApplication.getAlMemory().unsubscribeToEvent(barcodeReader);
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }
    }

    /**
     * start the LandMark detection, but when we tested it, the robot most of the time didn't recognized the landmarks in Java, on the Coregraphe Monitor he did...
     */
    public static void startLandMark(){
        try {
            landMark = currentApplication.getAlMemory().subscribeToEvent("LandmarkDetected", new EventCallback<ArrayList>() {
                @Override
                public void onEvent(ArrayList o) {
                    System.out.println("LandmarkDetected Raised: ");
                    System.out.println(o);
                    System.out.println("-------------------------------------");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * stop the LandMark detection
     */
    public static void stopLandMark(){
        try {
            currentApplication.getAlMemory().unsubscribeToEvent(landMark);
        } catch (InterruptedException | CallError e) {
            e.printStackTrace();
        }
    }

    /**
     * start the Laser
     */
    public static synchronized void startLaser(){
        try {
            //upsetting the laser...
            float degrees = (float) Math.toRadians(90);
            currentApplication.getAlLaser().setDetectingLength(500,3000);
            currentApplication.getAlLaser().setOpeningAngle(degrees*-1, degrees);
            currentApplication.getAlLaser().laserON();
        } catch (CallError | InterruptedException callError) {
            callError.printStackTrace();
        }

        //if the laser is running, quit this function
        if(laserThread != null){
            return;
        }

        //start the laser as a thread, so it is reading permanent
        laserThread = new Thread(){
            @Override
            public void run() {
                while (!this.isInterrupted()) {
                    try {
                        Object laser = currentApplication.getAlMemory().getData("Device/Laser/Value");
                        System.out.println("FaceDection Keys from ALMEMORY: ");
                        System.out.println(laser);
                        System.out.println("---------------------------");

                        Thread.sleep(1000);
                    } catch (CallError | InterruptedException callError) {
                        callError.printStackTrace();
                    }
                }
                laserThread = null;
            }
        };
        laserThread.start();
    }

    /**
     * stop the Laser
     */
    public static synchronized void stopLaser(){
        //if it is running, do nothing, else stop running
        if(laserThread == null) return;
        laserThread.interrupt();
        laserThread = null;
    }

}
