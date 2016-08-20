package com.lxchild.expressboard.drawingboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.lxchild.expressboard.bgc.BackgroundColorActivity;
import com.lxchild.expressboard.fontcolor.FontColorActivity;
import com.lxchild.expressboard.fontsize.FontSizeActivity;
import com.lxchild.expressboard.paintsize.PaintSizeActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by LXChi on 2015/8/8.
 */
public class DrawingBoard extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final String mScreenshotPath = Environment.getExternalStorageDirectory() + "/ExpressBoard";
    private Context cxt;

    private Random mRandom = new Random();
    private int color;

    private Paint paint = new Paint();
    private Path path = new Path();

    private Canvas cvs_sfc = null;


    public DrawingBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cxt = context;

        getHolder().addCallback(this);

        initPaint();
        color =Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));

        setOnTouchListener(this);
    }

    private void initPaint() {

        if (!cxt.getSharedPreferences(FontColorActivity.PREFS_FONTCOLOR_CUSTOM, Context.MODE_PRIVATE)
                .getBoolean(FontColorActivity.KEY_FONTCOLOR_CUSTOM, false)) {
            paint.setColor(Color.WHITE);
        } else {
            String color = cxt.getSharedPreferences(FontColorActivity.PREFS_FONTCOLOR, Context.MODE_PRIVATE)
                    .getString(FontColorActivity.KEY_FONTCOLOR, FontColorActivity.FONTCOLOR_DEFAULT);
            setPaintColor(paint, color);
        }


        if (!cxt.getSharedPreferences(PaintSizeActivity.PREFS_PAINTSIZE_CUSTOM, Context.MODE_PRIVATE)
                .getBoolean(PaintSizeActivity.KEY_PAINTSIZE_CUSTOM, false)) {
            paint.setStrokeWidth(PaintSizeActivity.PAINTSIZE_DEFAULT);
        } else {
            int size = cxt.getSharedPreferences(PaintSizeActivity.PREFS_PAINTSIZE, Context.MODE_PRIVATE)
                    .getInt(PaintSizeActivity.KEY_PAINTSIZE, PaintSizeActivity.PAINTSIZE_DEFAULT);
            paint.setStrokeWidth(size);
        }

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void doDraw(Canvas cvs) {
    //    Canvas cvs = getHolder().lockCanvas();

        if (!cxt.getSharedPreferences(BackgroundColorActivity.PREFS_BACKGROUNDCOLOR_CUSTOM, Context.MODE_PRIVATE)
                .getBoolean(BackgroundColorActivity.KEY_BACKGROUNDCOLOR_CUSTOM, false)) {
            cvs.drawColor(color);
        } else {
            String color = cxt.getSharedPreferences(BackgroundColorActivity.PREFS_BACKGROUNDCOLOR, Context.MODE_PRIVATE)
                    .getString(BackgroundColorActivity.KEY_BACKGROUNDCOLOR, BackgroundColorActivity.BACKGROUNDCOLOR_RANDOM);
            setCanvasColor(cvs, color);
        }

        cvs.drawPath(path, paint);

       // getHolder().unlockCanvasAndPost(cvs);
    }

    public void clear() {
        path.reset();
        cvs_sfc = getHolder().lockCanvas();
        doDraw(cvs_sfc);
        getHolder().unlockCanvasAndPost(cvs_sfc);
    }

    /**
     * If called, creates a screenshot and saves it as a JPG in the folder "droidnova" on the sdcard.
     */
    public String saveScreenshot() {
        if (ensureSDCardAccess()) {
            Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas cvs = new Canvas(bmp);
            doDraw(cvs);
            File file = new File(mScreenshotPath + "/" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                return file.getPath();
            } catch (FileNotFoundException e) {
                Log.e("Panel", "FileNotFoundException", e);
            } catch (IOException e) {
                Log.e("Panel", "IOEception", e);
            }
        }
        return null;
    }

    /**
     * Helper method to ensure that the given path exists.
     * TODO: check external storage state
     */
    private boolean ensureSDCardAccess() {
        File file = new File(mScreenshotPath);
        if (file.exists()) {
            return true;
        } else if (file.mkdirs()) {
            return true;
        }
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        cvs_sfc = getHolder().lockCanvas();
        doDraw(cvs_sfc);
        getHolder().unlockCanvasAndPost(cvs_sfc);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cvs_sfc = getHolder().lockCanvas();
                path.moveTo(event.getX(), event.getY());
                doDraw(cvs_sfc);
                getHolder().unlockCanvasAndPost(cvs_sfc);
                break;
            case MotionEvent.ACTION_MOVE:
                cvs_sfc = getHolder().lockCanvas();
                path.lineTo(event.getX(), event.getY());
                doDraw(cvs_sfc);
                getHolder().unlockCanvasAndPost(cvs_sfc);
                break;
        }
        return true;
    }

    private void setPaintColor(Paint p, String color) {
        switch (color) {
            case "黑色":
                p.setColor(Color.BLACK);
                break;
            case "灰色":
                p.setColor(Color.GRAY);
                break;
            case "白色":
                p.setColor(Color.WHITE);
                break;
            case "绿色":
                p.setColor(Color.GREEN);
                break;
            case "红色":
                p.setColor(Color.RED);
                break;
            default:
                p.setColor(Color.WHITE);
                break;
        }
    }

    private void setCanvasColor(Canvas cvs, String color) {
        switch (color) {
            case "黑色":
                cvs.drawColor(Color.BLACK);
                break;
            case "灰色":
                cvs.drawColor(Color.GRAY);
                break;
            case "白色":
                cvs.drawColor(Color.WHITE);
                break;
            case "绿色":
                cvs.drawColor(Color.GREEN);
                break;
            case "红色":
                cvs.drawColor(Color.RED);
                break;
            default:
                cvs.drawColor(Color.WHITE);
                break;
        }
    }
}
