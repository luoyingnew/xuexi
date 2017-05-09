package com.qinmr.mvp.ui.news.newslist;


import com.qinmr.mvp.ui.base.ILoadDataView;
import com.qinmr.mvp.adapter.item.NewsMultiItem;
import com.qinmr.mvp.api.bean.NewsInfo;

import java.util.List;

/**
 * Created by long on 2016/8/23.
 * 新闻列表视图接口
 */
public interface INewsListView extends ILoadDataView<List<NewsMultiItem>> {

    void loadHeadData(NewsInfo o);

}
