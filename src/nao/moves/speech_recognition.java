package nao.moves;

import java.util.ArrayList;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;

import components.json.JSONArray;

public class speech_recognition implements SendClassName {
    public Application application;
    public ArrayList<String> vocabulary = new ArrayList<>();
    public ALSpeechRecognition asr;
    public ALMemory memory;

    @Override
    public void start(Application application, JSONArray args){
        this.application = application;

        try {
            asr = new ALSpeechRecognition(application.session());
            asr.setLanguage("German");
            memory = new ALMemory(application.session());
            long frontTactilSubscriptionId = 0;

            asr.setVocabulary(vocabulary, false);

            memory.subscribeToEvent("WordRecognized", new EventCallback() {
                @Override
                public void onEvent(Object o) throws InterruptedException, CallError {

                }
            });
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
    public void startrecognition(){
        try {
            asr.subscribe("Test");
        }
        catch(Exception err){}
    }
    public void endrecognition(){
        try{
            asr.unsubscribe("Test");
        }
        catch(Exception err){}
    }
    public void addvocabulary(String word){
        vocabulary.add(word);
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean useArgs() {
        return false;
    }
}
