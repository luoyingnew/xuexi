package com.qinmr.util.ui.news.special;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.qinmr.util.adapter.item.SpecialItem;
import com.qinmr.util.api.OkgoService;
import com.qinmr.util.api.bean.SpecialInfo;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by mrq on 2017/4/19.
 */

public class SpecialHelper {


    private final SpecialActivity mView;
    private final String mSpecialId;

    public SpecialHelper(SpecialActivity activity, String mSpecialId) {
        this.mView = activity;
        this.mSpecialId = mSpecialId;
    }

    public void getData() {
        String specialUrl = OkgoService.getSpecial(mSpecialId);
        mView.showLoading();
        OkGo.get(specialUrl)
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new AbsCallback<List<SpecialItem>>() {

                    @Override
                    public List<SpecialItem> convertSuccess(Response response) throws Exception {
                        return convertSpecialBeanToItem(AnalysisData(response));
                    }

                    @Override
                    public void onSuccess(List<SpecialItem> o, Call call, Response response) {
                        mView.hideLoading();

                    }
                });

    }

    /**
     * 转换数据，接口数据有点乱，这里做一些处理
     *
     * @param specialBean
     * @return
     */
    private List<SpecialItem> convertSpecialBeanToItem(SpecialInfo specialBean) {
        // 这边 +1 是接口数据还有个 topicsplus 的字段可能是穿插在 topics 字段列表中间。这里没有处理 topicsplus
        final SpecialItem[] specialItems = new SpecialItem[specialBean.getTopics().size()+1];
        List<SpecialInfo.TopicsEntity> topics = specialBean.getTopics();
        for (SpecialInfo.TopicsEntity topicsEntity : topics) {
            specialItems[topicsEntity.getIndex() - 1] = new SpecialItem(true,
                    topicsEntity.getIndex() + "/" + specialItems.length + " " + topicsEntity.getTname());
        }

        return null;
    }

    /**
     * 解析网络数据
     *
     * @param response
     * @return
     */
    private SpecialInfo AnalysisData(Response response) {
        Reader reader = response.body().charStream();
        Map<String, SpecialInfo> map = new Gson()
                .fromJson(reader, new TypeToken<Map<String, SpecialInfo>>() {
                }.getType());
        response.close();
        return map.get(mSpecialId);
    }
}
