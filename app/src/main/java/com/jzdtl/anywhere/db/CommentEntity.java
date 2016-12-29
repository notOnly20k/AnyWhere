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
    private String userId;
    private String articleId;
    private String commentContent;
    private String isCommend;
    private String isLiked;
    private String commentTime;
    @Generated(hash = 2029744778)
    public CommentEntity(Long _id, String userId, String articleId,
            String commentContent, String isCommend, String isLiked,
            String commentTime) {
        this._id = _id;
        this.userId = userId;
        this.articleId = articleId;
        this.commentContent = commentContent;
        this.isCommend = isCommend;
        this.isLiked = isLiked;
        this.commentTime = commentTime;
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
    public String getCommentContent() {
        return this.commentContent;
    }
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    public String getIsCommend() {
        return this.isCommend;
    }
    public void setIsCommend(String isCommend) {
        this.isCommend = isCommend;
    }
    public String getIsLiked() {
        return this.isLiked;
    }
    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }
    public String getCommentTime() {
        return this.commentTime;
    }
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
