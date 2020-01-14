package nao.moves;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import components.json.JSONArray;

/**
 * this class is for the list on the left side of the client
 */

public class Interface_Controller {
    private static final Map<String, SendClassName> map = Collections.synchronizedMap(new HashMap<>());

    private static SendClassName currentClassNameRunning;
    private static Thread currentRunning;

    private Interface_Controller(){}

    /**
     * clear the map and putting the classes you want to use in a map...
     *
     * Adding a class: copy those lines and add the class...
     * current = new YOUR_CLASS();
     * map.put(current.name(), current);
     */
    public synchronized static void load(){
        map.clear();

        SendClassName current = new gangnam();
        map.put(current.name(), current);

        current = new say();
        map.put(current.name(), current);

        current = new winken();
        map.put(current.name(), current);

    }

    /**
     * @param name Name of the class, which will be started
     * @param args For example Strings receiving from the client, which the class need to start
     */
    public synchronized static void ausfuehren(String name, JSONArray args){
        //getting the name of the class
        SendClassName sendClassName = map.get(name);

        //if it is running, stop it
        if(currentRunning != null)
          stop();

        //start a new Thread in which the class is running
        currentClassNameRunning = sendClassName;
        if(sendClassName != null && currentRunning == null){
            currentRunning = new Thread(() -> sendClassName.start(args));
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentRunning = null;
        }
    }

    /**
     * @return getting all names and send to the client, which is adding them to the list on the left side
     */
    public static List<SendClassName> getSendClassNames(){
        List<SendClassName> list;

        synchronized (map){
            list = new LinkedList<>(map.values());
        }

        return list;
    }
}
