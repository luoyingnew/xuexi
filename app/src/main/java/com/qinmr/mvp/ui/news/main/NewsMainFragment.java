package com.qinmr.mvp.ui.news.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.qinmr.mvp.App;
import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.ViewPagerAdapter;
import com.qinmr.mvp.bus.ChannelEvent;
import com.qinmr.mvp.db.collect.DBNewsTypeInfoCollect;
import com.qinmr.mvp.db.table.NewsTypeInfo;
import com.qinmr.mvp.ui.base.BaseFragment;
import com.qinmr.mvp.ui.base.IRxBusPresenter;
import com.qinmr.mvp.ui.news.channel.ChannelActivity;
import com.qinmr.mvp.ui.news.newslist.NewsListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 新闻类主页面
 * Created by mrq on 2017/4/10.
 */

public class NewsMainFragment extends BaseFragment<IRxBusPresenter> implements INewsMainView {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private ViewPagerAdapter mPagerAdapter;

    @Override
    public int attachLayoutRes() {
        return R.layout.fragment_news_main;
    }

    @Override
    public void initData() {
        mPresenter = new NewsMainPresenter(this,App.getDaoSession().getNewsTypeInfoDao(),mRxBus);
        mPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
    }

    @Override
    public void initViews() {
        initToolBar(mToolBar, true, "新闻");
        setHasOptionsMenu(true);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void updateViews() {
//        getData();
        mPresenter.getData(false);
    }

    public void getData() {
        List<NewsTypeInfo> newsTypeInfo = DBNewsTypeInfoCollect.getNewsTypeInfo();
        if (newsTypeInfo.size() != 0) {
            List<Fragment> fragments = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (NewsTypeInfo bean : newsTypeInfo) {
                titles.add(bean.getName());
                fragments.add(NewsListFragment.newInstance(bean.getTypeId()));
            }
            mPagerAdapter.setItems(fragments, titles);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_channel, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_channel) {
            ChannelActivity.launch(mContext);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(ChannelEvent event) {
        updateViews();
    }

    @Override
    public void loadData(List<NewsTypeInfo> checkList) {
        if (checkList.size() != 0) {
            List<Fragment> fragments = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (NewsTypeInfo bean : checkList) {
                titles.add(bean.getName());
                fragments.add(NewsListFragment.newInstance(bean.getTypeId()));
            }
            mPagerAdapter.setItems(fragments, titles);
        }
    }
}
