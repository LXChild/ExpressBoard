package com.lxchild.expressboard.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by LXChi on 2015/8/10.
 */
public class MyUtil {

    public static Bitmap getBitmap(String resName) {
        try {
            return BitmapFactory.decodeFile(resName);
        } catch (Exception e) {
            return null;
        }
    }
}
