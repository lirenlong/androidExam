package org.roger.sample.androidexam.Exam5_Adapter;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.roger.sample.androidexam.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterLearnActivity extends Activity {

    private List<Map<String, Object>> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e5_activity_adapter_learn);

        data = getListViewData();
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(new MyAdapter(data, this));
    }

    private List<Map<String, Object>> getListViewData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map;
        for (int i = 0; i < 10; i++) {
            map = new HashMap<String, Object>();
            map.put("img", R.drawable.kgr);
            map.put("title", "Coder");
            map.put("content", "简单Coding，快乐生活~~~~");
            list.add(map);
        }
        return list;

    }

}
