package com.qinmr.mvp.ui.photo.main;


import com.qinmr.mvp.db.table.WelfarePhotoInfoDao;
import com.qinmr.mvp.rxbus.RxBus;
import com.qinmr.mvp.ui.base.IRxBusPresenter;
import com.qinmr.utillibrary.logger.KLog;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by long on 2016/9/23.
 * 图片专栏 Presenter
 */
public class PhotoMainPresenter implements IRxBusPresenter {

    private final IPhotoMainView mView;
    private final WelfarePhotoInfoDao mDbDao;
    private final RxBus mRxBus;

    public PhotoMainPresenter(IPhotoMainView view, WelfarePhotoInfoDao dbDao, RxBus rxBus) {
        mView = view;
        mDbDao = dbDao;
        mRxBus = rxBus;
    }

    @Override
    public void getData(boolean isRefresh) {
        mView.updateCount((int) mDbDao.queryBuilder().where(WelfarePhotoInfoDao.Properties.IsLove.eq(true)).count());
    }

    @Override
    public void getMoreData() {
    }

    @Override
    public <T> void registerRxBus(Class<T> eventType, Action1<T> action) {
        Subscription subscription = mRxBus.doSubscribe(eventType, action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                KLog.e(throwable.toString());
            }
        });
        mRxBus.addSubscription(this, subscription);
    }

    @Override
    public void unregisterRxBus() {
        mRxBus.unSubscribe(this);
    }
}
