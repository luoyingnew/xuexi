package com.qinmr.mvp.ui.photo.news;

import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.api.bean.PhotoInfo;
import com.qinmr.mvp.ui.base.IBasePresenter;
import com.qinmr.mvp.ui.base.ILoadDataView;
import com.qinmr.utillibrary.logger.KLog;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by mrq on 2017/5/19.
 */

public class PhotoNewsPresenter implements IBasePresenter {

    private String mNextSetId;
    private final ILoadDataView mView;

    public PhotoNewsPresenter(ILoadDataView mView) {
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getPhotoList()
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<List<PhotoInfo>>bindToLife())
                .subscribe(new Subscriber<List<PhotoInfo>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                        mView.showNetError();
                    }

                    @Override
                    public void onNext(List<PhotoInfo> photoInfos) {
                        mView.loadData(photoInfos);
                        mNextSetId = photoInfos.get(photoInfos.size() - 1).getSetid();
                    }
                });
    }

    @Override
    public void getMoreData() {
        RetrofitService.getPhotoMoreList(mNextSetId)
                .compose(mView.<List<PhotoInfo>>bindToLife())
                .subscribe(new Subscriber<List<PhotoInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loadNoData();
                    }

                    @Override
                    public void onNext(List<PhotoInfo> photoList) {
                        mView.loadMoreData(photoList);
                        mNextSetId = photoList.get(photoList.size() - 1).getSetid();
                    }
                });
    }
}
