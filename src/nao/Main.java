package nao;

import nao.debugger.Debugger;
import nao.functions.commands;
import nao.functions.events;
import nao.moves.Interface_Controller;

/**
 * Program for Nao-Robot written by Jannik Lieb
 *
 * @author Jannik Lieb
 *
 * @since 13.01.2020
 *
 * @version 1.0
 *
 * Sorry for my bad english.
 * Why didn't I wrote it in German?
 * Because I'm serious! No...I am dumb
 */

public class Main {
	public static receiver r;
	
	public static void main(String[] args) {
		//Debugger is writing a log file
		Debugger.setEnable(true);

		// nao.local and port 9559 is given from the API of the robot
		currentApplication.load("nao.local",9559 );

		//Loading the list on the left side of the client
		Interface_Controller.load();

		commands.setMaxTryNumber(5);

		// starting server-sockets
		r = new receiver();
		r.start();

		events.loadVocabulary();
	}
}
