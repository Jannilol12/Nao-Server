package nao;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import components.json.JSONObject;
import components.json.abstractJSON;
import components.json.finder.JSONFinder;
import components.json.parser.JSONParser;
import nao.moves.Interface_Controller;
import nao.moves.SendClassName;

import java.io.DataOutputStream;
import java.io.IOException;

public class Main {
	public static move move;
	public static receiver r;
	
	public static void main(String[] args) {
		currentApplication.load("127.0.0.1", 49848);
		Interface_Controller.load();

	    r = new receiver();
	    r.start();
//	      gandamstyle dance = new gandamstyle(args);
//        say say = new say(args);
//        say.saytext("Hallo");
//        speech_recognition speech = new speech_recognition(args);
//        speech.addvocabulary("Hallo");
		move = new move();
		move.stop();
	}

	public static void receiveText(String text, DataOutputStream dataOutputStream){
		System.out.println(text);
		abstractJSON json;
		try {
			json = JSONParser.parse(text);
			if(json == null)return;
		}catch (Exception e){
			e.printStackTrace();
			return;
		}

		String type = JSONFinder.getString("type", json);
		if(type == null)
			return;

		switch (type){
			case "Forward":
				String value = JSONFinder.getString("value", json);

				if(value.equals("0")){
					move.moveinfinity(1,0);
				}
				else{
					move.steps(Float.parseFloat(value), 0);
				}
				break;
			case "Left":
				value = JSONFinder.getString("value", json);

				if(value.equals("0")){
					move.moveinfinity(0,1);
				}
				else{
					move.steps(0, Float.parseFloat(value));
				}
				break;
			case "Right":
				value = JSONFinder.getString("value", json);

				if(value.equals("0")){
					move.moveinfinity(0,-1);
				}
				else{
					move.steps(0, Float.parseFloat(value)*-1);
				}
				break;
			case "Backwards":
				value = JSONFinder.getString("value", json);

				if(value.equals("0")){
					move.moveinfinity(-1,0);
				}
				else{
					move.steps(Float.parseFloat(value)*-1, 0);
				}
				break;
			case "Stop":
				move.stop();
				Interface_Controller.stop();
				break;
			case "Rotate":
				move.rotate(Integer.parseInt(JSONFinder.getString("value", json)));
				break;
			case "RunP":
				Interface_Controller.ausfuehren(JSONFinder.getString("value", json));
				break;
			case "StopP":
				Interface_Controller.stop();
				break;
			case "ListP":
				try {
					for(SendClassName prog : Interface_Controller.getClassName()) {
						JSONObject myjson = new JSONObject();
						myjson.add("type", "ProgAdd");
						myjson.add("name", prog.name());
						myjson.add("useArgs", prog.useArgs());
						dataOutputStream.writeUTF(myjson.toJSONString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Nothing to do!");
				break;
		}
    }
}
