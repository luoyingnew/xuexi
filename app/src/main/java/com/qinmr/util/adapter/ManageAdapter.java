package com.qinmr.util.adapter;

import android.content.Context;

import com.qinmr.mylibrary.adapter.BaseAdapter;
import com.qinmr.mylibrary.adapter.BaseViewHolder;
import com.qinmr.util.R;
import com.qinmr.util.db.table.NewsTypeInfo;

import java.util.List;

/**
 * Created by long on 2016/8/31.
 * 管理界面适配器
 */
public class ManageAdapter extends BaseAdapter<NewsTypeInfo> {

    public ManageAdapter(Context context) {
        super(context);
    }

    public ManageAdapter(Context context, List<NewsTypeInfo> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_manage;
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsTypeInfo item) {
        holder.setText(R.id.tv_channel_name, item.getName());
    }
}
