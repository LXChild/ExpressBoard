package com.lxchild.expressboard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LXChi on 2015/8/10.
 */
public class DrawEditTable extends SQLiteOpenHelper {

    private static final String TAG = DrawEditTable.class.getSimpleName();

    private String tb_name = "DrawTable";
    private String createTable = "CREATE TABLE " + tb_name + " ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "dir TEXT DEFAULE \"\")";
    private String dropTable = "DROP TABLE " + tb_name;

    public DrawEditTable(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DrawEditTable(Context context) {
        super(context, "drawdb", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>");
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTable);
        onCreate(db);
    }

    /**
     * 插入数据
     * @param dir 图片路径
     */
    public long insert(String dir) {
        SQLiteDatabase db = getWritableDatabase();//获取可写SQLiteDatabase对象
        //ContentValues类似map，存入的是键值对
        ContentValues contentValues = new ContentValues();
        contentValues.put("dir", dir);
        return db.insert(tb_name, null, contentValues);
    }

    /**
     * 删除记录
     * @param _id 列表条目
     */
    public void delete(int _id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = new String[]{String.valueOf(_id)};
        db.delete(tb_name, "_id=?", args);
    }

    /**
     * 更新记录
     *
     * @param _id     列表条目
     * @param dir 白板内容
     */
    public void update(int _id, String dir) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dir", dir);
        String[] args = new String[]{String.valueOf(_id)};
        db.update(tb_name, contentValues, "_id=?", args);
    }

    /**
     * 查询所有数据
     * @return Cursor
     */
    public Cursor select() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(tb_name, new String[]{"_id", "dir"}, null, null, null, null, "_id asc");
    }

    public Cursor selectWhere(String filter) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(tb_name, new String[]{"_id", "dir"}, filter, null, null, null, "_id asc");
    }
}
