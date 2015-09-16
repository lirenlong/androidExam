package org.roger.sample.androidexam.Exam0_wasteland;

import android.util.Log;

/**
 * Created by liren on 15/9/16.
 */
public class ClassRef {
    static {
        Log.i("classload", "ClassRef initialization.");
    }
}

//http://my.oschina.net/jasonultimate/blog/166932
//另外还有一个导致ClassNotFoundException的原因就是：当一个类已经某个类加载器加载到内存中了，此时另一个类加载器又尝试着动态地从同一个包中加载这个类。

//那么，加入现在主classloader a，声明了一个以a父的classloader，用a去装载一个特别class。这个时候：我可以使用这个class么？我可以用主classloader来load到这个class么？