package com.lxchild.expressboard.backup;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.lxchild.expressboard.widget.verticalviewpager.PagerAdapter;

import java.util.Random;

public class BoardPagerAdapterDefault extends PagerAdapter {

    private static final String TAG = "PagerAdapter";
    private Random mRandom = new Random();

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem:" + position);
        TextView tv = new TextView(container.getContext());
        tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(30);
        tv.setBackgroundColor(Color.rgb(mRandom.nextInt(255),
                mRandom.nextInt(255), mRandom.nextInt(255)));
        tv.setTextColor(Color.WHITE);
        tv.setText("Pager: " + position);
        container.addView(tv);

        return tv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem:" + position);
        container.removeView((View) object);
    }

}
