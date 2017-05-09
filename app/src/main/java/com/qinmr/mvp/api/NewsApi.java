package com.qinmr.mvp.api;

/**
 * Created by mrq on 2017/4/12.
 */

public class NewsApi {

    private static String BaseUrl = "http://c.m.163.com/";

    /**
     * http://c.m.163.com/nc/article/headline/T1348647909107/60-20.html
     *
     * @param type 新闻类型 1
     * @param id 新闻ID  2
     * @param startPage 3 起始页码
     */
    public static String getNewsList = BaseUrl + "nc/article/%1$s/%2$s/%3$s-20.html";


    /**
     * 获取美女图片，这个API不完整，省略了好多参数
     * eg: http://c.3g.163.com/recommend/getChanListNews?channel=T1456112189138&size=20&offset=0
     *
     * @param offset 起始页码
     * @return
     */
    public static String getBeautyPhoto = BaseUrl + "/recommend/getChanListNews?channel=T1456112189138&size=20&offset=%1$s";


    /**
     * 获取福利图片
     * eg: http://gank.io/api/data/福利/10/1
     *
     * @param page 页码
     * @return
     */
    public static String getWelfarePhoto = "http://gank.io/api/data/福利/10/%1$s";

    /**
     * 获取图片列表
     * eg: http://c.3g.163.com/photo/api/list/0096/4GJ60096.json
     *
     * @return
     */
    public static String getNewsPhotoUrl = "http://c.3g.163.com/photo/api/list/0096/4GJ60096.json";


    /**
     * 获取更多图片列表
     * eg: http://c.3g.163.com/photo/api/morelist/0096/4GJ60096/106571.json
     *
     * @return
     */
    public static String getPhotoMoreList = "http://c.3g.163.com/photo/api/morelist/0096/4GJ60096/%1$s.json";

    /**
     * 获取专题
     * eg: http://c.3g.163.com/nc/special/S1451880983492.html
     *
     * @param specialIde 专题ID
     * @return
     */
    public static String getSpecial = "http://c.3g.163.com/nc/special/%1$s.html";

    /**
     * 获取新闻详情
     * eg: http://c.3g.163.com/photo/api/set/0006/2136404.json
     *
     * @param photoId 图集ID
     * @return
     */
    public static String getPhotoSet = "http://c.3g.163.com/photo/api/set/%1$s.json";


    /**
     * 获取新闻详情
     * eg: http://c.3g.163.com/nc/article/BV56RVG600011229/full.html
     *
     * @param newsId 专题ID
     * @return
     */
    public static String getNewsDetail = "http://c.3g.163.com/nc/article/%1$s/full.html";

    /**
     * 获取视频列表
     * eg: http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html
     *
     * @param id  video ID
     * @param startPage 起始页码
     * @return
     */
    public static String getVideoList = "http://c.3g.163.com/nc/video/list/%1$s/n/%2$s-10.html";
}
