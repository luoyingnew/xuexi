package com.qinmr.util.ui.news.article;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qinmr.util.R;
import com.qinmr.util.api.NewsUtils;
import com.qinmr.util.api.bean.NewsDetailInfo;
import com.qinmr.util.ui.base.BaseSwipeBackActivity;
import com.qinmr.util.util.AnimateHelper;
import com.qinmr.util.util.ListUtils;
import com.qinmr.util.widgets.PullScrollView;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnURLClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mrq on 2017/4/20.
 */

public class NewsArticleActivity extends BaseSwipeBackActivity implements INewsArticleView {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_pre_toolbar)
    LinearLayout mLlPreToolbar;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_next_title)
    TextView mTvNextTitle;
    @BindView(R.id.ll_foot_view)
    LinearLayout mLlFootView;
    @BindView(R.id.scroll_view)
    PullScrollView mScrollView;
    @BindView(R.id.iv_back_2)
    ImageView mIvBack2;
    @BindView(R.id.tv_title_2)
    TextView mTvTitle2;
    @BindView(R.id.ll_top_bar)
    LinearLayout mLlTopBar;

    private int mToolbarHeight;
    private int mTopBarHeight;
    private Animator mTopBarAnimator;
    private int mLastScrollY = 0;
    // 最小触摸滑动距离
    private int mMinScrollSlop;
    private String mNewsId;
    private String mNextNewsId;

    private static final String SHOW_POPUP_DETAIL = "ShowPopupDetail";
    private static final String NEWS_ID_KEY = "NewsIdKey";
    private NewsArticleHelper helper;

    public static void launch(Context context, String newsId) {
        Intent intent = new Intent(context, NewsArticleActivity.class);
        intent.putExtra(NEWS_ID_KEY, newsId);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    private void launchInside(String newsId) {
        Intent intent = new Intent(this, NewsArticleActivity.class);
        intent.putExtra(NEWS_ID_KEY, newsId);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold);
    }

    @Override
    public int attachLayoutRes() {
        return R.layout.activity_news_article;
    }

    @Override
    public void initViews() {
        mNewsId = getIntent().getStringExtra(NEWS_ID_KEY);
        helper = new NewsArticleHelper(this,mNewsId);
        mToolbarHeight = getResources().getDimensionPixelSize(R.dimen.news_detail_toolbar_height);
        mTopBarHeight = getResources().getDimensionPixelSize(R.dimen.toolbar_height);
        mMinScrollSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        ViewCompat.setTranslationY(mLlTopBar, -mTopBarHeight);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > mToolbarHeight) {
                    if (AnimateHelper.isRunning(mTopBarAnimator)) {
                        return;
                    }
                    if (Math.abs(scrollY - mLastScrollY) > mMinScrollSlop) {
                        boolean isPullUp = scrollY > mLastScrollY;
                        mLastScrollY = scrollY;
                        if (isPullUp && mLlTopBar.getTranslationY() != -mTopBarHeight) {
                            mTopBarAnimator = AnimateHelper.doMoveVertical(mLlTopBar, (int) mLlTopBar.getTranslationY(),
                                    -mTopBarHeight, 300);
                        } else if (!isPullUp && mLlTopBar.getTranslationY() != 0) {
                            mTopBarAnimator = AnimateHelper.doMoveVertical(mLlTopBar, (int) mLlTopBar.getTranslationY(),
                                    0, 300);
                        }
                    }
                } else {
                    if (mLlTopBar.getTranslationY() != -mTopBarHeight) {
                        AnimateHelper.stopAnimator(mTopBarAnimator);
                        ViewCompat.setTranslationY(mLlTopBar, -mTopBarHeight);
                        mLastScrollY = 0;
                    }
                }
            }
        });
        mScrollView.setFootView(mLlFootView);
        mScrollView.setPullListener(new PullScrollView.OnPullListener() {
//            boolean isShowPopup = PreferencesUtils.getBoolean(NewsArticleActivity.this, SHOW_POPUP_DETAIL, true);

            @Override
            public boolean isDoPull() {
//                if (mEmptyLayout.getEmptyStatus() != EmptyLayout.STATUS_HIDE) {
//                    return false;
//                }
//                if (isShowPopup) {
////                    _showPopup();
//                    isShowPopup = false;
//                }
                return true;
            }

            @Override
            public boolean handlePull() {
                if (TextUtils.isEmpty(mNextNewsId)) {
                    return false;
                } else {
                    launchInside(mNextNewsId);
                    return true;
                }
            }
        });
    }

    @Override
    public void updateViews() {
        helper.getData();
    }

    @Override
    public void loadData(NewsDetailInfo newsDetailBean) {
        mTvTitle.setText(newsDetailBean.getTitle());
        mTvTitle2.setText(newsDetailBean.getTitle());
        mTvTime.setText(newsDetailBean.getPtime());
        RichText.from(newsDetailBean.getBody())
                .into(mTvContent);
        handleSpInfo(newsDetailBean.getSpinfo());
        handleRelatedNews(newsDetailBean);
    }

    /**
     * 处理关联的内容
     *
     * @param spinfo
     */
    private void handleSpInfo(List<NewsDetailInfo.SpinfoEntity> spinfo) {
        if (!ListUtils.isEmpty(spinfo)) {
            ViewStub stub = (ViewStub) findViewById(R.id.vs_related_content);
            assert stub != null;
            stub.inflate();
            TextView tvType = (TextView) findViewById(R.id.tv_type);
            TextView tvRelatedContent = (TextView) findViewById(R.id.tv_related_content);
            tvType.setText(spinfo.get(0).getSptype());
            RichText.from(spinfo.get(0).getSpcontent())
                    // 这里处理超链接的点击
                    .urlClick(new OnURLClickListener() {
                        @Override
                        public boolean urlClicked(String url) {
                            String newsId = NewsUtils.clipNewsIdFromUrl(url);
                            if (newsId != null) {
                                launch(NewsArticleActivity.this, newsId);
                            }
                            return true;
                        }
                    })
                    .into(tvRelatedContent);
        }
    }

    /**
     * 处理关联新闻
     *
     * @param newsDetailBean
     */
    private void handleRelatedNews(NewsDetailInfo newsDetailBean) {
        if (ListUtils.isEmpty(newsDetailBean.getRelative_sys())) {
            mTvNextTitle.setText("没有相关文章了");
        } else {
            mNextNewsId = newsDetailBean.getRelative_sys().get(0).getId();
            mTvNextTitle.setText(newsDetailBean.getRelative_sys().get(0).getTitle());
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_back_2, R.id.tv_title_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.iv_back_2:
                finish();
                break;
            case R.id.tv_title_2:
                mScrollView.stopNestedScroll();
                mScrollView.smoothScrollTo(0, 0);
                break;
        }
    }

}
