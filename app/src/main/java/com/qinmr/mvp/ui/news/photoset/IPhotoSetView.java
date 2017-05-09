package com.qinmr.mvp.ui.news.photoset;

import com.qinmr.mvp.ui.base.IBaseView;
import com.qinmr.mvp.api.bean.PhotoSetInfo;

/**
 * Created by mrq on 2017/4/20.
 */

public interface IPhotoSetView extends IBaseView {

    /**
     * 显示数据
     * @param photoSetBean 图集
     */
    void loadData(PhotoSetInfo photoSetBean);

}
