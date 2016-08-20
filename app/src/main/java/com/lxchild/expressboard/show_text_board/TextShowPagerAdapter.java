package com.lxchild.expressboard.show_text_board;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxchild.expressboard.bgc.BackgroundColorActivity;
import com.lxchild.expressboard.fontcolor.FontColorActivity;
import com.lxchild.expressboard.fontsize.FontSizeActivity;
import com.lxchild.expressboard.widget.verticalviewpager.PagerAdapter;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by LXChild on 2015/6/1.
 */
public class TextShowPagerAdapter extends PagerAdapter {

    private final String TAG = TextShowPagerAdapter.class.getSimpleName();
    private Context cxt;
    private ArrayList<TextPageEntity> itemList;
    private Random mRandom = new Random();

    public TextShowPagerAdapter(Context cxt, ArrayList<TextPageEntity> itemList) {
        this.cxt = cxt;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList != null ? itemList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView tv = new TextView(container.getContext());
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setGravity(Gravity.CENTER);
        if (!cxt.getSharedPreferences(BackgroundColorActivity.PREFS_BACKGROUNDCOLOR_CUSTOM, Context.MODE_PRIVATE)
                .getBoolean(BackgroundColorActivity.KEY_BACKGROUNDCOLOR_CUSTOM, false)) {
            tv.setBackgroundColor(Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255)));
        } else {
            String color = cxt.getSharedPreferences(BackgroundColorActivity.PREFS_BACKGROUNDCOLOR, Context.MODE_PRIVATE)
                    .getString(BackgroundColorActivity.KEY_BACKGROUNDCOLOR, BackgroundColorActivity.BACKGROUNDCOLOR_RANDOM);
            setBackgroundColor(tv, color);
        }

        if (!cxt.getSharedPreferences(FontSizeActivity.PREFS_FONTSIZE_CUSTOM, Context.MODE_PRIVATE)
                .getBoolean(FontSizeActivity.KEY_FONTSIZE_CUSTOM, false)) {
            tv.setTextSize(FontSizeActivity.FONTSIZE_DEFAULT);
        } else {
            int size = cxt.getSharedPreferences(FontSizeActivity.PREFS_FONTSIZE, Context.MODE_PRIVATE)
                    .getInt(FontSizeActivity.KEY_FONTSIZE, FontSizeActivity.FONTSIZE_DEFAULT);
            tv.setTextSize(size);
        }

        if (!cxt.getSharedPreferences(FontColorActivity.PREFS_FONTCOLOR_CUSTOM, Context.MODE_PRIVATE)
                .getBoolean(FontColorActivity.KEY_FONTCOLOR_CUSTOM, false)) {
            tv.setTextColor(Color.WHITE);
        } else {
            String color = cxt.getSharedPreferences(FontColorActivity.PREFS_FONTCOLOR, Context.MODE_PRIVATE)
                    .getString(FontColorActivity.KEY_FONTCOLOR, FontColorActivity.FONTCOLOR_DEFAULT);
            setTextColor(tv, color);
        }
        tv.setText(itemList.get(position).getContentText());
        container.addView(tv);

        return tv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem:" + position);
        container.removeView((View) object);
    }

    private void setTextColor(TextView tv, String color) {
        switch (color) {
            case "黑色":
                tv.setTextColor(Color.BLACK);
                break;
            case "灰色":
                tv.setTextColor(Color.GRAY);
                break;
            case "白色":
                tv.setTextColor(Color.WHITE);
                break;
            case "绿色":
                tv.setTextColor(Color.GREEN);
                break;
            case "红色":
                tv.setTextColor(Color.RED);
                break;
            default:
                tv.setTextColor(Color.WHITE);
                break;
        }
    }

    private void setBackgroundColor(TextView tv, String color) {
        switch (color) {
            case "黑色":
                tv.setBackgroundColor(Color.BLACK);
                break;
            case "灰色":
                tv.setBackgroundColor(Color.GRAY);
                break;
            case "白色":
                tv.setBackgroundColor(Color.WHITE);
                break;
            case "绿色":
                tv.setBackgroundColor(Color.GREEN);
                break;
            case "红色":
                tv.setBackgroundColor(Color.RED);
                break;
            default:
                tv.setBackgroundColor(Color.WHITE);
                break;
        }
    }
}
