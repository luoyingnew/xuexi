package com.qinmr.util.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qinmr.util.db.collect.DBNewsTypeInfoCollect;

/**
 * Created by mrq on 2017/2/17.
 */

public class SQLiteTool extends SQLiteOpenHelper {


    private static final String DB_NAME = "News.db"; // 数据库文件名
    private static final int VERSION = 1;// 数据库版本

    public static SQLiteTool newInstance(Context context) {
        return new SQLiteTool(context, DB_NAME, null, VERSION);
    }

    public SQLiteTool(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBNewsTypeInfoCollect.createTableSQL(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
