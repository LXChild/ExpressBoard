package com.lxchild.expressboard.timing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxchild.expressboard.main.R;

/**
 * Created by LXChild on 2015/6/6.
 */
public class TimingAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int[] timelist = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    public TimingAdapter(Context cxt) {
        this.inflater = LayoutInflater.from(cxt);
    }

    @Override
    public int getCount() {
        return timelist.length;
    }

    @Override
    public Integer getItem(int position) {
        return timelist[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        timingItemHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_timing_item, parent, false);
            holder = new timingItemHolder();
            holder.content = (TextView) convertView.findViewById(R.id.tv_timing_time);

            convertView.setTag(holder);
        } else {
            holder = (timingItemHolder) convertView.getTag();
        }
        holder.content.setText(timelist[position] + "s");

        return convertView;
    }

    private class timingItemHolder {
        TextView content;
    }
}
