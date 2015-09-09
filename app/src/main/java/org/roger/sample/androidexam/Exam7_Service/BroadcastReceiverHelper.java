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

import org.roger.sample.androidexam.R;

public class BroadcastReceiverHelper extends BroadcastReceiver {

    NotificationManager mn = null;
    Notification notification = null;
    Context ct = null;
    BroadcastReceiverHelper receiver;

    public BroadcastReceiverHelper(Context c) {
        ct = c;
        receiver = this;
    }

    //注册
    public void registerAction(String action) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        ct.registerReceiver(receiver, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg = intent.getStringExtra("msg");
        int id = intent.getIntExtra("who", 0);
        Bitmap bm = intent.getParcelableExtra("bitmap");

        if (intent.getAction().equals("com.roger.broadcastreceiver")) {
            mn = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notification = new Notification(R.drawable.ic_launcher, id + "发送广播", System.currentTimeMillis());
            intent.setClass(context, IntentActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context,
                    0, intent, 0);
            notification.setLatestEventInfo(context,
                    "msg", msg, contentIntent);
            mn.notify(0, notification);
        }
    }
}