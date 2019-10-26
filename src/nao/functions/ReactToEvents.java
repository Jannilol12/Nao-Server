package nao.functions;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;
import nao.currentApplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReactToEvents {
    private static long sonarId;
    ALMemory memory;
    public void run(Session session)throws Exception{
        memory = currentApplication.getAlMemory();
        sonarId = 0;

//        System.out.println("Start Sonar method");
//        String distanceLeft = null;
//        distanceLeft = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Left/Sensor/Value").toString();
//        String distanceRight = currentApplication.getAlMemory().getData("Device/SubDeviceList/US/Right/Sensor/Value").toString();
//        System.out.println(distanceLeft + "|" + distanceRight);
//            System.out.println("Try method");

        System.out.println("startSpeechRecognition method");
        List<String> vocabulary = new ArrayList<>();
        vocabulary.add("Hello");
            System.out.println("Try");
            currentApplication.getAlSpeechRecognition().setWordListAsVocabulary(vocabulary);
            currentApplication.getAlSpeechRecognition().subscribe("Test");
            sonarId =  currentApplication.getAlMemory().subscribeToEvent("WordRecognized", new EventCallback<String>() {
                @Override
                public void onEvent(String o) throws InterruptedException, CallError {
                    System.out.println("On Events");

                    System.out.println(o);
                    System.out.println(currentApplication.getAlMemory().getData("WordRecognized") + "" +  currentApplication.getAlMemory().getData("Test"));
                }
            });
    }

    public static void unsubscribe(){
        try {
            currentApplication.getAlMemory().unsubscribeToEvent(sonarId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CallError callError) {
            callError.printStackTrace();
        }
    }
}
