package com.lxchild.expressboard.timing;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lxchild.expressboard.main.R;

/**
 * Created by LXChild on 2015/6/6.
 */
public class TimingActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public static final String PREFS_TIMING = "TimingStatus";
    public static final String KEY_TIMING = "isOpen";
    public static final String PREFS_TIME = "TimingTime";
    public static final String KEY_TIME = "time";
    public static final int TIMINGTIME_DEFAULT = 0;
    private ListView lv_timing;
    private TimingAdapter adapter;
    private LinearLayout ll_manual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        initView();
    }

    private void initView() {
        ll_manual = new LinearLayout(this);
        ll_manual.setOrientation(LinearLayout.VERTICAL);

        int current = getSharedPreferences(PREFS_TIME, Context.MODE_PRIVATE)
                .getInt(KEY_TIME, TIMINGTIME_DEFAULT);
        TextView tv_head = new TextView(this);
        tv_head.setText("启用手动模式\n" + "(当前定时：" + current + "s)");
        tv_head.setTextSize(15);
        tv_head.setPadding(0, 20, 0, 20);
        tv_head.setGravity(Gravity.CENTER);

        ll_manual.addView(tv_head, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ll_manual.setGravity(Gravity.CENTER);

        lv_timing = (ListView) findViewById(R.id.lv_settings);
        lv_timing.addHeaderView(ll_manual);
        adapter = new TimingAdapter(this);
        lv_timing.setAdapter(adapter);
        lv_timing.setOnItemClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        showActionBar();
        return true;
    }

    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (view == ll_manual) {
            saveTimingTime(TIMINGTIME_DEFAULT);
            saveTimingTimeStatus(false);
            Toast.makeText(this, "已启用手动模式", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        int time = adapter.getItem(position - 1);
        if (time != 0) {
            saveTimingTime(time);
            saveTimingTimeStatus(true);
            Toast.makeText(this, "定时播放设置成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveTimingTimeStatus(boolean isTiming) {
        SharedPreferences settings = getSharedPreferences(PREFS_TIMING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(KEY_TIMING, isTiming);
        editor.apply();
    }

    private void saveTimingTime(int time) {
        SharedPreferences settings = this.getSharedPreferences(PREFS_TIME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_TIME, time);
        editor.apply();
    }
}
