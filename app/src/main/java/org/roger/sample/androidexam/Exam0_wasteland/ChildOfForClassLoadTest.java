package org.roger.sample.androidexam.Exam0_wasteland;

import android.util.Log;

/**
 * Created by liren on 15/9/16.
 */
public class ChildOfForClassLoadTest extends ForClassLoadTest {

    public static ClassRef cref;

    static {
        Log.i("classload", "ChildOfForClassLoadTest's initialization.");
    }

    @Override
    public void fun() {
        Log.i("classload","Child's fun()");
        cref = new ClassRef();
        // 这里会在Child's fun()之后，执行ClassRef中的static块。不写new的话，不会初始化。那么如何再现NoClassDefException的问题呢？
    }
}
