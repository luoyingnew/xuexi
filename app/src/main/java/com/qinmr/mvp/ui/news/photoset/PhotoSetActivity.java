package com.qinmr.mvp.ui.news.photoset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dl7.drag.DragSlopLayout;
import com.qinmr.utillibrary.logger.KLog;
import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.PhotoSetAdapter;
import com.qinmr.mvp.api.bean.PhotoSetInfo;
import com.qinmr.mvp.api.bean.PhotoSetInfo.PhotosEntity;
import com.qinmr.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

;

/**
 * Created by mrq on 2017/4/20.
 */

public class PhotoSetActivity extends BaseActivity implements IPhotoSetView {

    private static final String PHOTO_SET_KEY = "PhotoSetKey";

    @BindView(R.id.vp_photo)
    ViewPager mVpPhoto;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_index)
    TextView mTvIndex;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drag_layout)
    DragSlopLayout mDragLayout;

    private String mPhotoSetId;
    private PhotoSetAdapter mAdapter;
    private List<PhotosEntity> mPhotosEntities;
    private boolean mIsHideToolbar = false;

    public static void launch(Context context, String photoId) {
        Intent intent = new Intent(context, PhotoSetActivity.class);
        intent.putExtra(PHOTO_SET_KEY, photoId);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold);
    }

    @Override
    public int attachLayoutRes() {
        return R.layout.activity_photo_set;
    }

    @Override
    public void initData() {
        mPhotoSetId = getIntent().getStringExtra(PHOTO_SET_KEY);
        mPresenter = new PhotoSetPresenter(this,mPhotoSetId);
    }

    @Override
    public void initViews() {
        initToolBar(mToolbar, true, "");
    }

    @Override
    public void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(PhotoSetInfo photoSetBean) {
        List<String> imgUrls = new ArrayList<>();
        mPhotosEntities = photoSetBean.getPhotos();
        for (PhotosEntity entity : mPhotosEntities) {
            imgUrls.add(entity.getImgurl());
        }
        KLog.e(imgUrls.size());
        mAdapter = new PhotoSetAdapter(this, imgUrls);
        mVpPhoto.setAdapter(mAdapter);
        mVpPhoto.setOffscreenPageLimit(imgUrls.size());

        mTvCount.setText(mPhotosEntities.size() + "");
        mTvTitle.setText(photoSetBean.getSetname());
        mTvIndex.setText(1 + "/");
        mTvContent.setText(mPhotosEntities.get(0).getNote());

        mVpPhoto.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTvContent.setText(mPhotosEntities.get(position).getNote());
                mTvIndex.setText((position + 1) + "/");
            }
        });

        mAdapter.setTapListener(new PhotoSetAdapter.OnTapListener() {
            @Override
            public void onPhotoClick() {
                mIsHideToolbar = !mIsHideToolbar;
                if (mIsHideToolbar) {
                    mDragLayout.scrollOutScreen(300);
                    mToolbar.animate().translationY(-mToolbar.getBottom()).setDuration(300);
                } else {
                    mDragLayout.scrollInScreen(300);
                    mToolbar.animate().translationY(0).setDuration(300);
                }
            }
        });
    }
}
