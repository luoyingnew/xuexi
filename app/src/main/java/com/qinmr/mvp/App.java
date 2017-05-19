package com.qinmr.mvp;

import android.app.Application;

import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.db.NewsTypeDao;
import com.qinmr.mvp.db.table.DaoMaster;
import com.qinmr.mvp.db.table.DaoSession;
import com.qinmr.mvp.rxbus.RxBus;
import com.qinmr.utillibrary.logger.KLog;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;


/**
 * Created by mrq on 2017/3/23.
 */

public class App extends Application {

    private static final String DB_NAME = "news-db";

    private static App sContext;
    private static DaoSession sDaoSession;
    // 因为下载那边需要用，这里在外面实例化在通过 ApplicationModule 设置
    private static RxBus sRxBus = new RxBus();

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initConfig();
        initDatabase();
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        KLog.init(BuildConfig.LOG_DEBUG);
//        AutoLayoutConifg.getInstance().useDeviceSize();
        RetrofitService.init();
        LeakCanary.install(this);
    }

    /**
     * 初始化数据
     */
    private void initDatabase() {
        //存入greenDao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), DB_NAME);
        Database database = helper.getWritableDb();
        sDaoSession = new DaoMaster(database).newSession();
        NewsTypeDao.updateLocalData(getContext(), sDaoSession);
    }


    public static App getContext() {
        return sContext;
    }

    public static DaoSession getDaoSession() {
        return sDaoSession;
    }

    public static RxBus getRxBus() {
        return sRxBus;
    }
}
