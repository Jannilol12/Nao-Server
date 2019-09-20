package nao;

import java.io.*;

import java.nio.file.Files;
import java.util.Base64;

import components.json.JSONArray;
import components.json.JSONObject;
import components.json.abstractJSON;
import components.json.finder.JSONFinder;
import components.json.parser.JSONParser;
import nao.functions.*;
import nao.moves.Interface_Controller;
import nao.moves.SendClassName;

public class MainReceiver {
	private MainReceiver() {}
	
	public static void receiveText(String text, DataOutputStream dataOutputStream){
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

			// -------------------  MOVE  --------------------------------
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

			// -------------------  Rotate  --------------------------------

			case "Rotate":
				move.rotate(Integer.parseInt(JSONFinder.getString("value", json)));
				break;

			// -------------------  LIST  --------------------------------

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

			// -------------------  MOTORS (like arm,knee etc.)  --------------------------------
				
			case "motors":
				String motorName = JSONFinder.getString("motorname", json);
				float speed_value = (float)JSONFinder.getDouble("value",json);
				byte addSub = 0;
				
				if(motorName == null)
					return;
				
				String motorRealName = null;
				
				if(addSub == 0) { //Gegebenfalls in switch oder hier nochmals anpassen
					if(motorName.endsWith("Down") || motorName.endsWith("Left"))
						addSub = 2;
					else if(motorName.endsWith("Up") || motorName.endsWith("Right"))
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
						
					case "LElbowYaw_Left":
					case "LElbowYaw_Right":
						motorRealName = motors.LElbowYaw.name;
						break;
					case "LElbowRoll_Up":
					case "LElbowRoll_Down":
						motorRealName = motors.LElbowRoll.name;
						break;
					case "RElbowYaw_Left":
					case "RElbowYaw_Right":
						motorRealName = motors.RElbowYaw.name;
						break;
					case "RElbowRoll_Up":
					case "RElbowRoll_Down":
						motorRealName = motors.RElbowRoll.name;
						break;
						
					case "LWristYaw_Up":
					case "LWristYaw_Down":
						motorRealName = motors.LWristYaw.name;
						break;
					case "LHand_Up":
						move.handopenclose("LHand", "O");
						break;
					case "LHand_Down":
						move.handopenclose("LHand", "C");
						break;
					case "RWristYaw_Up":
					case "RWristYaw_Down":
						motorRealName = motors.RWristYaw.name;
						break;
					case "RHand_Up":
						move.handopenclose("RHand", "O");
						break;
					case "RHand_Down":
						move.handopenclose("RHand", "C");
						break;
						
					case "LHipPitch_Up":
					case "LHipPitch_Down":
						motorRealName = motors.LHipPitch.name;
						break;
					case "LHipRoll_Left":
					case "LHipRoll_Right":
						motorRealName = motors.LHipRoll.name;
						break;
					case "RHipPitch_Up":
					case "RHipPitch_Down":
						motorRealName = motors.RHipPitch.name;
						break;
					case "RHipRoll_Left":
					case "RHipRoll_Right":
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
				
				move.motors(motorRealName, move.getAngle(motorRealName) + (addSub == 1 ? -0.1f : 0.1f), speed_value);
					
				
				break;


			// -------------------  LEDs  --------------------------------

			case "leds":
				String ledname = JSONFinder.getString("ledname", json);
				String method = JSONFinder.getString("method", json);
				float red = (float)JSONFinder.getDouble("red",json);
				float green = (float)JSONFinder.getDouble("green",json);
				float blue = (float)JSONFinder.getDouble("blue",json);
				float fade = (float)JSONFinder.getDouble("Fade",json);
				float random_duration = (float)JSONFinder.getDouble("duration", json);
				int rotate_rgb = JSONFinder.getInt("rgbe",json);
				float rotate_time_rotation = (float) JSONFinder.getDouble("timeR", json);
				float rotate_time_duration = (float) JSONFinder.getDouble("timeD", json);

				switch (method){
					case"rgb":
						led.setRGBled(ledname,red,green,blue,fade);
						break;
					case"on":
						led.onled(ledname);
						break;
					case "off":
						led.offled(ledname);
						break;
					case "random":
						led.randomEyes(random_duration);
						break;
					case "rotate":
						led.rotateEyes(rotate_rgb,rotate_time_rotation,rotate_time_duration);
					default:
						System.out.println("Da lief was schief");
						break;
				}
				break;


			// -------------------  POSTURE  --------------------------------

			case "posture":
				String posture = JSONFinder.getString("position", json);
				float speed = (float) JSONFinder.getDouble("speed", json);
				commands.goToPosture(posture, speed);
				break;

			//-------------------- AUDIO PLAYER -----------------------------

			case "audioPlayer":
				String audio = JSONFinder.getString("function", json);
				int id = -1;
				switch(audio){
					case "play":
						if(id != 1){
							audioPlayer.playPlayer(id);
						}
						break;

					case "playInLoop":
						if(id != 1){
							audioPlayer.playinLoop(id);
						}
						break;

					case "pause":
						if(id != 1){
							audioPlayer.pausePlayer(id);
						}
						break;

					case "stop":
						if(id != 1){
							audioPlayer.stopPlayer();
						}
						break;

					case "jump":
						if(id != 1){
							audioPlayer.goToPosition(id, (float)JSONFinder.getDouble("jumpToFloat", json));
						}
						break;

					case "volume":
							audioPlayer.setMasterVolume((float)JSONFinder.getDouble("masterVolume", json));
						break;

					case "file":
						String base64 = "";
						base64 += JSONFinder.getString("bytes",json);
						if(JSONFinder.getString("end", json) == "end") {
							byte[] bytes = Base64.getDecoder().decode(base64);
							try(FileOutputStream fileOutputStream = new FileOutputStream("../")){
								fileOutputStream.write(bytes);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}


							id = audioPlayer.loadFile("FILEPATH");
							base64 = "";


						}
						//------- SEND LENGTH OF FILE -----------
						JSONObject myjson = new JSONObject();
						myjson.add( "type", "audioPlayer");
						myjson.add( "function", "getLength");
						myjson.add( "Length",  audioPlayer.getFileLengthInSec(id));
						try {
							dataOutputStream.writeUTF(myjson.toJSONString());
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case "unloadFiles":
						audioPlayer.unloadAllFiles();
						break;

					case "deleteFiles":
						break;


					case "getLength":
//						JSONObject myjson3 = new JSONObject();
//						myjson3.add( "type", "audioPlayer");
//						myjson3.add( "function", "getLength");
//						myjson3.add( "Length",  audioPlayer.getFileLengthInSec(id));
//
//						try {
//							dataOutputStream.writeUTF(myjson3.toJSONString());
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
						break;

					case "getPosition":
						JSONObject myjson1 = new JSONObject();
						myjson1.add( "type", "audioPlayer");
						myjson1.add( "function", "getPosition");
						myjson1.add( "Position",  audioPlayer.getcurrentPosition(id));

						try {
							dataOutputStream.writeUTF(myjson1.toJSONString());
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case "getVolume":
						JSONObject myjson2 = new JSONObject();
						myjson2.add( "type", "audioPlayer");
						myjson2.add( "function", "getVol");
						myjson2.add( "Vol",  audioPlayer.getMasterVolume());

						try {
							dataOutputStream.writeUTF(myjson2.toJSONString());
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					default:
						System.out.println("audioPlayer lief schief");
						break;
				}
				break;

			// -------------------  SYSTEM  --------------------------------

			case "Wakeup":
				move.wakeup();
				break;
			case "reboot":
				commands.reboot();
				break;
			case "shutdown":
				commands.shutdown();
				break;
			case "battery":
				try {
					JSONObject myjson = new JSONObject();
					myjson.add("type", "battery");
					myjson.add("battery", commands.getBatteryCharge());

					dataOutputStream.writeUTF(myjson.toJSONString());
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
