package com.lxchild.expressboard.drawingboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.lxchild.expressboard.main.R;

public class DrawingBoardActivity extends Activity {

    private static final String TAG = DrawingBoardActivity.class.getSimpleName();
    private static final int RESULT_CODE_DRAWINGBOARD = 0;

    private DrawingBoard board_drawing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawingboard);

        initView();
    }

    private void initView() {
        board_drawing = (DrawingBoard) findViewById(R.id.board_drawing);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_drawingboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String path = board_drawing.saveScreenshot();
                Log.d(TAG, path);
                Intent intent = new Intent();
                intent.putExtra("path", path);
                setResult(RESULT_CODE_DRAWINGBOARD, intent);
                finish();
                break;
            case R.id.action_clear:
                board_drawing.clear();
                break;
        }
        return true;
    }

    //    private void saveDraw() {
//        Bitmap bmp = board_drawing.getDrawingCache();
//        File folder = new File(Environment.getExternalStorageDirectory(), "lxchild");
//        if(!folder.exists()) {
//            folder.mkdirs();
//        }
//        File file = new File(folder, System.currentTimeMillis() + ".png");
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            if (bmp == null) {
//                Toast.makeText(MainActivity.this, "klsdfj;als is here", Toast.LENGTH_SHORT).show();
//            }
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            fos.close();
//            Toast.makeText(MainActivity.this, "saveBmp is here", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
