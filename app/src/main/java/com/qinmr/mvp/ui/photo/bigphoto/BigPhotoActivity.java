package com.qinmr.mvp.ui.photo.bigphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dl7.drag.DragSlopLayout;
import com.qinmr.mvp.ui.base.ILoadDataView;
import com.qinmr.mvp.R;
import com.qinmr.mvp.db.table.BeautyPhotoInfo;
import com.qinmr.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mrq on 2017/5/9.
 */

public class BigPhotoActivity extends BaseActivity implements ILoadDataView<List<BeautyPhotoInfo>> {

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

    private List<BeautyPhotoInfo> mPhotoList;
    private int mIndex; // 初始索引
    private boolean mIsFromLoveActivity;    // 是否从 LoveActivity 启动进来
    private boolean mIsHideToolbar = false; // 是否隐藏 Toolbar
    private boolean mIsInteract = false;    // 是否和 ViewPager 联动
    private int mCurPosition;   // Adapter 当前位置
    private boolean[] mIsDelLove;   // 保存被删除的收藏项
    private BigPhotoHelper helper;

    public static void launch(Context context, ArrayList<BeautyPhotoInfo> datas, int index) {
        Intent intent = new Intent(context, BigPhotoActivity.class);
        intent.putParcelableArrayListExtra(BIG_PHOTO_KEY, datas);
        intent.putExtra(PHOTO_INDEX_KEY, index);
        intent.putExtra(FROM_LOVE_ACTIVITY, false);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.expand_vertical_entry, R.anim.hold);
    }

    // 这个给 LoveActivity 使用，配合 setResult() 返回取消的收藏，这样做体验会好点，其实用 RxBus 会更容易做
    public static void launchForResult(Fragment fragment, ArrayList<BeautyPhotoInfo> datas, int index) {
        Intent intent = new Intent(fragment.getContext(), BigPhotoActivity.class);
        intent.putParcelableArrayListExtra(BIG_PHOTO_KEY, datas);
        intent.putExtra(PHOTO_INDEX_KEY, index);
        intent.putExtra(FROM_LOVE_ACTIVITY, true);
        fragment.startActivityForResult(intent, 10086);
        fragment.getActivity().overridePendingTransition(R.anim.expand_vertical_entry, R.anim.hold);
    }

    @Override
    public int attachLayoutRes() {
        return R.layout.activity_big_photo;
    }

    @Override
    public void initData() {
        mPhotoList = getIntent().getParcelableArrayListExtra(BIG_PHOTO_KEY);
        mIndex = getIntent().getIntExtra(PHOTO_INDEX_KEY, 0);
        mIsFromLoveActivity = getIntent().getBooleanExtra(FROM_LOVE_ACTIVITY, false);
        helper = new BigPhotoHelper(this, mPhotoList);
    }

    @Override
    public void initViews() {
        initToolBar(mToolbar, true, "");
    }

    @Override
    public void updateViews() {

    }

    @Override
    public void loadData(List<BeautyPhotoInfo> data) {

    }

    @Override
    public void loadMoreData(List<BeautyPhotoInfo> data) {

    }

    @Override
    public void loadNoData() {

    }
}
