package org.roger.sample.androidexam.Exam11_rxjava_mvp;

/**
 * Created by liren on 16/3/1.
 */

import android.app.Application;
import android.util.Log;

import retrofit.RestAdapter;

public class App extends Application {

    private static App instance;
    private static ServerAPI serverAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        serverAPI = new RestAdapter.Builder()
                .setEndpoint(ServerAPI.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.v("Retrofit", message);
                    }
                })
                .build().create(ServerAPI.class);
    }

    public static ServerAPI getServerAPI() {
        return serverAPI;
    }
}