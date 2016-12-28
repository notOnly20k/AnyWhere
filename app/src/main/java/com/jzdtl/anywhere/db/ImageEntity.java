package com.jzdtl.anywhere.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by gcy on 2016/12/28.
 */
@Entity
public class ImageEntity {
    //    表的ID  文章ID 图片地址
    @Id
    private Long _id;
    private String mArticleId;
    private String mImageUrl;
    @Generated(hash = 2097378684)
    public ImageEntity(Long _id, String mArticleId, String mImageUrl) {
        this._id = _id;
        this.mArticleId = mArticleId;
        this.mImageUrl = mImageUrl;
    }
    @Generated(hash = 2080458212)
    public ImageEntity() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getMArticleId() {
        return this.mArticleId;
    }
    public void setMArticleId(String mArticleId) {
        this.mArticleId = mArticleId;
    }
    public String getMImageUrl() {
        return this.mImageUrl;
    }
    public void setMImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
