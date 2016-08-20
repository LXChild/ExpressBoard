package com.lxchild.expressboard.show_text_board;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lxchild.expressboard.bgm.BGMActivity;
import com.lxchild.expressboard.main.R;
import com.lxchild.expressboard.timing.TimingActivity;
import com.lxchild.expressboard.widget.verticalviewpager.VerticalViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LXChild on 2015/6/1.
 */
public class TextShowActivity extends Activity {
    private final String TAG = TextShowActivity.class.getSimpleName();

    private ArrayList<TextPageEntity> itemList;

    private VerticalViewPager viewPager;
    private MediaPlayer mp;
    private int position;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(position++);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        initView();
        initData();
        if (getSharedPreferences(BGMActivity.PREFS_BGM, Context.MODE_PRIVATE)
                .getBoolean(BGMActivity.KEY_BGM, false)) {
            playMusic();
        }
    }

    private void initView() {
        viewPager = (VerticalViewPager) findViewById(R.id.vvp_board);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        itemList = bundle.getParcelableArrayList("Pages");
        viewPager.setAdapter(new TextShowPagerAdapter(this, itemList));

        position = 0;

        if (getSharedPreferences(TimingActivity.PREFS_TIMING, Context.MODE_PRIVATE)
                .getBoolean(TimingActivity.KEY_TIMING, false)) {
            timerPlay();
        }
    }

    private void timerPlay() {
        int time = getSharedPreferences(TimingActivity.PREFS_TIME, Context.MODE_PRIVATE)
                .getInt(TimingActivity.KEY_TIME, TimingActivity.TIMINGTIME_DEFAULT);
        if (time != 0) {
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (position < itemList.size()) {
                        handler.sendEmptyMessage(0);
                    } else {
                        timer.cancel();
                    }
                }
            }, 0, time * 1000);
        }
    }

    private void playMusic() {

        if (getSharedPreferences(BGMActivity.PREFS_URL, Context.MODE_PRIVATE)
                .getString(BGMActivity.KEY_URL, null) != null) {

            String url = getSharedPreferences(BGMActivity.PREFS_URL, Context.MODE_PRIVATE)
                    .getString(BGMActivity.KEY_URL, null);
            mp = new MediaPlayer();
            try {
                mp.setDataSource(url);
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (mp != null && mp.isPlaying()) {
                mp.stop();
                mp.reset();
                mp.release();
                mp = null;
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        super.onDestroy();
    }
}
