package org.roger.sample.androidexam.Exam6_DataBinding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.roger.sample.androidexam.R;
import org.roger.sample.androidexam.databinding.E6ActivityDataBindingMainBinding;

public class DataBindingActivity extends Activity {

    ObservableUser user = new ObservableUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        E6ActivityDataBindingMainBinding db = DataBindingUtil.setContentView(this, R.layout.e6_activity_data_binding_main);
//        User user = new User("roger","lee");
        user.setLastName("lee");
        user.setFirstName("roger");
        db.setUser(user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_binding_main, menu);
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

    public void onBtnClick1(View view) {
        user.setFirstName("big");
        user.setLastName("girl");
    }
}
