package com.qinmr.util.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qinmr.mylibrary.adapter.BaseAdapter;
import com.qinmr.mylibrary.adapter.BaseViewHolder;
import com.qinmr.util.R;
import com.qinmr.util.db.table.VideoInfo;
import com.qinmr.util.util.DefIconFactory;

import java.util.List;

/**
 * Created by long on 2016/10/11.
 */

public class VideoListAdapter extends BaseAdapter<VideoInfo> {

    public VideoListAdapter(Context context) {
        super(context);
    }

    public VideoListAdapter(Context context, List<VideoInfo> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_video_list;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final VideoInfo item) {
        final ImageView ivPhoto = holder.getView(R.id.iv_photo);
        Glide.with(mContext).load(item.getCover()).fitCenter().dontAnimate().placeholder(DefIconFactory.provideIcon()).into(ivPhoto);
        holder.setText(R.id.tv_title, item.getTitle());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                VideoPlayerActivity.launch(mContext, item);
            }
        });
    }

}
