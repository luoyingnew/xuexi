package com.qinmr.mvp.ui.video.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.ViewPagerAdapter;
import com.qinmr.mvp.ui.base.BaseFragment;
import com.qinmr.mvp.ui.video.list.VideoListFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by mrq on 2017/4/25.
 */

public class VideoMainFragment extends BaseFragment {

    private final String[] VIDEO_ID = new String[]{
            "V9LG4B3A0", "V9LG4E6VR", "V9LG4CHOR", "00850FRB"
    };
    private final String[] VIDEO_TITLE = new String[]{
            "热点", "搞笑", "娱乐", "精品"
    };

    @BindView(R.id.iv_love_count)
    TextView mIvLoveCount;
    @BindView(R.id.fl_love_layout)
    FrameLayout mFlLoveLayout;
    @BindView(R.id.tv_download_count)
    TextView mTvDownloadCount;
    @BindView(R.id.fl_download_layout)
    FrameLayout mFlDownloadLayout;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    ViewPagerAdapter mPagerAdapter;

    @Override
    public int attachLayoutRes() {
        return R.layout.fragment_video_main;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        initToolBar(mToolBar, true, "视频");
        mPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
    }

    @Override
    public void updateViews() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < VIDEO_ID.length; i++) {
            fragments.add(VideoListFragment.newInstance(VIDEO_ID[i]));
        }
        mPagerAdapter.setItems(fragments, VIDEO_TITLE);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(1);
    }
}
