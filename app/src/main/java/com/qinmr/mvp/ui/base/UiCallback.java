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

    void updateViews();

}
