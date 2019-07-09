package nao.moves;

import com.aldebaran.qi.Application;

import components.json.JSONArray;

public abstract interface SendClassName {
    public String name();
    public abstract void start(Application application, JSONArray args);
    public abstract void stop();

    /**
     * @return NULL if no args used, else JSONArray (StringFormat) is returned.
     */
    public JSONArray getArgsRequest();
}
