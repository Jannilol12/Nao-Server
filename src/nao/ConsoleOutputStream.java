package nao;

import components.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * This Method is for the Console on the client.
 */
public class ConsoleOutputStream extends OutputStream {
    private StringBuilder builder;
    private DataOutputStream dataOutputStream;
    private PrintStream stream;

    /**
     * Setting everything up
     */
    public ConsoleOutputStream(){
        stream = System.out; //getting the console from the Nao.
        System.setOut(new PrintStream(this)); //instead of writing in the console, write here
        builder = new StringBuilder(); //instead of sending every single char to the client, make a String
    }

    /**
     * Send the Console to the Client
     * @param dataOutputStream needed, for sending the messages to the client
     */
    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    /**
     * Writing the bytes from the console into a String and sending it to the client
     * @param b char from the console as an int
     * @throws IOException Exception
     */
    @Override
    public void write(int b) throws IOException {
        char c = (char) b;
        if(c != '\n'){ //if not a "new Line"
            stream.write(c); //write into the Console from the nao, because otherwise there isn't anything because of the System.setOut in line 23
            builder.append(c); //add the char to the StringBuilder to receive a String in the End
        }else{
            stream.write(c);
            String stringToSend = builder.toString(); //make a new String out of the chars
            JSONObject myjson1 = new JSONObject();
            if(!stringToSend.isEmpty()) {
                myjson1.add("type", "Console");
                myjson1.add("String", stringToSend);
                try {
                    dataOutputStream.writeUTF(myjson1.toJSONString()); //Send it to the client
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            builder = new StringBuilder(); //make a new StringBuilder, so emptying the old
        }
    }
}
