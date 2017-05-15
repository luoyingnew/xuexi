package com.qinmr.mvp.ui.news.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.ViewPagerAdapter;
import com.qinmr.mvp.db.table.NewsTypeInfo;
import com.qinmr.mvp.rxbus.event.ChannelEvent;
import com.qinmr.mvp.ui.base.BaseFragment;
import com.qinmr.mvp.ui.base.IRxBusPresenter;
import com.qinmr.mvp.ui.news.channel.ChannelActivity;
import com.qinmr.mvp.ui.news.newslist.NewsListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;


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
        mPresenter = new NewsMainPresenter(this, mDaoSession.getNewsTypeInfoDao(), mRxBus);
        mPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
    }

    @Override
    public void initViews() {
        initToolBar(mToolBar, true, "新闻");
        setHasOptionsMenu(true);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mPresenter.registerRxBus(ChannelEvent.class, new Action1<ChannelEvent>() {
            @Override
            public void call(ChannelEvent channelEvent) {
                handleChannelEvent(channelEvent);
            }
        });
    }

    /**
     * 处理频道事件
     *
     * @param channelEvent
     */
    private void handleChannelEvent(ChannelEvent channelEvent) {
        switch (channelEvent.eventType) {
            case ChannelEvent.ADD_EVENT:
                mPagerAdapter.addItem(NewsListFragment.newInstance(channelEvent.newsInfo.getTypeId()), channelEvent.newsInfo.getName());
                break;
            case ChannelEvent.DEL_EVENT:
                // 如果是删除操作直接切换第一项，不然容易出现加载到不存在的Fragment
                mViewPager.setCurrentItem(0);
                mPagerAdapter.delItem(channelEvent.newsInfo.getName());
                break;
            case ChannelEvent.SWAP_EVENT:
                mPagerAdapter.swapItems(channelEvent.fromPos, channelEvent.toPos);
                break;
        }
    }

    @Override
    public void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
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
        mPresenter.unregisterRxBus();
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
