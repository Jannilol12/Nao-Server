package nao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class receiver extends Thread {
    private Socket s;
    private DataOutputStream dos;

    public void run(){
        final ServerSocket ss;//port auf den Server hoert

        try {
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
                s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());

                while (running) {
                    String str = dis.readUTF();
                    if(!str.isEmpty()) {
                        try {
                            System.out.println(str);
                            Main.receiveText(str, dos);

                            if (str.equalsIgnoreCase("end"))
                                break;

                            if (str.equalsIgnoreCase("finalEnd"))
                                running = false;
                        }catch (Exception err){
                            System.out.println("Programm crash");
                            err.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {}
        }
        
        System.out.println("Stop Server");

        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
