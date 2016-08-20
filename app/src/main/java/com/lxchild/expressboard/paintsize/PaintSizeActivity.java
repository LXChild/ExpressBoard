package com.lxchild.expressboard.paintsize;

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
 * Created by LXChi on 2015/8/11.
 */
public class PaintSizeActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public static final String PREFS_PAINTSIZE_CUSTOM = "paintSizeDefault";
    public static final String KEY_PAINTSIZE_CUSTOM = "isSizeDefault";
    public static final String PREFS_PAINTSIZE = "paintSize";
    public static final String KEY_PAINTSIZE = "size";
    public static final int PAINTSIZE_DEFAULT = 6;

    private ListView lv_paintsize;
    private PaintSizeAdapter adapter;
    private LinearLayout ll_default;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        
        initView();
    }

    private void initView() {
        ll_default = new LinearLayout(this);
        ll_default.setOrientation(LinearLayout.VERTICAL);

        int current = getSharedPreferences(PREFS_PAINTSIZE, Context.MODE_PRIVATE)
                .getInt(KEY_PAINTSIZE, PAINTSIZE_DEFAULT);
        TextView tv_head = new TextView(this);
        tv_head.setText("使用默认大小\n" + "(当前大小：" + current + "px)");
        tv_head.setTextSize(15);
        tv_head.setPadding(0, 20, 0, 20);
        tv_head.setGravity(Gravity.CENTER);

        ll_default.addView(tv_head, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ll_default.setGravity(Gravity.CENTER);

        lv_paintsize = (ListView) findViewById(R.id.lv_settings);
        lv_paintsize.addHeaderView(ll_default);
        adapter = new PaintSizeAdapter(this);
        lv_paintsize.setAdapter(adapter);
        lv_paintsize.setOnItemClickListener(this);
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

        if (view == ll_default) {
            savePaintSize(PAINTSIZE_DEFAULT);
            savePaintSizeStatus(false);
            Toast.makeText(this, "已使用默认大小", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        int size = adapter.getItem(position - 1);
        if (size > -1) {
            savePaintSize(size);
            savePaintSizeStatus(true);
            Toast.makeText(this, "笔刷大小设置成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void savePaintSizeStatus(boolean isCustom) {
        SharedPreferences settings = getSharedPreferences(PREFS_PAINTSIZE_CUSTOM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(KEY_PAINTSIZE_CUSTOM, isCustom);
        editor.apply();
    }

    private void savePaintSize(int size) {
        SharedPreferences settings = getSharedPreferences(PREFS_PAINTSIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_PAINTSIZE, size);
        editor.apply();
    }
}
