package nao;

import nao.moves.Interface_Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class receiver extends Thread {
    private Socket s;
    private DataOutputStream dos;

    public void run(){
        final ServerSocket ss;//port auf den Server hört

        try {
            ss = new ServerSocket(7777);
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
                        System.out.println(str);
                        Main.receiveText(str, dos);

                        if (str.equalsIgnoreCase("end"))
                            break;

                        if (str.equalsIgnoreCase("finalEnd"))
                            running = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
