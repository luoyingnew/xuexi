package com.qinmr.mvp.ui.video.list;

import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.db.table.VideoInfo;
import com.qinmr.mvp.ui.base.IBasePresenter;
import com.qinmr.mvp.ui.base.ILoadDataView;
import com.qinmr.utillibrary.logger.KLog;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by mrq on 2017/5/19.
 */

public class VideoListPensenter implements IBasePresenter {

    private final ILoadDataView mView;
    private final String mVideoId;

    private int mPage = 0;

    public VideoListPensenter(ILoadDataView mView, String mVideoId) {
        this.mView = mView;
        this.mVideoId = mVideoId;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getVideoList(mVideoId, mPage)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<List<VideoInfo>>bindToLife())
                .subscribe(new Subscriber<List<VideoInfo>>() {
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
                    public void onNext(List<VideoInfo> videoList) {
                        mView.loadData(videoList);
                        mPage++;
                    }
                });
    }

    @Override
    public void getMoreData() {
        RetrofitService.getVideoList(mVideoId, mPage)
                .compose(mView.<List<VideoInfo>>bindToLife())
                .subscribe(new Subscriber<List<VideoInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                        mView.loadNoData();
                    }

                    @Override
                    public void onNext(List<VideoInfo> videoList) {
                        mView.loadMoreData(videoList);
                        mPage++;
                    }
                });
    }
}
