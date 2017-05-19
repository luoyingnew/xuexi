package com.qinmr.mvp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qinmr.mvp.R;
import com.qinmr.mvp.db.table.WelfarePhotoInfo;
import com.qinmr.mvp.ui.photo.bigphoto.BigPhotoActivity;
import com.qinmr.mvp.util.DefIconFactory;
import com.qinmr.mvp.util.StringUtils;
import com.qinmr.recycler.adapter.BaseAdapter;
import com.qinmr.recycler.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by long on 2016/10/11.
 * 福利图适配器
 */
public class WelfarePhotoAdapter extends BaseAdapter<WelfarePhotoInfo> {

    // 图片的宽度
    private int mPhotoWidth;


    public WelfarePhotoAdapter(Context context) {
        super(context);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int marginPixels = context.getResources().getDimensionPixelOffset(R.dimen.photo_margin_width);
        mPhotoWidth = widthPixels / 2 - marginPixels;
    }

    public WelfarePhotoAdapter(Context context, List<WelfarePhotoInfo> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_welfare_photo;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final WelfarePhotoInfo item) {
        final ImageView ivPhoto = holder.getView(R.id.iv_photo);
        int photoHeight = StringUtils.calcPhotoHeight(item.getPixel(), mPhotoWidth);
        // 返回的数据有像素分辨率，根据这个来缩放图片大小
        final ViewGroup.LayoutParams params = ivPhoto.getLayoutParams();
        params.width = mPhotoWidth;
        params.height = photoHeight;
        ivPhoto.setLayoutParams(params);
        Glide.with(mContext).load(item.getUrl()).fitCenter().dontAnimate().placeholder(DefIconFactory.provideIcon()).into(ivPhoto);
        holder.setText(R.id.tv_title, item.getCreatedAt());

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast("这里没有做大图显示处理，请参考美图界面");
                BigPhotoActivity.launch(mContext,(ArrayList<WelfarePhotoInfo>)getData(),holder.getAdapterPosition());
            }
        });
    }
}