package com.lxchild.expressboard.feedback;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lxchild.expressboard.main.R;

/**
 * Created by LXChild on 2015/6/3.
 */
public class FeedbackFragment extends Fragment {

    private ListView lv_about;
    private AboutAdapter aboutAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        lv_about = (ListView) rootView.findViewById(R.id.lv_settings);
        initView();
        return rootView;
    }

    private void initView() {
        aboutAdapter = new AboutAdapter(getActivity());
        lv_about.setAdapter(aboutAdapter);
    }
}
