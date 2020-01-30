package nao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * connect the Server with the client with ServerSockets
 */
public class receiver extends Thread {
    private ConsoleOutputStream customOutputStream = null;

    /**
     * run the server-socket -> connection between robot and pc
     */
    public void run(){
        final ServerSocket ss;

        try {
              //for the port, you can select everything you want...
        	int port = 7777;
            ss = new ServerSocket(port);
            System.out.println("Start Server on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        boolean running = true;

        while(running) {
            try {
                //For the connection from the robot and the client i am using ServerSockets and sending JSON compiled Strings
                Socket s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                //this is for writing something into the Console
                if(customOutputStream == null){
                    customOutputStream = new ConsoleOutputStream();
                }
                customOutputStream.setDataOutputStream(dos);
                MainReceiver.setDataOutputStream(dos);
                while (running) {
                    String str = dis.readUTF();
                    if(!str.isEmpty()) {
                        try {
                            //every String received is going to the MainReceiver
                            MainReceiver.receiveText(str);

                            if (str.equalsIgnoreCase("end"))
                                break;

                            //stopping running, and close connection
                            if (str.equalsIgnoreCase("finalEnd"))
                                running = false;
                        }catch (Exception err){
                            System.out.println("Programm crash");
                            err.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Stop Server");

        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
