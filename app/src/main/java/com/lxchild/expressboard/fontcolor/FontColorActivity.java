package com.lxchild.expressboard.fontcolor;

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
public class FontColorActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public static final String PREFS_FONTCOLOR_CUSTOM = "fontColorDefault";
    public static final String KEY_FONTCOLOR_CUSTOM = "isColorDefault";
    public static final String PREFS_FONTCOLOR = "fontColor";
    public static final String KEY_FONTCOLOR = "color";
    public static final String FONTCOLOR_DEFAULT = "白色";
    private ListView lv_fontcolor;
    private FontColorAdapter adapter;
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

        String current = getSharedPreferences(PREFS_FONTCOLOR, Context.MODE_PRIVATE)
                .getString(KEY_FONTCOLOR, FONTCOLOR_DEFAULT);
        TextView tv_head = new TextView(this);
        tv_head.setText("使用默认颜色\n" + "(当前颜色：" + current + ")");
        tv_head.setTextSize(15);
        tv_head.setPadding(0, 20, 0, 20);
        tv_head.setGravity(Gravity.CENTER);

        ll_default.addView(tv_head, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ll_default.setGravity(Gravity.CENTER);

        lv_fontcolor = (ListView) findViewById(R.id.lv_settings);
        lv_fontcolor.addHeaderView(ll_default);
        adapter = new FontColorAdapter(this);
        lv_fontcolor.setAdapter(adapter);
        lv_fontcolor.setOnItemClickListener(this);
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
            saveFontColor(FONTCOLOR_DEFAULT);
            saveFontColorStatus(false);
            Toast.makeText(this, "已使用默认颜色", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        String color = adapter.getItem(position - 1);
        if (color != null) {
            saveFontColor(color);
            saveFontColorStatus(true);
            Toast.makeText(this, "字体颜色设置成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveFontColorStatus(boolean isCustom) {
        SharedPreferences settings = getSharedPreferences(PREFS_FONTCOLOR_CUSTOM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(KEY_FONTCOLOR_CUSTOM, isCustom);
        editor.apply();
    }

    private void saveFontColor(String color) {
        SharedPreferences settings = this.getSharedPreferences(PREFS_FONTCOLOR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_FONTCOLOR, color);
        editor.apply();
    }
}
