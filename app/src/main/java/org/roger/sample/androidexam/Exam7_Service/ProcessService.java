package org.roger.sample.androidexam.Exam7_Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by liren on 15/12/30.
 */
public class ProcessService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LocalActivity.TAG, "ProcessService --onCreate. ThreadName = " + Thread.currentThread().getName());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LocalActivity.TAG, "ProcessService --onStartCommand. ThreadName = " + Thread.currentThread().getName());

        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new NoSuchFieldError();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LocalActivity.TAG, "ProcessService --onDestroy.");
    }
}
