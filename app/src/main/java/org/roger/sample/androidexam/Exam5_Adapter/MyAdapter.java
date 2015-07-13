package org.roger.sample.androidexam.Exam5_Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.roger.sample.androidexam.R;

import java.util.List;
import java.util.Map;

/**
 * Created by liren on 15/7/8.
 */

// ViewHolder静态类
class ViewHolder {
    public ImageView img;
    public TextView title;
    public TextView content;
}
public class MyAdapter extends BaseAdapter{
    public static final String TAG = "MyAdapter";
    List<Map<String,Object>> m_data = null;
    Context m_context = null;
    public MyAdapter(List<Map<String,Object>> data , Context context) {
        m_data = data;
        m_context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView position = " + position);
        ViewHolder holder = null;
        // 如果缓存convertView为空，则需要创建View
        if (convertView == null) {
            holder = new ViewHolder();
            // 根据自定义的Item布局加载布局
            convertView = LayoutInflater.from(m_context).inflate(R.layout.e5_list_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img.setBackgroundResource((Integer) m_data.get(position).get(
                "img"));
        holder.title.setText((String) m_data.get(position).get("title"));
        holder.content.setText((String) m_data.get(position).get("content"));

        return convertView;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount called");
        return m_data.size();
    }

    // 没被调用过
    @Override
    public Object getItem(int position) {
        Log.d(TAG, "getItem position = " + position);
        return m_data.get(position);
    }

    // 没被调用过
    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId position = " + position);
        return position;
    }
}
