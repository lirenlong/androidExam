package org.roger.sample.androidexam.Exam7_Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.roger.sample.androidexam.MyApplication;
import org.roger.sample.androidexam.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class LocalActivity extends ActionBarActivity {

    @Nullable
    private ServiceConnection sc = null;
    public static final String TAG = "EXAM7";
    private boolean isBind = false;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "LocalActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        initialServiceConnection();

        context = this;

        MyApplication.addActivity(this);
    }

    private void initialServiceConnection() {
        // bind a local service
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected");
                LocalService.SimpleBinder sBinder = ((LocalService.SimpleBinder) service);
                Log.i(TAG, "3 + 2 = " + sBinder.add(3, 2));
                Log.i(TAG, sBinder.getService().toString());

                // TEST_用Service传递大数据 测试通过service获取，service上的bitmap。
//                Bitmap bm = sBinder.getService().getDefaultBitmap();
//                if (bm != null) {
//                    ((ImageView) findViewById(R.id.imageview)).setImageBitmap(bm);
//                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected");
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_local, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "LocalActivity onDestory.");

        MyApplication.removeActivity(this);

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
    public void onBtnClick(@NonNull View obj) {
        Intent i = new Intent();

        switch (obj.getId()) {
            case R.id.startService:
                doStartService(i);
                break;
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
            case R.id.SendBroadCast:
                doSendBroadcast(i);
                break;
            case R.id.StartRemoteService:
                doStartRemoteService(i);
                break;
            case R.id.crashIt:
                throw new NoSuchFieldError();
            case R.id.exitTest:
                System.exit(2);
                break;
            case R.id.crashActivity:
                Log.i(TAG, "Local Activity TaskID = " + this.getTaskId());
                Intent iTmp = new Intent(this, CrashExitActivity.class);
                startActivity(iTmp);
                break;
            case R.id.alarmTest:
                Log.i(TAG, "Local Activity TaskID = " + this.getTaskId());

                Intent intent = new Intent(this, CrashExitActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.myApplication.getBaseContext(), 0, intent, intent.getFlags());
                AlarmManager mgr = (AlarmManager) MyApplication.myApplication.getBaseContext().getSystemService(Context.ALARM_SERVICE);

                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 5000, pendingIntent);
                break;
        }
    }

    private void doStartRemoteService(Intent i) {
        i.setClass(this, ProcessService.class);
        startService(i);
    }

    private void doSendBroadcast(@NonNull Intent i) {
        String path = Environment.getExternalStorageDirectory() + "/test_small.jpg";
        //test_big.jpg 1.2M
        //test_medium.jpg 640k
        //test_small.jpg 11k

        Bitmap bitmap = getLocalBitmap(path);
        i.putExtra("bitmap", bitmap);
        i.putExtra("who", 1);
        i.putExtra("msg", "hello LocalService from LocalAcitivty via notifaction.");

        i.setAction("com.roger.broadcastreceiver");
//        sendBroadcast(i);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "broadcastreceiver, send broadcast in thread = " + Thread.currentThread().getName());
                LocalBroadcastManager.getInstance(context).sendBroadcastSync(new Intent("com.roger.broadcastreceiver"));
            }
        }).start();

    }

    private void doStartService(@NonNull Intent i) {
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

        Log.i(TAG, "LocalActivity's thread name : " + Thread.currentThread().getName());//main
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

    private void doIntentBig(@NonNull Intent i) {
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

    private void doIntentSerializeal(@NonNull Intent i) {
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


    public static Bitmap getLocalBitmap(@NonNull String url) {
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

    public static FileInputStream getLocalFile(@NonNull String url) {
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
