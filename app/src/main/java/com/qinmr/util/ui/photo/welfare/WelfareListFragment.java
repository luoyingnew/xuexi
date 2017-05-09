package com.qinmr.util.ui.photo.welfare;

import android.support.v7.widget.RecyclerView;

import com.qinmr.mylibrary.callback.ILoadDataView;
import com.qinmr.mylibrary.listener.OnRequestDataListener;
import com.qinmr.util.R;
import com.qinmr.util.adapter.WelfarePhotoAdapter;
import com.qinmr.util.api.bean.WelfarePhotoInfo;
import com.qinmr.util.helper.RecyclerViewHelper;
import com.qinmr.util.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by mrq on 2017/4/17.
 */

public class WelfareListFragment extends BaseFragment implements ILoadDataView<List<WelfarePhotoInfo>> {

    @BindView(R.id.rv_photo_list)
    RecyclerView mRvPhotoList;
    private WelfareListHelper helper;
    private WelfarePhotoAdapter mAdapter;

    @Override
    public int attachLayoutRes() {
        return R.layout.fragment_photo_list;
    }

    @Override
    public void initData() {
        helper = new WelfareListHelper(this);
    }

    @Override
    public void initViews() {
        mAdapter = new WelfarePhotoAdapter(mContext);
        RecyclerViewHelper.initRecyclerViewSV(mContext, mRvPhotoList, new SlideInBottomAnimationAdapter(mAdapter), 2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                helper.getMoreData();
            }
        });
    }

    @Override
    public void updateViews() {
        helper.getData();
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
