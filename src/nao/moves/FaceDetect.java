package nao.moves;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALFaceDetection;
import components.json.JSONArray;
import components.json.finder.JSONFinder;
import components.json.parser.JSONParser;

public class FaceDetect implements SendClassName {
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
            faceDetection.learnFace(JSONFinder.getString("[0].value", args));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {

    }

    @Override
    public JSONArray getArgsRequest() {
        return (JSONArray) JSONParser.parse("[{\"id\":\"Learn_Face\", \"type\":\"text\", \"prompt\":\"id\"}]");
    }
}
