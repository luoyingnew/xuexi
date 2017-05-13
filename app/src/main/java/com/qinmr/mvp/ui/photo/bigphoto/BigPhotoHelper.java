package com.qinmr.mvp.ui.photo.bigphoto;

import com.qinmr.mvp.db.table.BeautyPhotoInfo;
import com.qinmr.mvp.ui.base.ILocalPresenter;

import java.util.List;

/**
 * Created by mrq on 2017/5/9.
 */

public class BigPhotoHelper implements ILocalPresenter<BeautyPhotoInfo> {


    private final BigPhotoActivity mView;
    private final Iterable<BeautyPhotoInfo> mPhotoList;

    public BigPhotoHelper(BigPhotoActivity activity, List<BeautyPhotoInfo> mPhotoList) {
        this.mView = activity;
        this.mPhotoList = mPhotoList;
    }

    @Override
    public void getData(boolean isRefresh) {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void insert(BeautyPhotoInfo data) {

    }

    @Override
    public void delete(BeautyPhotoInfo data) {

    }

    @Override
    public void update(List<BeautyPhotoInfo> list) {

    }
}
