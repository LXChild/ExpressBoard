package com.lxchild.expressboard.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LXChild on 2015/6/3.
 */
public class DrawerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<DrawerItemEntity> itemList;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawerAdapter(Context cxt) {
        this.inflater = LayoutInflater.from(cxt);

        itemList = new ArrayList<>();
        String[] texts = cxt.getResources().getStringArray(R.array.drawerlist);
        for (int i = 0; i < texts.length; i++) {
            DrawerItemEntity item = new DrawerItemEntity();
            item.setImg(i);
            item.setTitle(texts[i]);
            itemList.add(item);
        }
    }

    @Override
    public int getCount() {
        return itemList.size();
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
            convertView = inflater.inflate(R.layout.layout_drawer_item, parent, false);
            holder = new ItemViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.iv_drawer_img);
            holder.txt = (TextView) convertView.findViewById(R.id.tv_drawer_txt);

            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        holder.img.setImageResource(itemList.get(position).getImg());
        holder.txt.setText(itemList.get(position).getTitle());
        return convertView;
    }

    private class ItemViewHolder {
        ImageView img;
        TextView txt;
    }
}
