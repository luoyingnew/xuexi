package com.qinmr.mvp.ui.base;

import android.support.annotation.LayoutRes;

/**
 * Created by mrq on 2017/4/10.
 */

public interface UiCallback {

    @LayoutRes
    int attachLayoutRes();

    void initData();

    void initViews();

    /**
     * 更新视图控件
     * @param isRefresh 新增参数，用来判断是否为下拉刷新调用，下拉刷新的时候不应该再显示加载界面和异常界面
     */
    void updateViews(boolean isRefresh);

}
