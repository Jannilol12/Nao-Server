package nao.moves;

import java.util.List;

import com.aldebaran.qi.helper.proxies.ALLeds;

import components.json.JSONArray;
import nao.currentApplication;

public class led implements SendClassName {
    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void start(JSONArray args){
        try {
            ALLeds leds = new ALLeds(currentApplication.getApplication().session());

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
