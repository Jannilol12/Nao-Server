package nao;

import components.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class CustomOutputStream extends OutputStream {
    private StringBuilder builder;
    private DataOutputStream dataOutputStream;
    private PrintStream stream;

    public CustomOutputStream(){
        stream = System.out;
        System.setOut(new PrintStream(this));
        builder = new StringBuilder();
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void write(int b) throws IOException {
        char c = (char) b;
        if(c != '\n'){
            stream.write(c);
            builder.append(c);
        }else{
            stream.write(c);
            String stringToSend = builder.toString();
            JSONObject myjson1 = new JSONObject();
            if(!stringToSend.isEmpty()) {
                myjson1.add("type", "Console");
                myjson1.add("String", stringToSend);
                try {
                    dataOutputStream.writeUTF(myjson1.toJSONString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            builder = new StringBuilder();
        }
    }
}
