package nao.moves;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import nao.moves.SendClassName;

public class say implements SendClassName {
    public Application application;

    @Override
    public void start(Application application){
        this.application = application;

        try {
            ALTextToSpeech tts = new ALTextToSpeech(application.session());
            tts.setLanguage("English");

        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
    public void saytext(String text){
        try{
           ALTextToSpeech tts = new ALTextToSpeech(application.session());
           tts.say(text);
        }
        catch (Exception err){
            err.printStackTrace();
        }

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean useArgs() {
        return true;
    }
}
