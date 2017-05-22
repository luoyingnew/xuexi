package com.qinmr.mvp.ui.news.special;

import com.qinmr.mvp.adapter.item.SpecialItem;
import com.qinmr.mvp.api.RetrofitService;
import com.qinmr.mvp.api.bean.NewsItemInfo;
import com.qinmr.mvp.api.bean.SpecialInfo;
import com.qinmr.mvp.api.bean.SpecialInfo.TopicsEntity;
import com.qinmr.mvp.ui.base.IBasePresenter;
import com.qinmr.utillibrary.logger.KLog;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by mrq on 2017/5/17.
 */

public class SpecialPresenter implements IBasePresenter {


    private final ISpecialView mView;
    private final String mSpecialId;

    public SpecialPresenter(ISpecialView mView, String mSpecialId) {
        this.mView = mView;
        this.mSpecialId = mSpecialId;
    }

    @Override
    public void getData(boolean isRefresh) {
        KLog.e(mSpecialId);
        RetrofitService.getSpecial(mSpecialId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showLoading();
                    }
                })
                .compose(mView.<SpecialInfo>bindToLife())
                .flatMap(new Func1<SpecialInfo, Observable<SpecialItem>>() {
                    @Override
                    public Observable<SpecialItem> call(SpecialInfo specialBean) {
                        mView.loadBanner(specialBean);
                        return convertSpecialBeanToItem(specialBean);
                    }
                })
                .toList()
                .subscribe(new Subscriber<List<SpecialItem>>() {
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
                    public void onNext(List<SpecialItem> specialItems) {
                        mView.loadData(specialItems);
                    }
                });
    }

    @Override
    public void getMoreData() {

    }

    /**
     * 转换数据，接口数据有点乱，这里做一些处理
     *
     * @param specialBean
     * @return
     */
    private Observable<SpecialItem> convertSpecialBeanToItem(SpecialInfo specialBean) {
        // 这边 +1 是接口数据还有个 topicsplus 的字段可能是穿插在 topics 字段列表中间。这里没有处理 topicsplus
        final SpecialItem[] specialItems = new SpecialItem[specialBean.getTopics().size()];
        return Observable.from(specialBean.getTopics())
                // 获取头部
                .doOnNext(new Action1<TopicsEntity>() {
                    @Override
                    public void call(TopicsEntity topicsEntity) {
                        specialItems[topicsEntity.getIndex() - 1] = new SpecialItem(true,
                                topicsEntity.getIndex() + "/" + specialItems.length + " " + topicsEntity.getTname());
                    }
                })
                // 排序
                .toSortedList(new Func2<TopicsEntity, TopicsEntity, Integer>() {
                    @Override
                    public Integer call(TopicsEntity topicsEntity, TopicsEntity topicsEntity2) {
                        return topicsEntity.getIndex() - topicsEntity2.getIndex();
                    }
                })
                // 拆分
                .flatMap(new Func1<List<TopicsEntity>, Observable<TopicsEntity>>() {
                    @Override
                    public Observable<TopicsEntity> call(List<TopicsEntity> topicsEntities) {
                        return Observable.from(topicsEntities);
                    }
                })
                .flatMap(new Func1<TopicsEntity, Observable<SpecialItem>>() {
                    @Override
                    public Observable<SpecialItem> call(TopicsEntity topicsEntity) {
                        // 转换并在每个列表项增加头部
                        return Observable.from(topicsEntity.getDocs())
                                .map(new Func1<NewsItemInfo, SpecialItem>() {
                                    @Override
                                    public SpecialItem call(NewsItemInfo newsItemBean) {
                                        return new SpecialItem(newsItemBean);
                                    }
                                })
                                .startWith(specialItems[topicsEntity.getIndex() - 1]);
                    }
                });
    }
}
