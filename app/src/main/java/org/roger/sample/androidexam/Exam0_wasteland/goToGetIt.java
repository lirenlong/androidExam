package org.roger.sample.androidexam.Exam0_wasteland;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import junit.framework.Test;
import junit.runner.BaseTestRunner;
import junit.runner.Version;

import org.roger.sample.androidexam.R;

import dalvik.system.DexClassLoader;

import com.taobao.updatecenter.hotpatch.HotPatchManager;

/**
 * Created by liren on 15/8/26.
 */
public class goToGetIt extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gotogetit_activity);
//        testUri();
//        classLoaderLearn();
//        testClassLoad();
//        testClassInitialize();
//        smartObjectTest();
//        testClassNotFound();
    }

    private void testClassNotFound() {
//        try {
//            Class a = Class.forName("com.roger.abc");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        // 有父类
//        BaseTestRunner bt = new BaseTestRunner() {
//            @Override
//            public void testStarted(String s) {
//
//            }
//
//            @Override
//            public void testEnded(String s) {
//
//            }
//
//            @Override
//            public void testFailed(int i, Test test, Throwable throwable) {
//
//            }
//
//            @Override
//            protected void runFailed(String s) {
//
//            }
//        };

//        try {
//            Version.id();
//        } catch (Throwable e) {
//            Log.i("roger","catched");
//            e.printStackTrace();
//        }

        try {
            HotPatchManager ins = HotPatchManager.getInstance();
        } catch (Throwable e) {
            Log.i("roger","catched");
            e.printStackTrace();
        }

    }

    private void smartObjectTest() {
        ForClassLoadTest obj = new ChildOfForClassLoadTest();
        obj.fun();//直接掉到子类的fun
    }

    private void testClassInitialize() {
        Log.i("classload", "before testClassInitialize");
        Class target = ForClassLoadTest.class;
//        Log.i("roger","ForClassLoadTest's static a = " + ForClassLoadTest.a);//如果有机会输出a的话，应该是0.等初始化之后，变成1.
//        try {
//            Object obj = (Object) target.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        Log.i("classload", "after testClassInitialize");


//        如果调用了newInstance()才会输出ForClassLoadTest's initialization.只是ForClassLoadTest.class;不会初始化。
//        09-16 08:46:43.630    5719-5719/org.roger.sample.androidexam I/roger﹕ before testClassInitialize
//        09-16 08:46:43.630    5719-5719/org.roger.sample.androidexam I/roger﹕ ForClassLoadTest's initialization.
//        09-16 08:46:43.630    5719-5719/org.roger.sample.androidexam I/roger﹕ after testClassInitialize
    }

    private void testClassLoad() {
        // class的装载对象，经历3个步骤。才能使用对象实例。
        // 1. 加载到内存(将二进制的class读取到内存中)
        // 2. 链接(申请内存空间，并赋上系统默认值，比如int为0等，链接并加载引用的类)
        // 3. 初始化(会执行static块，并赋上用户指定的值)
        Log.i("classload", "before load ForClassLoadTest");
        ClassLoader cl = this.getApplication().getClassLoader();
        try {
            Class target = cl.loadClass("org.roger.sample.androidexam.Exam0_wasteland.ForClassLoadTest");
            Object obj = (Object) target.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.i("classload", "after load ForClassLoadTest");
    }

    private void classLoaderLearn() {
//        ClassLoader cl = this.getClassLoader();
        ClassLoader cl = this.getApplication().getClassLoader();


        ClassLoader mcl = new DexClassLoader(this.getApplication().getFilesDir().getAbsolutePath() + "/" + "a.apk", this.getApplication().getFilesDir().getAbsolutePath(), null, cl);
        while (mcl != null) {
            Log.i("classload", "classloader is " + mcl);
            mcl = mcl.getParent();
        }
        Log.i("classload", "last classloader is " + mcl);


        while (cl != null) {
            Log.i("classload", "classloader is " + cl);
            cl = cl.getParent();
        }
        Log.i("classload", "last classloader is " + cl);

    }

    private void testUri() {
        String str = new String("//www.baidu.com/asd/asdf/asdf/df?df=df&as=as=!");
        String str2 = new String("http://www.baidu.com/#asd/asdf/asdf/df?df=df&as=as=!");
        String str3 = new String("www.baidu.com/@$asd/asdf/asdf/df?df=df&as=as=!");
        Uri uri = Uri.parse(str);
        Log.i("roger", uri.getEncodedSchemeSpecificPart());

        uri = Uri.parse(str2);
        Log.i("roger", uri.getEncodedSchemeSpecificPart());

        uri = Uri.parse(str3);
        Log.i("roger", uri.getEncodedSchemeSpecificPart());
    }

    public void onBtnClick(View view) {
        testClassNotFound();
    }
}
