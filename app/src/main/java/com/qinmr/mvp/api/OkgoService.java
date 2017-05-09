package com.qinmr.mvp.api;

/**
 * Created by mrq on 2017/4/12.
 */

public class OkgoService {

    private static final String HEAD_LINE_NEWS = "T1348647909107";

    // 递增页码
    private static final int INCREASE_PAGE = 20;

    public static String getNewsUrl(String mNewsId, int page) {
        String type;
        if (mNewsId.equals(HEAD_LINE_NEWS)) {
            type = "headline";
        } else {
            type = "list";
        }
        return String.format(NewsApi.getNewsList, type, mNewsId, page * INCREASE_PAGE);
    }

    public static String getBeautyPhotoUrl(int page) {
        return String.format(NewsApi.getBeautyPhoto, page * INCREASE_PAGE);
    }

    public static String getWelfarePhoto(int mPage) {
        return String.format(NewsApi.getWelfarePhoto, mPage);
    }

    public static String getNewsPhotoUrl() {
        return NewsApi.getNewsPhotoUrl;
    }

    public static String getNewsMoreUrl(String mNextSetId) {
        return String.format(NewsApi.getPhotoMoreList, mNextSetId);
    }

    public static String getSpecial(String mSpecialId) {
        return String.format(NewsApi.getSpecial, mSpecialId);
    }

    public static String getPhotoSet(String mPhotoSetId) {
        return String.format(NewsApi.getPhotoSet, mPhotoSetId);
    }

    public static String getNewsDetail(String mNewsId) {
        return String.format(NewsApi.getNewsDetail, mNewsId);
    }

    public static String getVideoList(String mVideoId, int mPage) {
        return String.format(NewsApi.getVideoList, mVideoId, mPage * INCREASE_PAGE / 2);
    }
}
