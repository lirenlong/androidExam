package org.roger.sample.androidexam.Exam7_Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by liren on 15/9/8.
 */
public class LocalService extends Service {
    public static final String TAG = "LocalService";
    public SimpleBinder simpleBinder = null;

    public class SimpleBinder extends Binder {
        public LocalService getService() {
            return LocalService.this;
        }

        public int add(int a, int b) {
            return (a + b);
        }
    }

    @Override
    public void onCreate() {
        Log.i(TAG, TAG + "--onCreate");
        super.onCreate();

        simpleBinder = new SimpleBinder();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, TAG + "--onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
//    public IBinder onBind(Intent intent) {
//        Log.i(TAG, TAG + "onBind");
//        return null;
//    }
    public IBinder onBind(Intent intent) {
        Log.i(TAG, TAG + "--onBind");
        return simpleBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, TAG + "--onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
