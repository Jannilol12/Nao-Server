package nao;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMemory;
import nao.debugger.Debugger;
import nao.functions.commands;
import nao.moves.Interface_Controller;

import java.util.List;

public class Main {
	public static receiver r;
	
	public static void main(String[] args) {
		Debugger.setEnable(true);

		currentApplication.load("nao.local",9559 );
		Interface_Controller.load();

		commands.setMaxTryNumber(5);

	    r = new receiver();
	    r.start();
//        say say = new say(args);
//        say.saytext("Hallo");
//        speech_recognition speech = new speech_recognition(args);
//        speech.addvocabulary("Hallo");
	}
}
