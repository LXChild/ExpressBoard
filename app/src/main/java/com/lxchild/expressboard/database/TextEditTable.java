package com.lxchild.expressboard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LXChild on 2015/6/3.
 */
public class TextEditTable extends SQLiteOpenHelper {

    private String tb_name = "TextTable";
    private String createTable = "CREATE TABLE " + tb_name + " ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "content TEXT DEFAULE \"\")";
    private String dropTable = "DROP TABLE " + tb_name;

    public TextEditTable(Context context) {
        super(context, "textdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTable);
        onCreate(db);
    }

    /**
     * 插入数据
     *
     * @param content 白板文本
     */
    public long insert(String content) {
        SQLiteDatabase db = getWritableDatabase();//获取可写SQLiteDatabase对象
        //ContentValues类似map，存入的是键值对
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        return db.insert(tb_name, null, contentValues);
    }

    /**
     * 删除记录
     *
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
     * @param content 白板内容
     */
    public void update(int _id, String content) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        String[] args = new String[]{String.valueOf(_id)};
        db.update(tb_name, contentValues, "_id=?", args);
    }

    /**
     * 查询所有数据
     *
     * @return Cursor
     */
    public Cursor select() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(tb_name, new String[]{"_id", "content"}, null, null, null, null, "_id asc");
    }

    public Cursor selectWhere(String filter) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(tb_name, new String[]{"_id", "content"}, filter, null, null, null, "_id asc");
    }
}
