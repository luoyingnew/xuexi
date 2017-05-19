package com.qinmr.mvp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qinmr.mvp.R;
import com.qinmr.mvp.db.table.BeautyPhotoInfo;
import com.qinmr.mvp.util.DefIconFactory;
import com.qinmr.mvp.util.StringUtils;
import com.qinmr.recycler.adapter.BaseAdapter;
import com.qinmr.recycler.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by mrq on 2017/4/17.
 */

public class BeautyPhotosAdapter extends BaseAdapter<BeautyPhotoInfo> {

    // 图片的宽度
    private int mPhotoWidth;
    private Fragment mFragment;

    public BeautyPhotosAdapter(Fragment fragment) {
        this(fragment.getContext());
        mFragment = fragment;
    }

    public BeautyPhotosAdapter(Context context) {
        super(context);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int marginPixels = context.getResources().getDimensionPixelOffset(R.dimen.photo_margin_width);
        mPhotoWidth = widthPixels / 2 - marginPixels;
    }

    public BeautyPhotosAdapter(Context context, List<BeautyPhotoInfo> data) {
        super(context, data);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_beauty_photos;
    }

    @Override
    protected void convert(final BaseViewHolder holder, BeautyPhotoInfo item) {
        final ImageView ivPhoto = holder.getView(R.id.iv_photo);
        int photoHeight = StringUtils.calcPhotoHeight(item.getPixel(), mPhotoWidth);
        // 接口返回的数据有像素分辨率，根据这个来缩放图片大小
        final ViewGroup.LayoutParams params = ivPhoto.getLayoutParams();
        params.width = mPhotoWidth;
        params.height = photoHeight;
        ivPhoto.setLayoutParams(params);
        Glide.with(mContext).load(item.getImgsrc()).fitCenter().dontAnimate().placeholder(DefIconFactory.provideIcon()).into(ivPhoto);
        holder.setText(R.id.tv_title, item.getTitle());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragment != null) {
//                    BigPhotoActivity.launchForResult(mFragment, (ArrayList<BeautyPhotoInfo>) getData(), holder.getAdapterPosition());
                } else {
//                    BigPhotoActivity.launch(mContext, (ArrayList<BeautyPhotoInfo>) getData(), holder.getAdapterPosition());
                }
            }
        });
    }
}
