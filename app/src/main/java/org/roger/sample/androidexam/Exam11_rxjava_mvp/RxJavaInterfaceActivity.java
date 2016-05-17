package org.roger.sample.androidexam.Exam11_rxjava_mvp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.roger.sample.androidexam.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class RxJavaInterfaceActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_interface);

        myObservable.subscribe(mySubscriber);
    }

    Observable<String> myObservable = Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> sub) {
                    sub.onNext("Hello, world!");
                    sub.onCompleted();
                }
            }
    );

    Subscriber<String> mySubscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) { System.out.println(s); }

        @Override
        public void onCompleted() { }

        @Override
        public void onError(Throwable e) { }
    };


    public static void hello(String... names) {
        Observable.from(names).subscribe(new Action1<String>(){
            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }
        });
    }
}
