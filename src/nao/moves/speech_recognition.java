package nao.moves;

import java.util.ArrayList;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALMotionRecorder;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;

import components.json.JSONArray;
import nao.currentApplication;

public class speech_recognition implements SendClassName {
    public ArrayList<String> vocabulary = new ArrayList<>();
    public ALSpeechRecognition asr;
    public ALMemory memory;

    @Override
    public void start(JSONArray args){
        try {
            asr = new ALSpeechRecognition(currentApplication.getApplication().session());
            asr.setLanguage("German");
            memory = new ALMemory(currentApplication.getApplication().session());
            long frontTactilSubscriptionId = 0;

            asr.setVocabulary(vocabulary, false);

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
	public JSONArray getArgsRequest() {
		return null;
	}
}
