package org.roger.sample.androidexam.Exam7_Service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.roger.sample.androidexam.R;

public class LocalActivity extends ActionBarActivity {

    private ServiceConnection sc = null;
    public static final String TAG = "LocalActivity";
    private boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "LocalActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        // start a local service
//        startService(new Intent(this, LocalService.class));

        // bind a local service
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LocalService.SimpleBinder sBinder = ((LocalService.SimpleBinder) service);
                Log.i(TAG, "3 + 2 = " + sBinder.add(3, 2));
                Log.i(TAG, sBinder.getService().toString());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_local, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // stop the local service
//        stopService(new Intent(this, LocalService.class));
    }

    public void onBtnClick(View obj) {
        switch (obj.getId()) {
            case R.id.bind:
                bindService(new Intent(LocalActivity.this, LocalService.class), sc, Context.BIND_AUTO_CREATE);
                isBind = true;
                break;
            case R.id.unbind:
                if (isBind) {
                    unbindService(sc);
                    isBind = false;
                }
                break;
        }
    }
}
