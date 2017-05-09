package com.qinmr.util.ui.news.channel;

import com.qinmr.util.bus.ChannelEvent;
import com.qinmr.util.db.NewsTypeDao;
import com.qinmr.util.db.collect.DBNewsTypeInfoCollect;
import com.qinmr.util.db.table.NewsTypeInfo;
import com.qinmr.util.ui.base.ILocalPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrq on 2017/4/15.
 */

public class ChannelHelper implements ILocalPresenter<NewsTypeInfo> {

    private final IChannelView mView;

    public ChannelHelper(IChannelView view) {
        mView = view;
    }

    public void getData() {

        List<NewsTypeInfo> checkList = DBNewsTypeInfoCollect.getNewsTypeInfo();
        List<NewsTypeInfo> allChannels = NewsTypeDao.getAllChannels();
        List<NewsTypeInfo> unCheckList = new ArrayList<>();
        List<String> type = new ArrayList<>();

        for (NewsTypeInfo newsTypeInfo : checkList) {
            type.add(newsTypeInfo.getTypeId());
        }

        for (NewsTypeInfo newsTypeInfo : allChannels) {
            if (!type.contains(newsTypeInfo.getTypeId())) {
                unCheckList.add(newsTypeInfo);
            }
        }
        mView.loadData(checkList, unCheckList);
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void insert(NewsTypeInfo data) {
        DBNewsTypeInfoCollect.insert(data);
        EventBus.getDefault().post(new ChannelEvent());
    }

    @Override
    public void delete(NewsTypeInfo data) {
        DBNewsTypeInfoCollect.delete(data.getTypeId());
        EventBus.getDefault().post(new ChannelEvent());
    }

    @Override
    public void update(List<NewsTypeInfo> list) {

    }
}
