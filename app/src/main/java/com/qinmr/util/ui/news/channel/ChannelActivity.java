package com.qinmr.util.ui.news.channel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qinmr.mylibrary.listener.OnItemMoveListener;
import com.qinmr.mylibrary.listener.OnRecyclerViewItemClickListener;
import com.qinmr.mylibrary.listener.OnRemoveDataListener;
import com.qinmr.mylibrary.logger.KLog;
import com.qinmr.util.R;
import com.qinmr.util.adapter.ManageAdapter;
import com.qinmr.util.db.table.NewsTypeInfo;
import com.qinmr.util.helper.RecyclerViewHelper;
import com.qinmr.util.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class ChannelActivity extends BaseActivity implements IChannelView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_checked_list)
    RecyclerView mRvCheckedList;
    @BindView(R.id.rv_unchecked_list)
    RecyclerView mRvUncheckedList;
    private ChannelHelper channelHelper;
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
    public void initViews() {
        channelHelper = new ChannelHelper(this);
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
                channelHelper.delete(mCheckedAdapter.getItem(position));
                KLog.e(1111111);
            }
        });
        // 设置移动监听器
        mCheckedAdapter.setItemMoveListener(new OnItemMoveListener() {
            @Override
            public void onItemMove(int fromPosition, int toPosition) {
//                mPresenter.update(mCheckedAdapter.getData());
//                mPresenter.swap(fromPosition, toPosition);
//                KLog.e(22222222);
            }
        });
        // 设置点击删除添加到unCheck中
        mCheckedAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 删除前获取数据，不然获取不到对应数据
                NewsTypeInfo data = mCheckedAdapter.getItem(position);
                mCheckedAdapter.removeItem(position);
                mUncheckedAdapter.addLastItem(data);
                channelHelper.delete(data);
            }
        });  // 设置点击删除添加到check中
        mUncheckedAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 删除前获取数据，不然获取不到对应数据
                NewsTypeInfo data = mUncheckedAdapter.getItem(position);
                mUncheckedAdapter.removeItem(position);
                mCheckedAdapter.addLastItem(data);
                channelHelper.insert(data);
            }
        });


    }

    @Override
    public void updateViews() {
        channelHelper.getData();
    }

    @Override
    public void loadData(List<NewsTypeInfo> checkList, List<NewsTypeInfo> uncheckList) {
        mCheckedAdapter.updateItems(checkList);
        mUncheckedAdapter.updateItems(uncheckList);
    }
}
