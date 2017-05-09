package com.qinmr.util.db.table;

import java.io.Serializable;


/**
 * Created by long on 2016/10/11.
 * 视频实体
 */
public class VideoInfo implements Serializable {


    /**
     * sizeSHD : 7800
     * replyCount : 203
     * videosource : 新媒体
     * mp4Hd_url : http://flv2.bn.netease.com/videolib3/1505/05/iSHsM6026/HD/iSHsM6026-mobile.mp4
     * title : 第239期:央视主播戴苹果手表出镜
     * cover : http://img4.cache.netease.com/3g/2015/5/6/2015050607331756f56.jpg
     * description : 1&#041;发改委：6月1日起取消绝大部分药品政府定价 2&#041;成都被打女司机就第一次变道道歉:正接受道德批判 3&#041;实拍：央视美女主播戴苹果手表出镜
     * priority : 80
     * replyid : ANUF0VGS008535RB
     * length : 52
     * vid : VANUF0VGS
     * m3u8_url : http://flv2.bn.netease.com/videolib3/1505/05/iSHsM6026/SD/movie_index.m3u8
     * topicName : 52秒新闻
     * votecount : 181
     * topicImg : http://vimg1.ws.126.net/image/snapshot/2014/2/D/S/V9L5TAPDS.jpg
     * topicDesc : 网易视频每日为您精选三条新闻，时长严控在52秒，闲暇时间内快速浏览当日重点。
     * topicSid : V9L5TAPDP
     * replyBoard : videonews_bbs
     * playCount : 286608
     * sectiontitle : 新闻52秒
     * mp4_url : http://flv2.bn.netease.com/videolib3/1505/05/iSHsM6026/SD/iSHsM6026-mobile.mp4
     * playersize : 1
     * sizeSD : 3900
     * sizeHD : 5200
     * latest :
     * m3u8Hd_url : http://flv2.bn.netease.com/videolib3/1505/05/iSHsM6026/HD/movie_index.m3u8
     * ptime : 2015-05-05 16:50:39
     */

    private int sizeSHD;
    private int replyCount;
    private String videosource;
    private String mp4Hd_url;
    private String title;
    private String cover;
    private String description;
    private String priority;
    private String replyid;
    private int length;
    private String vid;
    private String m3u8_url;
    private String topicName;
    private int votecount;
    private String topicImg;
    private String topicDesc;
    private String topicSid;
    private String replyBoard;
    private int playCount;
    private String sectiontitle;
    private String mp4_url;
    private int playersize;
    private int sizeSD;
    private int sizeHD;
    private String latest;
    private String m3u8Hd_url;
    private String ptime;

    public int getSizeSHD() {
        return sizeSHD;
    }

    public void setSizeSHD(int sizeSHD) {
        this.sizeSHD = sizeSHD;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getVideosource() {
        return videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public String getMp4Hd_url() {
        return mp4Hd_url;
    }

    public void setMp4Hd_url(String mp4Hd_url) {
        this.mp4Hd_url = mp4Hd_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getM3u8_url() {
        return m3u8_url;
    }

    public void setM3u8_url(String m3u8_url) {
        this.m3u8_url = m3u8_url;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public String getTopicSid() {
        return topicSid;
    }

    public void setTopicSid(String topicSid) {
        this.topicSid = topicSid;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getSectiontitle() {
        return sectiontitle;
    }

    public void setSectiontitle(String sectiontitle) {
        this.sectiontitle = sectiontitle;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public int getPlayersize() {
        return playersize;
    }

    public void setPlayersize(int playersize) {
        this.playersize = playersize;
    }

    public int getSizeSD() {
        return sizeSD;
    }

    public void setSizeSD(int sizeSD) {
        this.sizeSD = sizeSD;
    }

    public int getSizeHD() {
        return sizeHD;
    }

    public void setSizeHD(int sizeHD) {
        this.sizeHD = sizeHD;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getM3u8Hd_url() {
        return m3u8Hd_url;
    }

    public void setM3u8Hd_url(String m3u8Hd_url) {
        this.m3u8Hd_url = m3u8Hd_url;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
}
