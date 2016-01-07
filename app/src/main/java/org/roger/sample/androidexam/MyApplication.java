package org.roger.sample.androidexam;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.mmin18.layoutcast.LayoutCast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liren on 15/11/24.
 */

public class MyApplication extends Application {
    public static MyApplication myApplication;
    public static ArrayList<Activity> listActs = null;
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MyApplication", "onCreate.");

        myApplication = this;

        listActs = new ArrayList<>();

        if (BuildConfig.DEBUG) {
            LayoutCast.init(this);
        }

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                if(ex == null) {
                    return;
                }

//                for(Activity act : listActs) {
//                    act.finish();
//                }
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    public static void addActivity(Activity a) {
        listActs.add(a);
    }

    // 当在crashHandler里面，把activity都清掉的时候，可以避免被os重新建task，重新launch activity
    public static void removeActivity(Activity a) {
        listActs.remove(a);
    }

}
