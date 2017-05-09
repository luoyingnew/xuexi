package com.qinmr.mvp.ui.video.list;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.qinmr.mvp.api.OkgoService;
import com.qinmr.mvp.db.table.VideoInfo;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 视频帮助类
 * Created by mrq on 2017/4/25.
 */

public class VideoListHelper {
    private final VideoListFragment mView;
    private final String mVideoId;
    private int mPage = 0;
    private Object moreData;

    public VideoListHelper(VideoListFragment fragment, String mVideoId) {
        this.mView = fragment;
        this.mVideoId = mVideoId;
    }

    public void getData() {
        String url = OkgoService.getVideoList(mVideoId, mPage);
        mView.showLoading();
        OkGo.get(url)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<VideoInfo>>() {

                    @Override
                    public List<VideoInfo> convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        Map<String, List<VideoInfo>> infos = new Gson().fromJson(reader, new TypeToken<Map<String, List<VideoInfo>>>() {
                        }.getType());
                        response.close();
                        return infos.get(mVideoId);
                    }

                    @Override
                    public void onSuccess(List<VideoInfo> photoInfos, Call call, Response response) {
                        mView.loadData(photoInfos);
                        mView.hideLoading();
                        mPage++;
                    }
                });
    }

    public void getMoreData() {
        String url = OkgoService.getVideoList(mVideoId, mPage);
        OkGo.get(url)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<VideoInfo>>() {

                    @Override
                    public List<VideoInfo> convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        Map<String, List<VideoInfo>> infos = new Gson().fromJson(reader, new TypeToken<Map<String, List<VideoInfo>>>() {
                        }.getType());
                        response.close();
                        return infos.get(mVideoId);
                    }

                    @Override
                    public void onSuccess(List<VideoInfo> photoInfos, Call call, Response response) {
                        mView.loadMoreData(photoInfos);
                        mPage++;
                    }
                });
    }
}
