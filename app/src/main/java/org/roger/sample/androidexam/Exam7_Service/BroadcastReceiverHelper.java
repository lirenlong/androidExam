package org.roger.sample.androidexam.Exam7_Service;

/**
 * Created by liren on 15/9/9.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.roger.sample.androidexam.R;

public class BroadcastReceiverHelper extends BroadcastReceiver {

    @Nullable
    NotificationManager mn = null;
    @Nullable
    Notification notification = null;
    @Nullable
    Context ct = null;
    BroadcastReceiverHelper receiver;

    public BroadcastReceiverHelper(Context c) {
        ct = c;
        receiver = this;
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        Log.i("BroadcastReceiverHelper", "BroadcastReceiverHelper's thread name : " + Thread.currentThread().getName());//

        String msg = intent.getStringExtra("msg");
        int id = intent.getIntExtra("who", 0);
        Bitmap bm = intent.getParcelableExtra("bitmap");

        if (intent.getAction().equals("com.roger.broadcastreceiver")) {
            mn = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notification = new Notification(R.drawable.ic_launcher, id + "发送广播", System.currentTimeMillis());
            intent.setClass(context, IntentActivity.class);

            //for big test
            String path = Environment.getExternalStorageDirectory() + "/test_medium.jpg";
            Bitmap bigBM = LocalActivity.getLocalBitmap(path);
            intent.putExtra("bitmap",bigBM);

            PendingIntent contentIntent = PendingIntent.getActivity(context,
                    0, intent, 0);
            notification.setLatestEventInfo(context,
                    "msg", msg, contentIntent);
            mn.notify(0, notification);
        }
    }
}