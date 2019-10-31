package nao.functions;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import components.json.JSONArray;
import components.json.JSONReader;
import nao.currentApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class events {
    private static ExecutorService executor;
    private static long footContact;
    private static long speechRecognition;
    private static ArrayList<String> vocabulary = new ArrayList<>();
    private static long sonar;
    private static long faceDetection;
    private static long barcodeReader;
    private static long landMark;
    private static Thread laserThread;

    static {
        executor = Executors.newFixedThreadPool(3);
    }

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
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        //rightFootContact: 1.0f if the right foot touched the ground
        // leftFootTotalWeight: the total weight on the left foot in kilograms
        // rightFootTotalWeight: the total weight on the right foot in kilograms

        try {
            footContact  = currentApplication.getAlMemory().subscribeToEvent("footContactChanged", new EventCallback<Boolean>() {
                @Override
                public void onEvent(Boolean variable) throws InterruptedException, CallError {
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

    public static void stopFootContactChanged(){
        try {
            currentApplication.getAlMemory().unsubscribeToEvent(footContact);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CallError callError) {
            callError.printStackTrace();
        }
    }

    public static void addVocabulary(String voc){
        vocabulary.add(voc);
        writeVocabulary();
    }

    public static void delVocabulary(String voc){
        vocabulary.remove(voc);
        writeVocabulary();
    }

    public static List<String> getVocabulary(){ return vocabulary;}

    public static void writeVocabulary(){
        JSONArray array = new JSONArray(vocabulary);
        try{
            File file = new File(new File("./").getParentFile(), "setup/" + "vocabulary");
            file.getParentFile().mkdirs();
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(array.toJSONString());
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadVocabulary(){
            File file = new File(new File("./").getParentFile(), "setup/" + "vocabulary");
            JSONArray array = (JSONArray) JSONReader.read(file);
            List<?> vList = array.toObjectList();
            System.out.println(vList.size());

            ArrayList<String> arrayList = new ArrayList(vList);
            vocabulary = arrayList;
    }

    public static void startSpeechRecognition() {
        try {
            currentApplication.getAlSpeechRecognition().setWordListAsVocabulary(vocabulary);
            currentApplication.getAlSpeechRecognition().subscribe("SpeechRecognition");
            speechRecognition =  currentApplication.getAlMemory().subscribeToEvent("WordRecognized", new EventCallback<ArrayList>() {
                @Override
                public void onEvent(ArrayList o) throws InterruptedException, CallError {
                    System.out.println("SubscribeToEevent Raised:");
                    System.out.println(o.toString());
                    System.out.println(currentApplication.getAlMemory().getData("WordRecognized") + "" +  currentApplication.getAlMemory().getData("Test"));
                }
            });
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopSpeechRecognition(){
        try {
            currentApplication.getAlSpeechRecognition().unsubscribe("SpeechRecognition");
            currentApplication.getAlMemory().unsubscribeToEvent(speechRecognition);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
            
            String distanceLeft = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Left/Sensor/Value").toString();
            String distanceRight = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Right/Sensor/Value").toString();
            System.out.println("Sonar Keys from ALMEMORY: ");
            System.out.println("Distance Left: " + distanceLeft + "|" + "Distance Right: " + distanceRight);
            System.out.println("--------------------------------");

            sonar = currentApplication.getAlMemory().subscribeToEvent("SonarLeftDetected", new EventCallback<Float>() {
                @Override
                public void onEvent(Float o) throws InterruptedException, CallError {
                    System.out.println("SubscribeToEevent Raised:");
                    System.out.println("Float raised: " + o);
                    String distanceLeft = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Left/Sensor/Value").toString();
                    String distanceRight = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Right/Sensor/Value").toString();
                    System.out.println("Distance Left: " + distanceLeft + "|" + "Distance Right: " + distanceRight);
                    System.out.println("---------------------------------");
                }
            });
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void stopSonar(){
        try {
            currentApplication.getAlMemory().unsubscribeToEvent(sonar);
            currentApplication.getAlSonar().unsubscribe("ALSonar");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CallError callError) {
            callError.printStackTrace();
        }
    }

    public static boolean learnFace(String name){
        boolean learnFace = false;
        try {
            learnFace = currentApplication.getAlFaceDetection().learnFace(name);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return learnFace;
    }

    public static void deleteFace(String name){
        try {
            currentApplication.getAlFaceDetection().forgetPerson(name);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getLearnedFaced(){
        try {
            return (List<String>) currentApplication.getAlFaceDetection().getLearnedFacesList();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void startFaceDectection(){
        try {
            String Face = currentApplication.getAlMemory().getData("FaceDetected").toString();
            System.out.println("FaceDection Keys from ALMEMORY: ");
            System.out.println(Face);
            System.out.println("---------------------------");

            currentApplication.getAlFaceDetection().subscribe("FaceDetection");
            faceDetection = currentApplication.getAlMemory().subscribeToEvent("FaceDetected", new EventCallback<ArrayList>() {
                @Override
                public void onEvent(ArrayList o) throws InterruptedException, CallError {
                    System.out.println("SubscribeToEevent Raised: ");
                    System.out.println(o);
                    System.out.println("-------------------------------------");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopFaceDetection(){
        try {
            currentApplication.getAlFaceDetection().unsubscribe("FaceDetection");
            currentApplication.getAlMemory().unsubscribeToEvent(faceDetection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CallError callError) {
            callError.printStackTrace();
        }
    }

    public static void startFaceTracking(){
        try {
            currentApplication.getAlFaceDetection().setTrackingEnabled(true);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stopFaceTracking(){
        try {
            currentApplication.getAlFaceDetection().setTrackingEnabled(false);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startBarcodeReader(){
        try {
            currentApplication.getAlBarcodeReader().subscribe("BarcodeReader");
            String Barcode = currentApplication.getAlMemory().getData("BarcodeReader/BarcodeDetected").toString();
            System.out.println("FaceDection Keys from ALMEMORY: ");
            System.out.println(Barcode);
            System.out.println("---------------------------");
            barcodeReader = currentApplication.getAlMemory().subscribeToEvent("BarcodeReader/BarcodeDetected", new EventCallback<ArrayList>() {
                @Override
                public void onEvent(ArrayList o) throws InterruptedException, CallError {
                    System.out.println("SubscribeToEevent Raised: ");
                    System.out.println(o);
                    System.out.println("-------------------------------------");
                }
            });
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBarcodeReader(){
        try {
            currentApplication.getAlBarcodeReader().unsubscribe("BarcodeReader");
            currentApplication.getAlMemory().unsubscribeToEvent(barcodeReader);
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startLandMark(){
        try {
            String LandmarkDetected = currentApplication.getAlMemory().getData("LandmarkDetected").toString();
            System.out.println("FaceDection Keys from ALMEMORY: ");
            System.out.println(LandmarkDetected);
            System.out.println("---------------------------");

            landMark = currentApplication.getAlMemory().subscribeToEvent("LandmarkDetected", new EventCallback<ArrayList>() {
                @Override
                public void onEvent(ArrayList o) throws InterruptedException, CallError {
                    System.out.println("LandmarkDetected Raised: ");
                    System.out.println(o);
                    System.out.println("-------------------------------------");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLandMark(){
        try {
            currentApplication.getAlMemory().unsubscribeToEvent(landMark);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CallError callError) {
            callError.printStackTrace();
        }
    }


    public static synchronized void startLaser(){

        try {
            float degrees = (float) Math.toRadians(90);
            currentApplication.getAlLaser().setDetectingLength(500,3000);
            currentApplication.getAlLaser().setOpeningAngle(degrees*-1, degrees);
            currentApplication.getAlLaser().laserON();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(laserThread != null){
            return;
        }
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
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                laserThread = null;
            }
        };
        laserThread.start();

    }

    public static synchronized void stopLaser(){
        if(laserThread == null) return;
        laserThread.interrupt();
        laserThread = null;
    }

}
