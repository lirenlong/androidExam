package org.roger.sample.androidexam.Exam7_Service;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import org.roger.sample.androidexam.R;

public class CrashExitActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_exit);
    }

    public void onBtnClick(@NonNull View obj) {
        switch (obj.getId()) {
            case R.id.crash:
                throw new NoSuchFieldError();
            case R.id.exit:
                this.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
        }
    }
}
