package com.qinmr.mvp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qinmr.recycler.adapter.BaseAdapter;
import com.qinmr.recycler.adapter.BaseViewHolder;
import com.qinmr.mvp.R;
import com.qinmr.mvp.api.bean.PhotoInfo;
import com.qinmr.mvp.util.DefIconFactory;

import java.util.List;

/**
 * Created by mrq on 2017/4/19.
 */

public class PhotoListAdapter extends BaseAdapter<PhotoInfo> {


    public PhotoListAdapter(Context context) {
        super(context);
    }

    public PhotoListAdapter(Context context, List<PhotoInfo> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_photo_list;
    }

    @Override
    protected void convert(BaseViewHolder holder, final PhotoInfo item) {
        ImageView ivPhoto1 = holder.getView(R.id.iv_photo_1);
        ImageView ivPhoto2 = holder.getView(R.id.iv_photo_2);
        ImageView ivPhoto3 = holder.getView(R.id.iv_photo_3);
        Glide.with(mContext).load(item.getPics().get(0)).fitCenter().dontAnimate().placeholder(DefIconFactory.provideIcon()).into(ivPhoto1);
        Glide.with(mContext).load(item.getPics().get(1)).fitCenter().dontAnimate().placeholder(DefIconFactory.provideIcon()).into(ivPhoto2);
        Glide.with(mContext).load(item.getPics().get(2)).fitCenter().dontAnimate().placeholder(DefIconFactory.provideIcon()).into(ivPhoto3);
        holder.setText(R.id.tv_title, item.getSetname());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PhotoSetActivity.launch(mContext, _mergePhotoId(item.getSetid()));
            }
        });
    }
}
