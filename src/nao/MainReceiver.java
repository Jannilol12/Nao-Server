package nao;

import java.io.*;

import java.nio.charset.StandardCharsets;
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

/**
 * Main thing from the server.
 * Receiving the messages and sort them.
 */
public class MainReceiver {
	private static int id;
	private static boolean jump = false;
	private static boolean jumpVoc = false;
	private static DataOutputStream dataOutputStream;

	private MainReceiver() {}

	/**
	 * Sending a message to the Client
	 * @param message Message which shell be send to the Client
	 * @throws IOException Throws errors
	 */
	public static void sendMessage(String message) throws IOException {
		if(dataOutputStream != null) {
			System.out.println(message);
			dataOutputStream.writeUTF(message);
		}else{
			System.out.println("No dataOutputStream set!");
		}
	}

	/**
	 * Set the DataOutputStream
	 * @param dataOutputS get the DataOutputStream for sending messages to the Client
	 */
	public static void setDataOutputStream(DataOutputStream dataOutputS){
		dataOutputStream = dataOutputS;
	}

	/**
	 * This function is receiving and distributing the messages from the client
	 * @param text Input, which the client sent as JSON
	 * @throws Exception Throws Apples, er...i meant Exceptions
	 */
	public static void receiveText(String text) throws Exception {
		abstractJSON json;
		try {
			//jsonParser is converting the JSON in something useful
			json = JSONParser.parse(text);
			if(json == null)return;
		}catch (Exception e){
			e.printStackTrace();
			return;
		}

		String type = JSONFinder.getString("type", json);

		if(type == null) {
			return;
		}
		//When receiving a file the there are multiple Strings with over 30000 bytes,
		// so just System.out.println that there is a file which is received
		if(type.compareToIgnoreCase("audioPlayer") == 0){
			if(Objects.requireNonNull(JSONFinder.getString("function", json)).compareToIgnoreCase("file") == 0){
				System.out.println("Receive: Receiving a file! (audio Player)");
			}
		} else if(type.compareToIgnoreCase("Files") == 0){
			if(Objects.requireNonNull(JSONFinder.getString("function", json)).compareToIgnoreCase("uploadFile") == 0){
				System.out.println("Receive: Receiving a file! (files)");
			}
		} else{
			System.out.println(text);
		}

		/*
		This HUGE Switch-Case (874 lines) is the brain of this program, it is receiving everything from the client and distribute the message to the classes which need it
		It is sorting in this steps:
		1. Main thing, roughly each tab of the client is one case
		2. There are more switch-cases :D
		3. This inner switches sorting the function of the parts which are used
		 */

		switch (type){

			// -------------------  LIST on the left side of the client --------------------------------

			case "RunP": //run one program of the class, which was clicked.
				if(!(json instanceof JSONObject))return;

				//getting which inputs the client had when double-click a program in the list
				abstractJSON abstractArgs = ((JSONObject) json).get("inputs");

				if(abstractArgs != null && !(abstractArgs instanceof JSONArray)) return;

				//getting the value of the inputs, for example at say, what the robot shall say...the String
				Interface_Controller.ausfuehren(JSONFinder.getString("value", json), (JSONArray) abstractArgs);
				break;
			case "StopP": //stop program
				Interface_Controller.stop();
				break;
			case "ListP": //getting all the programs (classes) which the client later is adding to his list on the left side
				try {
					//for: every program
					for(SendClassName prog : Interface_Controller.getSendClassNames()) {
						JSONObject myjson = new JSONObject();
						myjson.add("type", "ProgAdd");
						myjson.add("name", prog.name());

						//getting all the inputs, which are needed...for example the String input for say
						JSONArray args = prog.getArgsRequest();
						if(args != null)
							myjson.add("inputs", args);

						//sending the programs
						sendMessage(myjson.toJSONString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			// -------------------  MOVE  --------------------------------

			case "Forward": //robot is moving forward
				String stepsTheRobotShouldMake;
				stepsTheRobotShouldMake = JSONFinder.getString("value", json);

				if(Objects.equals(stepsTheRobotShouldMake, "0")){ //if value is 0 the robot is moving until you stop him
					move.moveInfinity(1,0);
				}
				else{ //otherwise the robot is making how many steps you told him to make
					move.steps(Float.parseFloat(Objects.requireNonNull(stepsTheRobotShouldMake)), 0);
				}
				break;
			case "Left": //robot is moving left
				stepsTheRobotShouldMake = JSONFinder.getString("value", json);

				if(Objects.equals(stepsTheRobotShouldMake, "0")){ //if value is 0 the robot is moving until you stop him
					move.moveInfinity(0,1);
				}
				else{ //otherwise the robot is making how many steps you told him to make
					move.steps(0, Float.parseFloat(Objects.requireNonNull(stepsTheRobotShouldMake)));
				}
				break;
			case "Right": //robot is moving right
				stepsTheRobotShouldMake = JSONFinder.getString("value", json);

				if(Objects.equals(stepsTheRobotShouldMake, "0")){ //if value is 0 the robot is moving until you stop him
					move.moveInfinity(0,-1);
				}
				else{ //otherwise the robot is making how many steps you told him to make
					move.steps(0, Float.parseFloat(Objects.requireNonNull(stepsTheRobotShouldMake))*-1);
				}
				break;
			case "Backward": //robot is moving backwards
				stepsTheRobotShouldMake = JSONFinder.getString("value", json);

				if(Objects.equals(stepsTheRobotShouldMake, "0")){ //if value is 0 the robot is moving until you stop him
					move.moveInfinity(-1,0);
				}
				else{ //otherwise the robot is making how many steps you told him to make
					move.steps(Float.parseFloat(Objects.requireNonNull(stepsTheRobotShouldMake))*-1, 0);
				}
				break;
			case "Stop": //robot is stopping
				move.stop();
				Interface_Controller.stop();
				break;

			// -------------------  Rotate  --------------------------------

			case "Rotate": //robot is rotating (in degrees)
				move.rotate(Integer.parseInt(Objects.requireNonNull(JSONFinder.getString("value", json))));
				break;

			// -------------------  POSTURE  --------------------------------

			case "Posture": //robot is going into a position receiving from the client
				//the position he will go into
				String posture = JSONFinder.getString("position", json);
				//how fast the robot is moving his motors
				float speed = (float) JSONFinder.getDouble("speed", json);
				commands.goToPosture(posture, speed);
				break;

			// -------------------  MOTORS (like arm,knee etc.)  --------------------------------
				
			case "Motors": //move motors of the robot
				String motorName = JSONFinder.getString("motorname", json);
				float speed_value = (float)JSONFinder.getDouble("value",json);
				byte addSub = 0;
				
				if(motorName == null)
					return;
				
				String motorRealName = null;

				//1 or 2, to decide if robot has to move his motors forward or backward, see at the end of this case
				if(motorName.endsWith("Down") || motorName.endsWith("Left")) {
					addSub = 2;
				}else if(motorName.endsWith("Up") || motorName.endsWith("Right")) {
					addSub = 1;
				}

				//setting the name of the motor, for later, where it is executed
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
						move.handOpenClose("LHand", "O");
						break;

					case "lHandDown":
						move.handOpenClose("LHand", "C");
						break;

					case "rWristYawUp":
					case "rWristYawDown":
						motorRealName = motors.RWristYaw.name;
						break;

					case "rHandUp":
						move.handOpenClose("RHand", "O");
						break;

					case "rHandDown":
						move.handOpenClose("RHand", "C");
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

					default:
						System.out.println("Motors, no case found!");
						break;
				}
				
				if(motorRealName == null)
					return;

				//Motors will be moved...which motor, forward or backward, how fast
				move.motors(motorRealName, move.getAngle(motorRealName) + (addSub == 1 ? -0.1f : 0.1f), speed_value);

				break;

			// -------------------  LEDs  --------------------------------

			case "Leds": //turn on/off,blink led
				//getting all information which could be sent
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

				//switch-case what the robot shall do
				switch (Objects.requireNonNull(method)){
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
						System.out.println("LED, no case found!");
						break;

				}
				break;


			//-------------------- AUDIO PLAYER -----------------------------

			case "audioPlayer": //play audio files
				String audio = JSONFinder.getString("function", json);
				switch(Objects.requireNonNull(audio)){
					case "setId": //loading the file, that the robot shall play

						//getting the absolute path + the name, for loading it
						String name = JSONFinder.getString("filename", json);
						String file1 = new File(new File("./").getParentFile(), "files/").getAbsolutePath();
						id = audioPlayer.loadFile(file1 + "/" + name);

						//------- SEND LENGTH OF FILE -----------
						JSONObject myjson = new JSONObject();
						myjson.add( "type", "audioPlayer");
						myjson.add( "function", "getLength");
						myjson.add( "Length",  audioPlayer.getFileLengthInSec(id));
						sendMessage(myjson.toJSONString());

						break;
					case "play": //play file in a need Thread because if you don't start a Thread you can't do anything anymore
						try{
							Thread a = new Thread(() -> audioPlayer.playPlayer(id));
							a.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "playInLoop": //play file in loop
						try{
							Thread a = new Thread(() -> audioPlayer.playinLoop(id));
							a.start();
						} catch(Exception e) {
							e.printStackTrace();
						}
						break;
					case "pause": //pause file
						try{
							audioPlayer.pausePlayer(id);
						}catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "stop": //stop file
						try{
							audioPlayer.stopPlayer();
						}catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "jump": //jump to Second xy
						try{
							audioPlayer.goToPosition(id, (float)JSONFinder.getDouble("jumpToFloat", json));
						}catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "volume": //setting the volume
							float volume = (float)JSONFinder.getDouble("masterVolume", json);
							audioPlayer.setMasterVolume(volume);
						break;
					case "file": //getting the file
						/*
						how this works:
						The File is decoded in Base64 and is split into many Messages
						Every time a message is received the file will be written until it's finished
						So the file will be written step by step
						 */
						String base64 = JSONFinder.getString("bytes",json);
						byte[] bytes = Base64.getDecoder().decode(Objects.requireNonNull(base64));
						try{
							File file = new File(new File("./").getParentFile(), "files/" + JSONFinder.getString("name", json));
							file.getParentFile().mkdirs();
							//append is true, so the new bytes will be attached at the end of the file
							FileOutputStream fileOutputStream = new FileOutputStream(file, true);
							fileOutputStream.write(bytes);
							fileOutputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case "unloadFiles": //unload all Files
						audioPlayer.unloadAllFiles();
						break;
					case "getPosition": //get the position where the file, which is played, is
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
					case "getVolume": //get the volume of the player
						JSONObject myjson2 = new JSONObject();
						myjson2.add( "type", "audioPlayer");
						myjson2.add( "function", "getVol");
						myjson2.add( "Vol",  audioPlayer.getMasterVolume());
						sendMessage(myjson2.toJSONString());
						break;
					case "deleteFiles": //delete all files
						File[] fileDelete = new File(new File("./").getParentFile(), "files/").listFiles();
						for(int i = 0; i< Objects.requireNonNull(fileDelete).length; i++){
							new File(new File("./").getParentFile(), "files/" + fileDelete[i].getName()).delete();
						}
						jump = true; //Jump is needed here, to reload the files on the client
					case "deleteFile": //delete one file
						if(!jump) {
							audioPlayer.unloadAllFiles();
							String fileName = JSONFinder.getString("fileDelete", json);
							new File(new File("./").getParentFile(), "files/" + fileName).delete();
							//NO BREAK! because files shell be reloaded at the client
						}
					case "getFiles": //load the files which can be played with the player
						//creating a list with all files and send it to the client
						File[] file = new File(new File("./").getParentFile(), "files/").listFiles();
						List<String> list = new LinkedList<>();
						//for every file which isn't a picture or a video
						for(int i = 0; i< Objects.requireNonNull(file).length; i++){
							if(!(file[i].getName().contains("Picture") || file[i].getName().contains("Video"))) {
								list.add(file[i].getName());
							}
						}
						JSONObject myjson3 = new JSONObject();
						myjson3.add( "type", "audioPlayer");
						myjson3.add( "function", "getFiles");
						myjson3.add( "File", list);
						sendMessage(myjson3.toJSONString());
						jump = false; //set the jumper for deleteFiles to default = false
						break;
					default:
						System.out.println("Audioplayer, no case found!");
						break;
				}
				break;

			// ---------------------- EVENTS ------------------------------

			case "Events": //starting, stopping or sending information the Events like SpeechRecognition etc.

				String Events = JSONFinder.getString("function", json);
				//if it shell start or stop, i tried using real boolean, but it didn't worked, so String
				String bolean = JSONFinder.getString("boolean", json);
				switch (Objects.requireNonNull(Events)){
					case "FootContact": //start/stop footContact
						if(Objects.requireNonNull(bolean).equalsIgnoreCase("true")){
							events.startFootContactChanged();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopFootContactChanged();
						}
						break;
					case "AddVocabulary": //adding a new vocabulary for the speech recognition of the nao
						//this jumper is used for reload the vocabulary on the client
						jumpVoc = true;
						String vocabularAdd = JSONFinder.getString("String", json);
						events.addVocabulary(vocabularAdd);
					case "DeleteVocabulary": //delete a vocabulary
						if(!jumpVoc) {
							String vocabularDel = JSONFinder.getString("String", json);
							events.delVocabulary(vocabularDel);
						}
					case "getVocabulary": //reloading the vocabulary on the client
						JSONObject myjson4 = new JSONObject();
						myjson4.add( "type", "SpeechRecognition");
						myjson4.add( "Voc", events.getVocabulary());
						sendMessage(myjson4.toJSONString());
						jumpVoc = false; //resetting the jumper for AddVocabulary to default = false
						break;
					case "SpeechRecognition": //start/stop speech recognition
						if(Objects.requireNonNull(bolean).equalsIgnoreCase("true")){
							events.startSpeechRecognition();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopSpeechRecognition();
						}
						break;
					case "Sonar": //start stop the sonar
						if(Objects.requireNonNull(bolean).equalsIgnoreCase("true")){
							events.startSonar();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopSonar();
						}
						break;
					case "LearnFace": //learn a new face for the face detection
						String addName = JSONFinder.getString("String",json);
						events.learnFace(addName);
						break;
						//here no jump like at the AudioPlayer, because the client automatically reload the faces... why i made this with two different solutions, I don't now
					case "DeleteFace": //delete a face
						String delName = JSONFinder.getString("Face",json);
						events.deleteFace(delName);
						break;
					case "FaceDetection": //start/stop face detection
						if(Objects.requireNonNull(bolean).equalsIgnoreCase("true")){
							events.startFaceDectection();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopFaceDetection();
						}
						break;
					case "getFaces": //reloading the name of the faces on the client
						JSONObject myjson5 = new JSONObject();
						myjson5.add( "type", "FaceDetection");
						myjson5.add( "Faces", events.getLearnedFaced());
						sendMessage(myjson5.toJSONString());
						break;
					case "FaceTracking": //start/stop face tracking
						if(Objects.requireNonNull(bolean).equalsIgnoreCase("true")){
							events.startFaceTracking();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopFaceTracking();
						}
						break;
					case "Barcode": //start/stop the QR-Code reader
						if(Objects.requireNonNull(bolean).equalsIgnoreCase("true")){
							events.startBarcodeReader();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopBarcodeReader();
						}
						break;
					case "Landmark"://start/stop the landmark reader
						//I want to add, that the nao never really recognized the landmarks....
						if(Objects.requireNonNull(bolean).equalsIgnoreCase("true")){
						events.startLandMark();
					}
						if(bolean.equalsIgnoreCase("false")){
							events.stopLandMark();
						}
						break;
					case "Laser"://start/stop the laser
						if(Objects.requireNonNull(bolean).equalsIgnoreCase("true")){
							events.startLaser();
						}
						if(bolean.equalsIgnoreCase("false")){
							events.stopLaser();
						}
						break;
					default: //default
						System.out.println("Events, no case found!");
						break;

				}
				break;

			case "Behavior": //this is for the Behaviors, you can create with coregraphe or are installed by nature
				String behaviorFunction = JSONFinder.getString("function", json);
				switch(Objects.requireNonNull(behaviorFunction)) {
					case "play": //starting a behavior
						String BehaviorName = JSONFinder.getString("name",json);
						try{
							//starting them in a new thread, because otherwise you cannot control the nao anymore
							Thread b = new Thread(() -> behavior.runBehavior(BehaviorName));
							b.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "stop": //stopping the running behavior
						behavior.stopBehavior();
						break;
					case "removeBehavior": //remove a behavior
						String BehaviorRemoveName = JSONFinder.getString("behaviorname",json);
						behavior.removeBehavior(BehaviorRemoveName);
						break;
					case "getBehaviors": //reloading the name of the behaviors on the client
                        JSONObject myjson3 = new JSONObject();
                        myjson3.add( "type", "Behavior");
                        myjson3.add( "Behaviors", behavior.getBehaviors());
                        sendMessage(myjson3.toJSONString());
                        break;
					default: //default
						System.out.println("Behavior, no case found!");
						break;
				}
				break;

			case "AutoLife": //autonomous life from the robot
				//what each mode actually does? I don't know!

				String AutoLife = JSONFinder.getString("function", json);
				String LifeModeString;
				switch(Objects.requireNonNull(AutoLife)) {
					case "BackgroundStrategy": //setting the background strategy
						LifeModeString = JSONFinder.getString("Strategy", json);
						autoLifeOfRobot.setBackgroundStrategy(LifeModeString);
						break;
					case "ExpressiveListening": //switch the expressive listening on or off
						//if it shell start or stop, i tried using real boolean, but it didn't worked, so String
						String bolean1 = JSONFinder.getString("boolean", json);
						if(Objects.requireNonNull(bolean1).equalsIgnoreCase("true")){
							autoLifeOfRobot.setExpressiveListeningEnabled(true);
						}
						if(bolean1.equalsIgnoreCase("false")){
							autoLifeOfRobot.setExpressiveListeningEnabled(false);
						}
						break;
					case "LifeMode": //setting the LifeMode
						LifeModeString = JSONFinder.getString("Mode",json);
						autoLifeOfRobot.setLife(LifeModeString);
						break;
					case "RobotOffsetFromFloor": //setting, how high the robot is standing, for example on a table or not...in meters
						float JsonFinderFloat = (float) JSONFinder.getDouble("Offset",json);
						autoLifeOfRobot.setRobotOffsetFromFloor(JsonFinderFloat);
						break;
					default: //default
						System.out.println("Autonomous Life, no case found!");
						break;
				}
				break;

			case "Recorder": //start the Video/Audio Recorder or take a picture
				String Recorder = JSONFinder.getString("function",json);
				switch (Objects.requireNonNull(Recorder)){
					case "startAudioRecorder": //start Audio Recorder
						nao.functions.Recorder.startAudioRecording();
						break;
					case "stopAudioRecorder": //stop Audio Recorder
						nao.functions.Recorder.stopAudioRecording();
						break;
					case "takePicture": //take a picture
						nao.functions.Recorder.takePicture();
						break;
					case "startVideoRecorder": //start Video Recorder
						nao.functions.Recorder.startVideoRecording();
						break;
					case "stopVideoRecorder": //stop Video Recorder
						nao.functions.Recorder.stopVideoRecording();
						break;
					default: //default
					System.out.println("Recorder, no case found!");
					break;
				}
				break;

            case "Touch": //this case i wrote, but never used it, i want to use it for the touch sensors from the robot, so if you want to use it, do it!
                String TouchEvent = JSONFinder.getString("function", json);
                switch (Objects.requireNonNull(TouchEvent)){
                    case "startRightBumperPressed": //start ...
                        Touch.startRightBumperPressed();
                        break;
                    case "startLeftBumperPressed": //start ...
                        Touch.startLeftBumperPressed();
                        break;
                    case "startFrontTactilTouched": //start ...
                        Touch.startFrontTactilTouched();
                        break;
                    case "startRearTactilTouched": //start ...
                        Touch.startRearTactilTouched();
                        break;
                    case "startHandRightBackTouched": //start ...
                        Touch.startHandRightBackTouched();
                        break;
                    case "startHandRightLeftTouched": //start ...
                        Touch.startHandRightLeftTouched();
                        break;
                    case "startHandRightRightTouched": //start ...
                        Touch.startHandRightRightTouched();
                        break;
                    case "startHandLeftBackTouched": //start ...
                        Touch.startHandLeftBackTouched();
                        break;
                    case "startHandLeftLeftTouched": //start ...
                        Touch.startHandLeftLeftTouched();
                        break;
                    case "startHandLeftRightTouched": //start ...
                        Touch.startHandLeftRightTouched();
                        break;
                    case "stopRightBumperPressed": //stop ...
                        Touch.stopRightBumperPressed();
                        break;
                    case "stopLeftBumperPressed": //stop ...
                        Touch.stopLeftBumperPressed();
                        break;
                    case "stopFrontTactilTouched": //stop ...
                        Touch.stopFrontTactilTouched();
                        break;
                    case "stopRearTactilTouched": //stop ...
                        Touch.stopRearTactilTouched();
                        break;
                    case "stopHandRightBackTouched": //stop ...
                        Touch.stopHandRightBackTouched();
                        break;
                    case "stopHandRightLeftTouched": //stop ...
                        Touch.stopHandRightLeftTouched();
                        break;
                    case "stopHandRightRightTouched": //stop ...
                        Touch.stopHandRightRightTouched();
                        break;
                    case "stopHandLeftBackTouched": //stop ...
                        Touch.stopHandLeftBackTouched();
                        break;
                    case "stopHandLeftLeftTouched": //stop ...
                        Touch.stopHandLeftLeftTouched();
                        break;
                    case "stopHandLeftRightTouched": //stop ...
                        Touch.stopHandLeftRightTouched();
                        break;
					default: //default
						System.out.println("Touch, no case found!");
						break;
                }
                break;

			case "Files": //this is the little file-sending-client
				String FilesEvent = JSONFinder.getString("function", json);
				switch (Objects.requireNonNull(FilesEvent)) {
					case "getAllFiles": //reloading the name of all files on the client
						File[] file = new File(new File("./").getParentFile(), "files/").listFiles();
						List<String> list = new LinkedList<>();
						//write every filename into a List
						for (int i = 0; i < Objects.requireNonNull(file).length; i++) {
							list.add(file[i].getName());
						}
						//and send this list to the client
						JSONObject myjson5 = new JSONObject();
						myjson5.add("type", "getAllFiles");
						myjson5.add("File", list);
						sendMessage(myjson5.toJSONString());
						break;
					case "deleteFile": //delete a file
						String fileNameDelete = JSONFinder.getString("filename", json);
						new File(new File("./").getParentFile(), "files/" + fileNameDelete).delete();
						break;
					case "downloadFile": //download a file from the nao
						String fileNameDownload = JSONFinder.getString("filename", json);
						File fileDownload = new File(new File("./").getParentFile(), "files/" + fileNameDownload);
						String path = fileDownload.getAbsolutePath();
						JSONObject jsonObject = new JSONObject();
						/*
						how this works:
						The File is decoded in Base64 and is split into many Messages
						And the client receive the messages and write a file will until it's finished
						So the file will be written step by step
						 */
						try {
							FileInputStream fileInputStream = new FileInputStream(path);
							byte[] bytes = new byte[30000];
							int length;
							while((length = fileInputStream.read(bytes)) != -1){
								byte[] base64 = Base64.getEncoder().encode(Arrays.copyOf(bytes, length));
								jsonObject.add("type", "downloadFile");
								jsonObject.add("function", "file");
								jsonObject.add("name", fileNameDownload);
								jsonObject.add("bytes", new String(base64, StandardCharsets.UTF_8));
								dataOutputStream.writeUTF(jsonObject.toJSONString());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "uploadFile": //uploading a file to the nao
						String base64 = JSONFinder.getString("bytes",json);
						byte[] bytes = Base64.getDecoder().decode(Objects.requireNonNull(base64));
						/*
						how this works:
						The File is decoded in Base64 and is split into many Messages
						Every time a message is received the file will be written until it's finished
						So the file will be written step by step
						 */
						try{
							File fileUpload = new File(new File("./").getParentFile(), "files/" + JSONFinder.getString("name", json));
							fileUpload.getParentFile().mkdirs();
							FileOutputStream fileOutputStream = new FileOutputStream(fileUpload, true);
							fileOutputStream.write(bytes);
							fileOutputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					default:
						System.out.println("Files, no case found!");
						break;
				}
				break;

			// -------------------  SYSTEM  --------------------------------

			case "Wakeup": //let the robot stand up
				move.wakeup();
				break;
			case "Reboot": //reboot the system
				commands.reboot();
				break;
			case "Shutdown": //shutdown the system
				commands.shutdown();
				break;
			case "battery": //getting the battery level
					JSONObject myjsonBattery = new JSONObject();
					myjsonBattery.add("type", "battery");
					myjsonBattery.add("battery", commands.getBatteryCharge());
					sendMessage(myjsonBattery.toJSONString());
				break;

			case "temperature": //getting all temperatures of the motors in  degrees
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

					sendMessage(myjson.toJSONString());
				} catch (CallError | InterruptedException | IOException callError) {
					callError.printStackTrace();
				}

			default: //default
				System.out.println("No case found!");
				break;
		}
    }

}
