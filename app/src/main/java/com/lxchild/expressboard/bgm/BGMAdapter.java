package com.lxchild.expressboard.bgm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxchild.expressboard.main.R;

import java.util.ArrayList;

/**
 * Created by LXChild on 2015/6/3.
 */
public class BGMAdapter extends BaseAdapter {

    private Context cxt;
    private LayoutInflater inflater;
    private ArrayList<MusicInfo> musicInfos;

    public BGMAdapter(Context cxt) {
        this.cxt = cxt;
        this.inflater = LayoutInflater.from(cxt);
    }

    public void setData(ArrayList<MusicInfo> data) {
        this.musicInfos = data;
    }

    @Override
    public int getCount() {
        return musicInfos != null ? musicInfos.size() : 0;
    }

    @Override
    public MusicInfo getItem(int position) {
        return musicInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        musicInfoHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_bgm_item, parent, false);
            holder = new musicInfoHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.iv_bgm);
            holder.content = (TextView) convertView.findViewById(R.id.tv_bgmname);
            holder.duration = (TextView) convertView.findViewById(R.id.tv_bgmduration);

            convertView.setTag(holder);
        } else {
            holder = (musicInfoHolder) convertView.getTag();
        }

        holder.img.setImageResource(R.mipmap.ic_sound);
        holder.content.setText(musicInfos.get(position).getTitle());
        int duration = musicInfos.get(position).getDuration() / 1000;

        int m = (duration % 3600) / 60;
        int s = duration % 60;
        if (s < 10) {
            holder.duration.setText(m + ":0" + s);
        } else {
            holder.duration.setText(m + ":" + s);
        }

        return convertView;
    }

    private class musicInfoHolder {
        ImageView img;
        TextView content;
        TextView duration;
    }
}
