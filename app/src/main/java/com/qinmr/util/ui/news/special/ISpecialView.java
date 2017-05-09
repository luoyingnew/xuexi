package com.qinmr.util.ui.news.special;


import com.qinmr.mylibrary.callback.IBaseView;
import com.qinmr.util.adapter.item.SpecialItem;
import com.qinmr.util.api.bean.SpecialInfo;

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
