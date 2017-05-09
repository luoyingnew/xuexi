package com.qinmr.mvp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.qinmr.recycler.adapter.BaseMultiItemQuickAdapter;
import com.qinmr.recycler.adapter.BaseViewHolder;
import com.qinmr.mvp.widgets.LabelView;
import com.qinmr.mvp.R;
import com.qinmr.mvp.adapter.item.NewsMultiItem;
import com.qinmr.mvp.api.NewsUtils;
import com.qinmr.mvp.api.bean.NewsInfo;
import com.qinmr.mvp.ui.news.article.NewsArticleActivity;
import com.qinmr.mvp.ui.news.photoset.PhotoSetActivity;
import com.qinmr.mvp.ui.news.special.SpecialActivity;
import com.qinmr.mvp.util.DefIconFactory;
import com.qinmr.mvp.util.ListUtils;
import com.qinmr.mvp.util.StringUtils;

import java.util.List;

/**
 * Created by mrq on 2017/4/14.
 */

public class NewsMultiListAdapter extends BaseMultiItemQuickAdapter<NewsMultiItem> {


    public NewsMultiListAdapter(Context context) {
        super(context);
    }

    public NewsMultiListAdapter(Context context, List<NewsMultiItem> data) {
        super(context, data);
    }

    @Override
    protected void attachItemType() {
        addItemType(NewsMultiItem.ITEM_TYPE_NORMAL, R.layout.adapter_news_list);
        addItemType(NewsMultiItem.ITEM_TYPE_PHOTO_SET, R.layout.adapter_news_photo_set);
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsMultiItem item) {
        switch (item.getItemType()) {
            case NewsMultiItem.ITEM_TYPE_NORMAL:
                handleNewsNormal(holder, item.getNewsBean());
                break;
            case NewsMultiItem.ITEM_TYPE_PHOTO_SET:
                handleNewsPhotoSet(holder, item.getNewsBean());
                break;
        }
    }

    /**
     * 处理正常的新闻
     *
     * @param holder
     * @param item
     */
    private void handleNewsNormal(final BaseViewHolder holder, final NewsInfo item) {
        ImageView newsIcon = holder.getView(R.id.iv_icon);
        Glide.with(mContext).load(item.getImgsrc())
                .centerCrop()
                .dontAnimate()
                .placeholder(DefIconFactory.provideIcon())
                .into(newsIcon);
        holder.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_source, StringUtils.clipNewsSource(item.getSource()))
                .setText(R.id.tv_time, item.getPtime());
        // 设置标签
        if (NewsUtils.isNewsSpecial(item.getSkipType())) {
            LabelView labelView = holder.getView(R.id.label_view);
            labelView.setVisibility(View.VISIBLE);
            labelView.setText("专题");
        } else {
            holder.setVisible(R.id.label_view, false);
        }
        RelativeLayout rippleLayout = holder.getView(R.id.item_ripple);
        rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NewsUtils.isNewsSpecial(item.getSkipType())) {
                    SpecialActivity.launch(mContext, item.getSpecialID());
                } else {
                    // 旧的实现方式和网易的比较相近，感兴趣的可以切换看看
//                    NewsDetailActivity.launch(mContext, item.getPostid());
                    NewsArticleActivity.launch(mContext, item.getPostid());
                }
            }
        });
    }

    /**
     * 处理图片的新闻
     *
     * @param holder
     * @param item
     */
    private void handleNewsPhotoSet(BaseViewHolder holder, final NewsInfo item) {
        ImageView[] newsPhoto = new ImageView[3];
        newsPhoto[0] = holder.getView(R.id.iv_icon_1);
        newsPhoto[1] = holder.getView(R.id.iv_icon_2);
        newsPhoto[2] = holder.getView(R.id.iv_icon_3);
        holder.setVisible(R.id.iv_icon_2, false).setVisible(R.id.iv_icon_3, false);
        Glide.with(mContext).load(item.getImgsrc())
                .centerCrop()
                .dontAnimate()
                .placeholder(DefIconFactory.provideIcon())
                .into(newsPhoto[0]);
        if (!ListUtils.isEmpty(item.getImgextra())) {
            for (int i = 0; i < Math.min(2, item.getImgextra().size()); i++) {
                newsPhoto[i + 1].setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(item.getImgextra().get(i).getImgsrc())
                        .centerCrop()
                        .dontAnimate()
                        .placeholder(DefIconFactory.provideIcon())
                        .into(newsPhoto[i + 1]);
            }
        }
        holder.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_source, StringUtils.clipNewsSource(item.getSource()))
                .setText(R.id.tv_time, item.getPtime());
        RelativeLayout rippleLayout = holder.getView(R.id.item_ripple);
        rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoSetActivity.launch(mContext, item.getPhotosetID());
            }
        });
    }

}
