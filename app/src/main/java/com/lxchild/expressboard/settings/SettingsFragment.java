package com.lxchild.expressboard.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lxchild.expressboard.bgc.BackgroundColorActivity;
import com.lxchild.expressboard.bgm.BGMActivity;
import com.lxchild.expressboard.fontcolor.FontColorActivity;
import com.lxchild.expressboard.fontsize.FontSizeActivity;
import com.lxchild.expressboard.main.R;
import com.lxchild.expressboard.paintsize.PaintSizeActivity;
import com.lxchild.expressboard.timing.TimingActivity;

/**
 * Created by LXChild on 2015/6/3.
 */
public class SettingsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private final String TAG = SettingsFragment.class.getSimpleName();
    private ListView lv_settings;
    private SettingsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        lv_settings = (ListView) rootView.findViewById(R.id.lv_settings);
        initView();
        return rootView;
    }

    private void initView() {
        adapter = new SettingsAdapter(getActivity());
        lv_settings.setAdapter(adapter);
        lv_settings.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(getActivity(), TimingActivity.class);
                break;
            case 1:
                intent.setClass(getActivity(), FontSizeActivity.class);
                break;
            case 2:
                intent.setClass(getActivity(), PaintSizeActivity.class);
                break;
            case 3:
                intent.setClass(getActivity(), FontColorActivity.class);
                break;
            case 4:
                intent.setClass(getActivity(), BackgroundColorActivity.class);
                break;
            case 5:
                intent.setClass(getActivity(), BGMActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
