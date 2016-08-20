package com.lxchild.expressboard.show_draw_board;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lxchild.expressboard.widget.verticalviewpager.PagerAdapter;

import java.util.ArrayList;

/**
 * Created by LXChi on 2015/8/10.
 */
public class DrawShowPagerAdapter extends PagerAdapter {

    private static final String TAG = DrawShowPagerAdapter.class.getSimpleName();

    private ArrayList<Bitmap> itemList;

    public DrawShowPagerAdapter(ArrayList<Bitmap> itemList) {
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
        ImageView iv = new ImageView(container.getContext());
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setImageBitmap(itemList.get(position));
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem:" + position);
        container.removeView((View) object);
    }
}
