package org.roger.sample.androidexam.Exam6_DataBinding.viewwithID;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import org.roger.sample.androidexam.R;
import org.roger.sample.androidexam.databinding.E6ActivityViewWithIdBinding;


public class ViewWithIDActivity extends Activity {
    E6ActivityViewWithIdBinding db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this, R.layout.e6_activity_view_with_id);
    }

    public void onBtnClick(View view) {
        db.firstname.setText("hao");
    }
}
