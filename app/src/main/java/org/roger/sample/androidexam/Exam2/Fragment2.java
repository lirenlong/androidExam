package org.roger.sample.androidexam.Exam2;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.progressfragment.ProgressFragment;

import org.roger.sample.androidexam.R;

public class Fragment2 extends ProgressFragment {
    private View mContentView;
    private Handler mHandler;
    @NonNull
    private Runnable mShowContentRunnable = new Runnable() {

        @Override
        public void run() {
            setContentShown(true);
        }

    };

    @NonNull
    public static Fragment2 newInstance() {
        Fragment2 fragment = new Fragment2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.e2_progress_content, null);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Setup content view
        setContentView(mContentView);
        // Setup text for empty content
        setEmptyText(R.string.empty);
        obtainData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(mShowContentRunnable);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.e2_menu_activity2, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                obtainData();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public void obtainData() {
        // Show indeterminate progress
        setContentShown(false);

        mHandler = new Handler();
        mHandler.postDelayed(mShowContentRunnable, 3000);
    }
}
