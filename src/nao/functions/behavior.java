package nao.functions;

import com.aldebaran.qi.CallError;
import nao.currentApplication;

import java.util.List;
import java.util.concurrent.Executors;


/**
 * using the behaviors created in coregraphe or which are installed from nature
 */
public class behavior {

    //the maximum of threads the nao could use
    static {
        Executors.newFixedThreadPool(3);
    }

    /**
     * Removing a behavior
     * @param name the actual name of the behavior, the id, not the name you gave it in coregraphe
     * @throws InterruptedException error
     * @throws CallError error
     */
    public static void removeBehavior(String name) throws InterruptedException, CallError {
        currentApplication.getAlBehaviorManager().removeBehavior(name);
    }

    /**
     * Run a behavior
     * @param name the actual name of the behavior, the id, not the name you gave it in coregraphe
     */
    public static void runBehavior(String name) {
        try{
            currentApplication.getAlBehaviorManager().runBehavior(name);
        }catch(CallError | InterruptedException err){
            err.printStackTrace();
        }

    }

    /**
     * Stop all behaviors which are running
     * @throws CallError error
     * @throws InterruptedException error
     */
    public static void stopBehavior() throws CallError, InterruptedException {
        currentApplication.getAlBehaviorManager().stopAllBehaviors();
    }

    /**
     * getting all behaviors, that are installed...by you or by nature
     * @return getting a List with all behaviors
     * @throws CallError error
     * @throws InterruptedException error
     */
    public static List<String> getBehaviors() throws CallError, InterruptedException {
        return currentApplication.getAlBehaviorManager().getInstalledBehaviors();
    }

}
