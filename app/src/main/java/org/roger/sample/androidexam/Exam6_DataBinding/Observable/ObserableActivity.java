package org.roger.sample.androidexam.Exam6_DataBinding.Observable;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayMap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.roger.sample.androidexam.R;
import org.roger.sample.androidexam.databinding.E6ActivityObservableBinding;

public class ObserableActivity extends ActionBarActivity {

    private ObservableUser ou = null;
    private PlainUser pu = null;
    private ObservableArrayMap<String, Object> userMap = new ObservableArrayMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        E6ActivityObservableBinding oab = DataBindingUtil.setContentView(this, R.layout.e6_activity_observable);
        ou = new ObservableUser();
        oab.setObservableUser(ou);

        pu = new PlainUser();
        oab.setPlainUser(pu);

        oab.setMapUser(userMap);

        onBtnClick(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_observable, menu);
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

    public void onBtnClick(View view) {
        ou.setFirstName("roger");
        ou.setLastName("lee");

        pu.firstName.set("jason");
        pu.lastName.set("wood");
        pu.grade.set(6);

        userMap.put("firstName", "xiaoming");
        userMap.put("lastName", "qu");
        userMap.put("grade",3);
    }
    public void onBtnClick1(View view) {
        ou.setFirstName("ren");
        ou.setLastName("li");

        pu.firstName.set("jack");
        pu.lastName.set("jones");
        pu.grade.set(30);

        userMap.put("firstName","zhixu");
        userMap.put("lastName","ma");
        userMap.put("grade",1);
    }
}
