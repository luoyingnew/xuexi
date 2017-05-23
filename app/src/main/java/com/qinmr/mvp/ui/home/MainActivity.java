package com.qinmr.mvp.ui.home;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.qinmr.mvp.R;
import com.qinmr.mvp.ui.base.BaseActivity;
import com.qinmr.mvp.ui.base.BaseFragment;
import com.qinmr.mvp.ui.news.main.NewsMainFragment;
import com.qinmr.mvp.ui.photo.main.PhotoMainFragment;
import com.qinmr.mvp.ui.video.main.VideoMainFragment;
import com.qinmr.mvp.util.SharedPreferencesUtil;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private boolean mIsChangeTheme;

    private BaseFragment newsMainfragment;
    private BaseFragment photoMainFragment;
    private BaseFragment videoMainFragment;

    private SparseArray<String> mSparseTags = new SparseArray<>();
    private long mExitTime = 0;
    private int mItemId = -1;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case R.id.nav_news:
                    if (null == newsMainfragment) {
                        newsMainfragment = new NewsMainFragment();
                    }
                    replaceFragment(R.id.fl_container, newsMainfragment, mSparseTags.get(R.id.nav_news));
                    break;
                case R.id.nav_gallery:
                    if (null == photoMainFragment) {
                        photoMainFragment = new PhotoMainFragment();
                    }
                    replaceFragment(R.id.fl_container, photoMainFragment, mSparseTags.get(R.id.nav_gallery));
                    break;
                case R.id.nav_videos:
                    if (null == videoMainFragment) {
                        videoMainFragment = new VideoMainFragment();
                    }
                    replaceFragment(R.id.fl_container, videoMainFragment, mSparseTags.get(R.id.nav_videos));
                    break;
                case R.id.nav_shop:

                    break;
            }
            mItemId = -1;
            return true;
        }
    });

    @Override
    public int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        initDrawerLayout(mDrawerLayout, mNavView);
        mSparseTags.put(R.id.nav_news, "News");
        mSparseTags.put(R.id.nav_gallery, "Photos");
        mSparseTags.put(R.id.nav_videos, "Videos");
        mSparseTags.put(R.id.nav_shop, "Shop");

        //设置夜间模式
        MenuItem menuNightMode = mNavView.getMenu().findItem(R.id.nav_night_mode);
        SwitchCompat dayNightSwitch = (SwitchCompat) MenuItemCompat.getActionView(menuNightMode);
        //这两行代码如果替换一下位置，会导致盒子关闭会更新UI出现闪动，但是先设置按钮状态，在设置按钮监听就没有这个现象
        setCheckedState(dayNightSwitch);
        setCheckedEvent(dayNightSwitch);
    }

    @Override
    public void updateViews(boolean isRefresh) {
        mNavView.setCheckedItem(R.id.nav_news);
        replaceFragment(R.id.fl_container, new NewsMainFragment(), mSparseTags.get(R.id.nav_news));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mItemId = item.getItemId();
        if (item.isChecked()) {
            return true;
        }
        return true;
    }

    /**
     * 初始化 DrawerLayout
     *
     * @param drawerLayout DrawerLayout
     * @param navView      NavigationView
     */
    private void initDrawerLayout(DrawerLayout drawerLayout, NavigationView navView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //将侧边栏顶部延伸至status bar
            drawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
            drawerLayout.setClipToPadding(false);
        }
        navView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                mHandler.sendEmptyMessage(mItemId);

                if (mIsChangeTheme) {
                    mIsChangeTheme = false;
                    getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                    recreate();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // 获取堆栈里有几个
        final int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        //抽屉开着就关闭
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (stackEntryCount == 1 || stackEntryCount == 0) {
            // 如果剩一个说明在主页，提示按两次退出app
            exit();
        } else {
            // 获取上一个堆栈中保存的是哪个页面，根据name来设置导航项的选中状态
            final String tagName = getSupportFragmentManager().getBackStackEntryAt(stackEntryCount - 2).getName();
            mNavView.setCheckedItem(mSparseTags.keyAt(mSparseTags.indexOfValue(tagName)));
            super.onBackPressed();
        }
    }

    /**
     * 退出
     */
    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化夜间模式的按钮
     *
     * @param dayNightSwitch
     */
    private void setCheckedState(SwitchCompat dayNightSwitch) {
        boolean isNight = SharedPreferencesUtil.getBoolean(mContext, "isNight", false);
        if (isNight) {
            dayNightSwitch.setChecked(true);
            //由于上面描述的现象，没有监听，初始化的时候可能出现模式不对，所以在初始化时候做这个操作
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            dayNightSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * 设置夜间模式的按效果
     *
     * @param dayNightSwitch
     */
    private void setCheckedEvent(SwitchCompat dayNightSwitch) {
        dayNightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferencesUtil.setBoolean(mContext, "isNight", true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    SharedPreferencesUtil.setBoolean(mContext, "isNight", false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                mIsChangeTheme = true;
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }
}
