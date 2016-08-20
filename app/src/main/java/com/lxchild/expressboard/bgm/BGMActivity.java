package com.lxchild.expressboard.bgm;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
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

import java.util.ArrayList;

/**
 * Created by LXChild on 2015/6/3.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BGMActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<ArrayList<MusicInfo>> {
    public static final String PREFS_URL = "BGMURL";
    public static final String KEY_URL = "URL";
    public static final String PREFS_BGM = "UseBGM";
    public static final String KEY_BGM = "isUse";
    public static final String PREFS_POS = "BGMPos";
    public static final String KEY_POS = "pos";
    private final String TAG = BGMActivity.class.getSimpleName();
    private ListView lv_bgm;
    private BGMAdapter adapter;
    private String filter;
    private LinearLayout ll_silence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        initView();
        initData();
    }

    private void initView() {
        ll_silence = new LinearLayout(this);
        ll_silence.setOrientation(LinearLayout.VERTICAL);

        String current = getSharedPreferences(BGMActivity.PREFS_POS, Context.MODE_PRIVATE)
                .getString(BGMActivity.KEY_POS, "无");
        TextView tv_head = new TextView(this);
        tv_head.setText("启用安静模式\n" + "(当前音乐：" + current + ")");
        tv_head.setTextSize(15);
        tv_head.setPadding(0, 20, 0, 20);
        tv_head.setGravity(Gravity.CENTER);

        ll_silence.addView(tv_head, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        ll_silence.setGravity(Gravity.CENTER);

        lv_bgm = (ListView) findViewById(R.id.lv_settings);
        lv_bgm.addHeaderView(ll_silence);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initData() {
        getLoaderManager().initLoader(0, null, this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bgm, menu);
        showActionBar();
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (view == ll_silence) {
            saveCurrentBGM("无");
            saveBGMStatus(false);
            Toast.makeText(this, "已启用安静模式", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            saveCurrentBGM(adapter.getItem(position - 1).getTitle());
            String url = adapter.getItem(position - 1).getUrl();
            if (url != null) {
                saveBGMURL(url);
                saveBGMStatus(true);
                Toast.makeText(this, "背景音乐设置成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void saveBGMStatus(boolean isSilence) {
        SharedPreferences settings = getSharedPreferences(PREFS_BGM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(KEY_BGM, isSilence);
        editor.apply();
    }

    private void saveCurrentBGM(String name) {
        SharedPreferences s_pos = getSharedPreferences(PREFS_POS, Context.MODE_PRIVATE);
        SharedPreferences.Editor s_pos_editor = s_pos.edit();
        s_pos_editor.putString(KEY_POS, name);
        s_pos_editor.apply();
    }

    private void saveBGMURL(String bgmurl) {
        SharedPreferences settings = this.getSharedPreferences(PREFS_URL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_URL, bgmurl);
        editor.apply();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onQueryTextChange(String s) {
        filter = !TextUtils.isEmpty(s) ? s : null;
        getLoaderManager().restartLoader(0, null, this);

        return true;
    }


    @Override
    public Loader<ArrayList<MusicInfo>> onCreateLoader(int id, Bundle args) {
        return new MusicListLoader(this, filter);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MusicInfo>> loader, ArrayList<MusicInfo> data) {
        adapter = new BGMAdapter(this);
        adapter.setData(data);
        lv_bgm.setAdapter(adapter);
        lv_bgm.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
        lv_bgm.setOnItemClickListener(this);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MusicInfo>> loader) {
        adapter.setData(null);
    }
}
