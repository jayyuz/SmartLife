package com.gaussic.repository;

import com.gaussic.model.DevicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dzkan on 2016/3/8.
 */
@Repository
public interface DevicesRepository extends JpaRepository<DevicesEntity, Integer> {

    @Transactional
    @Query("select devices from DevicesEntity devices where devices.devId=:qDevId and devices.sn=:qSn")
    public List<DevicesEntity> verifyDev(@Param("qDevId") String devId, @Param("qSn") String sn);

    @Transactional
    @Query("select devices from DevicesEntity devices where devices.devId=:qDevicesId")
    public List<DevicesEntity> getDevicesById(@Param("qDevicesId") String devId);


}