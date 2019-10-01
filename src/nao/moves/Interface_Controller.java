package nao.moves;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import components.json.JSONArray;

public class Interface_Controller {
    private static Map<String, SendClassName> map = Collections.synchronizedMap(new HashMap<>());

    private static SendClassName currentClassNameRunning;
    private static Thread currentRunning;

    private Interface_Controller(){}

    public synchronized static void load(){
        map.clear();

        SendClassName current = new gandamstyle();
        map.put(current.name(), current);

        current = new say();
        map.put(current.name(), current);

        current = new winken();
        map.put(current.name(), current);


        current = new FaceDetect();
        map.put(current.name(), current);

    }

    public synchronized static void unload(){
        map.clear();
    }

    public synchronized static void ausfuehren(String name, JSONArray args){
        SendClassName sendClassName = map.get(name);

        if(currentRunning != null)
          stop();

        currentClassNameRunning = sendClassName;
        if(sendClassName != null && currentRunning == null){
            currentRunning = new Thread(() -> {
                sendClassName.start(args);
            });
            currentRunning.start();
        }
    }

    public synchronized static void stop(){
        if(currentClassNameRunning != null) {
            currentClassNameRunning.stop();
            currentClassNameRunning = null;
        }

        if(currentRunning != null){
            currentRunning.interrupt();

            try {
                currentRunning.join(1000);
            } catch (InterruptedException e) {}

            currentRunning.stop();
            currentRunning = null;
        }
    }

    public static List<String> getProgsName(){
        List<String> list = new LinkedList<>();

        synchronized (map){
            for(String prog : map.keySet()){
                list.add(prog);
            }
        }

        return list;
    }

    public static List<SendClassName> getSendClassNames(){
        List<SendClassName> list = new LinkedList<>();

        synchronized (map){
            for(SendClassName prog : map.values()){
                list.add(prog);
            }
        }

        return list;
    }
}
