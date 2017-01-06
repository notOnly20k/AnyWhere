package com.jzdtl.anywhere.bean;

import java.util.List;

/**
 * Created by gcy on 2016/12/27.
 */

public class CommentsResult {

    /**
     * message : ok
     * status : 200
     * data : [{"user":{"id":1119208,"photo_url":"http://images4.c-ctrip.com/target/t1/headphoto/646/318/109/a865fe5f5465407d8befd5dc487a8554_C_180_180.jpg","name":"aaa","level":1},"photo_url":null,"id":88897,"comment":"bang","created_at":"2016-12-27T02:23:58.000Z","commentable_id":64157,"commentable_type":"UserActivity"}]
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommentsResult{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * user : {"id":1119208,"photo_url":"http://images4.c-ctrip.com/target/t1/headphoto/646/318/109/a865fe5f5465407d8befd5dc487a8554_C_180_180.jpg","name":"aaa","level":1}
         * photo_url : null
         * id : 88897
         * comment : bang
         * created_at : 2016-12-27T02:23:58.000Z
         * commentable_id : 64157
         * commentable_type : UserActivity
         */

        private UserBean user;
        private Object photo_url;
        private int id;
        private String comment;
        private String created_at;
        private int commentable_id;
        private String commentable_type;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public Object getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(Object photo_url) {
            this.photo_url = photo_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getCommentable_id() {
            return commentable_id;
        }

        public void setCommentable_id(int commentable_id) {
            this.commentable_id = commentable_id;
        }

        public String getCommentable_type() {
            return commentable_type;
        }

        public void setCommentable_type(String commentable_type) {
            this.commentable_type = commentable_type;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "user=" + user +
                    ", photo_url=" + photo_url +
                    ", id=" + id +
                    ", comment='" + comment + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", commentable_id=" + commentable_id +
                    ", commentable_type='" + commentable_type + '\'' +
                    '}';
        }

        public static class UserBean {
            /**
             * id : 1119208
             * photo_url : http://images4.c-ctrip.com/target/t1/headphoto/646/318/109/a865fe5f5465407d8befd5dc487a8554_C_180_180.jpg
             * name : aaa
             * level : 1
             */

            private int id;
            private String photo_url;
            private String name;
            private int level;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhoto_url() {
                return photo_url;
            }

            public void setPhoto_url(String photo_url) {
                this.photo_url = photo_url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }
        }
    }
}
