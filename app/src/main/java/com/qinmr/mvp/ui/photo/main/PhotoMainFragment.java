package com.qinmr.mvp.ui.photo.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.ViewPagerAdapter;
import com.qinmr.mvp.ui.base.BaseFragment;
import com.qinmr.mvp.ui.photo.beauty.BeautyListFragment;
import com.qinmr.mvp.ui.photo.news.PhotoNewsFragment;
import com.qinmr.mvp.ui.photo.welfare.WelfareListFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by mrq on 2017/4/11.
 */

public class PhotoMainFragment extends BaseFragment {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.iv_count)
    TextView mIvCount;

    ViewPagerAdapter mPagerAdapter;

    @Override
    public int attachLayoutRes() {
        return R.layout.fragment_photo_main;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        initToolBar(mToolBar, true, "图片");
        mPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new BeautyListFragment());
        fragments.add(new WelfareListFragment());
        fragments.add(new PhotoNewsFragment());
        mPagerAdapter.setItems(fragments, new String[] {"美女", "福利", "生活"});
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(1);
//        mPresenter.registerRxBus(LoveEvent.class, new Action1<LoveEvent>() {
//            @Override
//            public void call(LoveEvent loveEvent) {
//                mPresenter.getData();
//            }
//        });
    }

    @Override
    public void updateViews(boolean isRefresh) {

    }
}
