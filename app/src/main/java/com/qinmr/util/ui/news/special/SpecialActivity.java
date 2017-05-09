package com.qinmr.util.ui.news.special;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.qinmr.util.R;
import com.qinmr.util.adapter.item.SpecialItem;
import com.qinmr.util.api.bean.SpecialInfo;
import com.qinmr.util.ui.base.BaseSwipeBackActivity;

import java.util.List;

import butterknife.BindView;

public class SpecialActivity extends BaseSwipeBackActivity implements ISpecialView {

    private static final String SPECIAL_KEY = "SpecialKey";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_news_list)
    RecyclerView mRvNewsList;
    @BindView(R.id.fab_coping)
    FloatingActionButton mFabCoping;

    private String mSpecialId;
    private final int[] mSkipId = new int[20];
    private LinearLayoutManager mLayoutManager;
    private SpecialHelper helper;

    public static void launch(Context context, String newsId) {
        Intent intent = new Intent(context, SpecialActivity.class);
        intent.putExtra(SPECIAL_KEY, newsId);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    public int attachLayoutRes() {
        return R.layout.activity_special;
    }

    @Override
    public void initViews() {
        mSpecialId = getIntent().getStringExtra(SPECIAL_KEY);
        helper = new SpecialHelper(this, mSpecialId);
        initToolBar(mToolbar, true, "");

//        ScaleInAnimationAdapter animAdapter = new ScaleInAnimationAdapter(mSpecialAdapter);
//        RecyclerViewHelper.initRecyclerViewV(this, mRvNewsList, new AlphaInAnimationAdapter(animAdapter));
//        mLayoutManager = (LinearLayoutManager) mRvNewsList.getLayoutManager();
    }

    @Override
    public void updateViews() {
        helper.getData();
    }

    @Override
    public void loadData(List<SpecialItem> specialItems) {

    }

    @Override
    public void loadBanner(SpecialInfo specialBean) {

    }
}
