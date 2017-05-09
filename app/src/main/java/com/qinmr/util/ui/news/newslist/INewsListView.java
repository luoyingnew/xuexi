package com.qinmr.util.ui.news.newslist;


import com.qinmr.mylibrary.callback.ILoadDataView;
import com.qinmr.util.adapter.item.NewsMultiItem;
import com.qinmr.util.api.bean.NewsInfo;

import java.util.List;

/**
 * Created by long on 2016/8/23.
 * 新闻列表视图接口
 */
public interface INewsListView extends ILoadDataView<List<NewsMultiItem>> {

    void loadHeadData(NewsInfo o);

}
