package com.qinmr.mvp.adapter;

import android.content.Context;

import com.qinmr.recycler.adapter.BaseAdapter;
import com.qinmr.recycler.adapter.BaseViewHolder;

/**
 * Created by mrq on 2017/5/17.
 */

public class SpecialAdapter extends BaseAdapter {

    public SpecialAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item) {

    }
}
