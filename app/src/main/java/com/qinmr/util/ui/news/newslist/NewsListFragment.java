package com.qinmr.util.ui.news.newslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.qinmr.mylibrary.listener.OnRequestDataListener;
import com.qinmr.util.R;
import com.qinmr.util.adapter.NewsMultiListAdapter;
import com.qinmr.util.adapter.item.NewsMultiItem;
import com.qinmr.util.api.bean.NewsInfo;
import com.qinmr.util.helper.RecyclerViewHelper;
import com.qinmr.util.ui.base.BaseFragment;
import com.qinmr.util.util.SliderHelper;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by mrq on 2017/4/12.
 */

public class NewsListFragment extends BaseFragment implements INewsListView {

    private static final String NEWS_TYPE_KEY = "NewsTypeKey";

    @BindView(R.id.rv_news_list)
    RecyclerView mRvNewsList;

    private String mNewsId;
    private NewsListDataHelper mDataHelper;
    private NewsMultiListAdapter mAdapter;

    private SliderLayout mAdSlider;

    public static Fragment newInstance(String typeId) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_TYPE_KEY, typeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsId = getArguments().getString(NEWS_TYPE_KEY);
        }
        mDataHelper = new NewsListDataHelper(this, mNewsId);
    }

    @Override
    public int attachLayoutRes() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void initViews() {
        //检测网络状态
        showNetView();

        mAdapter = new NewsMultiListAdapter(mContext);
        SlideInBottomAnimationAdapter buttomAnimAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(buttomAnimAdapter);
        RecyclerViewHelper.initRecyclerViewV(mContext, mRvNewsList, alphaAdapter);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mDataHelper.getMoreData();
            }
        });
    }

    @Override
    public void updateViews() {
        mDataHelper.getData();
    }

    @Override
    public void loadData(List<NewsMultiItem> newsList) {
        mAdapter.updateItems(newsList);
    }

    @Override
    public void loadMoreData(List<NewsMultiItem> data) {
        mAdapter.loadComplete();
        mAdapter.addItems(data);
    }

    @Override
    public void loadNoData() {
        mAdapter.loadAbnormal();
    }

    @Override
    public void loadHeadData(NewsInfo o) {
        addHeadView(o);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdSlider != null) {
            mAdSlider.startAutoCycle();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdSlider != null) {
            mAdSlider.stopAutoCycle();
        }
    }


    private void addHeadView(NewsInfo newsInfo) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.head_news_list, null);
        mAdSlider = (SliderLayout) view.findViewById(R.id.slider_ads);
        SliderHelper.initAdSlider(mContext, mAdSlider, newsInfo);
        mAdapter.addHeaderView(view);
    }

}
