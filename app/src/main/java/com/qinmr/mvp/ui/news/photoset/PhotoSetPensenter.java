package com.qinmr.mvp.ui.news.photoset;

import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.api.bean.PhotoSetInfo;
import com.qinmr.mvp.ui.base.IBasePresenter;
import com.qinmr.utillibrary.logger.KLog;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by mrq on 2017/5/17.
 */

public class PhotoSetPensenter implements IBasePresenter {

    private final IPhotoSetView mView;
    private final String mPhotoSetId;

    public PhotoSetPensenter(IPhotoSetView mView, String mPhotoSetId) {
        this.mView = mView;
        this.mPhotoSetId = mPhotoSetId;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getPhotoSet(mPhotoSetId)
                .compose(mView.<PhotoSetInfo>bindToLife())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .subscribe(new Subscriber<PhotoSetInfo>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNetError();
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(PhotoSetInfo photoSetInfo) {
                        mView.loadData(photoSetInfo);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
