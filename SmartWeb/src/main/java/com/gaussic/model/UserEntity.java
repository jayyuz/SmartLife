package com.gaussic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/2.
 */
@Entity
@Table(name = "user", schema = "smart", catalog = "")
public class UserEntity {
    @Expose
    @SerializedName("UserMobileNO")
    private String userMobileNo;
    @Expose
    @SerializedName("Password")
    private String password;
    @Expose
    @SerializedName("Devices")
    private String devices;
    @Expose
    @SerializedName("RegistTime")
    private Date registTime;

    private Collection<DevicesEntity> devicesByUserMobileNo;

    @Id
    @Column(name = "UserMobileNO", nullable = false, length = 11)
    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    @Basic
    @Column(name = "Password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "Devices", nullable = true, length = 255)
    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    @Basic
    @Column(name = "RegistTime", nullable = false)
    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userMobileNo != null ? !userMobileNo.equals(that.userMobileNo) : that.userMobileNo != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (devices != null ? !devices.equals(that.devices) : that.devices != null) return false;
        if (registTime != null ? !registTime.equals(that.registTime) : that.registTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userMobileNo != null ? userMobileNo.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (devices != null ? devices.hashCode() : 0);
        result = 31 * result + (registTime != null ? registTime.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUserMobileNo", fetch = FetchType.EAGER)
    public Collection<DevicesEntity> getDevicesByUserMobileNo() {
        return devicesByUserMobileNo;
    }

    public void setDevicesByUserMobileNo(Collection<DevicesEntity> devicesByUserMobileNo) {
        this.devicesByUserMobileNo = devicesByUserMobileNo;
    }

    //验证码
    private String code;

    @Transient   //不需要持久到DB的属性使用该注解
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
