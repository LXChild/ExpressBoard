package com.lxchild.expressboard.fontsize;

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
public class FontSizeAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private int[] sizelist = new int[]{12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40};

    public FontSizeAdapter(Context cxt) {
        this.inflater = LayoutInflater.from(cxt);
    }

    @Override
    public int getCount() {
        return sizelist.length;
    }

    @Override
    public Integer getItem(int position) {
        return sizelist[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        sizeItemHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_timing_item, parent, false);
            holder = new sizeItemHolder();
            holder.content = (TextView) convertView.findViewById(R.id.tv_timing_time);

            convertView.setTag(holder);
        } else {
            holder = (sizeItemHolder) convertView.getTag();
        }
        holder.content.setTextSize(sizelist[position]);
        holder.content.setText(sizelist[position] + "sp");

        return convertView;
    }

    private class sizeItemHolder {
        TextView content;
    }
}
