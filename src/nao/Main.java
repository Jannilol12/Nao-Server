package nao;

import nao.debugger.Debugger;
import nao.functions.commands;
import nao.functions.currentApplication;
import nao.moves.Interface_Controller;

public class Main {
	public static receiver r;
	public static commands commands;
	
	public static void main(String[] args) {
		Debugger.setEnable(true);
		
		currentApplication.load("127.0.0.1",9559 );
		Interface_Controller.load();

	    r = new receiver();
	    r.start();
//        say say = new say(args);
//        say.saytext("Hallo");
//        speech_recognition speech = new speech_recognition(args);
//        speech.addvocabulary("Hallo");
		commands = new commands();
	}
}
