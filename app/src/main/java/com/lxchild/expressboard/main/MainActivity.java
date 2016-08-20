package com.lxchild.expressboard.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;

import com.lxchild.expressboard.collection.CollectionFragment;
import com.lxchild.expressboard.edit_draw.DrawEditFragment;
import com.lxchild.expressboard.edit_text.TextEditFragment;
import com.lxchild.expressboard.feedback.FeedbackFragment;
import com.lxchild.expressboard.settings.SettingsFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager fm = getSupportFragmentManager();

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_main);

        //创建NavigationDrawer
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        //  setOverflowShowingAlways();
    }

    private void initData() {
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        mTitle = getResources().getStringArray(R.array.drawerlist)[number - 1];
        switch (number) {
            case 1:
                TextEditFragment tef = new TextEditFragment();
                fm.beginTransaction().replace(R.id.container, tef).commit();
                break;
            case 2:
                DrawEditFragment def = new DrawEditFragment();
                fm.beginTransaction().replace(R.id.container, def).commit();
                break;
            case 3:
                CollectionFragment cf = new CollectionFragment();
                fm.beginTransaction().replace(R.id.container, cf).commit();
                break;
            case 4:
                SettingsFragment sf = new SettingsFragment();
                fm.beginTransaction().replace(R.id.container, sf).commit();
                break;
            case 5:
                FeedbackFragment af = new FeedbackFragment();
                fm.beginTransaction().replace(R.id.container, af).commit();
                break;
            case 6:
                finish();
                System.exit(0);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * 按键监控事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //如果按键为返回键则调用双击退出程序函数
            case KeyEvent.KEYCODE_BACK:
                exitBy2Click();
                break;
            case KeyEvent.KEYCODE_MENU:
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 双击退出程序
     */
    private void exitBy2Click() {

        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);//关闭进程
        }
    }

//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
//            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//                try {
//                    Method m = menu.getClass().getDeclaredMethod(
//                            "setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return super.onMenuOpened(featureId, menu);
//    }
//
//    private void setOverflowShowingAlways() {
//        try {
//            ViewConfiguration config = ViewConfiguration.get(this);
//            Field menuKeyField = ViewConfiguration.class
//                    .getDeclaredField("sHasPermanentMenuKey");
//            menuKeyField.setAccessible(true);
//            menuKeyField.setBoolean(config, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}