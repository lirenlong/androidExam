package org.roger.sample.androidexam.Exam9_rootAuth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.roger.sample.androidexam.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by liren on 15/10/19.
 */
public class rootAuth extends Activity {
    private static final String TAG = "rootAuth";
    Process process = null;
    Process process1 = null;
    DataOutputStream os = null;
    DataInputStream is = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
            process = Runtime.getRuntime().exec("/system/xbin/su");
            /*这里可能需要修改su的源代码 （注掉  if (myuid != AID_ROOT && myuid != AID_SHELL) {*/

            os = new DataOutputStream(process.getOutputStream());
            is = new DataInputStream(process.getInputStream());
            os.writeBytes("/system/bin/ls" + " \n");  //这里可以执行具有root 权限的程序了
            os.writeBytes(" exit \n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error - Here is what I know:" + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }// get the root privileges
    }
}