package org.roger.sample.androidexam.Exam6_DataBinding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.roger.sample.androidexam.Exam6_DataBinding.Observable.ObserableActivity;
import org.roger.sample.androidexam.Exam6_DataBinding.basic.DataBindingActivity;
import org.roger.sample.androidexam.Exam6_DataBinding.viewwithID.ViewWithIDActivity;
import org.roger.sample.androidexam.R;

public class DBInterfaceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e6_activity_dbinterface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dbinterface, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBtnClick(@NonNull View view) {
        Intent i = new Intent();
        switch (view.getId()) {
            case R.id.btnBasic:
                i.setClass(this, DataBindingActivity.class);
                break;
            case R.id.btnObservable:
                i.setClass(this, ObserableActivity.class);
                break;
            case R.id.btnViewWithID:
                i.setClass(this, ViewWithIDActivity.class);
                break;
        }
        startActivity(i);
    }
}
