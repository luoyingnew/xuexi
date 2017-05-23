package com.qinmr.mvp.ui.photo.bigphoto;

import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.db.table.WelfarePhotoInfo;
import com.qinmr.mvp.db.table.WelfarePhotoInfoDao;
import com.qinmr.mvp.rxbus.RxBus;
import com.qinmr.mvp.rxbus.event.LoveEvent;
import com.qinmr.mvp.ui.base.ILocalPresenter;
import com.qinmr.mvp.util.ImageLoader;
import com.qinmr.utillibrary.logger.KLog;

import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mrq on 2017/5/19.
 */

public class BigPhotoPresenter implements ILocalPresenter<WelfarePhotoInfo> {

    private final BigPhotoActivity mView;
    private final List<WelfarePhotoInfo> mPhotoList;
    private final WelfarePhotoInfoDao mDbDao;
    private final RxBus mRxBus;
    private List<WelfarePhotoInfo> mDbLovedData;

    private int mPage = 1;

    public BigPhotoPresenter(BigPhotoActivity activity, List<WelfarePhotoInfo> mPhotoList, WelfarePhotoInfoDao newsTypeInfoDao, RxBus mRxBus) {
        this.mView = activity;
        this.mPhotoList = mPhotoList;
        this.mDbDao = newsTypeInfoDao;
        this.mRxBus = mRxBus;
        mDbLovedData = newsTypeInfoDao.queryBuilder().list();
    }

    @Override
    public void getData(boolean isRefresh) {
        Observable.from(mPhotoList)
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
                        mView.showNetError();
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(List<WelfarePhotoInfo> welfarePhotoInfos) {
                        mView.loadData(welfarePhotoInfos);
                    }
                });


    }

    @Override
    public void getMoreData() {
        RetrofitService.getWelfarePhoto(mPage)
                //转换为一个集合
               .compose(transformer)
                .flatMap(new Func1<List<WelfarePhotoInfo>, Observable<WelfarePhotoInfo>>() {
                    @Override
                    public Observable<WelfarePhotoInfo> call(List<WelfarePhotoInfo> welfarePhotoInfos) {
                        return Observable.from(welfarePhotoInfos);
                    }
                })
                .compose(mTransformer)
                .subscribe(new Subscriber<List<WelfarePhotoInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(List<WelfarePhotoInfo> photoList) {
                        mView.loadMoreData(photoList);
                        mPage++;
                    }
                });
    }

    @Override
    public void insert(WelfarePhotoInfo data) {
        if (mDbLovedData.contains(data)) {
            mDbDao.update(data);
        } else {
            mDbDao.insert(data);
            mDbLovedData.add(data);
        }
        mRxBus.post(new LoveEvent());
    }

    @Override
    public void delete(WelfarePhotoInfo data) {
        if (!data.isLove() && !data.isDownload() && !data.isPraise()) {
            mDbDao.delete(data);
            mDbLovedData.remove(data);
        } else {
            mDbDao.update(data);
        }
        mRxBus.post(new LoveEvent());
    }

    @Override
    public void update(List<WelfarePhotoInfo> list) {

    }

    private Observable.Transformer<WelfarePhotoInfo, List<WelfarePhotoInfo>> mTransformer
            = new Observable.Transformer<WelfarePhotoInfo, List<WelfarePhotoInfo>>() {
        @Override
        public Observable<List<WelfarePhotoInfo>> call(Observable<WelfarePhotoInfo> listObservable) {
            return listObservable
                    .doOnNext(new Action1<WelfarePhotoInfo>() {
                        WelfarePhotoInfo tmpBean;

                        @Override
                        public void call(WelfarePhotoInfo bean) {
                            // 判断数据库是否有数据，有则设置对应参数
                            if (mDbLovedData.contains(bean)) {
                                tmpBean = mDbLovedData.get(mDbLovedData.indexOf(bean));
                                bean.setLove(tmpBean.isLove());
                                bean.setPraise(tmpBean.isPraise());
                                bean.setDownload(tmpBean.isDownload());
                            }
                        }
                    })
                    .toList()
                    .compose(mView.<List<WelfarePhotoInfo>>bindToLife());
        }
    };

    /**
     * 统一变换
     */
    private Observable.Transformer<WelfarePhotoInfo, List<WelfarePhotoInfo>> transformer = new Observable.Transformer<WelfarePhotoInfo, List<WelfarePhotoInfo>>() {

        @Override
        public Observable<List<WelfarePhotoInfo>> call(Observable<WelfarePhotoInfo> welfarePhotoInfoObservable) {
            return welfarePhotoInfoObservable
                    .observeOn(Schedulers.io())
                    // 接口返回的数据是没有宽高参数的，所以这里设置图片的宽度和高度，速度会慢一点
                    .filter(new Func1<WelfarePhotoInfo, Boolean>() {
                        @Override
                        public Boolean call(WelfarePhotoInfo photoBean) {
                            try {
                                photoBean.setPixel(ImageLoader.calePhotoSize(mView, photoBean.getUrl()));
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
                    .toList();
        }
    };
}
