package com.gaussic.controller;

import com.gaussic.LoginListenner;
import com.gaussic.model.DevicesEntity;
import com.gaussic.model.UserEntity;
import com.gaussic.repository.DevicesRepository;
import com.gaussic.repository.ProcuctDeviceRepository;
import com.gaussic.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jaesoon.core.entity.Device;
import com.jaesoon.core.entity.PushToUserReq;
import com.jaesoon.websocket.WebSocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2016/10/4.
 */
@Controller
public class DeviceContoller {

//    public static final String USER_SESSION_NAME = "userEntity";

    // 自动装配数据库接口，不需要再写原始的Connection来操作数据库
    @Autowired
    UserRepository userRepository;

    @Autowired
    DevicesRepository devicesRepository;

    @Autowired
    ProcuctDeviceRepository procuctDeviceRepository;


    @RequestMapping(value = "/devLogin", method = {RequestMethod.POST})
    @ResponseBody
    public String login(@RequestBody String str, ModelMap modelMap) {
        Gson gson = new Gson();
        DevicesEntity devicesEntity = gson.fromJson(str, DevicesEntity.class);
        List<DevicesEntity> devicesEntities = devicesRepository.verifyDev(devicesEntity.getDevId(), devicesEntity.getSn());
        if (devicesEntities.size() > 0) {
            devicesEntity = devicesEntities.get(0);
            Device device = new Device();
            device.setDevId(devicesEntity.getDevId());
            device.setSn(devicesEntity.getSn());
            device.setCreateTime(devicesEntity.getCreateTime());
            device.setUserMobileNo(devicesEntity.getUserMobileNo());
            device.setContent(devicesEntity.getContent());
            device.setUsers(devicesEntity.getUsers());
            String res = gson.toJson(device);
            devicesEntity.setState(true);
            devicesRepository.saveAndFlush(devicesEntity);
            return res;
        } else {
            return "failer";
        }
    }

    @RequestMapping(value = "/devUpdate", method = {RequestMethod.POST})
    @ResponseBody
    public String pushToUser(@RequestBody String str, ModelMap modelMap) {
        System.out.println(str);
        Gson gson = new Gson();
        PushToUserReq pushToUserReq = gson.fromJson(str, PushToUserReq.class);
        List<DevicesEntity> devicesEntities = devicesRepository.getDevicesById(pushToUserReq.DevId);
        if (devicesEntities != null && devicesEntities.size() > 0) {
            DevicesEntity devicesEntity = devicesEntities.get(0);
            devicesEntity.setContent(gson.toJson(pushToUserReq.Content));
            //更新服务器数据
            devicesRepository.saveAndFlush(devicesEntity);

            //push to user
            HttpSession httpSession = LoginListenner.getSession(devicesEntity.getUserByUserMobileNo().getUserMobileNo());
            UserEntity userEntity;
            if (httpSession != null) {
                userEntity = (UserEntity) httpSession.getAttribute(Utils.USER_SESSION_NAME);
                if (userEntity != null) {//在线，推送
                    WebSocketManager.sendToUser(userEntity.getUserMobileNo(), "refresh");
                }
            }

            //push to owners
            if (devicesEntity.getUsers() != null) {
                String[] users = devicesEntity.getUsers().split(",");
                for (String userMobile : users) {
                    httpSession = LoginListenner.getSession(userMobile);
                    if (httpSession != null) {
                        userEntity = (UserEntity)httpSession.getAttribute(Utils.USER_SESSION_NAME);
                        if (userEntity != null) {
                            WebSocketManager.sendToUser(userEntity.getUserMobileNo(), "refresh");
                        }
                    }
                }
            }
        }
        return "success";
    }

    @RequestMapping(value = "/devUpdateState/{devId}/{state}", method = RequestMethod.GET)
    public void devUpdateState(@PathVariable("devId") String devId, @PathVariable("state") Boolean state, ModelMap modelMap) {
        List<DevicesEntity> devicesEntities = devicesRepository.getDevicesById(devId);
        if (devicesEntities.size() > 0) {
            if (devicesEntities.get(0).getState() != state) {
                devicesEntities.get(0).setState(state);
                devicesRepository.saveAndFlush(devicesEntities.get(0));

                DevicesEntity devicesEntity = devicesEntities.get(0);
                //push to user
                UserEntity userEntity = (UserEntity) LoginListenner.getSession(devicesEntity.getUserByUserMobileNo().getUserMobileNo()).getAttribute(Utils.USER_SESSION_NAME);
                if (userEntity != null) {//在线，推送
                    WebSocketManager.sendToUser(userEntity.getUserMobileNo(), "refresh");
                }
                //push to owners
                if (devicesEntity.getUsers() != null) {
                    String[] users = devicesEntity.getUsers().split(",");
                    for (String userMobile : users) {
                        userEntity = (UserEntity) LoginListenner.getSession(userMobile).getAttribute(Utils.USER_SESSION_NAME);
                        if (userEntity != null) {
                            WebSocketManager.sendToUser(userEntity.getUserMobileNo(), "refresh");
                        }
                    }
                }
            }
        }
    }

}
