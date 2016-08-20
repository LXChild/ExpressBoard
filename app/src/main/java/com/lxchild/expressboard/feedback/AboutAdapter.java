package com.lxchild.expressboard.feedback;

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
public class AboutAdapter extends BaseAdapter {

    String[] title = new String[]{"作者", "QQ", "微信", "微博", "备注"};
    String[] content = new String[]{"LXChild", "1092982300", "LXChild_1092982300", "LXChild", "©版权所有，侵权必究"};
    private LayoutInflater inflater;

    public AboutAdapter(Context cxt) {
        this.inflater = LayoutInflater.from(cxt);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public String getItem(int position) {
        return title[position] + ":" + content[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        aboutItemHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_settings_item, parent, false);
            holder = new aboutItemHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_settingsitem_title);
            holder.status = (TextView) convertView.findViewById(R.id.tv_settingsitem_status);

            convertView.setTag(holder);
        } else {
            holder = (aboutItemHolder) convertView.getTag();
        }
        holder.title.setText(title[position]);
        holder.status.setText(content[position]);

        return convertView;
    }

    private class aboutItemHolder {
        TextView title;
        TextView status;
    }
}
