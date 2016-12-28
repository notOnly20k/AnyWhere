package com.jzdtl.anywhere.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by gcy on 2016/12/28.
 */
@Entity
public class CommentEntity {
    //    表的id 用户id 文章id 评论内容 是否点赞 是否收藏 评论时间
    @Id
    private Long _id;
    private String mUserId;
    private String mArticleId;
    private String mCommentContent;
    private String mIsCommend;
    private String mIsLiked;
    private String mCommentTime;
    @Generated(hash = 1569949722)
    public CommentEntity(Long _id, String mUserId, String mArticleId,
            String mCommentContent, String mIsCommend, String mIsLiked,
            String mCommentTime) {
        this._id = _id;
        this.mUserId = mUserId;
        this.mArticleId = mArticleId;
        this.mCommentContent = mCommentContent;
        this.mIsCommend = mIsCommend;
        this.mIsLiked = mIsLiked;
        this.mCommentTime = mCommentTime;
    }
    @Generated(hash = 339855539)
    public CommentEntity() {
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
    public String getMCommentContent() {
        return this.mCommentContent;
    }
    public void setMCommentContent(String mCommentContent) {
        this.mCommentContent = mCommentContent;
    }
    public String getMIsCommend() {
        return this.mIsCommend;
    }
    public void setMIsCommend(String mIsCommend) {
        this.mIsCommend = mIsCommend;
    }
    public String getMIsLiked() {
        return this.mIsLiked;
    }
    public void setMIsLiked(String mIsLiked) {
        this.mIsLiked = mIsLiked;
    }
    public String getMCommentTime() {
        return this.mCommentTime;
    }
    public void setMCommentTime(String mCommentTime) {
        this.mCommentTime = mCommentTime;
    }
}
