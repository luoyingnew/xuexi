package com.qinmr.mvp.ui.photo.news;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.qinmr.mvp.api.OkgoService;
import com.qinmr.mvp.api.bean.PhotoInfo;

import java.io.Reader;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by mrq on 2017/4/19.
 */

public class PhotoNewsHelper {


    private final PhotoNewsFragment mView;
    private String mNextSetId;


    public PhotoNewsHelper(PhotoNewsFragment view) {
        this.mView = view;
    }

    public void getData() {
        String newsPhotoUrl = OkgoService.getNewsPhotoUrl();
        mView.showLoading();
        OkGo.get(newsPhotoUrl)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<PhotoInfo>>() {

                    @Override
                    public List<PhotoInfo> convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        List<PhotoInfo> infos = new Gson().fromJson(reader, new TypeToken<List<PhotoInfo>>() {
                        }.getType());
                        response.close();
                        return infos;
                    }

                    @Override
                    public void onSuccess(List<PhotoInfo> photoInfos, Call call, Response response) {
                        mView.loadData(photoInfos);
                        mNextSetId = photoInfos.get(photoInfos.size() - 1).getSetid();
                        mView.hideLoading();
                    }
                });
    }

    public void getMoreData() {
        String url = OkgoService.getNewsMoreUrl(mNextSetId);
        OkGo.get(url)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<PhotoInfo>>() {

                    @Override
                    public List<PhotoInfo> convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        List<PhotoInfo> infos = new Gson().fromJson(reader, new TypeToken<List<PhotoInfo>>() {
                        }.getType());
                        response.close();
                        return infos;
                    }

                    @Override
                    public void onSuccess(List<PhotoInfo> photoInfos, Call call, Response response) {
                        mView.loadMoreData(photoInfos);
                        mNextSetId = photoInfos.get(photoInfos.size() - 1).getSetid();
                    }
                });
    }


}
