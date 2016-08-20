package com.lxchild.expressboard.edit_text;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.lxchild.expressboard.show_text_board.TextShowActivity;
import com.lxchild.expressboard.show_text_board.TextPageEntity;
import com.lxchild.expressboard.database.TextEditTable;
import com.lxchild.expressboard.main.R;
import com.lxchild.expressboard.widget.swipemenulistview.SwipeMenu;
import com.lxchild.expressboard.widget.swipemenulistview.SwipeMenuCreator;
import com.lxchild.expressboard.widget.swipemenulistview.SwipeMenuItem;
import com.lxchild.expressboard.widget.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

/**
 * Created by LXChild on 2015/6/3.
 */
public class TextEditFragment extends Fragment {

    private final String TAG = TextEditFragment.class.getSimpleName();

    private SwipeMenuListView lv_item;
    private ArrayList<TextPageEntity> itemList;
    private TextListAdapter adapter;

    private TextEditTable db;

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
        db = new TextEditTable(getActivity());
        adapter = new TextListAdapter(getActivity(), itemList);
        readData();

        lv_item.setAdapter(adapter);

        initSwipeMenuListView();
    }

    private void readData() {

        Cursor c = db.select();
        if (c.getCount() != 0) {
            if (c.moveToFirst()) {
                do {
                    String content = c.getString(c.getColumnIndex("content"));
                    if (!content.trim().equals("")) {
                        TextPageEntity page = new TextPageEntity();
                        page.setContentText(content);
                        itemList.add(page);
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
        switch (item.getItemId()) {
            case R.id.action_add:
                showAddDialog();
                return true;
            case R.id.action_show:
                if (itemList.size() > 0) {
                    Intent intent = new Intent(getActivity(), TextShowActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("Pages", itemList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "白板列表为空", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return false;
        }
    }


    private void showAddDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialog_layout = inflater.inflate(R.layout.layout_dialog_edit, null);
        final EditText et_add = (EditText) dialog_layout.findViewById(R.id.et_edit_content);

        final AlertDialog.Builder connectDialog = new AlertDialog.Builder(getActivity());
        connectDialog.setTitle("添加");
        connectDialog.setView(dialog_layout);
        connectDialog.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(et_add.getText()) && !et_add.getText().toString().trim().equals("")) {
                            TextPageEntity textPageEntity = new TextPageEntity();
                            textPageEntity.setContentText(et_add.getText().toString());
                            itemList.add(textPageEntity);
                            db.insert(et_add.getText().toString());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "白板内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        connectDialog.setNegativeButton("取消", null);
        connectDialog.show();
    }

    private void showEditDialog(final int position, final TextPageEntity page) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialog_layout = inflater.inflate(R.layout.layout_dialog_edit, null);
        final EditText et_edit = (EditText) dialog_layout.findViewById(R.id.et_edit_content);
        et_edit.setText(page.getContentText());

        final AlertDialog.Builder connectDialog = new AlertDialog.Builder(getActivity());
        connectDialog.setTitle("修改");
        connectDialog.setView(dialog_layout);
        connectDialog.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(et_edit.getText()) && !et_edit.getText().toString().trim().equals("")) {

                            Cursor c = db.select();
                            if (c.getCount() > 0) {
                                c.moveToFirst();
                                for (int i = 0; i < position; i++) {
                                    Log.d(TAG, "Moved");
                                    c.moveToNext();
                                }
                                int _id = c.getInt(c.getColumnIndex("_id"));
                                Log.d(TAG, "update: " + _id + "pos: " + position);
                                db.update(_id, et_edit.getText().toString());
                            }

                            page.setContentText(et_edit.getText().toString());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "白板内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

        );
        connectDialog.setNegativeButton("取消", null);
        connectDialog.show();
    }


    private void initSwipeMenuListView() {
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "edit" item
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(80));
//                // set item title
//                openItem.setTitle("Edit");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
                openItem.setIcon(R.mipmap.ic_edit);
                // add to menu
                menu.addMenuItem(openItem);

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
                TextPageEntity item = itemList.get(position);
                switch (index) {
                    case 0:
                        edit(position, item);
                        break;
                    case 1:
                        delete(position);
                        break;
                }
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

    private void edit(int position, TextPageEntity page) {
        showEditDialog(position, page);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
