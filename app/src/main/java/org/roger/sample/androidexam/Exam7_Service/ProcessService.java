package org.roger.sample.androidexam.Exam7_Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by liren on 15/12/30.
 */
public class ProcessService extends Service {

    public static final String TAG = "ProcessService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand.");

        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new NoSuchFieldError();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
