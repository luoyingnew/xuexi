package com.qinmr.mvp.ui.photo.welfare;

import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.api.bean.WelfarePhotoInfo;
import com.qinmr.mvp.ui.base.IBasePresenter;
import com.qinmr.mvp.util.ImageLoader;
import com.qinmr.utillibrary.logger.KLog;

import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mrq on 2017/5/18.
 */

public class WelfareListPensenter implements IBasePresenter {

    private WelfareListFragment mView;

    private int mPage = 1;

    public WelfareListPensenter(WelfareListFragment mView) {
        this.mView = mView;
    }

    @Override
    public void getData(boolean isRefresh) {
        RetrofitService.getWelfarePhoto(mPage)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mTransformer)
                .subscribe(new Subscriber<List<WelfarePhotoInfo>>() {
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
                    public void onNext(List<WelfarePhotoInfo> welfarePhotoInfos) {
                        mView.loadData(welfarePhotoInfos);
                        mPage++;
                    }
                });
    }

    @Override
    public void getMoreData() {

    }

    /**
     * 统一变换
     */
    private Observable.Transformer<WelfarePhotoInfo, List<WelfarePhotoInfo>> mTransformer = new Observable.Transformer<WelfarePhotoInfo, List<WelfarePhotoInfo>>() {

        @Override
        public Observable<List<WelfarePhotoInfo>> call(Observable<WelfarePhotoInfo> welfarePhotoInfoObservable) {
            return welfarePhotoInfoObservable
                    .observeOn(Schedulers.io())
                    // 接口返回的数据是没有宽高参数的，所以这里设置图片的宽度和高度，速度会慢一点
                    .filter(new Func1<WelfarePhotoInfo, Boolean>() {
                        @Override
                        public Boolean call(WelfarePhotoInfo photoBean) {
                            try {
                                photoBean.setPixel(ImageLoader.calePhotoSize(mView.getContext(), photoBean.getUrl()));
                                return true;
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                return false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .toList()
                    .compose(mView.<List<WelfarePhotoInfo>>bindToLife());
        }
    };
}
