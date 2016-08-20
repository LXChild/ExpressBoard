package com.lxchild.expressboard.bgm;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by LXChild on 2015/5/3.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MusicListLoader extends AsyncTaskLoader<ArrayList<MusicInfo>> {

    private final String TAG = MusicListLoader.class.getSimpleName();
    private Context cxt;
    private ProgressDialog progressDialog;

    private ArrayList<MusicInfo> musicInfos;
    private ArrayList<MusicInfo> musicInfos_loaded;

    private String filter;

    public MusicListLoader(Context context, String filter) {
        super(context);
        this.cxt = context;
        this.filter = filter;
    }

    @Override
    protected void onStartLoading() {
        showProgressDialog();
        forceLoad();
        if (musicInfos_loaded != null) {
            deliverResult(musicInfos_loaded);
        }
        super.onStartLoading();
    }

    @Override
    public ArrayList<MusicInfo> loadInBackground() {
        if (musicInfos == null) {
            musicInfos = new ArrayList<>();
        }

        for (MusicInfo info : MusicLoader.instance(cxt.getContentResolver()).getmusicInfoList()) {
            addMusicInfo(info);
        }

        return musicInfos;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCanceled(ArrayList<MusicInfo> data) {
        super.onCanceled(data);
        cancelLoad();
        progressDialog.dismiss();
    }

    @Override
    protected void onStopLoading() {
        Log.d(TAG, "onStopLoading");
        progressDialog.dismiss();
        super.onStopLoading();
    }

    @Override
    public void deliverResult(ArrayList<MusicInfo> data) {
        progressDialog.dismiss();
        if (isReset()) {
            if (data != null) {
                data.clear();
                data = null;
            }
        }
        musicInfos_loaded = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onReset() {
        showProgressDialog();
        onStopLoading();
        if (musicInfos_loaded != null) {
            musicInfos_loaded = null;
        }
        super.onReset();
    }

    private void addMusicInfo(MusicInfo info) {
        if (filter != null && !filter.trim().equals("") && info != null && info.getTitle() != null && !info.getTitle().trim().equals("")) {
            if ((info.getTitle().trim().contains(filter)) || (filter.contains(info.getTitle().trim()))) {
                musicInfos.add(info);
            }
        } else {
            musicInfos.add(info);
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(cxt);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("请稍候");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}
