package com.qinmr.util.ui.home;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.qinmr.util.R;
import com.qinmr.util.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_tag_skip)
    TextView tvTagSkip;

    private int time = 5;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            tvTagSkip.setText("跳转\t\t" + time);
            if (time == 0) {
                doSkip();
            }
            handler.sendEmptyMessageDelayed(0, 1000);
            time--;
        }
    };

    @Override
    public int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews() {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void updateViews() {

    }

    @OnClick(R.id.tv_tag_skip)
    public void onClick() {
        doSkip();
    }

    private void doSkip() {
        showActivity(SplashActivity.this, MainActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
