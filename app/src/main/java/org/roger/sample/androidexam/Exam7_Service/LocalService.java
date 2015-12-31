package org.roger.sample.androidexam.Exam7_Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by liren on 15/9/8.
 */
public class LocalService extends Service {
    public static final String TAG = "LocalService";
    @Nullable
    public SimpleBinder simpleBinder = null;
    @Nullable
    public LocalService localService = null;

    public class SimpleBinder extends Binder {
        @NonNull
        public LocalService getService() {
            return LocalService.this;
        }

        public int add(int a, int b) {
            return (a + b);
        }
    }

    @Nullable
    public Bitmap getDefaultBitmap() {
        String path = Environment.getExternalStorageDirectory() + "/test_big.jpg";
        return LocalActivity.getLocalBitmap(path);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, TAG + "--onCreate");
        super.onCreate();

        simpleBinder = new SimpleBinder();
        localService = this;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, TAG + "--onDestroy");
        super.onDestroy();
        LocalBroadcastManager.getInstance(localService).unregisterReceiver(rhelper);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, TAG + "--onBind");
        return simpleBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, TAG + "--onStartCommand. Thread name : " + Thread.currentThread().getId());//main

        Log.i(TAG, "NewThread" + "--runnable. Thread name : " + Thread.currentThread().getName() + ". Thread id : " + Thread.currentThread().getId());
        rhelper = new BroadcastReceiverHelper(localService);
        LocalBroadcastManager.getInstance(localService).registerReceiver(rhelper, new IntentFilter("com.roger.broadcastreceiver"));

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    private BroadcastReceiverHelper rhelper;
}
