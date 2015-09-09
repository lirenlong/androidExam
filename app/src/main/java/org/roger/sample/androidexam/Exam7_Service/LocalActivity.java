package org.roger.sample.androidexam.Exam7_Service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.roger.sample.androidexam.InterfaceActivity;
import org.roger.sample.androidexam.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

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
        Intent i = new Intent();

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
            case R.id.IntentSerializal:
                doIntentSerializeal(i);
                break;
            case R.id.IntentParcelable:
                doIntentParcelable(i);
                break;
            case R.id.IntentBigData:
                doIntentBig(i);
                break;
        }
    }

    private void doIntentParcelable(Intent i) {
        Book mBook = new Book();
        mBook.setBookName("Android Tutor");
        mBook.setAuthor("Frankie");
        mBook.setPublishTime(2010);
        Intent mIntent = new Intent(this, IntentActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("parcelable", mBook);
        mIntent.putExtras(mBundle);

        startActivity(mIntent);
    }

    private void doIntentBig(Intent i) {
        //这个路径是不可以的，需要通过Environment.getExternalStorageDirectory()
//        FileInputStream aa = getLocalFile("/storage/emulated/legacy/test.jpg");

        String path = Environment.getExternalStorageDirectory() + "/test.jpg";
        Bitmap bitmap = getLocalBitmap(path);
        i.putExtra("bitmap", bitmap);
        try {
            startActivity(i);
        } catch (Exception e) {
            Log.i(TAG, "exception is : " + e);
        }
    }

    private void doIntentSerializeal(Intent i) {
        Person person = new Person("JimGreen", 21);

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("abc", person);
//        i.putExtras(bundle);

        i.putExtra("person", person);
        i.setClass(this, IntentActivity.class);
        startActivity(i);
    }


    // inner static Person(若是inner class，必须是static)
    public static class Person implements Serializable {
        private static final long serialVersionUID = -7060210544600464481L;
        public String name;
        private int age;

        public Person(String s, int i) {
            name = s;
            age = i;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }


    public static Bitmap getLocalBitmap(String url) {
        FileInputStream fis = null;
        Bitmap bitmap;
        try {
            fis = new FileInputStream(url);
            bitmap = BitmapFactory.decodeStream(fis);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FileInputStream getLocalFile(String url) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(url);
            return fis;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
