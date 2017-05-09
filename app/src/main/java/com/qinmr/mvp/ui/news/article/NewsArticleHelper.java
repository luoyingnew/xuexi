package com.qinmr.mvp.ui.news.article;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.qinmr.utillibrary.logger.KLog;
import com.qinmr.mvp.api.OkgoService;
import com.qinmr.mvp.api.bean.NewsDetailInfo;
import com.qinmr.mvp.util.ListUtils;

import java.io.Reader;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by mrq on 2017/4/20.
 */

public class NewsArticleHelper {

    private static final String HTML_IMG_TEMPLATE = "<img src=\"http\" />";

    private final NewsArticleActivity mView;
    private final String mNewsId;

    public NewsArticleHelper(NewsArticleActivity activity, String mNewsId) {
        this.mView = activity;
        this.mNewsId = mNewsId;
    }

    public void getData() {
        String url = OkgoService.getNewsDetail(mNewsId);
        KLog.e(url);
        OkGo.get(url)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<NewsDetailInfo>() {

                    @Override
                    public NewsDetailInfo convertSuccess(Response response) throws Exception {
                        Reader reader = response.body().charStream();
                        Map<String, NewsDetailInfo> info = new Gson().fromJson(reader, new TypeToken<Map<String, NewsDetailInfo>>() {
                        }.getType());
                        response.close();
                        NewsDetailInfo newsDetailInfo = info.get(mNewsId);
                        handleRichTextWithImg(newsDetailInfo);
                        return info.get(mNewsId);
                    }

                    @Override
                    public void onSuccess(NewsDetailInfo info, Call call, Response response) {
                        mView.loadData(info);
                    }

                    @Override
                    public void onCacheSuccess(NewsDetailInfo info, Call call) {
                        mView.loadData(info);
                    }
                });
    }

    /**
     * 处理富文本包含图片的情况
     *
     * @param newsDetailBean 原始数据
     */
    private void handleRichTextWithImg(NewsDetailInfo newsDetailBean) {
        if (!ListUtils.isEmpty(newsDetailBean.getImg())) {
            String body = newsDetailBean.getBody();
            for (NewsDetailInfo.ImgEntity imgEntity : newsDetailBean.getImg()) {
                String ref = imgEntity.getRef();
                String src = imgEntity.getSrc();
                String img = HTML_IMG_TEMPLATE.replace("http", src);
                body = body.replaceAll(ref, img);
            }
            newsDetailBean.setBody(body);
        }
    }
}
