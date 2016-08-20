package com.lxchild.expressboard.edit_draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxchild.expressboard.main.R;

import java.util.ArrayList;

/**
 * Created by LXChi on 2015/8/10.
 */
public class DrawListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Bitmap> itemList;

    public DrawListAdapter(Context cxt, ArrayList<Bitmap> itemList) {
        this.itemList = itemList;
        this.inflater = LayoutInflater.from(cxt);
    }

    @Override
    public int getCount() {
        return itemList != null ? itemList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_draw_item, parent, false);
            holder = new ItemViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.iv_list_edit_draw);
        //    holder.info = (TextView) convertView.findViewById(R.id.tv_list_edit_draw_info);

            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        holder.img.setImageBitmap(itemList.get(position));
      //  holder.info.setText("第" + (position + 1) + "张");
        return convertView;
    }

    private class ItemViewHolder {
        ImageView img;
//        TextView info;
    }
}
