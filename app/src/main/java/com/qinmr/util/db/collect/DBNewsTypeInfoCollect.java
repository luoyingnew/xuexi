package com.qinmr.util.db.collect;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qinmr.util.db.base.DBManager;
import com.qinmr.util.db.table.NewsTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrq on 2017/4/12.
 */

public class DBNewsTypeInfoCollect {

    public static final String TNAME = "NewsType";

    public static void createTableSQL(SQLiteDatabase db) {
//        "create table if not exists " + TABLE_NAME + " (Id integer primary key, CustomName text, OrderPrice integer, Country text)";
//        db.execSQL("CREATE TABLE " + TNAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, TYPE_ID TEXT);");
        db.execSQL("create table if not exists " + TNAME + " (id integer primary key AUTOINCREMENT,NAME text,TYPE_ID text)");
    }

    /**
     * 插入标题信息
     *
     * @param Bean
     */
    public static boolean insertTitleTable(List<NewsTypeInfo> Bean) {
        boolean result = false;
        for (int i = 0; i < Bean.size(); i++) {
            try {
                NewsTypeInfo newsTypeInfo = Bean.get(i);
                if (null == newsTypeInfo) {
                    return false;
                }
                result = insert(newsTypeInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean insert(NewsTypeInfo Bean) {
        boolean result = false;
        try {
            if (null == Bean) {
                return false;
            }
            SQLiteDatabase db = DBManager.getInstance().openDatabase();
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put("NAME", Bean.getName());
            cv.put("TYPE_ID", Bean.getTypeId());
            db.insert(TNAME, null, cv);
            db.setTransactionSuccessful();
            db.endTransaction();
            DBManager.getInstance().closeDatabase();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取任务信息
     */
    public static List<NewsTypeInfo> getNewsTypeInfo() {
        List<NewsTypeInfo> taskList = new ArrayList<>();
        try {
            SQLiteDatabase db = DBManager.getInstance().openDatabase();
            db.beginTransaction();
            String sql = "select * from " + TNAME;
            Cursor c = db.rawQuery(sql, null);
            // 处理信息
            if (c != null) {
                while (c.moveToNext()) {
                    NewsTypeInfo newsTypeInfo = new NewsTypeInfo();
                    newsTypeInfo.setName(c.getString(1));
                    newsTypeInfo.setTypeId(c.getString(2));
                    taskList.add(newsTypeInfo);
                }
            }
            c.close();
            db.setTransactionSuccessful();
            db.endTransaction();
            SQLiteDatabase.releaseMemory();
            DBManager.getInstance().closeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskList;
    }

    public static void delete(String info) {
        try {
            SQLiteDatabase db = DBManager.getInstance().openDatabase();
            db.beginTransaction();
            db.delete(TNAME, "TYPE_ID = ?", new String[]{info});
            db.setTransactionSuccessful();
            db.endTransaction();
            DBManager.getInstance().closeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
