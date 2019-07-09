package nao.moves;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALLeds;

import components.json.JSONArray;

public class led implements SendClassName {
    public Application application;

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void start(Application application, JSONArray args){
        this.application = application;

        try {
            ALLeds leds = new ALLeds(application.session());


        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }

	@Override
	public JSONArray getArgsRequest() {
		// TODO Auto-generated method stub
		return null;
	}
}
