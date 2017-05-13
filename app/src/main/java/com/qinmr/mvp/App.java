package com.qinmr.mvp;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.db.NewsTypeDao;
import com.qinmr.mvp.db.table.DaoMaster;
import com.qinmr.mvp.db.table.DaoSession;
import com.qinmr.mvp.rxbus.RxBus;
import com.qinmr.utillibrary.logger.KLog;

import org.greenrobot.greendao.database.Database;


/**
 * Created by mrq on 2017/3/23.
 */

public class App extends Application {

    private static final String DB_NAME = "news-db";

    private static App mContext;
    public static DaoSession mDaoSession;
    // 因为下载那边需要用，这里在外面实例化在通过 ApplicationModule 设置
    private RxBus mRxBus = new RxBus();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
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
        OkGo.init(this);
    }

    /**
     * 初始化数据
     */
    private void initDatabase() {
        //存入sqlite
        NewsTypeDao.updateLocalData(mContext);
        //存入greenDao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), DB_NAME);
        Database database = helper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();
        NewsTypeDao.updateLocalData(getContext(), mDaoSession);
    }


    public static App getContext() {
        return mContext;
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}
