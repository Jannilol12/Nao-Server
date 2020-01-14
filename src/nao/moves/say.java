package nao.moves;

import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import components.json.JSONArray;
import components.json.finder.JSONFinder;
import components.json.parser.JSONParser;
import nao.currentApplication;

public class say implements SendClassName {

    /**
     * @param args getting the String, what Nao should say
     */
    @Override
    public void start(JSONArray args){
        try {
            //getting the API from currentApplication
            ALTextToSpeech tts = currentApplication.getAlTextToSpeech();

            String speechText = JSONFinder.getString("[0].value", args);
        	if(speechText != null)
        		saytext(tts, speechText);
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }

    /**
     *
     * @param tts getting the API of the robot
     * @param text what the robot will say
     */
    public void saytext(ALTextToSpeech tts, String text){
        try{
           tts.say(text);
        }
        catch (Exception err){
            err.printStackTrace();
        }

    }

    /**
     * @return name of the class
     */
    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void stop() {

    }

    /**
     * @return sending that the user must type in a text what the robot will say, with a prompt for the user, what this is
     */
	@Override
	public JSONArray getArgsRequest() {
		// ["id":"say", "type":"text", "prompt":"speech message"]
		return (JSONArray) JSONParser.parse("[{\"id\":\"say\", \"type\":\"text\", \"prompt\":\"speech message\"}]");
	}
}
