package com.qinmr.mvp.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.qinmr.utillibrary.loading.LoadingLayout;
import com.qinmr.mvp.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mrq on 2017/4/10.
 */

public abstract class BaseActivity extends AutoLayoutActivity implements UiCallback , IBaseView {

    /**
     * 把 EmptyLayout 放在基类统一处理，@Nullable 表明 View 可以为 null，详细可看 ButterKnife
     */
    @Nullable
    @BindView(R.id.empty_layout)
    protected LoadingLayout mEmptyLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        ButterKnife.bind(this);
        initData();
        initViews();
        updateViews();
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            getSupportFragmentManager().popBackStack(tag, 0);
        }
    }

    /**
     * 跳转activity
     *
     * @param context
     * @param clazz
     */
    protected void showActivity(Context context, Class clazz) {
        showActivity(context, clazz, null);
    }

    protected void showActivity(Context context, Class clazz, @Nullable Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle));
    }


    @Override
    public void showLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Loading);
        }
    }

    @Override
    public void hideLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Success);
        }
    }

    @Override
    public void showNetError(LoadingLayout.OnReloadListener onRetryListener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Error);
            mEmptyLayout.setOnReloadListener(onRetryListener);
        }
    }

    @Override
    public void showEmpty() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Empty);
        }
    }

    @Override
    public void showError(LoadingLayout.OnReloadListener onRetryListener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setStatus(LoadingLayout.Error);
            mEmptyLayout.setOnReloadListener(onRetryListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
