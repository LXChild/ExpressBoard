package com.lxchild.expressboard.edit_text;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxchild.expressboard.show_text_board.TextPageEntity;
import com.lxchild.expressboard.main.R;

import java.util.ArrayList;

/**
 * Created by LXChild on 2015/6/1.
 */
public class TextListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<TextPageEntity> itemList;

    public TextListAdapter(Context cxt, ArrayList<TextPageEntity> itemList) {
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
            convertView = inflater.inflate(R.layout.layout_list_text_item, parent, false);
            holder = new ItemViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.iv_list_edit_text);
            holder.content = (TextView) convertView.findViewById(R.id.tv_list_edit_text_content);
            holder.info = (TextView) convertView.findViewById(R.id.tv_list_edit_text_info);

            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        holder.img.setImageResource(R.mipmap.ic_board);
        holder.content.setText(itemList.get(position).getContentText());
        holder.info.setText("第" + (position + 1) + "张");
        return convertView;
    }

    private class ItemViewHolder {
        ImageView img;
        TextView content;
        TextView info;
    }
}
