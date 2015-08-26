package org.roger.sample.androidexam.Exam0_wasteland;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by liren on 15/8/26.
 */
public class goToGetIt extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testUri();
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
