package nao.moves;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALLeds;

import components.json.JSONArray;

import java.util.List;

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

            List<String> led_names = leds.listLEDs();
            for(int i=0;i<led_names.size();i++){
                System.out.println(led_names.get(i));
            }


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
