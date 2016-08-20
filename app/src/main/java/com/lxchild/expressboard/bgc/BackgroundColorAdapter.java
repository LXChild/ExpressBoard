package com.lxchild.expressboard.bgc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxchild.expressboard.main.R;

/**
 * Created by LXChild on 2015/6/6.
 */
public class BackgroundColorAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private String[] colors = new String[]{"白色", "灰色", "黑色", "绿色", "红色"};

    public BackgroundColorAdapter(Context cxt) {
        this.inflater = LayoutInflater.from(cxt);
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public String getItem(int position) {
        return colors[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        colorItemHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_timing_item, parent, false);
            holder = new colorItemHolder();
            holder.content = (TextView) convertView.findViewById(R.id.tv_timing_time);

            convertView.setTag(holder);
        } else {
            holder = (colorItemHolder) convertView.getTag();
        }
        setTextColor(holder.content, position);
        holder.content.setText(colors[position]);

        return convertView;
    }

    private void setTextColor(TextView tv, int position) {
        switch (position) {
            case 0:
                tv.setTextColor(Color.WHITE);
                break;
            case 1:
                tv.setTextColor(Color.GRAY);
                break;
            case 2:
                tv.setTextColor(Color.BLACK);
                break;
            case 3:
                tv.setTextColor(Color.GREEN);
                break;
            case 4:
                tv.setTextColor(Color.RED);
                break;
            default:
                tv.setTextColor(Color.WHITE);
                break;
        }
    }

    private class colorItemHolder {
        TextView content;
    }
}
