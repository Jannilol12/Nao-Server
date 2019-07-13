package nao.moves;

import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import components.json.JSONArray;
import components.json.finder.JSONFinder;
import components.json.parser.JSONParser;
import nao.functions.currentApplication;

public class say implements SendClassName {
    @Override
    public void start(JSONArray args){
        try {
            ALTextToSpeech tts = new ALTextToSpeech(currentApplication.getApplication().session());
            tts.setLanguage("English");

            String speechText = JSONFinder.getString("[0].value", args);
        	if(speechText != null)
        		saytext(tts, speechText);
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
    public void saytext(ALTextToSpeech tts, String text){
        try{
           tts.say(text);
        }
        catch (Exception err){
            err.printStackTrace();
        }

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
		// ["id":"say", "type":"text", "prompt":"speech message"]
		return (JSONArray) JSONParser.parse("[{\"id\":\"say\", \"type\":\"text\", \"prompt\":\"speech message\"}]");
	}
}
