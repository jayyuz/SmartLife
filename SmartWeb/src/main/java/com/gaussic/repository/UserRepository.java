package com.gaussic.repository;

import com.gaussic.model.UserEntity;
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
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
//    @Modifying      // 说明该方法是修改操作
//    @Transactional  // 说明该方法是事务性操作
//    // 定义查询
//    // @Param注解用于提取参数
//    @Query("update User us set us.userMobileNo=:qMobileNo, us.password=:qPassword,us.registTime =: qRegistTime")
//    public void updateUser(@Param("qMobileNo") String mobileNo,@Param("qPassword") String password, @Param("qRegistTime") Date registTime, @Param("qId") String id);

    @Transactional
    @Query("select user from UserEntity user where user.userMobileNo=:qUserMobileNo and user.password=:qPassword")
    public List<UserEntity> verifyUser(@Param("qUserMobileNo") String userMobileNo, @Param("qPassword") String password);

    @Transactional
    @Query("select user from UserEntity user where user.userMobileNo=:qUserMobileNo")
    public List<UserEntity> getUser(@Param("qUserMobileNo") String userMobileNo);

}