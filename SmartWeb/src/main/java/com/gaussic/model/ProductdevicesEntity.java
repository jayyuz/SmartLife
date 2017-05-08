package com.gaussic.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/2.
 */
@Entity
@Table(name = "productdevices", schema = "smart", catalog = "")
public class ProductdevicesEntity {
    private String devId;
    private String sn;
    private String devType;
    private String contents;
    private Date createTime;

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
    @Column(name = "DevType", nullable = false, length = 45)
    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    @Basic
    @Column(name = "Contents", nullable = true, length = 255)
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

        ProductdevicesEntity that = (ProductdevicesEntity) o;

        if (devId != null ? !devId.equals(that.devId) : that.devId != null) return false;
        if (sn != null ? !sn.equals(that.sn) : that.sn != null) return false;
        if (devType != null ? !devType.equals(that.devType) : that.devType != null) return false;
        if (contents != null ? !contents.equals(that.contents) : that.contents != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = devId != null ? devId.hashCode() : 0;
        result = 31 * result + (sn != null ? sn.hashCode() : 0);
        result = 31 * result + (devType != null ? devType.hashCode() : 0);
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
