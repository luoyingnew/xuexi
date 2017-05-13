package com.qinmr.mvp.ui.news.main;

import com.qinmr.mvp.db.table.NewsTypeInfo;
import com.qinmr.mvp.db.table.NewsTypeInfoDao;
import com.qinmr.mvp.rxbus.RxBus;
import com.qinmr.mvp.ui.base.IRxBusPresenter;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by mrq on 2017/5/13.
 */

public class NewsMainPresenter implements IRxBusPresenter {

    private final NewsMainFragment mView;
    private final NewsTypeInfoDao mDbDao;
    private final RxBus mRxBus;

    public NewsMainPresenter(NewsMainFragment fragment, NewsTypeInfoDao infoDao, RxBus mRxBus) {
        this.mView = fragment;
        this.mDbDao = infoDao;
        this.mRxBus = mRxBus;
    }

    @Override
    public void getData(boolean isRefresh) {
        mDbDao.queryBuilder().rx().list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<NewsTypeInfo>>() {
                    @Override
                    public void call(List<NewsTypeInfo> newsTypeBeen) {
                        mView.loadData(newsTypeBeen);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {

    }

    @Override
    public void unregisterRxBus() {

    }
}
