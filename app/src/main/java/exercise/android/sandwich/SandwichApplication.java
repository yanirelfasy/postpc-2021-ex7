package exercise.android.sandwich;

import android.app.Application;

import java.io.Serializable;

public class SandwichApplication extends Application implements Serializable {
    private DataManager dataManager;

    public DataManager getDataManager(){
        return dataManager;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        dataManager = new DataManager(this);
    }
    private static SandwichApplication instance = null;

    public static SandwichApplication getInstance(){
        return instance;
    }
}
