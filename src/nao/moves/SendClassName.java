package nao.moves;

import com.aldebaran.qi.Application;

public abstract interface SendClassName {
    public String name();
    public abstract void start(Application application);
    public abstract void stop();

    public boolean useArgs();
}
