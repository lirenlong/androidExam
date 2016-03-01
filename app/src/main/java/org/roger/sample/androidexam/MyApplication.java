package org.roger.sample.androidexam;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.mmin18.layoutcast.LayoutCast;

import org.roger.sample.androidexam.Exam7_Service.CrashExitActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liren on 15/11/24.
 */

public class MyApplication extends Application {
    public static MyApplication myApplication;
    public static ArrayList<Activity> listActs = null;
    public File file = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MyApplication", "onCreate.");

        myApplication = this;

        listActs = new ArrayList<>();

        if (BuildConfig.DEBUG) {
            LayoutCast.init(this);
        }

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                if (ex == null) {
                    return;
                }

//                for(Activity act : listActs) {
//                    act.finish();
//                }

//                relaunch();

//                relaunchRightNow();


                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

            private void relaunchRightNow() {
                Intent intent = new Intent(MyApplication.myApplication, CrashExitActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }


            private void relaunch() {
                Intent intent = new Intent(MyApplication.myApplication, CrashExitActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        MyApplication.myApplication.getBaseContext(), 0, intent, intent.getFlags());

                //Following code will restart your application after 2 seconds
                AlarmManager mgr = (AlarmManager) MyApplication.myApplication.getBaseContext()
                        .getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                        pendingIntent);

                //This will finish your activity manually
//                    activity.finish();

            }
        });


        Log.i("tmp" ,"file is " + this.getCacheDir());
        file = new File(this.getCacheDir(),"1.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void addActivity(Activity a) {
        listActs.add(a);
    }

    // 当在crashHandler里面，把activity都清掉的时候，可以避免被os重新建task，重新launch activity
    public static void removeActivity(Activity a) {
        listActs.remove(a);
    }

}
