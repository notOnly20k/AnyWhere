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
    private String userId;
    private String articleId;
    private String title;
    private String content;
    private String sendTime;
    private String desstination;
    private String image;
    @Generated(hash = 1035281838)
    public ArticlesEntity(Long _id, String userId, String articleId, String title,
            String content, String sendTime, String desstination, String image) {
        this._id = _id;
        this.userId = userId;
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.sendTime = sendTime;
        this.desstination = desstination;
        this.image = image;
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
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getArticleId() {
        return this.articleId;
    }
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSendTime() {
        return this.sendTime;
    }
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    public String getDesstination() {
        return this.desstination;
    }
    public void setDesstination(String desstination) {
        this.desstination = desstination;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
