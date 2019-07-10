package nao;

import java.io.DataOutputStream;
import java.io.IOException;

import components.json.JSONArray;
import components.json.JSONObject;
import components.json.abstractJSON;
import components.json.finder.JSONFinder;
import components.json.parser.JSONParser;
import nao.moves.Interface_Controller;
import nao.moves.SendClassName;

public class Main {
	public static move move;
	public static receiver r;
	
	public static void main(String[] args) {
		currentApplication.load("127.0.0.1",50736 );
		Interface_Controller.load();

	    r = new receiver();
	    r.start();
//        say say = new say(args);
//        say.saytext("Hallo");
//        speech_recognition speech = new speech_recognition(args);
//        speech.addvocabulary("Hallo");

		move = new move();
		move.test();
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
				if(!(json instanceof JSONObject))return;
				
				abstractJSON abstractArgs = ((JSONObject) json).get("inputs");
				if(abstractArgs != null && !(abstractArgs instanceof JSONArray)) return;
				
				Interface_Controller.ausfuehren(JSONFinder.getString("value", json), (JSONArray) abstractArgs);
				break;
			case "StopP":
				Interface_Controller.stop();
				break;
			case "ListP":
				try {
					for(SendClassName prog : Interface_Controller.getSendClassNames()) {
						JSONObject myjson = new JSONObject();
						myjson.add("type", "ProgAdd");
						myjson.add("name", prog.name());
						
						JSONArray args = prog.getArgsRequest();
						if(args != null)
							myjson.add("inputs", args);
						
						dataOutputStream.writeUTF(myjson.toJSONString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case "motors":
				String motorName = JSONFinder.getString("motorname", json);
				float speed_value = (float)JSONFinder.getDouble("value",json);
				byte addSub = 0;
				
				if(motorName == null)
					return;
				
				String motorRealName = null;
				
				if(addSub == 0) { //Gegebenfalls in switch oder hier nochmals anpassen
					if(motorName.endsWith("Up") || motorName.startsWith("Left"))
						addSub = 2;
					else if(motorName.endsWith("Down") || motorName.startsWith("Right"))
						addSub = 1;
				}
				
				switch (motorName) {
					case "LShoulderPitch_Up":
					case "LShoulderPitch_Down":
						motorRealName = motors.LShoulderPitch.name;
						break;
						
					case "HeadPitch_Up":
					case "HeadPitch_Down":
						motorRealName = motors.HeadPitch.name;
						break;
					case "HeadYaw_Left":
					case "HeadYaw_Right":
						motorRealName = motors.HeadYaw.name;
						break;
						
					case "LShoulderRoll_Left":
					case "LShoulderRoll_Right":
						motorRealName = motors.LShoulderRoll.name;
						break;
					case "RShoulderPitch_Up":
					case "RShoulderPitch_Down":
						motorRealName = motors.RShoulderPitch.name;
						break;
					case "RShoulderRoll_Left":
					case "RShoulderRoll_Right":
						motorRealName = motors.RShoulderRoll.name;
						break;
						
					case "LElbowYaw_Up":
					case "LElbowYaw_Down":
						motorRealName = motors.LElbowYaw.name;
						break;
					case "LElbowRoll_Left":
					case "LElbowRoll_Right":
						motorRealName = motors.LElbowRoll.name;
						break;
					case "RElbowYaw_Up":
					case "RElbowYaw_Down":
						motorRealName = motors.RElbowYaw.name;
						break;
					case "RElbowRoll_Left":
					case "RElbowRoll_Right":
						motorRealName = motors.RElbowRoll.name;
						break;
						
					case "LWristYaw_Up":
					case "LWristYaw_Down":
						motorRealName = motors.LWristYaw.name;
						break;
					case "LHand_Up":
					case "LHand_Down":
						motorRealName = motors.LHand.name;
						break;
					case "RWristYaw_Up":
					case "RWristYaw_Down":
						motorRealName = motors.RWristYaw.name;
						break;
					case "RHand_Up":
					case "RHand_Down":
						motorRealName = motors.RHand.name;
						break;
						
					case "LHipPitch_Up":
					case "LHipPitch_Down":
						motorRealName = motors.LHipPitch.name;
						break;
					case "LHipRoll_Left":
					case "LHipRoll_Down":
						motorRealName = motors.LHipRoll.name;
						break;
					case "RHipPitch_Up":
					case "RHipPitch_Down":
						motorRealName = motors.RHipPitch.name;
						break;
					case "RHipRoll_Left":
					case "RHipRoll_Down":
						motorRealName = motors.RHipRoll.name;
						break;
						
					case "LKneePitch_Up":
					case "LKneePitch_Down":
						motorRealName = motors.LKneePitch.name;
						break;
					case "RKneePitch_Up":
					case "RKneePitch_Down":
						motorRealName = motors.RKneePitch.name;
						break;
						
					case "LAnklePitch_Up":
					case "LAnklePitch_Down":
						motorRealName = motors.LAnklePitch.name;
						break;
					case "LAnkleRoll_Left":
					case "LAnkleRoll_Right":
						motorRealName = motors.LAnkleRoll.name;
						break;
					case "RAnklePitch_Up":
					case "RAnklePitch_Down":
						motorRealName = motors.RAnklePitch.name;
						break;
					case "RAnkleRoll_Left":
					case "RAnkleRoll_Right":
						motorRealName = motors.RAnkleRoll.name;
						break;
				}
				
				if(motorRealName == null)
					return;
				
				move.motors(motorRealName, move.getAngle(motorRealName) + (addSub == 1 ? 0.1f : -0.1f), speed_value);
					
				
				break;
				
			default:
				System.out.println("Nothing to do!");
				break;
		}
    }
}
