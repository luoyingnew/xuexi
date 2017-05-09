package com.qinmr.mvp.ui.news.article;


import com.qinmr.mvp.ui.base.IBaseView;
import com.qinmr.mvp.api.bean.NewsDetailInfo;

/**
 * Created by long on 2017/2/3.
 * 新闻详情接口
 */
public interface INewsArticleView extends IBaseView {

    /**
     * 显示数据
     * @param newsDetailBean 新闻详情
     */
    void loadData(NewsDetailInfo newsDetailBean);
}


