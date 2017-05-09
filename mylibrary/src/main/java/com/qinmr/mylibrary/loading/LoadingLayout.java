package com.qinmr.mylibrary.loading;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qinmr.mylibrary.R;
import com.qinmr.mylibrary.util.Utils;

public class LoadingLayout extends FrameLayout {

    public final static int Success = 0;
    public final static int Empty = 1;
    public final static int Error = 2;
    public final static int No_Network = 3;
    public final static int Loading = 4;

    private Context mContext;
    private View loadingPage;
    private View errorPage;
    private View emptyPage;
    private View networkPage;

    private OnReloadListener listener;

    private static String emptyStr = "暂无数据";
    private static String errorStr = "加载失败，请稍后重试···";
    private static String netwrokStr = "无网络连接，请检查网络···";
    private static String reloadBtnStr = "点击重试";
    private static int emptyImgId = R.drawable.empty;
    private static int errorImgId = R.drawable.error;
    private static int networkImgId = R.drawable.no_network;
    private static int reloadBtnId = R.drawable.selector_btn_back_gray;
    private static int tipTextSize = 14;
    private static int buttonTextSize = 14;
    private static int buttonWidth = -1;
    private static int buttonHeight = -1;
    private static int loadingLayoutId = R.layout.widget_loading_page;
    private static int backgroundColor = R.color.color_f7f7f7;

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public LoadingLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        build();
    }

    private void build() {
        loadingPage = LayoutInflater.from(mContext).inflate(loadingLayoutId, null);
        errorPage = LayoutInflater.from(mContext).inflate(R.layout.widget_error_page, null);
        emptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_empty_page, null);
        networkPage = LayoutInflater.from(mContext).inflate(R.layout.widget_nonetwork_page, null);

        loadingPage.setBackgroundColor(Utils.getColor(mContext, backgroundColor));
        errorPage.setBackgroundColor(Utils.getColor(mContext, backgroundColor));
        emptyPage.setBackgroundColor(Utils.getColor(mContext, backgroundColor));
        networkPage.setBackgroundColor(Utils.getColor(mContext, backgroundColor));

        TextView errorText = Utils.findViewById(errorPage, R.id.error_text);
        TextView emptyText = Utils.findViewById(emptyPage, R.id.empty_text);
        TextView networkText = Utils.findViewById(networkPage, R.id.no_network_text);

        ImageView errorImg = Utils.findViewById(errorPage, R.id.error_img);
        ImageView emptyImg = Utils.findViewById(emptyPage, R.id.empty_img);
        ImageView networkImg = Utils.findViewById(networkPage, R.id.no_network_img);

        TextView errorReloadBtn = Utils.findViewById(errorPage, R.id.error_reload_btn);
        TextView networkReloadBtn = Utils.findViewById(networkPage, R.id.no_network_reload_btn);

        errorReloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onReload(v);
                }
            }
        });
        networkReloadBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onReload(v);
                }
            }
        });

        errorText.setText(errorStr);
        emptyText.setText(emptyStr);
        networkText.setText(netwrokStr);

        errorText.setTextSize(tipTextSize);
        emptyText.setTextSize(tipTextSize);
        networkText.setTextSize(tipTextSize);

        errorImg.setImageResource(errorImgId);
        emptyImg.setImageResource(emptyImgId);
        networkImg.setImageResource(networkImgId);

        errorReloadBtn.setBackgroundResource(reloadBtnId);
        networkReloadBtn.setBackgroundResource(reloadBtnId);

        errorReloadBtn.setText(reloadBtnStr);
        networkReloadBtn.setText(reloadBtnStr);

        errorReloadBtn.setTextSize(buttonTextSize);
        networkReloadBtn.setTextSize(buttonTextSize);

        if (buttonHeight != -1) {

            errorReloadBtn.setHeight(Utils.dp2px(mContext, buttonHeight));
            networkReloadBtn.setHeight(Utils.dp2px(mContext, buttonHeight));
        }
        if (buttonWidth != -1) {

            errorReloadBtn.setWidth(Utils.dp2px(mContext, buttonWidth));
            networkReloadBtn.setWidth(Utils.dp2px(mContext, buttonWidth));
        }

        this.addView(networkPage);
        this.addView(emptyPage);
        this.addView(errorPage);
        this.addView(loadingPage);
    }

    public void setStatus(@Flavour int status) {
        switch (status) {
            case Success:
                loadingPage.setVisibility(GONE);
                errorPage.setVisibility(GONE);
                emptyPage.setVisibility(GONE);
                networkPage.setVisibility(GONE);
                break;
            case Loading:
                loadingPage.setVisibility(View.VISIBLE);
                errorPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                break;
            case Empty:
                loadingPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.VISIBLE);
                networkPage.setVisibility(View.GONE);
                break;
            case Error:
                loadingPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.VISIBLE);
                emptyPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                break;
            case No_Network:
                loadingPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    /**
     * 设置ReloadButton的监听器
     */
    public LoadingLayout setOnReloadListener(OnReloadListener listener) {
        this.listener = listener;
        return this;
    }


    @IntDef({Success, Empty, Error, No_Network, Loading})
    public @interface Flavour {

    }

    public interface OnReloadListener {
        void onReload(View v);
    }

}
