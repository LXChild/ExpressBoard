package com.lxchild.expressboard.edit_draw;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lxchild.expressboard.database.DrawEditTable;
import com.lxchild.expressboard.show_draw_board.DrawShowActivity;
import com.lxchild.expressboard.drawingboard.DrawingBoardActivity;
import com.lxchild.expressboard.main.R;
import com.lxchild.expressboard.util.MyUtil;
import com.lxchild.expressboard.widget.swipemenulistview.SwipeMenu;
import com.lxchild.expressboard.widget.swipemenulistview.SwipeMenuCreator;
import com.lxchild.expressboard.widget.swipemenulistview.SwipeMenuItem;
import com.lxchild.expressboard.widget.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

/**
 * Created by LXChi on 2015/8/9.
 */
public class DrawEditFragment extends Fragment {

    private static final String TAG = DrawEditFragment.class.getSimpleName();
    private static final int REQUEST_CODE_DRAWINGBOARD = 0;

    private SwipeMenuListView lv_item;
    private ArrayList<Bitmap> itemList;
    private DrawListAdapter adapter;

    private DrawEditTable db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        lv_item = (SwipeMenuListView) rootView.findViewById(R.id.lv_item);
        initData();
        return rootView;
    }

    private void initData() {
        itemList = new ArrayList<>();
        db = new DrawEditTable(getActivity());
        adapter = new DrawListAdapter(getActivity(), itemList);
        readData();

        lv_item.setAdapter(adapter);

        initSwipeMenuListView();
    }

    private void readData() {

        Cursor c = db.select();
        if (c.getCount() != 0) {
            if (c.moveToFirst()) {
                do {
                    String dir = c.getString(c.getColumnIndex("dir"));
                    if (!dir.trim().equals("")) {
                        itemList.add(MyUtil.getBitmap(dir));
                        adapter.notifyDataSetChanged();
                    }
                } while (c.moveToNext());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_add:
                intent.setClass(getActivity(), DrawingBoardActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DRAWINGBOARD);
                // showAddDialog();
                return true;
            case R.id.action_show:
                if (itemList.size() > 0) {
                    Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    intent.setClass(getActivity(), DrawShowActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "白板列表为空", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_DRAWINGBOARD:
                if (data != null) {
                    String path = data.getStringExtra("path");
                    Log.d(TAG, path);
                    if (path != null && !path.trim().equals("")) {
                        itemList.add(MyUtil.getBitmap(path));
                        db.insert(path);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void initSwipeMenuListView() {
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(80));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        lv_item.setMenuCreator(creator);

        // step 2. listener item click event
        lv_item.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                delete(position);
            }
        });

        // set SwipeListener
        lv_item.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // test item long click
        lv_item.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    private void delete(int position) {
        Cursor c = db.select();
        if (c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < position; i++) {
                c.moveToNext();
            }
            int _id = c.getInt(c.getColumnIndex("_id"));
            Log.d(TAG, "delete: " + _id + "pos: " + position);
            db.delete(_id);
        }
        itemList.remove(position);
        adapter.notifyDataSetChanged();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
