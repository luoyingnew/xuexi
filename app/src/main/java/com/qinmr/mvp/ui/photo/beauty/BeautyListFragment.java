package com.qinmr.mvp.ui.photo.beauty;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.qinmr.mvp.ui.base.ILoadDataView;
import com.qinmr.recycler.listener.OnRequestDataListener;
import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.BeautyPhotosAdapter;
import com.qinmr.mvp.db.table.BeautyPhotoInfo;
import com.qinmr.mvp.util.RecyclerViewHelper;
import com.qinmr.mvp.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * 美女  接口失效  页面加载不出来
 * Created by mrq on 2017/4/17.
 */

public class BeautyListFragment extends BaseFragment implements ILoadDataView<List<BeautyPhotoInfo>> {

    @BindView(R.id.rv_photo_list)
    RecyclerView mRvPhotoList;
    @BindView(R.id.iv_transition_photo)
    ImageView mIvTransitionPhoto;

    BeautyPhotosAdapter mAdapter;

    @Override
    public int attachLayoutRes() {
        return R.layout.fragment_photo_list;
    }

    @Override
    public void initData() {
        mPresenter = new BeautyListPresenter(this);
    }

    @Override
    public void initViews() {
        mAdapter = new BeautyPhotosAdapter(this);
        RecyclerViewHelper.initRecyclerViewSV(mContext, mRvPhotoList, mAdapter, 2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMoreData();
            }
        });
    }

    @Override
    public void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<BeautyPhotoInfo> data) {
        mAdapter.updateItems(data);
    }

    @Override
    public void loadMoreData(List<BeautyPhotoInfo> data) {
        mAdapter.loadComplete();
        mAdapter.addItems(data);
    }

    @Override
    public void loadNoData() {

    }
}
