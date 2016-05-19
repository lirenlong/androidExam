package org.roger.sample.androidexam.Exam10_hotfix;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;

import org.roger.sample.androidexam.R;

import java.io.IOException;

public class hotFixActivity extends Activity {

    public static String sName = "abc";
    private PatchManager patchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix);

        A a = new A();
        a.getI();
        Log.i("euler", "i am " + a.a("roger"));
    }

    public static String stringAppendChar(String s, char c) {
        if (s == null || s.isEmpty()) return s;
        StringBuilder sb = new StringBuilder(s).append(c);
        sb.append(sName);
        sb.append("-ello");
        return sb.toString();
    }

    public void test() {
        // 观察使用内部变量时，在_CF类里如何查看。
        Log.i("ANDFIX", "HOOKED.");
        Log.i("ANDFIX", sName);
        Log.i("ANDFIX", "Class is " + patchManager.getClass());
    }

    public void onBtnClick(View view) {
        long begin = System.currentTimeMillis();
        switch (view.getId()) {
            case R.id.init:

                patchManager = new PatchManager(this);
                patchManager.init("1.0.0");

                Log.i("roger" ,"init cost : " + (System.currentTimeMillis() - begin));
                break;
            case R.id.Patch:
                String str = "/sdcard/roger.apatch";
                try {
                    patchManager.addPatch(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("roger" ,"init cost : " + (System.currentTimeMillis() - begin));
                break;
            case R.id.Load:
                patchManager.loadPatch();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        patchManager.loadPatch();
//                    }
//                }).start();
                Log.i("roger" ,"init cost : " + (System.currentTimeMillis() - begin));
                break;
            case R.id.Run:
                Toast.makeText(this, hotFixActivity.stringAppendChar("Mine", '1'), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
