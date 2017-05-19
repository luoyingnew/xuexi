package com.qinmr.mvp.ui.photo.beauty;


import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.db.table.BeautyPhotoInfo;
import com.qinmr.mvp.ui.base.IBasePresenter;
import com.qinmr.mvp.ui.base.ILoadDataView;
import com.qinmr.utillibrary.logger.KLog;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by long on 2016/9/5.
 * 美图 Presenter
 */
public class BeautyListPresenter implements IBasePresenter {

    private final ILoadDataView mView;

    private int mPage = 0;

    public BeautyListPresenter(ILoadDataView view) {
        this.mView = view;
    }

    @Override
    public void getData(boolean isRefresh) {
        // 因为网易这个原接口参数一大堆，我只传了部分参数，返回的数据会出现图片重复的情况，请不要在意这个问题- -
        RetrofitService.getBeautyPhoto(mPage)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<List<BeautyPhotoInfo>>bindToLife())
                .subscribe(new Subscriber<List<BeautyPhotoInfo>>() {
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
                    public void onNext(List<BeautyPhotoInfo> photoList) {
                        mView.loadData(photoList);
                        mPage++;
                    }
                });
    }

    @Override
    public void getMoreData() {
        RetrofitService.getBeautyPhoto(mPage)
                .compose(mView.<List<BeautyPhotoInfo>>bindToLife())
                .subscribe(new Subscriber<List<BeautyPhotoInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                        mView.loadNoData();
                    }

                    @Override
                    public void onNext(List<BeautyPhotoInfo> photoList) {
                        mView.loadMoreData(photoList);
                        mPage++;
                    }
                });
    }
}
