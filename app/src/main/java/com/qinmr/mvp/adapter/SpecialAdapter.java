package com.qinmr.mvp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.item.SpecialItem;
import com.qinmr.mvp.api.NewsUtils;
import com.qinmr.mvp.ui.news.article.NewsArticleActivity;
import com.qinmr.mvp.ui.news.photoset.PhotoSetActivity;
import com.qinmr.mvp.ui.news.special.SpecialActivity;
import com.qinmr.mvp.util.DefIconFactory;
import com.qinmr.mvp.util.StringUtils;
import com.qinmr.mvp.widgets.LabelView;
import com.qinmr.recycler.adapter.BaseSectionQuickAdapter;
import com.qinmr.recycler.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by mrq on 2017/5/17.
 */

public class SpecialAdapter extends BaseSectionQuickAdapter<SpecialItem> {

    public SpecialAdapter(Context context, List<SpecialItem> data) {
        super(context, data);
    }

    public SpecialAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_news_list;
    }

    @Override
    protected int attachHeadLayoutRes() {
        return R.layout.adapter_special_head;
    }

    @Override
    protected void convertHead(BaseViewHolder holder, SpecialItem item) {
        holder.setText(R.id.tv_head, item.header);
    }

    @Override
    protected void convert(BaseViewHolder holder, final SpecialItem item) {
        ImageView newsIcon = holder.getView(R.id.iv_icon);
//        ImageLoader.loadCenterCrop(mContext, item.t.getImgsrc(), newsIcon, DefIconFactory.provideIcon());
        Glide.with(mContext).load(item.t.getImgsrc())
                .centerCrop()
                .dontAnimate()
                .placeholder(DefIconFactory.provideIcon())
                .into(newsIcon);
        holder.setText(R.id.tv_title, item.t.getTitle())
                .setText(R.id.tv_source, StringUtils.clipNewsSource(item.t.getSource()))
                .setText(R.id.tv_time, item.t.getPtime());

        if (NewsUtils.isNewsSpecial(item.t.getSkipType())) {
            LabelView labelView = holder.getView(R.id.label_view);
            labelView.setVisibility(View.VISIBLE);
            labelView.setBgColor(ContextCompat.getColor(mContext, R.color.item_special_bg));
            labelView.setText("专题");
        } else if (NewsUtils.isNewsPhotoSet(item.t.getSkipType())) {
            LabelView labelView = holder.getView(R.id.label_view);
            labelView.setVisibility(View.VISIBLE);
            labelView.setBgColor(ContextCompat.getColor(mContext, R.color.item_photo_set_bg));
            labelView.setText("图集");
        } else {
            holder.setVisible(R.id.label_view, false);
        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NewsUtils.isNewsSpecial(item.t.getSkipType())) {
                    SpecialActivity.launch(mContext, item.t.getSpecialID());
                } else if (NewsUtils.isNewsPhotoSet(item.t.getSkipType())) {
                    PhotoSetActivity.launch(mContext, item.t.getPhotosetID());
                } else {
//                    NewsDetailActivity.launch(mContext, item.t.getPostid());
                    NewsArticleActivity.launch(mContext, item.t.getPostid());
                }
            }
        });
    }


}
