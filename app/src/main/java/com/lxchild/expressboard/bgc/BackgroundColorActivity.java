package com.lxchild.expressboard.bgc;

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
public class BackgroundColorActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public static final String PREFS_BACKGROUNDCOLOR_CUSTOM = "backgroundColorRandom";
    public static final String KEY_BACKGROUNDCOLOR_CUSTOM = "isColorRandom";
    public static final String PREFS_BACKGROUNDCOLOR = "backgroundColor";
    public static final String KEY_BACKGROUNDCOLOR = "color";
    public static final String BACKGROUNDCOLOR_RANDOM = "随机";
    private ListView lv_backgroundcolor;
    private BackgroundColorAdapter adapter;
    private LinearLayout ll_random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        initView();
    }

    private void initView() {
        ll_random = new LinearLayout(this);
        ll_random.setOrientation(LinearLayout.VERTICAL);

        String current = getSharedPreferences(PREFS_BACKGROUNDCOLOR, Context.MODE_PRIVATE)
                .getString(KEY_BACKGROUNDCOLOR, BACKGROUNDCOLOR_RANDOM);
        TextView tv_head = new TextView(this);
        tv_head.setText("使用随机颜色\n" + "(当前颜色：" + current + ")");
        tv_head.setTextSize(15);
        tv_head.setPadding(0, 20, 0, 20);
        tv_head.setGravity(Gravity.CENTER);

        ll_random.addView(tv_head, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ll_random.setGravity(Gravity.CENTER);

        lv_backgroundcolor = (ListView) findViewById(R.id.lv_settings);
        lv_backgroundcolor.addHeaderView(ll_random);
        adapter = new BackgroundColorAdapter(this);
        lv_backgroundcolor.setAdapter(adapter);
        lv_backgroundcolor.setOnItemClickListener(this);
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


        if (view == ll_random) {
            saveBackgroundColor(BACKGROUNDCOLOR_RANDOM);
            saveBackgroundColorStatus(false);
            Toast.makeText(this, "已使用随机颜色", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        String color = adapter.getItem(position - 1);
        if (color != null) {
            saveBackgroundColor(color);
            saveBackgroundColorStatus(true);
            Toast.makeText(this, "背景颜色设置成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveBackgroundColorStatus(boolean isRandom) {
        SharedPreferences settings = getSharedPreferences(PREFS_BACKGROUNDCOLOR_CUSTOM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(KEY_BACKGROUNDCOLOR_CUSTOM, isRandom);
        editor.apply();
    }

    private void saveBackgroundColor(String color) {
        SharedPreferences settings = this.getSharedPreferences(PREFS_BACKGROUNDCOLOR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_BACKGROUNDCOLOR, color);
        editor.apply();
    }
}
