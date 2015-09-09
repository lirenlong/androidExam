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
import android.widget.ImageView;

import org.apache.http.ReasonPhraseCatalog;
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

        initialServiceConnection();
    }

    private void initialServiceConnection() {
        // bind a local service
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LocalService.SimpleBinder sBinder = ((LocalService.SimpleBinder) service);
                Log.i(TAG, "3 + 2 = " + sBinder.add(3, 2));
                Log.i(TAG, sBinder.getService().toString());

                // 测试通过service获取，service上的bitmap。
                Bitmap bm = sBinder.getService().getDefaultBitmap();
                if (bm != null) {
                    ((ImageView) findViewById(R.id.imageview)).setImageBitmap(bm);
                }
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

    /*
  * ServiceRecord{43728060 u0 org.roger.sample.androidexam/.Exam7_Service.LocalService}
    intent={cmp=org.roger.sample.androidexam/.Exam7_Service.LocalService}
    packageName=org.roger.sample.androidexam
    processName=org.roger.sample.androidexam
    baseDir=/data/app/org.roger.sample.androidexam-2.apk
    dataDir=/data/data/org.roger.sample.androidexam
    app=ProcessRecord{436fb4f0 1235:org.roger.sample.androidexam/u0a213}
    createTime=-13s714ms startingBgTimeout=--
    lastActivity=-13s713ms restartTime=-13s713ms createdFromFg=true
    startRequested=true delayedStop=false stopIfKilled=false callStart=true lastStartId=1
    */
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
            case R.id.startService:
                doStartService(i);
                break;
            case R.id.SendBroadCast:
                doSendBroadcast(i);
                break;
        }
    }

    private void doSendBroadcast(Intent i) {
        String path = Environment.getExternalStorageDirectory() + "/test_small.jpg";
        //test_big.jpg 1.2M
        //test_medium.jpg 640k
        //test_small.jpg 11k

        Bitmap bitmap = getLocalBitmap(path);
        i.putExtra("bitmap", bitmap);
        i.putExtra("who",1);
        i.putExtra("msg","hello LocalService from LocalAcitivty via notifaction.");

        i.setAction("com.roger.broadcastreceiver");
        sendBroadcast(i);
    }

    private void doStartService(Intent i) {
        i.setClass(this, LocalService.class);
        Book mBook = new Book();
        mBook.setBookName("Android Tutor");
        mBook.setAuthor("Frankie");
        mBook.setPublishTime(2010);

        Bundle mBundle = new Bundle();
        mBundle.putParcelable("1", mBook);
        mBundle.putParcelable("2", mBook);
        mBundle.putParcelable("3", mBook);
        mBundle.putParcelable("4", mBook);
        mBundle.putParcelable("5", mBook);
        i.putExtras(mBundle);

        startService(i);
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
            i.setClass(this, IntentActivity.class);
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


    /*
     * INNER static Person(若是inner class，必须是static)
     */
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
