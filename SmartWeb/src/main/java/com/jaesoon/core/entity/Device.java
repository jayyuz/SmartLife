package com.jaesoon.core.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/5.
 */
public class Device {
    @Expose
    @SerializedName("DevId")
    private String devId;
    @Expose
    @SerializedName("SN")
    private String sn;
    @Expose
    @SerializedName("UserMobileNO")
    private String userMobileNo;
    @Expose
    @SerializedName("DevType")
    private String devType;
    @Expose
    @SerializedName("Users")
    private String users;
    @Expose
    @SerializedName("Content")
    private String content;
    @Expose
    @SerializedName("CreateTime")
    private Date createTime;

    @Expose
    @SerializedName("State")
    private Boolean state;

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
