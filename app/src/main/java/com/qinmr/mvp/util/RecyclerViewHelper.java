package com.qinmr.mvp.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.qinmr.recycler.adapter.BaseAdapter;
import com.qinmr.recycler.divider.DividerGridItemDecoration;
import com.qinmr.recycler.divider.DividerItemDecoration;
import com.qinmr.recycler.listener.OnStartDragListener;
import com.qinmr.recycler.listener.SimpleItemTouchHelperCallback;

/**
 * Created by mrq on 2017/4/15.
 */

public class RecyclerViewHelper {

    private RecyclerViewHelper() {
        throw new RuntimeException("RecyclerViewHelper cannot be initialized!");
    }

    /**
     * 配置垂直列表RecyclerView
     *
     * @param view
     */
    public static void initRecyclerViewV(Context context, RecyclerView view,
                                         RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        view.setAdapter(adapter);
    }

    public static void initRecyclerViewG(Context context, RecyclerView view, RecyclerView.Adapter adapter, int column) {
        initRecyclerViewG(context, view, false, adapter, column);
    }

    /**
     * 配置网格列表RecyclerView
     * @param view
     */
    public static void initRecyclerViewG(Context context, RecyclerView view, boolean isDivided,
                                         RecyclerView.Adapter adapter, int column) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, column, LinearLayoutManager.VERTICAL, false);
        view.setLayoutManager(layoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        if (isDivided) {
            view.addItemDecoration(new DividerGridItemDecoration(context));
        }
        view.setAdapter(adapter);
    }

    /**
     * 启动拖拽和滑动
     * @param view 视图
     * @param adapter 适配器
     * @param dragFixCount 固定数量
     */
    public static void startDragAndSwipe(RecyclerView view, BaseAdapter adapter, int dragFixCount) {
        startDragAndSwipe(view, adapter);
        adapter.setDragFixCount(dragFixCount);
    }

    /**
     * 启动拖拽和滑动
     * @param view 视图
     * @param adapter 适配器
     */
    public static void startDragAndSwipe(RecyclerView view, BaseAdapter adapter) {
        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(adapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(view);
        adapter.setDragStartListener(new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            }
        });
        adapter.setDragCallback(callback);
        adapter.setDragColor(Color.LTGRAY);
    }

    public static void initRecyclerViewSV(Context context, RecyclerView view, RecyclerView.Adapter adapter, int column) {
        initRecyclerViewSV(context, view, false, adapter, column);
    }

    /**
     * 配置瀑布流列表RecyclerView
     * @param view
     */
    public static void initRecyclerViewSV(Context context, RecyclerView view, boolean isDivided,
                                          RecyclerView.Adapter adapter, int column) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        if (isDivided) {
            view.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        }
        view.setAdapter(adapter);
    }
}
