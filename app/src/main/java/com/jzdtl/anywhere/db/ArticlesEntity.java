package com.jzdtl.anywhere.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by gcy on 2016/12/28.
 */
@Entity
public class ArticlesEntity {
    //表的id 用户id 标题 内容 发布时间 目的地 图片
    @Id
    private Long _id;
    private String mUserId;
    private String mArticleId;
    private String mTitle;
    private String mContent;
    private String mSendTime;
    private String mDesstination;
    private String mImage;
    @Generated(hash = 1733600515)
    public ArticlesEntity(Long _id, String mUserId, String mArticleId,
            String mTitle, String mContent, String mSendTime, String mDesstination,
            String mImage) {
        this._id = _id;
        this.mUserId = mUserId;
        this.mArticleId = mArticleId;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mSendTime = mSendTime;
        this.mDesstination = mDesstination;
        this.mImage = mImage;
    }
    @Generated(hash = 935861421)
    public ArticlesEntity() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getMUserId() {
        return this.mUserId;
    }
    public void setMUserId(String mUserId) {
        this.mUserId = mUserId;
    }
    public String getMArticleId() {
        return this.mArticleId;
    }
    public void setMArticleId(String mArticleId) {
        this.mArticleId = mArticleId;
    }
    public String getMTitle() {
        return this.mTitle;
    }
    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getMContent() {
        return this.mContent;
    }
    public void setMContent(String mContent) {
        this.mContent = mContent;
    }
    public String getMSendTime() {
        return this.mSendTime;
    }
    public void setMSendTime(String mSendTime) {
        this.mSendTime = mSendTime;
    }
    public String getMDesstination() {
        return this.mDesstination;
    }
    public void setMDesstination(String mDesstination) {
        this.mDesstination = mDesstination;
    }
    public String getMImage() {
        return this.mImage;
    }
    public void setMImage(String mImage) {
        this.mImage = mImage;
    }
}
