package nao.moves;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALFaceDetection;
import components.json.JSONArray;
import components.json.finder.JSONFinder;
import components.json.parser.JSONParser;

public class FaceForget implements SendClassName {
    public Application application;

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void start(Application application, JSONArray args) {
        this.application = application;
        try {
            ALFaceDetection faceDetection = new ALFaceDetection(application.session());
            faceDetection.forgetPerson(JSONFinder.getString("[0].value", args));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {

    }

    @Override
    public JSONArray getArgsRequest() {
        return (JSONArray) JSONParser.parse("[{\"id\":\"Forget_Person\", \"type\":\"text\", \"prompt\":\"id\"}]");
    }
}
