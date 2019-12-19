package nao;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMemory;
import nao.debugger.Debugger;
import nao.functions.Recorder;
import nao.functions.commands;
import nao.functions.events;
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

		events.loadVocabulary();
		Recorder.setCameraStats();
	}
}
