package com.qinmr.util.db;

import android.content.Context;

import com.qinmr.util.db.collect.DBNewsTypeInfoCollect;
import com.qinmr.util.db.table.NewsTypeInfo;
import com.qinmr.util.helper.AssetsHelper;
import com.qinmr.util.util.GsonHelper;

import java.util.List;

/**
 * Created by long on 2016/8/31.
 * 新闻分类数据访问
 */
public class NewsTypeDao {

    // 所有栏目
    private static List<NewsTypeInfo> sAllChannels;


    private NewsTypeDao() {
    }

    /**
     * 更新本地数据，如果数据库新闻列表栏目为 0 则添加头 3 个栏目
     *
     * @param context
     */
    public static void updateLocalData(Context context) {
        sAllChannels = GsonHelper.convertEntities(AssetsHelper.readData(context, "NewsChannel"), NewsTypeInfo.class);

        List<NewsTypeInfo> newsTypeInfo = DBNewsTypeInfoCollect.getNewsTypeInfo();

        if (null == newsTypeInfo || newsTypeInfo.size() == 0) {
            DBNewsTypeInfoCollect.insertTitleTable(sAllChannels.subList(0, 3));
        }
    }

    /**
     * 获取所有栏目
     *
     * @return
     */
    public static List<NewsTypeInfo> getAllChannels() {
        return sAllChannels;
    }
}
