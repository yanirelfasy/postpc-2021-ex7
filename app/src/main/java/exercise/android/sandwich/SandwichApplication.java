package exercise.android.sandwich;

import android.app.Application;

import java.io.Serializable;

public class SandwichApplication extends Application implements Serializable {
    private LocalDataManager storageManager;

    public LocalDataManager getStorageManager(){
        return storageManager;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        storageManager = new LocalDataManager(this);
    }
    private static SandwichApplication instance = null;

    public static SandwichApplication getInstance(){
        return instance;
    }
}
