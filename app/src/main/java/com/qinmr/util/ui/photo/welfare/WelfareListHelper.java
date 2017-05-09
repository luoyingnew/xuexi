package com.qinmr.util.ui.photo.welfare;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.qinmr.mylibrary.logger.KLog;
import com.qinmr.util.api.OkgoService;
import com.qinmr.util.api.bean.WelfarePhotoInfo;
import com.qinmr.util.api.bean.WelfarePhotoList;

import java.io.File;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by mrq on 2017/4/18.
 */

public class WelfareListHelper {

    private final WelfareListFragment mView;
    private int mPage = 1;

    public WelfareListHelper(WelfareListFragment view) {
        this.mView = view;
    }


    public void getData() {
        mView.showLoading();
        String welfarePhotoUrl = OkgoService.getWelfarePhoto(mPage);
        KLog.e(welfarePhotoUrl);
        OkGo.get(welfarePhotoUrl)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<WelfarePhotoInfo>>() {

                    @Override
                    public List<WelfarePhotoInfo> convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        WelfarePhotoList data = new Gson()
                                .fromJson(reader, new TypeToken<WelfarePhotoList>() {
                                }.getType());
                        response.close();
                        List<WelfarePhotoInfo> results = data.getResults();
                        for (WelfarePhotoInfo welfarePhotoInfo : results) {
                            welfarePhotoInfo.setPixel(calePhotoSize(mView.getContext(), welfarePhotoInfo.getUrl()));
                        }
                        return results;
                    }

                    @Override
                    public void onSuccess(List<WelfarePhotoInfo> info, Call call, Response response) {
                        mView.hideLoading();
                        mView.loadData(info);
                    }
                });
    }


    public void getMoreData() {
        String welfarePhotoUrl = OkgoService.getWelfarePhoto(mPage);
        OkGo.get(welfarePhotoUrl)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<WelfarePhotoInfo>>() {

                    @Override
                    public List<WelfarePhotoInfo> convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        WelfarePhotoList data = new Gson()
                                .fromJson(reader, new TypeToken<WelfarePhotoList>() {
                                }.getType());
                        response.close();
                        List<WelfarePhotoInfo> results = data.getResults();
                        KLog.e(results.size());
                        if (results.size() == 0) {
                            mView.loadNoData();
                        }
                        for (WelfarePhotoInfo welfarePhotoInfo : results) {
                            welfarePhotoInfo.setPixel(calePhotoSize(mView.getContext(), welfarePhotoInfo.getUrl()));
                        }
                        return results;
                    }

                    @Override
                    public void onSuccess(List<WelfarePhotoInfo> info, Call call, Response response) {
                        mPage++;
                        mView.loadMoreData(info);
                    }
                });
    }

    /**
     * 计算图片分辨率
     *
     * @param context
     * @param url
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String calePhotoSize(Context context, String url) throws ExecutionException, InterruptedException {
        File file = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return options.outWidth + "*" + options.outHeight;
    }

}
