package org.roger.sample.androidexam;

import android.app.Application;
import android.util.Log;

import com.github.mmin18.layoutcast.LayoutCast;

/**
 * Created by liren on 15/11/24.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MyApplication", "onCreate.");

        if (BuildConfig.DEBUG) {
            LayoutCast.init(this);
        }
    }
}
