package nao;

import java.io.*;

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
	
	public static void receiveText(String text, DataOutputStream dataOutputStream) throws Exception {
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
			case "Backward":
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

			// -------------------  POSTURE  --------------------------------

			case "Posture":
				String posture = JSONFinder.getString("position", json);
				float speed = (float) JSONFinder.getDouble("speed", json);
				commands.goToPosture(posture, speed);
				break;

			// -------------------  MOTORS (like arm,knee etc.)  --------------------------------
				
			case "Motors":
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
					case "lShoulderPitchUp":
					case "lShoulderPitchDown":
						motorRealName = motors.LShoulderPitch.name;
						break;
						
					case "headPitchUp":
					case "headPitchDown":
						motorRealName = motors.HeadPitch.name;
						break;
					case "headYawLeft":
					case "headYawRight":
						motorRealName = motors.HeadYaw.name;
						break;
						
					case "lShoulderRollLeft":
					case "lShoulderRollRight":
						motorRealName = motors.LShoulderRoll.name;
						break;
					case "rShoulderPitchUp":
					case "rShoulderPitchDown":
						motorRealName = motors.RShoulderPitch.name;
						break;
					case "rShoulderRollLeft":
					case "rShoulderRollRight":
						motorRealName = motors.RShoulderRoll.name;
						break;
						
					case "lElbowYawLeft":
					case "lElbowYawRight":
						motorRealName = motors.LElbowYaw.name;
						break;
					case "lElbowRollUp":
					case "lElbowRollDown":
						motorRealName = motors.LElbowRoll.name;
						break;
					case "rElbowYawLeft":
					case "rElbowYawRight":
						motorRealName = motors.RElbowYaw.name;
						break;
					case "rElbowRollUp":
					case "rElbowRollDown":
						motorRealName = motors.RElbowRoll.name;
						break;
						
					case "lWristYawUp":
					case "lWristYawDown":
						motorRealName = motors.LWristYaw.name;
						break;
					case "lHandUp":
						move.handopenclose("LHand", "O");
						break;
					case "lHandDown":
						move.handopenclose("LHand", "C");
						break;
					case "rWristYawUp":
					case "rWristYawDown":
						motorRealName = motors.RWristYaw.name;
						break;
					case "rHandUp":
						move.handopenclose("RHand", "O");
						break;
					case "rHandDown":
						move.handopenclose("RHand", "C");
						break;
						
					case "lHipPitchUp":
					case "lHipPitchDown":
						motorRealName = motors.LHipPitch.name;
						break;
					case "lHipRollLeft":
					case "lHipRollRight":
						motorRealName = motors.LHipRoll.name;
						break;
					case "rHipPitchUp":
					case "rHipPitchDown":
						motorRealName = motors.RHipPitch.name;
						break;
					case "rHipRollLeft":
					case "rHipRollRight":
						motorRealName = motors.RHipRoll.name;
						break;
						
					case "lKneePitchUp":
					case "lKneePitchDown":
						motorRealName = motors.LKneePitch.name;
						break;
					case "rKneePitchUp":
					case "rKneePitchDown":
						motorRealName = motors.RKneePitch.name;
						break;
						
					case "lAnklePitchUp":
					case "lAnklePitchDown":
						motorRealName = motors.LAnklePitch.name;
						break;
					case "lAnkleRollLeft":
					case "lAnkleRollRight":
						motorRealName = motors.LAnkleRoll.name;
						break;
					case "rAnklePitchUp":
					case "rAnklePitchDown":
						motorRealName = motors.RAnklePitch.name;
						break;
					case "rAnkleRollLeft":
					case "rAnkleRollRight":
						motorRealName = motors.RAnkleRoll.name;
						break;
				}
				
				if(motorRealName == null)
					return;
				
				move.motors(motorRealName, move.getAngle(motorRealName) + (addSub == 1 ? -0.1f : 0.1f), speed_value);
					
				
				break;


			// -------------------  LEDs  --------------------------------

			case "Leds":
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


			//-------------------- AUDIO PLAYER -----------------------------

			case "audioPlayer":
				String audio = JSONFinder.getString("function", json);
				switch(audio){
					case "setId":
						String name = JSONFinder.getString("filename", json);
						//System.out.println(name);
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
					case "unloadFiles":
						audioPlayer.unloadAllFiles();
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
							if(!(file[i].getName().contains("Picture") || file[i].getName().contains("Video"))) {
								list.add(file[i].getName());
							}
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
					case "FaceTracking":
						if(bolean.equalsIgnoreCase("true")){
							events.startFaceTracking();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopFaceTracking();
						}
						break;
					case "Barcode":
						if(bolean.equalsIgnoreCase("true")){
							events.startBarcodeReader();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopBarcodeReader();
						}
						break;
					case "Landmark":
						if(bolean.equalsIgnoreCase("true")){
						events.startLandMark();
					}
						if(bolean.equalsIgnoreCase("false")){
							events.stopLandMark();
						}
						break;
					case "Laser":
						if(bolean.equalsIgnoreCase("true")){
							events.startLaser();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopLaser();
						}
						break;
					default:
						System.out.println("Events lief schief!");
						break;

				}
				break;

			case "Behavior":
				String behaviorFunction = JSONFinder.getString("function", json);
				switch(behaviorFunction) {
					case "play":
						String BehaviorName = JSONFinder.getString("name",json);
						try{
							Thread b = new Thread(){
								@Override
								public void run() {
									behavior.runBehavior(BehaviorName);
								}
							};
							b.start();
						} catch (Exception e){}
						break;
					case "stop":
						behavior.stopBehavior();
						break;
					case "removeBehavior":
						String BehaviorRemoveName = JSONFinder.getString("behaviorname",json);
						behavior.removeBehavior(BehaviorRemoveName);
						break;
					case "getBehaviors":
                        JSONObject myjson3 = new JSONObject();
                        myjson3.add( "type", "Behavior");
                        myjson3.add( "Behaviors", behavior.getBehaviors());
                        try {
                            dataOutputStream.writeUTF(myjson3.toJSONString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
					default:
						System.out.println("Behavior lief schief");
						break;
				}
				break;

			case "AutoLife":
				String AutoLife = JSONFinder.getString("function", json);
				String LifeModeString;
				switch(AutoLife) {
					case "BackgroundStrategy":
						LifeModeString = JSONFinder.getString("Strategy", json);
						autoLifeOfRobot.setBackgroundStrategy(LifeModeString);
						break;
					case "ExpressiveListening":
						String bolean1 = JSONFinder.getString("boolean", json);
						if(bolean1.equalsIgnoreCase("true")){
							autoLifeOfRobot.setExpressiveListeningEnabled(true);
						}
						if(bolean1.equalsIgnoreCase("false")){
							autoLifeOfRobot.setExpressiveListeningEnabled(false);
						}
						break;
					case "LifeMode":
						LifeModeString = JSONFinder.getString("Mode",json);
						autoLifeOfRobot.setLife(LifeModeString);
						break;
					case "RobotOffsetFromFloor":
						float JsonFinderFloat = (float) JSONFinder.getDouble("Offset",json);
						autoLifeOfRobot.setRobotOffsetFromFloor(JsonFinderFloat);
						break;
					default:
						System.out.println("AutoLife no function found!");
				}
				break;

			case "Recorder":
				String Recorder = JSONFinder.getString("function",json);
				switch (Recorder){
					case "startAudioRecorder":
						nao.functions.Recorder.startAudioRecording();
						break;
					case "stopAudioRecorder":
						nao.functions.Recorder.stopAudioRecording();
						break;
					case "takePicture":
						nao.functions.Recorder.takePicture();
						break;
					case "startVideoRecorder":
						nao.functions.Recorder.startVideoRecording();
						break;
					case "stopVideoRecorder":
						nao.functions.Recorder.stopVideoRecording();
						break;
					default:
						System.out.println("Recorder no function found!");
				}
				break;

            case "Touch":
                String TouchEvent = JSONFinder.getString("function", json);
                switch (TouchEvent){
                    case "startRightBumperPressed":
                        Touch.startRightBumperPressed();
                        break;
                    case "startLeftBumperPressed":
                        Touch.startLeftBumperPressed();
                        break;
                    case "startFrontTactilTouched":
                        Touch.startFrontTactilTouched();
                        break;
                    case "startRearTactilTouched":
                        Touch.startRearTactilTouched();
                        break;
                    case "startHandRightBackTouched":
                        Touch.startHandRightBackTouched();
                        break;
                    case "startHandRightLeftTouched":
                        Touch.startHandRightLeftTouched();
                        break;
                    case "startHandRightRightTouched":
                        Touch.startHandRightRightTouched();
                        break;
                    case "startHandLeftBackTouched":
                        Touch.startHandLeftBackTouched();
                        break;
                    case "startHandLeftLeftTouched":
                        Touch.startHandLeftLeftTouched();
                        break;
                    case "startHandLeftRightTouched":
                        Touch.startHandLeftRightTouched();
                        break;
                    case "stopRightBumperPressed":
                        Touch.stopRightBumperPressed();
                        break;
                    case "stopLeftBumperPressed":
                        Touch.stopLeftBumperPressed();
                        break;
                    case "stopFrontTactilTouched":
                        Touch.stopFrontTactilTouched();
                        break;
                    case "stopRearTactilTouched":
                        Touch.stopRearTactilTouched();
                        break;
                    case "stopHandRightBackTouched":
                        Touch.stopHandRightBackTouched();
                        break;
                    case "stopHandRightLeftTouched":
                        Touch.stopHandRightLeftTouched();
                        break;
                    case "stopHandRightRightTouched":
                        Touch.stopHandRightRightTouched();
                        break;
                    case "stopHandLeftBackTouched":
                        Touch.stopHandLeftBackTouched();
                        break;
                    case "stopHandLeftLeftTouched":
                        Touch.stopHandLeftLeftTouched();
                        break;
                    case "stopHandLeftRightTouched":
                        Touch.stopHandLeftRightTouched();
                        break;
                }
                break;

			case "Files":
				String FilesEvent = JSONFinder.getString("function", json);
				switch (FilesEvent) {
					case "getAllFiles":
						File[] file = new File(new File("./").getParentFile(), "files/").listFiles();
						List<String> list = new LinkedList<>();
						for (int i = 0; i < file.length; i++) {
							list.add(file[i].getName());
						}
						JSONObject myjson5 = new JSONObject();
						myjson5.add("type", "getAllFiles");
						myjson5.add("File", list);
						try {
							dataOutputStream.writeUTF(myjson5.toJSONString());
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case "deleteFile":
						String fileNameDelete = JSONFinder.getString("filename", json);
						new File(new File("./").getParentFile(), "files/" + fileNameDelete).delete();
						break;
					case "downloadFile":
						String fileNameDownload = JSONFinder.getString("filename", json);
						System.out.println("File:" + fileNameDownload);
						File fileDownload = new File(new File("./").getParentFile(), "files/" + fileNameDownload);
						String path = fileDownload.getAbsolutePath();
						JSONObject jsonObject = new JSONObject();
						try {
							FileInputStream fileInputStream = new FileInputStream(path);
							byte[] bytes = new byte[30000];
							int length = 0;
							while((length = fileInputStream.read(bytes)) != -1){
								byte[] base64 = Base64.getEncoder().encode(Arrays.copyOf(bytes, length));
								jsonObject.add("type", "downloadFile");
								jsonObject.add("function", "file");
								jsonObject.add("name", fileNameDownload);
								jsonObject.add("bytes", new String(base64, "UTF-8"));
								dataOutputStream.writeUTF(jsonObject.toJSONString());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "uploadFile":
						String base64 = JSONFinder.getString("bytes",json);
						byte[] bytes = Base64.getDecoder().decode(base64);
						try{
							File fileUpload = new File(new File("./").getParentFile(), "files/" + JSONFinder.getString("name", json));
							fileUpload.getParentFile().mkdirs();
							FileOutputStream fileOutputStream = new FileOutputStream(fileUpload, true);
							fileOutputStream.write(bytes);
							fileOutputStream.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
				break;

			// -------------------  SYSTEM  --------------------------------

			case "Wakeup":
				move.wakeup();
				break;
			case "Reboot":
				commands.reboot();
				break;
			case "Shutdown":
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

					System.out.println("GetTemperatures");

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
