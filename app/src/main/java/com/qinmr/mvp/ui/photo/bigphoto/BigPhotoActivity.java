package com.qinmr.mvp.ui.photo.bigphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dl7.drag.DragSlopLayout;
import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.PhotoPagerAdapter;
import com.qinmr.mvp.db.table.WelfarePhotoInfo;
import com.qinmr.mvp.ui.base.BaseActivity;
import com.qinmr.mvp.ui.base.ILoadDataView;
import com.qinmr.mvp.ui.base.ILocalPresenter;
import com.qinmr.mvp.util.AnimateHelper;
import com.qinmr.mvp.util.NavUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  本来之前用的美女的数据做收藏美女的数据接口无效了
 *  现在改福利的数据，福利数据又干货IO提供的接口
 *  返回的字段包含id，和greenDao使用冲突，暂时放下这部分功能
 *
 * Created by mrq on 2017/5/9.
 */

public class BigPhotoActivity extends BaseActivity<ILocalPresenter> implements ILoadDataView<List<WelfarePhotoInfo>> {

    private static final String BIG_PHOTO_KEY = "BigPhotoKey";
    private static final String PHOTO_INDEX_KEY = "PhotoIndexKey";
    private static final String FROM_LOVE_ACTIVITY = "FromLoveActivity";

    @BindView(R.id.vp_photo)
    ViewPager mVpPhoto;
    @BindView(R.id.iv_favorite)
    ImageView mIvFavorite;
    @BindView(R.id.iv_download)
    ImageView mIvDownload;
    @BindView(R.id.iv_praise)
    ImageView mIvPraise;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drag_layout)
    DragSlopLayout mDragLayout;
    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;

    private List<WelfarePhotoInfo> mPhotoList;
    private int mIndex; // 初始索引
    private boolean mIsFromLoveActivity;    // 是否从 LoveActivity 启动进来
    private boolean mIsHideToolbar = false; // 是否隐藏 Toolbar
    private boolean mIsInteract = false;    // 是否和 ViewPager 联动
    private int mCurPosition;   // Adapter 当前位置
    private boolean[] mIsDelLove;   // 保存被删除的收藏项

    private PhotoPagerAdapter mAdapter;

    public static void launch(Context context, ArrayList<WelfarePhotoInfo> datas, int index) {
        Intent intent = new Intent(context, BigPhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BIG_PHOTO_KEY, datas);
        bundle.putInt(PHOTO_INDEX_KEY, index);
        bundle.putBoolean(FROM_LOVE_ACTIVITY, false);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.expand_vertical_entry, R.anim.hold);
    }

//    // 这个给 LoveActivity 使用，配合 setResult() 返回取消的收藏，这样做体验会好点，其实用 RxBus 会更容易做
//    public static void launchForResult(Fragment fragment, ArrayList<BeautyPhotoInfo> datas, int index) {
//        Intent intent = new Intent(fragment.getContext(), BigPhotoActivity.class);
//        intent.putParcelableArrayListExtra(BIG_PHOTO_KEY, datas);
//        intent.putExtra(PHOTO_INDEX_KEY, index);
//        intent.putExtra(FROM_LOVE_ACTIVITY, true);
//        fragment.startActivityForResult(intent, 10086);
//        fragment.getActivity().overridePendingTransition(R.anim.expand_vertical_entry, R.anim.hold);
//    }

    @Override
    public int attachLayoutRes() {
        return R.layout.activity_big_photo;
    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();
        mPhotoList = (ArrayList<WelfarePhotoInfo>) extras.getSerializable(BIG_PHOTO_KEY);
        mIndex = extras.getInt(PHOTO_INDEX_KEY, 0);
        mIsFromLoveActivity = extras.getBoolean(FROM_LOVE_ACTIVITY, false);
        mPresenter = new BigPhotoPresenter(this, mPhotoList, mDaoSession.getWelfarePhotoInfoDao(), mRxBus);
        mAdapter = new PhotoPagerAdapter(BigPhotoActivity.this);
    }

    @Override
    public void initViews() {
        initToolBar(mToolbar, true, "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //空出底部导航的高度，因为 NavigationBar 是透明的
            mLlLayout.setPadding(0, 0, 0, NavUtils.getNavigationBarHeight(this));
        }
        mVpPhoto.setAdapter(mAdapter);
        // 设置是否 ViewPager 联动和动画
        mDragLayout.interactWithViewPager(mIsInteract);
        mDragLayout.setAnimatorMode(DragSlopLayout.FLIP_Y);
        mAdapter.setTapListener(new PhotoPagerAdapter.OnTapListener() {
            @Override
            public void onPhotoClick() {
                mIsHideToolbar = !mIsHideToolbar;
                if (mIsHideToolbar) {
                    mDragLayout.startOutAnim();
                    mToolbar.animate().translationY(-mToolbar.getBottom()).setDuration(300);
                } else {
                    mDragLayout.startInAnim();
                    mToolbar.animate().translationY(0).setDuration(300);
                }
            }
        });
        if (!mIsFromLoveActivity) {
            mAdapter.setLoadMoreListener(new PhotoPagerAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    mPresenter.getMoreData();
                }
            });
        } else {
            // 收藏界面不需要加载更多
            mIsDelLove = new boolean[mPhotoList.size()];
        }
        mVpPhoto.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurPosition = position;
                // 设置图标状态
                mIvFavorite.setSelected(mAdapter.isLoved(position));
                mIvDownload.setSelected(mAdapter.isDownload(position));
                mIvPraise.setSelected(mAdapter.isPraise(position));
            }
        });
    }

    @Override
    public void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<WelfarePhotoInfo> data) {
        mAdapter.updateData(data);
        mVpPhoto.setCurrentItem(mIndex);
        if (mIndex == 0) {
            // 为 0 不会回调 addOnPageChangeListener，所以这里要处理下
            mIvFavorite.setSelected(mAdapter.isLoved(0));
            mIvDownload.setSelected(mAdapter.isDownload(0));
            mIvPraise.setSelected(mAdapter.isPraise(0));
        }
    }

    @Override
    public void loadMoreData(List<WelfarePhotoInfo> data) {
        mAdapter.addData(data);
        mAdapter.startUpdate(mVpPhoto);
    }

    @Override
    public void loadNoData() {

    }

    @OnClick({R.id.iv_favorite, R.id.iv_praise, R.id.iv_share})
    public void onClick(final View view) {
        final boolean isSelected = !view.isSelected();
        switch (view.getId()) {
            case R.id.iv_favorite:
                mAdapter.getData(mCurPosition).setLove(isSelected);
                break;
            case R.id.iv_praise:
                mAdapter.getData(mCurPosition).setPraise(isSelected);
                break;
            case R.id.iv_share:
//                ToastUtils.showToast("分享:功能没加(╯-╰)");
                break;
        }
        // 除分享外都做动画和数据库处理
        if (view.getId() != R.id.iv_share) {
            view.setSelected(isSelected);
            AnimateHelper.doHeartBeat(view, 500);
            if (isSelected) {
                mPresenter.insert(mAdapter.getData(mCurPosition));
            } else {
                mPresenter.delete(mAdapter.getData(mCurPosition));
            }
        }
        if (mIsFromLoveActivity && view.getId() == R.id.iv_favorite) {
            // 不选中即去除收藏
            mIsDelLove[mCurPosition] = !isSelected;
        }
    }
}
