package com.qinmr.mvp.ui.news.special;


import com.qinmr.mvp.ui.base.IBaseView;
import com.qinmr.mvp.adapter.item.SpecialItem;
import com.qinmr.mvp.api.bean.SpecialInfo;

import java.util.List;

/**
 * Created by long on 2016/8/26.
 * 专题View接口
 */
public interface ISpecialView extends IBaseView {

    /**
     * 显示数据
     * @param specialItems 新闻
     */
    void loadData(List<SpecialItem> specialItems);

    /**
     * 添加头部
     * @param specialBean
     */
    void loadBanner(SpecialInfo specialBean);
}
