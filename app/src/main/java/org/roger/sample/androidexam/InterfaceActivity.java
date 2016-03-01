package org.roger.sample.androidexam;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import org.roger.sample.androidexam.Exam0_wasteland.goToGetIt;
import org.roger.sample.androidexam.Exam1.Activity1;
import org.roger.sample.androidexam.Exam10_hotfix.hotFixActivity;
import org.roger.sample.androidexam.Exam2.Activity2;
import org.roger.sample.androidexam.Exam3.Activity3;
import org.roger.sample.androidexam.Exam4_surfaceview.ViewTest;
import org.roger.sample.androidexam.Exam5_Adapter.AdapterLearnActivity;
import org.roger.sample.androidexam.Exam6_DataBinding.DBInterfaceActivity;
import org.roger.sample.androidexam.Exam7_Service.LocalActivity;
import org.roger.sample.androidexam.Exam9_layoutCast.LayoutCastTest;

import java.io.File;
import java.io.IOException;


public class InterfaceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("InterfaceActivity", "onCreate " + this.toString());
        Log.i("InterfaceActivity", "InterfaceActivity TaskID = " + this.getTaskId());
        setContentView(R.layout.activity_interface);

        MyApplication.addActivity(this);
    }

    public void onBtnClick(@NonNull View view) {
        Intent i = new Intent();
        switch (view.getId()) {
            case R.id.exam0:
                i.setClass(this, goToGetIt.class);
                break;
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
            case R.id.exam6:
                i.setClass(this, DBInterfaceActivity.class);
                break;
            case R.id.exam7:
                i.setClass(this, LocalActivity.class);
                break;
            case R.id.exam9:
                i.setClass(this, LayoutCastTest.class);
                break;
            case R.id.exit:
                this.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
            case R.id.crash:
                throw new NoSuchFieldError();
            case R.id.hotfix:
                i.setClass(this, hotFixActivity.class);
                break;
        }
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        Log.i("InterfaceActivity", "onDestroy " + this.toString());
        super.onDestroy();

        MyApplication.removeActivity(this);
    }
}
