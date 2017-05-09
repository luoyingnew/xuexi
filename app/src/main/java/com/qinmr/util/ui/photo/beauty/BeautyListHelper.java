package com.qinmr.util.ui.photo.beauty;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.qinmr.mylibrary.callback.ILoadDataView;
import com.qinmr.mylibrary.loading.LoadingLayout;
import com.qinmr.mylibrary.logger.KLog;
import com.qinmr.util.api.OkgoService;
import com.qinmr.util.db.table.BeautyPhotoInfo;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by mrq on 2017/4/18.
 */

public class BeautyListHelper {

    private final ILoadDataView mView;
    private int mPage = 0;

    public BeautyListHelper(ILoadDataView view) {
        this.mView = view;
    }

    public void getData() {
        mView.showLoading();
        String url = OkgoService.getBeautyPhotoUrl(mPage);
        OkGo.get(url)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<BeautyPhotoInfo>>() {

                    @Override
                    public List<BeautyPhotoInfo> convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        Map<String, List<BeautyPhotoInfo>> map = new Gson()
                                .fromJson(reader, new TypeToken<Map<String, List<BeautyPhotoInfo>>>() {
                                }.getType());
                        response.close();
                        return map.get("美女");
                    }

                    @Override
                    public void onSuccess(List<BeautyPhotoInfo> newsInfos, Call call, Response response) {
                        mView.hideLoading();
                        mView.loadData(newsInfos);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mView.showError(new LoadingLayout.OnReloadListener() {
                            @Override
                            public void onReload(View v) {
                                getData();
                            }
                        });
                    }
                });
    }

    public void getMoreData() {
        String url = OkgoService.getBeautyPhotoUrl(mPage);
        KLog.e(url);
        OkGo.get(url)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)// 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<BeautyPhotoInfo>>() {

                    @Override
                    public List<BeautyPhotoInfo> convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        Map<String, List<BeautyPhotoInfo>> map = new Gson()
                                .fromJson(reader, new TypeToken<Map<String, List<BeautyPhotoInfo>>>() {
                                }.getType());
                        response.close();
                        return map.get("美女");
                    }

                    @Override
                    public void onSuccess(List<BeautyPhotoInfo> newsInfos, Call call, Response response) {
                        mPage++;
                        mView.loadMoreData(newsInfos);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mView.loadNoData();
                    }
                });
    }

}
