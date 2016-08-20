package com.lxchild.expressboard.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxchild.expressboard.bgc.BackgroundColorActivity;
import com.lxchild.expressboard.bgm.BGMActivity;
import com.lxchild.expressboard.fontcolor.FontColorActivity;
import com.lxchild.expressboard.fontsize.FontSizeActivity;
import com.lxchild.expressboard.main.R;
import com.lxchild.expressboard.paintsize.PaintSizeActivity;
import com.lxchild.expressboard.timing.TimingActivity;

/**
 * Created by LXChild on 2015/6/3.
 */
public class SettingsAdapter extends BaseAdapter {

    private Context cxt;
    private LayoutInflater inflater;
    private String[] settings;


    public SettingsAdapter(Context cxt) {
        this.cxt = cxt;
        this.inflater = LayoutInflater.from(cxt);
        this.settings = cxt.getResources().getStringArray(R.array.settings);
    }

    @Override
    public int getCount() {
        return settings.length;
    }

    @Override
    public Object getItem(int position) {
        return settings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_settings_item, parent, false);
            holder = new ItemViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_settingsitem_title);
            holder.status = (TextView) convertView.findViewById(R.id.tv_settingsitem_status);

            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        holder.title.setText(settings[position]);
        String status = null;
        switch (position) {
            case 0:
                status = cxt.getSharedPreferences(TimingActivity.PREFS_TIMING, Context.MODE_PRIVATE)
                        .getBoolean(TimingActivity.KEY_TIMING, false) ? "On" : "Off";
                break;
            case 1:
                status = cxt.getSharedPreferences(FontSizeActivity.PREFS_FONTSIZE_CUSTOM, Context.MODE_PRIVATE)
                        .getBoolean(FontSizeActivity.KEY_FONTSIZE_CUSTOM, false) ? "On" : "Off";
                break;
            case 2:
                status = cxt.getSharedPreferences(PaintSizeActivity.PREFS_PAINTSIZE_CUSTOM, Context.MODE_PRIVATE)
                        .getBoolean(PaintSizeActivity.KEY_PAINTSIZE_CUSTOM, false) ? "On" : "Off";
                break;
            case 3:
                status = cxt.getSharedPreferences(FontColorActivity.PREFS_FONTCOLOR_CUSTOM, Context.MODE_PRIVATE)
                        .getBoolean(FontColorActivity.KEY_FONTCOLOR_CUSTOM, false) ? "On" : "Off";
                break;
            case 4:
                status = cxt.getSharedPreferences(BackgroundColorActivity.PREFS_BACKGROUNDCOLOR_CUSTOM, Context.MODE_PRIVATE)
                        .getBoolean(BackgroundColorActivity.KEY_BACKGROUNDCOLOR_CUSTOM, false) ? "On" : "Off";
                break;
            case 5:
                status = cxt.getSharedPreferences(BGMActivity.PREFS_BGM, Context.MODE_PRIVATE)
                        .getBoolean(BGMActivity.KEY_BGM, false) ? "On" : "Off";
                break;
            default:
                break;
        }
        holder.status.setText(status);

        return convertView;
    }

    private class ItemViewHolder {
        TextView title;
        TextView status;
    }
}
