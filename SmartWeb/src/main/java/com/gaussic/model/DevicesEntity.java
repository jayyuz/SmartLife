package com.gaussic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/2.
 */
@Entity
@Table(name = "devices", schema = "smart", catalog = "")
public class DevicesEntity {
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

    private UserEntity userByUserMobileNo;


    @Id
    @Column(name = "DevId", nullable = false, length = 15)
    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    @Basic
    @Column(name = "SN", nullable = false, length = 15)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Basic
    @Column(name = "UserMobileNO", nullable = false, length = 11)
    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    @Basic
    @Column(name = "DevType", nullable = false, length = 45)
    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    @Basic
    @Column(name = "Users", nullable = true, length = 255)
    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    @Basic
    @Column(name = "Content", nullable = true, length = 255)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "CreateTime", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DevicesEntity that = (DevicesEntity) o;

        if (devId != null ? !devId.equals(that.devId) : that.devId != null) return false;
        if (sn != null ? !sn.equals(that.sn) : that.sn != null) return false;
        if (userMobileNo != null ? !userMobileNo.equals(that.userMobileNo) : that.userMobileNo != null) return false;
        if (devType != null ? !devType.equals(that.devType) : that.devType != null) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Basic
    @Column(name = "State", nullable = true)
    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        int result = devId != null ? devId.hashCode() : 0;
        result = 31 * result + (sn != null ? sn.hashCode() : 0);
        result = 31 * result + (userMobileNo != null ? userMobileNo.hashCode() : 0);
        result = 31 * result + (devType != null ? devType.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "UserMobileNO", referencedColumnName = "UserMobileNO", nullable = false,insertable = false, updatable = false)
    public UserEntity getUserByUserMobileNo() {
        return userByUserMobileNo;
    }

    public void setUserByUserMobileNo(UserEntity userByUserMobileNo) {
        this.userByUserMobileNo = userByUserMobileNo;
    }


}
