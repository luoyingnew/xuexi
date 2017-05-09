package com.qinmr.util.ui.news.photoset;

import com.qinmr.mylibrary.callback.IBaseView;
import com.qinmr.util.api.bean.PhotoSetInfo;

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
