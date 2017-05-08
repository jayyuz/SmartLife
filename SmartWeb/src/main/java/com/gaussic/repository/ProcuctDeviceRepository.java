package com.gaussic.repository;

import com.gaussic.model.ProductdevicesEntity;
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
public interface ProcuctDeviceRepository extends JpaRepository<ProductdevicesEntity, Integer> {

    @Transactional
    @Query("select devices from ProductdevicesEntity devices where devices.devId=:qDevicesId and devices.sn=:qSN")
    public List<ProductdevicesEntity> verifyDevice(@Param("qDevicesId") String devId, @Param("qSN") String sn);

}