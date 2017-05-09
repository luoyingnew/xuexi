package com.qinmr.mvp;

import android.app.Application;

import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.db.NewsTypeDao;
import com.qinmr.utillibrary.logger.KLog;
import com.zhy.autolayout.config.AutoLayoutConifg;


/**
 * Created by mrq on 2017/3/23.
 */

public class App extends Application {

    private static App mContext;

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
        AutoLayoutConifg.getInstance().useDeviceSize();
        RetrofitService.init();
//        OkGo.init(this);
    }

    /**
     * 初始化数据
     */
    private void initDatabase() {
        NewsTypeDao.updateLocalData(mContext);
    }

    public static App getContext() {
        return mContext;
    }
}
