package com.qinmr.util.db.base;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.qinmr.util.App;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mrq on 2017/2/17.
 */

public class DBManager {

    private AtomicInteger dbOpenCount = new AtomicInteger();// 计数器
    private static DBManager instance;
    private static SQLiteTool sqlLiteTool;
    private SQLiteDatabase database;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    private DBManager() {
        sqlLiteTool = SQLiteTool.newInstance(App.getContext());
    }

    @SuppressLint("NewApi")
    public synchronized SQLiteDatabase openDatabase() {
        if (dbOpenCount.incrementAndGet() == 1) {
            database = sqlLiteTool.getWritableDatabase();
            if (Build.VERSION.SDK_INT >= 11) {
                database.enableWriteAheadLogging();// 允许读写同时进行
            }
        }
        return database;
    }

    public synchronized void closeDatabase() {
        if (dbOpenCount.decrementAndGet() == 0) {
            database.close();
            database = null;
        }
    }

}
