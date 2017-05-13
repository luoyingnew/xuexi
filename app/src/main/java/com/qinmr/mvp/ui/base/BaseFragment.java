package com.qinmr.mvp.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qinmr.mvp.App;
import com.qinmr.mvp.R;
import com.qinmr.mvp.db.table.DaoSession;
import com.qinmr.mvp.rxbus.RxBus;
import com.qinmr.mvp.util.NetUtil;
import com.qinmr.mvp.util.SwipeRefreshHelper;
import com.qinmr.utillibrary.loading.LoadingLayout;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mrq on 2017/3/30.
 */

public abstract class BaseFragment<T extends IBasePresenter> extends RxFragment implements UiCallback, IBaseView, LoadingLayout.OnReloadListener {

    protected T mPresenter;

    protected Context mContext;
    //缓存Fragment view
    protected View mRootView;
    private boolean mIsMulti = false;

    protected RxBus mRxBus = new RxBus();
    protected DaoSession mDaoSession = new App().getDaoSession();

    /**
     * 注意，资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.empty_layout)
    public LoadingLayout mEmptyLayout;

    @Nullable
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            ButterKnife.bind(this, mRootView);
            initData();
            initViews();
            initSwipeRefresh();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews();
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Loading);
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, false);
        }
    }

    @Override
    public void hideLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Success);
            SwipeRefreshHelper.enableRefresh(mSwipeRefresh, true);
            SwipeRefreshHelper.controlRefresh(mSwipeRefresh, false);
        }
    }

    @Override
    public void showNetError() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Error);
            mEmptyLayout.setOnReloadListener(this);
        }
    }

    @Override
    public void showEmpty() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Empty);
        }
    }

    @Override
    public void showError() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Error);
            mEmptyLayout.setOnReloadListener(this);
        }
    }

    public void showNetView() {
        boolean networkAvailable = NetUtil.isNetworkAvailable(App.getContext());
        if (!networkAvailable) {
            showNetError();
        }
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        if (mSwipeRefresh != null) {
            SwipeRefreshHelper.init(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateViews();
                }
            });
        }
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity) getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }

    @Override
    public void onReload(View v) {
        updateViews();
    }
}
