package nao.moves;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;

import components.json.JSONArray;
import nao.currentApplication;

public class sonar implements SendClassName {
    @Override
    public void start(JSONArray args){
        try {
            ALMemory memory = currentApplication.getAlMemory();

            //http://doc.aldebaran.com/2-1/family/nao_dcm/actuator_sensor_names.html#term-us-sensor-m
            //The results of the first echo detected on each receiver are in Value, the 9 following echoes are from Value1 to Value9.
            //
            //Value of 0 means an error. A value of Max Detection range means no echo.
            //
            //For example, if Value contains 0,40, Value1 1,2 and Value2 Max Detection range, the following values (3 to 9) will contain Max Detection range too.
            // It probably means you have the echo of the ground at 0,40m and another object at 1,2m. Left and Right sensors work the same way and allow you to locate objects.

            String distanceLeft = memory.getData("Device/SubDeviceList/US/Left/Sensor/Value").toString();
            String distanceRight = memory.getData("Device/SubDeviceList/US/Right/Sensor/Value").toString();

            memory.subscribeToEvent("SonarLeftDetected", new EventCallback() {
                @Override
                public void onEvent(Object o) throws InterruptedException, CallError {
                    System.out.println("Sonar Left Detected");
                }
            });
            System.out.println(distanceLeft + "|" + distanceRight);
        }
        catch (Exception err) {
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
    public JSONArray getArgsRequest() {
        // ["id":"say", "type":"text", "prompt":"speech message"]
        return null;
    }
}
