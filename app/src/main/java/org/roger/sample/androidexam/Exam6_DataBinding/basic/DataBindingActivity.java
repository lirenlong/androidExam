package org.roger.sample.androidexam.Exam6_DataBinding.basic;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import org.roger.sample.androidexam.R;
import org.roger.sample.androidexam.databinding.E6ActivityDataBindingMainBinding;

public class DataBindingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        E6ActivityDataBindingMainBinding db = DataBindingUtil.setContentView(this, R.layout.e6_activity_data_binding_main);
        User user = new User("roger","lee");
        db.setUser(user);
    }
}
