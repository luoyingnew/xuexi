package com.qinmr.mvp.db.table;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by long on 2016/10/10.
 */
@Entity
public class WelfarePhotoInfo implements Serializable, Parcelable {

    /**
     * _id : 57facc74421aa95de3b8ab6b
     * createdAt : 2016-10-10T07:02:12.35Z
     * desc : 10-10
     * publishedAt : 2016-10-10T11:41:33.500Z
     * source : chrome
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/610dc034jw1f8mssipb9sj20u011hgqk.jpg
     * used : true
     * who : daimajia
     */
    @Id(autoincrement = true)
    private Long idd;
    private String id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    // 保存图片宽高
    private String pixel;

    // 喜欢
    private boolean isLove;
    // 点赞
    private boolean isPraise;
    // 下载
    private boolean isDownload;

    protected WelfarePhotoInfo(Parcel in) {
        id = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        source = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
        pixel = in.readString();
        isLove = in.readByte() != 0;
        isPraise = in.readByte() != 0;
        isDownload = in.readByte() != 0;
    }

    @Generated(hash = 821300839)
    public WelfarePhotoInfo(Long idd, String id, String createdAt, String desc,
            String publishedAt, String source, String type, String url, boolean used,
            String who, String pixel, boolean isLove, boolean isPraise, boolean isDownload) {
        this.idd = idd;
        this.id = id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
        this.pixel = pixel;
        this.isLove = isLove;
        this.isPraise = isPraise;
        this.isDownload = isDownload;
    }

    @Generated(hash = 1670556588)
    public WelfarePhotoInfo() {
    }

    public static final Creator<WelfarePhotoInfo> CREATOR = new Creator<WelfarePhotoInfo>() {
        @Override
        public WelfarePhotoInfo createFromParcel(Parcel in) {
            return new WelfarePhotoInfo(in);
        }

        @Override
        public WelfarePhotoInfo[] newArray(int size) {
            return new WelfarePhotoInfo[size];
        }
    };

    @Override
    public String toString() {
        return "WelfarePhotoInfo{" +
                "idd=" + idd +
                ", id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", pixel='" + pixel + '\'' +
                ", isLove=" + isLove +
                ", isPraise=" + isPraise +
                ", isDownload=" + isDownload +
                '}';
    }

    public Long getIdd() {
        return idd;
    }

    public void setIdd(Long idd) {
        this.idd = idd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(source);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeByte((byte) (used ? 1 : 0));
        dest.writeString(who);
        dest.writeString(pixel);
        dest.writeByte((byte) (isLove ? 1 : 0));
        dest.writeByte((byte) (isPraise ? 1 : 0));
        dest.writeByte((byte) (isDownload ? 1 : 0));
    }

    public boolean getIsDownload() {
        return this.isDownload;
    }

    public void setIsDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

    public boolean getIsPraise() {
        return this.isPraise;
    }

    public void setIsPraise(boolean isPraise) {
        this.isPraise = isPraise;
    }

    public boolean getIsLove() {
        return this.isLove;
    }

    public void setIsLove(boolean isLove) {
        this.isLove = isLove;
    }

    public boolean getUsed() {
        return this.used;
    }
}
