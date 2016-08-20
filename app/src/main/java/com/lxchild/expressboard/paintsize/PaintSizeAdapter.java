package com.lxchild.expressboard.paintsize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxchild.expressboard.main.R;

/**
 * Created by LXChi on 2015/8/11.
 */
public class PaintSizeAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private int[] sizelist = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 20, 24, 30};

    public PaintSizeAdapter(Context cxt) {
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
        holder.content.setText(sizelist[position] + "px");

        return convertView;
    }

    private class sizeItemHolder {
        TextView content;
    }
}
