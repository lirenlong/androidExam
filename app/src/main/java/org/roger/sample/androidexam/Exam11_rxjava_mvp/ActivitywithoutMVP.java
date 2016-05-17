package org.roger.sample.androidexam.Exam11_rxjava_mvp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.roger.sample.androidexam.MyApplication;
import org.roger.sample.androidexam.R;

import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by liren on 16/3/1.
 */

public class ActivitywithoutMVP extends Activity {
    public static final String DEFAULT_NAME = "Chuck Norris";

    private ArrayAdapter<ServerAPI.Item> adapter;
    private Subscription subscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_interface);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter = new ArrayAdapter<>(this, R.layout.item));
        requestItems(DEFAULT_NAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    public void requestItems(String name) {
        unsubscribe();
        subscription = MyApplication.getServerAPI()
                .getItems(name.split("\\s+")[0], name.split("\\s+")[1])
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ServerAPI.Response>() {
                    @Override
                    public void call(ServerAPI.Response response) {
                        onItemsNext(response.items);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        onItemsError(error);
                    }
                });
    }

    public void onItemsNext(ServerAPI.Item[] items) {
        adapter.clear();
        adapter.addAll(items);
    }

    public void onItemsError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void unsubscribe() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }
}