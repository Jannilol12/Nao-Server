package nao;

import java.io.*;

import java.nio.file.Files;
import java.util.*;

import com.aldebaran.qi.CallError;
import components.json.JSONArray;
import components.json.JSONObject;
import components.json.abstractJSON;
import components.json.finder.JSONFinder;
import components.json.parser.JSONParser;
import nao.functions.*;
import nao.moves.Interface_Controller;
import nao.moves.SendClassName;

public class MainReceiver {
	private static int id;
	private static boolean jump = false;
	private static boolean jumpVoc = false;

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
				switch(audio){
					case "setId":
						String name = JSONFinder.getString("filename", json);
						System.out.println(name);
						String file1 = new File(new File("./").getParentFile(), "files/").getAbsolutePath();
						id = audioPlayer.loadFile(file1 + "/" + name);

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

					case "play":
						try{
							Thread a = new Thread(){
								@Override
								public void run() {
										audioPlayer.playPlayer(id);
								}
							};
							a.start();

						} catch (Exception e){}
						break;

					case "playInLoop":
						try{
							audioPlayer.playinLoop(id);
						} catch(Exception e){}
						break;

					case "pause":
						try{
							System.out.println("pause");
							audioPlayer.pausePlayer(id);
						}catch (Exception e){}
						break;

					case "stop":
						try{
							audioPlayer.stopPlayer();
						}catch (Exception e){}
						break;

					case "jump":
						try{
							audioPlayer.goToPosition(id, (float)JSONFinder.getDouble("jumpToFloat", json));
						}catch (Exception e){}
						break;

					case "volume":
							float volume = (float)JSONFinder.getDouble("masterVolume", json);
							audioPlayer.setMasterVolume(volume);
							System.out.println(volume);
						break;

					case "file":
						String base64 = JSONFinder.getString("bytes",json);
						byte[] bytes = Base64.getDecoder().decode(base64);
						try{
							File file = new File(new File("./").getParentFile(), "files/" + JSONFinder.getString("name", json));
							file.getParentFile().mkdirs();
							FileOutputStream fileOutputStream = new FileOutputStream(file, true);
							fileOutputStream.write(bytes);
							fileOutputStream.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					case "loadFile":

						break;

					case "unloadFiles":
						audioPlayer.unloadAllFiles();
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

					case "deleteFiles":
						File[] fileDelete = new File(new File("./").getParentFile(), "files/").listFiles();
						for(int i=0;i<fileDelete.length;i++){
							new File(new File("./").getParentFile(), "files/" + fileDelete[i].getName()).delete();
						}
						jump = true;


					case "deleteFile":
						if(!jump) {
							audioPlayer.unloadAllFiles();
							String fileName = JSONFinder.getString("fileDelete", json);
							new File(new File("./").getParentFile(), "files/" + fileName).delete();
							//NO BREAK!
						}


					case "getFiles":
						File[] file = new File(new File("./").getParentFile(), "files/").listFiles();
						List<String> list = new LinkedList<>();
						for(int i=0;i<file.length;i++){
							list.add(file[i].getName());
						}
						JSONObject myjson3 = new JSONObject();
						myjson3.add( "type", "audioPlayer");
						myjson3.add( "function", "getFiles");
						myjson3.add( "File", list);
						try {
							dataOutputStream.writeUTF(myjson3.toJSONString());
						} catch (IOException e) {
							e.printStackTrace();
						}
						jump = false;
						break;

					default:
						System.out.println("audioPlayer lief schief");
						break;
				}
				break;

			// ---------------------- EVENTS ------------------------------

			case "Events":
				String Events = JSONFinder.getString("function", json);
				String bolean = JSONFinder.getString("boolean", json);
				System.out.println("Events case");
				switch (Events){
					case "FootContact":
						if(bolean.equalsIgnoreCase("true")){
							events.startFootContactChanged();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopFootContactChanged();
						}
						break;
					case "AddVocabulary":
						jumpVoc = true;
						String vocabularAdd = JSONFinder.getString("String", json);
						events.addVocabulary(vocabularAdd);
					case "DeleteVocabulary":
						if(!jumpVoc) {
							String vocabularDel = JSONFinder.getString("String", json);
							events.delVocabulary(vocabularDel);
						}
					case "getVocabulary":
						JSONObject myjson4 = new JSONObject();
						myjson4.add( "type", "SpeechRecognition");
						myjson4.add( "Voc", events.getVocabulary());
						try {
							dataOutputStream.writeUTF(myjson4.toJSONString());
						} catch (IOException e) {
							e.printStackTrace();
						}
						jumpVoc = false;
						break;
					case "SpeechRecognition":
						if(bolean.equalsIgnoreCase("true")){
							events.startSpeechRecognition();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopSpeechRecognition();
						}
						break;
					case "Sonar":
						if(bolean.equalsIgnoreCase("true")){
							events.startSonar();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopSonar();
						}
						break;
					case "LearnFace":
						String addName = JSONFinder.getString("String",json);
						events.learnFace(addName);
						break;
					case "DeleteFace":
						String delName = JSONFinder.getString("Face",json);
						events.deleteFace(delName);
						break;
					case "FaceDetection":
						if(bolean.equalsIgnoreCase("true")){
							events.startFaceDectection();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopFaceDetection();
						}
						break;
					case "getFaces":
						JSONObject myjson5 = new JSONObject();
						myjson5.add( "type", "FaceDetection");
						myjson5.add( "Faces", events.getLearnedFaced());
						try {
							dataOutputStream.writeUTF(myjson5.toJSONString());
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					default:
						System.out.println("Events lief schief!");
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

			case "temperature":
				try {
					JSONObject myjson = new JSONObject();

					myjson.add("type", "temperature");

					myjson.add("HeadYaw", currentApplication.getAlMemory().getData("Device/SubDeviceList/HeadYaw/Temperature/Sensor/Value"));
					myjson.add("HeadPitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/HeadPitch/Temperature/Sensor/Value"));
					myjson.add("LElbowYaw", currentApplication.getAlMemory().getData("Device/SubDeviceList/LElbowYaw/Temperature/Sensor/Value"));
					myjson.add("LElbowRoll", currentApplication.getAlMemory().getData("Device/SubDeviceList/LElbowRoll/Temperature/Sensor/Value"));
					myjson.add("RElbowYaw", currentApplication.getAlMemory().getData("Device/SubDeviceList/RElbowYaw/Temperature/Sensor/Value"));
					myjson.add("RElbowRoll", currentApplication.getAlMemory().getData("Device/SubDeviceList/RElbowRoll/Temperature/Sensor/Value"));
					myjson.add("LHand", currentApplication.getAlMemory().getData("Device/SubDeviceList/LHand/Temperature/Sensor/Value"));
					myjson.add("LWristYaw", currentApplication.getAlMemory().getData("Device/SubDeviceList/LWristYaw/Temperature/Sensor/Value"));
					myjson.add("RHand", currentApplication.getAlMemory().getData("Device/SubDeviceList/RHand/Temperature/Sensor/Value"));
					myjson.add("RWristYaw", currentApplication.getAlMemory().getData("Device/SubDeviceList/RWristYaw/Temperature/Sensor/Value"));
					myjson.add("LShoulderPitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/LShoulderPitch/Temperature/Sensor/Value"));
					myjson.add("LShoulderRoll", currentApplication.getAlMemory().getData("Device/SubDeviceList/LShoulderRoll/Temperature/Sensor/Value"));
					myjson.add("RShoulderPitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/RShoulderPitch/Temperature/Sensor/Value"));
					myjson.add("RShoulderRoll", currentApplication.getAlMemory().getData("Device/SubDeviceList/RShoulderRoll/Temperature/Sensor/Value"));
					myjson.add("RHipRoll", currentApplication.getAlMemory().getData("Device/SubDeviceList/RHipRoll/Temperature/Sensor/Value"));
					myjson.add("LHipRoll", currentApplication.getAlMemory().getData("Device/SubDeviceList/LHipRoll/Temperature/Sensor/Value"));
					myjson.add("RHipYawPitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/RHipYawPitch/Temperature/Sensor/Value"));
					myjson.add("LHipYawPitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/LHipYawPitch/Temperature/Sensor/Value"));
					myjson.add("RHipPitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/RHipPitch/Temperature/Sensor/Value"));
					myjson.add("LHipPitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/LHipPitch/Temperature/Sensor/Value"));
					myjson.add("RKneePitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/RKneePitch/Temperature/Sensor/Value"));
					myjson.add("LKneePitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/LKneePitch/Temperature/Sensor/Value"));
					myjson.add("RAnklePitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/RAnklePitch/Temperature/Sensor/Value"));
					myjson.add("LAnklePitch", currentApplication.getAlMemory().getData("Device/SubDeviceList/LAnklePitch/Temperature/Sensor/Value"));
					myjson.add("RAnkleRoll", currentApplication.getAlMemory().getData("Device/SubDeviceList/RAnkleRoll/Temperature/Sensor/Value"));
					myjson.add("LAnkleRoll", currentApplication.getAlMemory().getData("Device/SubDeviceList/LAnkleRoll/Temperature/Sensor/Value"));
					myjson.add("HeadCPU", currentApplication.getAlMemory().getData("Device/SubDeviceList/Head/Temperature/Sensor/Value"));
					myjson.add("Battery", currentApplication.getAlMemory().getData("Device/SubDeviceList/Battery/Temperature/Sensor/Value"));

					dataOutputStream.writeUTF(myjson.toJSONString());
				} catch (CallError callError) {
					callError.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			default:
				System.out.println("Nothing to do!");
				break;
		}
    }

}
