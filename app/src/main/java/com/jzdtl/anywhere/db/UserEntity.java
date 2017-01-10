package com.jzdtl.anywhere.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by gcy on 2016/12/28.
 */
@Entity
public class UserEntity {
    //   表的id 手机号码 邮箱 性别 昵称 密码 头像
    @Id
    private Long _id;
    private String userName;
    private String phoneNumber;
    private String email;
    private String sex;
    private String nickName;
    private String password;
    private String head;
    private String userId;
    @Generated(hash = 964647344)
    public UserEntity(Long _id, String userName, String phoneNumber, String email,
            String sex, String nickName, String password, String head,
            String userId) {
        this._id = _id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.sex = sex;
        this.nickName = nickName;
        this.password = password;
        this.head = head;
        this.userId = userId;
    }
    public UserEntity(String userName, String phoneNumber, String email,
                      String sex, String nickName, String password, String head,
                      String userId) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.sex = sex;
        this.nickName = nickName;
        this.password = password;
        this.head = head;
        this.userId = userId;
    }
    @Generated(hash = 1433178141)
    public UserEntity() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getHead() {
        return this.head;
    }
    public void setHead(String head) {
        this.head = head;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "_id=" + _id +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", head='" + head + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
