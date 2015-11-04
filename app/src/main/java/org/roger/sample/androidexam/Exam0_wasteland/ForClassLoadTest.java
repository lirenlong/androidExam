package org.roger.sample.androidexam.Exam0_wasteland;

import android.util.Log;

/**
 * Created by liren on 15/9/16.
 */
public class ForClassLoadTest {
    public static int a = 1;
    static {
        Log.i("classload","ForClassLoadTest's initialization.");
    }

    public void fun() {
        Log.i("classload","super's fun");
    }
}
