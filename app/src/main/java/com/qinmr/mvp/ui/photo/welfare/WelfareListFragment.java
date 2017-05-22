package com.qinmr.mvp.ui.photo.welfare;

import android.support.v7.widget.RecyclerView;

import com.qinmr.mvp.ui.base.IBasePresenter;
import com.qinmr.mvp.ui.base.ILoadDataView;
import com.qinmr.recycler.listener.OnRequestDataListener;
import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.WelfarePhotoAdapter;
import com.qinmr.mvp.db.table.WelfarePhotoInfo;
import com.qinmr.mvp.util.RecyclerViewHelper;
import com.qinmr.mvp.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * 福利
 * Created by mrq on 2017/4/17.
 */

public class WelfareListFragment extends BaseFragment<IBasePresenter> implements ILoadDataView<List<WelfarePhotoInfo>> {

    @BindView(R.id.rv_photo_list)
    RecyclerView mRvPhotoList;
    private WelfarePhotoAdapter mAdapter;

    @Override
    public int attachLayoutRes() {
        return R.layout.fragment_photo_list;
    }

    @Override
    public void initData() {
        mPresenter = new WelfareListPresenter(this);
    }

    @Override
    public void initViews() {
        mAdapter = new WelfarePhotoAdapter(mContext);
        RecyclerViewHelper.initRecyclerViewSV(mContext, mRvPhotoList, new SlideInBottomAnimationAdapter(mAdapter), 2);
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
    public void loadData(List<WelfarePhotoInfo> data) {
        mAdapter.updateItems(data);
    }

    @Override
    public void loadMoreData(List<WelfarePhotoInfo> data) {
        mAdapter.loadComplete();
        mAdapter.addItems(data);
    }

    @Override
    public void loadNoData() {
        mAdapter.noMoreData();
    }
}
