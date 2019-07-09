package nao.moves;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import components.json.JSONArray;
import components.json.parser.JSONParser;

public class say implements SendClassName {
    public Application application;

    @Override
    public void start(Application application, JSONArray args){
        this.application = application;

        try {
            ALTextToSpeech tts = new ALTextToSpeech(application.session());
            tts.setLanguage("English");

        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
    public void saytext(String text){
        try{
           ALTextToSpeech tts = new ALTextToSpeech(application.session());
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
