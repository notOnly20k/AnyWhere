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
    private String mUserId;
    private String mPhoneNumber;
    private String mEmail;
    private String mSex;
    private String mNickName;
    private String mPassword;
    private String mHead;
    @Generated(hash = 2046057164)
    public UserEntity(Long _id, String mUserId, String mPhoneNumber, String mEmail,
            String mSex, String mNickName, String mPassword, String mHead) {
        this._id = _id;
        this.mUserId = mUserId;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
        this.mSex = mSex;
        this.mNickName = mNickName;
        this.mPassword = mPassword;
        this.mHead = mHead;
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
    public String getMUserId() {
        return this.mUserId;
    }
    public void setMUserId(String mUserId) {
        this.mUserId = mUserId;
    }
    public String getMPhoneNumber() {
        return this.mPhoneNumber;
    }
    public void setMPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
    public String getMEmail() {
        return this.mEmail;
    }
    public void setMEmail(String mEmail) {
        this.mEmail = mEmail;
    }
    public String getMSex() {
        return this.mSex;
    }
    public void setMSex(String mSex) {
        this.mSex = mSex;
    }
    public String getMNickName() {
        return this.mNickName;
    }
    public void setMNickName(String mNickName) {
        this.mNickName = mNickName;
    }
    public String getMPassword() {
        return this.mPassword;
    }
    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }
    public String getMHead() {
        return this.mHead;
    }
    public void setMHead(String mHead) {
        this.mHead = mHead;
    }
}
