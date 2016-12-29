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
    private String articleId;
    private String imageUrl;
    @Generated(hash = 813205590)
    public ImageEntity(Long _id, String articleId, String imageUrl) {
        this._id = _id;
        this.articleId = articleId;
        this.imageUrl = imageUrl;
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
    public String getArticleId() {
        return this.articleId;
    }
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
