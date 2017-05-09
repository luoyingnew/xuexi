package com.qinmr.util.adapter.item;


import com.qinmr.mylibrary.entity.SectionEntity;
import com.qinmr.util.api.bean.NewsItemInfo;

/**
 * Created by long on 2016/8/26.
 * 专题列表项
 */
public class SpecialItem extends SectionEntity<NewsItemInfo> {

    public SpecialItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SpecialItem(NewsItemInfo newsItemBean) {
        super(newsItemBean);
    }
}
