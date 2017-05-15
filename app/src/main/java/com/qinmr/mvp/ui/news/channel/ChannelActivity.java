package com.qinmr.mvp.ui.news.channel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.ManageAdapter;
import com.qinmr.mvp.db.table.NewsTypeInfo;
import com.qinmr.mvp.helper.RecyclerViewHelper;
import com.qinmr.mvp.ui.base.BaseActivity;
import com.qinmr.recycler.listener.OnRecyclerViewItemClickListener;
import com.qinmr.recycler.listener.OnRemoveDataListener;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class ChannelActivity extends BaseActivity<ChannelPresenter> implements IChannelView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_checked_list)
    RecyclerView mRvCheckedList;
    @BindView(R.id.rv_unchecked_list)
    RecyclerView mRvUncheckedList;
    private ManageAdapter mCheckedAdapter;
    private ManageAdapter mUncheckedAdapter;


    public static void launch(Context context) {
        Intent intent = new Intent(context, ChannelActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    public int attachLayoutRes() {
        return R.layout.activity_channel;
    }

    @Override
    public void initData() {
        mPresenter = new ChannelPresenter(this, mDaoSession.getNewsTypeInfoDao(), mRxBus);
    }

    @Override
    public void initViews() {
        initToolBar(mToolbar, true, "栏目管理");
        mCheckedAdapter = new ManageAdapter(ChannelActivity.this);
        mUncheckedAdapter = new ManageAdapter(ChannelActivity.this);

        RecyclerViewHelper.initRecyclerViewG(this, mRvCheckedList, mCheckedAdapter, 4);
        RecyclerViewHelper.initRecyclerViewG(this, mRvUncheckedList, mUncheckedAdapter, 4);

        RecyclerViewHelper.startDragAndSwipe(mRvCheckedList, mCheckedAdapter, 3);
        // 设置动画
        mRvCheckedList.setItemAnimator(new ScaleInAnimator());
        mRvUncheckedList.setItemAnimator(new FlipInBottomXAnimator());
        // 设置拖拽背景
        mCheckedAdapter.setDragDrawable(ContextCompat.getDrawable(this, R.drawable.shape_channel_drag));
        // 设置移除监听器
        mCheckedAdapter.setRemoveDataListener(new OnRemoveDataListener() {
            @Override
            public void onRemove(int position) {
                mUncheckedAdapter.addLastItem(mCheckedAdapter.getItem(position));
                mPresenter.delete(mCheckedAdapter.getItem(position));
            }
        });
//        // 设置移动监听器
//        mCheckedAdapter.setItemMoveListener(new OnItemMoveListener() {
//            @Override
//            public void onItemMove(int fromPosition, int toPosition) {
////                mPresenter.update(mCheckedAdapter.getData());
////                mPresenter.swap(fromPosition, toPosition);
//            }
//        });
        // 设置点击删除添加到unCheck中
        mCheckedAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 删除前获取数据，不然获取不到对应数据
                NewsTypeInfo data = mCheckedAdapter.getItem(position);
                mCheckedAdapter.removeItem(position);
                mUncheckedAdapter.addLastItem(data);
                mPresenter.delete(data);
            }
        });  // 设置点击删除添加到check中
        mUncheckedAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 删除前获取数据，不然获取不到对应数据
                NewsTypeInfo data = mUncheckedAdapter.getItem(position);
                mUncheckedAdapter.removeItem(position);
                mCheckedAdapter.addLastItem(data);
                mPresenter.insert(data);
            }
        });


    }

    @Override
    public void updateViews(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<NewsTypeInfo> checkList, List<NewsTypeInfo> uncheckList) {
        mCheckedAdapter.updateItems(checkList);
        mUncheckedAdapter.updateItems(uncheckList);
    }
}
