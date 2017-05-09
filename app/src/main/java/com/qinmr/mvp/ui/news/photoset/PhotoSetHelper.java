package com.qinmr.mvp.ui.news.photoset;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.qinmr.utillibrary.logger.KLog;
import com.qinmr.mvp.api.OkgoService;
import com.qinmr.mvp.api.bean.PhotoSetInfo;
import com.qinmr.mvp.util.StringUtils;

import java.io.Reader;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by mrq on 2017/4/20.
 */

public class PhotoSetHelper {

    private final PhotoSetActivity mView;
    private final String mPhotoSetId;

    public PhotoSetHelper(PhotoSetActivity activity, String mPhotoSetId) {
        this.mView = activity;
        this.mPhotoSetId = mPhotoSetId;
    }

    public void getData() {
        String url = OkgoService.getPhotoSet(StringUtils.clipPhotoSetId(mPhotoSetId));
        KLog.e(url);
        OkGo.get(url)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<PhotoSetInfo>() {

                    @Override
                    public PhotoSetInfo convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        PhotoSetInfo info = new Gson().fromJson(reader, PhotoSetInfo.class);
                        response.close();
                        return info;
                    }

                    @Override
                    public void onSuccess(PhotoSetInfo info, Call call, Response response) {
                        mView.loadData(info);
                    }

                    @Override
                    public void onCacheSuccess(PhotoSetInfo info, Call call) {
                        mView.loadData(info);
                    }
                });
    }
}
