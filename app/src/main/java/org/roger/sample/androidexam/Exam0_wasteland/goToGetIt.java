package org.roger.sample.androidexam.Exam0_wasteland;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import dalvik.system.DexClassLoader;

/**
 * Created by liren on 15/8/26.
 */
public class goToGetIt extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        testUri();
        classLoaderLearn();
    }

    private void classLoaderLearn() {
//        ClassLoader cl = this.getClassLoader();
        ClassLoader cl = this.getApplication().getClassLoader();


        ClassLoader mcl = new DexClassLoader(this.getApplication().getFilesDir().getAbsolutePath() + "/" + "a.apk",this.getApplication().getFilesDir().getAbsolutePath(),null,cl);
        while(mcl != null) {
            Log.i("goToGetIt","classloader is " + mcl);
            mcl = mcl.getParent();
        }
        Log.i("goToGetIt","last classloader is " + mcl);



        while(cl != null) {
            Log.i("goToGetIt","classloader is " + cl);
            cl = cl.getParent();
        }
        Log.i("goToGetIt","last classloader is " + cl);

    }

    private void testUri() {
        String str = new String("//www.baidu.com/asd/asdf/asdf/df?df=df&as=as=!");
        String str2 = new String("http://www.baidu.com/#asd/asdf/asdf/df?df=df&as=as=!");
        String str3 = new String("www.baidu.com/@$asd/asdf/asdf/df?df=df&as=as=!");
        Uri uri = Uri.parse(str);
        Log.i("roger", uri.getEncodedSchemeSpecificPart());

        uri = Uri.parse(str2);
        Log.i("roger",uri.getEncodedSchemeSpecificPart());

        uri = Uri.parse(str3);
        Log.i("roger",uri.getEncodedSchemeSpecificPart());
    }

}
