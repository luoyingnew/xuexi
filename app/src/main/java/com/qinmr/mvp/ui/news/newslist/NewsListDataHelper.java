package com.qinmr.mvp.ui.news.newslist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.qinmr.mvp.adapter.item.NewsMultiItem;
import com.qinmr.mvp.api.NewsUtils;
import com.qinmr.mvp.api.OkgoService;
import com.qinmr.mvp.api.bean.NewsInfo;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by mrq on 2017/4/12.
 */

public class NewsListDataHelper {

    private final NewsListFragment mView;
    private final String mNewsId;
    int page = 0;

    public NewsListDataHelper(NewsListFragment fragment, String mNewsId) {
        this.mView = fragment;
        this.mNewsId = mNewsId;
    }

    /**
     * 获取数据
     */
    public void getData() {
        String url = OkgoService.getNewsUrl(mNewsId, page);
        mView.showLoading();
        OkGo.get(url)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<NewsInfo>>() {

                    @Override
                    public List<NewsInfo> convertSuccess(Response response) throws Exception {
                        return AnalysisData(response);
                    }

                    @Override
                    public void onSuccess(List<NewsInfo> o, Call call, Response response) {
                        page++;
                        //头部数据
                        NewsInfo newsInfo = o.subList(0, 1).get(0);
                        if (NewsUtils.isAbNews(newsInfo)) {
                            mView.loadHeadData(newsInfo);
                            List<NewsInfo> newsInfos = o.subList(1, o.size());
                            mView.loadData(packNews(newsInfos));
                        } else {
                            mView.loadData(packNews(o));
                        }
                        mView.hideLoading();
                    }

                });
    }

    /**
     * 获取更多数据
     */
    public void getMoreData() {
        String url = OkgoService.getNewsUrl(mNewsId, page);
        OkGo.get(url)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<NewsInfo>>() {

                    @Override
                    public List<NewsInfo> convertSuccess(Response response) throws Exception {
                        return AnalysisData(response);
                    }

                    @Override
                    public void onSuccess(List<NewsInfo> o, Call call, Response response) {
                        page++;
                        mView.loadMoreData(packNews(o));
                    }

                });
    }

    /**
     * 封装成一个NewsMultiItem对象
     *
     * @param newsInfos
     */
    private List<NewsMultiItem> packNews(List<NewsInfo> newsInfos) {
        List<NewsMultiItem> list = new ArrayList<>();
        for (NewsInfo info : newsInfos) {
            NewsMultiItem a;
            if (NewsUtils.isNewsPhotoSet(info.getSkipType())) {
                a = new NewsMultiItem(NewsMultiItem.ITEM_TYPE_PHOTO_SET, info);
            } else {
                a = new NewsMultiItem(NewsMultiItem.ITEM_TYPE_NORMAL, info);
            }
            list.add(a);
        }
        return list;
    }

    /**
     * 解析网络数据
     *
     * @param response
     * @return
     */
    private List<NewsInfo> AnalysisData(Response response) {
        Reader reader = response.body().charStream();
        Map<String, List<NewsInfo>> map = new Gson()
                .fromJson(reader, new TypeToken<Map<String, List<NewsInfo>>>() {
                }.getType());
        response.close();
        return map.get(mNewsId);
    }
}
