package org.roger.sample.androidexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.roger.sample.androidexam.Exam1.Activity1;
import org.roger.sample.androidexam.Exam2.Activity2;
import org.roger.sample.androidexam.Exam3.Activity3;
import org.roger.sample.androidexam.Exam4_surfaceview.ViewTest;
import org.roger.sample.androidexam.Exam5_Adapter.AdapterLearnActivity;


public class InterfaceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);
    }

    public void onBtnClick(View view) {
        Intent i = new Intent();
        switch (view.getId()) {
            case R.id.exam1:
                i.setClass(this, Activity1.class);
                break;
            case R.id.exam2:
                i.setClass(this, Activity2.class);
                break;
            case R.id.exam3:
                i.setClass(this, Activity3.class);
                break;
            case R.id.exam4:
                i.setClass(this, ViewTest.class);
                break;
            case R.id.exam5:
                i.setClass(this, AdapterLearnActivity.class);
                break;
        }
        startActivity(i);
    }
}
