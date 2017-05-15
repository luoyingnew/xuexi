package com.qinmr.mvp.ui.base;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * Created by long on 2016/8/23.
 * 基础 BaseView 接口
 */
public interface IBaseView {

    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示空页面
     */
    void showEmpty();

    /**
     * 显示错误页面
     */
    void showError();

    /**
     * 显示网络错误
     */
    void showNetError();

    /**
     * 绑定生命周期
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();

}
