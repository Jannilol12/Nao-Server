package nao.moves;

import com.aldebaran.qi.Application;

import components.json.JSONArray;

public abstract interface SendClassName {
    public String name();
    public abstract void start(Application application, JSONArray args);
    public abstract void stop();

    public boolean useArgs();
}
