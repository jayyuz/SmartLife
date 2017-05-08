package com.gaussic.controller;

import com.gaussic.model.DevicesEntity;
import com.gaussic.model.ProductdevicesEntity;
import com.gaussic.model.UserEntity;
import com.gaussic.repository.DevicesRepository;
import com.gaussic.repository.ProcuctDeviceRepository;
import com.gaussic.repository.UserRepository;
import com.google.gson.Gson;
import com.jaesoon.core.devicetype.Switch;
import com.jaesoon.websocket.SocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
@Controller
public class MobileController {

    // 自动装配数据库接口，不需要再写原始的Connection来操作数据库
    @Autowired
    UserRepository userRepository;

    @Autowired
    DevicesRepository devicesRepository;

    @Autowired
    ProcuctDeviceRepository procuctDeviceRepository;

    /**
     * 请求返回注册界面
     */
    @RequestMapping(value = "/mregist", method = RequestMethod.GET)
    public String preregist() {
        return "mobile/mregist";
    }

    /**
     * 注册提交数据
     */
    @RequestMapping(value = "/mregistp", method = RequestMethod.POST)
    public String regist(@ModelAttribute("user") UserEntity userEntity, ModelMap modelMap, final RedirectAttributes redirectAttributes) {

        if (userEntity == null) {
            redirectAttributes.addFlashAttribute("errCode", 0);
            return "redirect:/mlogin";
        }
        if (userEntity.getUserMobileNo() == null) {

            redirectAttributes.addFlashAttribute("errCode", 1);
            return "redirect:/mlogin";
        }
        if (userEntity.getPassword() == null) {
            redirectAttributes.addFlashAttribute("userMobile", userEntity.getUserMobileNo());
            redirectAttributes.addFlashAttribute("errCode", 2);
            return "redirect:/mlogin";
        }
        if (userEntity.getCode() == null) {
            redirectAttributes.addFlashAttribute("userMobile", userEntity.getUserMobileNo());
            redirectAttributes.addFlashAttribute("errCode", 3);
            return "redirect:/mlogin";
        }

        if (!(userEntity.getCode().equalsIgnoreCase(Utils.getSession().getAttribute("code").toString()))) {  //忽略验证码大小写
            redirectAttributes.addFlashAttribute("userMobile", userEntity.getUserMobileNo());
            redirectAttributes.addFlashAttribute("errCode", 4);
            return "redirect:/mlogin";
        }

        if (userRepository.getUser(userEntity.getUserMobileNo()).size() > 0) {
            modelMap.addAttribute(Utils.USER_SESSION_NAME, userEntity);
            return "/mobile/mregisterr";
        } else {
            userEntity.setRegistTime(new Date());
            userRepository.saveAndFlush(userEntity);
            //登录
            redirectAttributes.addFlashAttribute("userMobile", userEntity.getUserMobileNo());
            return "redirect:/mlogin";
        }
    }

    @RequestMapping(value = "/mobile/mregister", method = RequestMethod.GET)
    public String registerr(ModelMap modelMap) {
        modelMap.addAttribute("errCode", 1);
        modelMap.addAttribute("errString", "用户已经存在，请仔细检查手机号。");
        return "/mobile/mregisterr";
    }

    @RequestMapping(value = "/mlogin", method = RequestMethod.GET)
    public String mlogin(ModelMap modelMap) {
        if (Utils.getSession().getAttribute(Utils.USER_SESSION_NAME) == null) {
            return "/mobile/mlogin";
        } else {
            return "redirect:/mindex";
        }
    }

    @RequestMapping(value = "/mloginp", method = RequestMethod.POST)
    public String loginQ(@ModelAttribute("user") UserEntity userEntity, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (userEntity == null) {
            redirectAttributes.addFlashAttribute("errCode", 0);
            return "redirect:/mlogin";
        }
        if (userEntity.getUserMobileNo() == null) {

            redirectAttributes.addFlashAttribute("errCode", 1);
            return "redirect:/mlogin";
        }
        if (userEntity.getPassword() == null) {
            redirectAttributes.addFlashAttribute("userMobile", userEntity.getUserMobileNo());
            redirectAttributes.addFlashAttribute("errCode", 2);
            return "redirect:/mlogin";
        }
        if (userEntity.getCode() == null) {
            redirectAttributes.addFlashAttribute("userMobile", userEntity.getUserMobileNo());
            redirectAttributes.addFlashAttribute("errCode", 3);
            return "redirect:/mlogin";
        }

        if (!(userEntity.getCode().equalsIgnoreCase(Utils.getSession().getAttribute("code").toString()))) {  //忽略验证码大小写
            redirectAttributes.addFlashAttribute("userMobile", userEntity.getUserMobileNo());
            redirectAttributes.addFlashAttribute("errCode", 4);
            return "redirect:/mlogin";
        }

        //modelMap自动与session对应，你在往modelmap中添加对应属性便是往session中添加属性（前提是你已经在@SessionAttributes注解中定义好）
        if (userRepository.verifyUser(userEntity.getUserMobileNo(), userEntity.getPassword()).size() > 0) {
            Utils.getSession().setAttribute(Utils.USER_SESSION_NAME, userRepository.getUser(userEntity.getUserMobileNo()).get(0));                   //成功将存入session中
            return "redirect:/mindex";
        }else{
            redirectAttributes.addFlashAttribute("userMobile", userEntity.getUserMobileNo());
            redirectAttributes.addFlashAttribute("errCode", 5);
            return "redirect:/mlogin";
        }
    }

    @RequestMapping(value = "/mloginout", method = RequestMethod.GET)
    public String loginOut(@ModelAttribute("user") UserEntity userEntity, ModelMap modelMap) {
        if (Utils.getSession().getAttribute(Utils.USER_SESSION_NAME) != null) {
            Utils.getSession().setAttribute(Utils.USER_SESSION_NAME, null);
            Utils.getSession().setAttribute("userMobile", null);
        }
        return "redirect:/mindex";
    }


    @RequestMapping(value = "/mindex", method = RequestMethod.GET)
    public String mIndex(ModelMap modelMap) {
        UserEntity userEntityOne = (UserEntity) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
        //刷新一次
        UserEntity userEntity = null;
        if (userEntityOne != null && userEntityOne.getUserMobileNo() != null) {
            List<UserEntity> userEntities = userRepository.getUser(userEntityOne.getUserMobileNo());
            if (userEntities != null && userEntities.size() > 0) {
                userEntity = userEntities.get(0);
            } else {
                userEntity = userEntityOne;
            }
        }
        if (userEntity != null) {
            modelMap.addAttribute("userMobile", userEntity.getUserMobileNo());
            modelMap.addAttribute("userEntity", userEntity);
            Collection<DevicesEntity> devicesEntities = userEntity.getDevicesByUserMobileNo();
            ArrayList<DevicesEntity> devices = new ArrayList<>();
            if (devicesEntities != null) {
                devices.addAll(devicesEntities);
            }
            if (userEntity.getDevices() != null) {
                String[] devIds = userEntity.getDevices().split(",");
                if (devIds != null) {
                    for (String devId : devIds) {
                        Collection<DevicesEntity> devicesEntities1 = devicesRepository.getDevicesById(devId);
                        if (devicesEntities1 != null && devicesEntities1.size() > 0) {
                            devices.addAll(devicesEntities1);
                        }
                    }
                }
            }
            if (devices.size() > 0) {
                modelMap.addAttribute("devices", devices);
            }
        }
        return "/mobile/mindex";
    }

    @RequestMapping(value = "/mdevice/{id}", method = RequestMethod.GET)
    public String mdevice(@PathVariable("id") String devId, ModelMap modelMap) {
        UserEntity userEntity = (UserEntity) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
        if (userEntity == null) {
            return "redirect:/mlogin";
        }
        if (devId != null) {
            List<DevicesEntity> devicesEntities = devicesRepository.getDevicesById(devId);
            if (devicesEntities.size() > 0) {
                DevicesEntity deviceEntity = devicesEntities.get(0);
                modelMap.addAttribute("deviceEntity", deviceEntity);
                modelMap.addAttribute("userEntity", deviceEntity.getUserByUserMobileNo());
                return "/mobile/mdevice";
            }
        }
        return "redirect:/mindex";
    }

    @RequestMapping(value = "/mchangestate/{id}/{state}", method = RequestMethod.GET)
    public void mchangestate(@PathVariable("id") String devId, @PathVariable("state") Boolean state, ModelMap modelMap) {
        UserEntity userEntity = (UserEntity) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
        if (userEntity == null) {
            return ;
        }
        if (devId != null) {
            List<DevicesEntity> devicesEntities = devicesRepository.getDevicesById(devId);
            if (devicesEntities.size() > 0) {
                DevicesEntity deviceEntity = devicesEntities.get(0);
                Gson gson = new Gson();
                Switch iswitch = new Switch();
                iswitch.setState(state);
                deviceEntity.setContent(gson.toJson(iswitch));
                devicesRepository.saveAndFlush(deviceEntity);
                try {
                    SocketManager.pushToDev(deviceEntity.getDevId(),gson.toJson(iswitch));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/mmarket", method = RequestMethod.GET)
    public String mmarket(ModelMap modelMap) {
        UserEntity userEntity = (UserEntity) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
        if (userEntity != null) {
            modelMap.addAttribute("userMobile", userEntity.getUserMobileNo());
        }
        return "/mobile/mmarket";
    }

    @RequestMapping(value = "/msetting", method = RequestMethod.GET)
    public String msetting(ModelMap modelMap) {
        UserEntity userEntity = (UserEntity) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
        if (userEntity != null) {
            modelMap.addAttribute("userMobile", userEntity.getUserMobileNo());
        }
        return "/mobile/msetting";
    }

    @RequestMapping(value = "/madddevice", method = RequestMethod.GET)
    public String madddevice(ModelMap modelMap) {
        UserEntity userEntity = (UserEntity) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
        if (userEntity == null) {
            return "redirect:/mlogin";
        }
        return "/mobile/madddevice";
    }

    @RequestMapping(value = "/madddevicep", method = RequestMethod.POST)
    public String madddevicep(@ModelAttribute("device") DevicesEntity devicesEntity,ModelMap modelMap, RedirectAttributes redirectAttributes) {
        UserEntity userEntity = (UserEntity) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
        if (userEntity == null) {
            return "redirect:/mlogin";
        }

        if(devicesEntity==null){
            redirectAttributes.addFlashAttribute("errCode", 0);
            return "redirect:/madddevice";
        }

        if(devicesEntity.getDevId()==null){
            redirectAttributes.addFlashAttribute("errCode", 1);
            return "redirect:/madddevice";
        }
        if(devicesEntity.getSn()==null){
            redirectAttributes.addFlashAttribute("errCode", 2);
            return "redirect:/madddevice";
        }

        List<DevicesEntity> entities = devicesRepository.getDevicesById(devicesEntity.getDevId());
        if(entities!=null&&entities.size()>0){
            redirectAttributes.addFlashAttribute("errCode", 3);
            return "redirect:/madddevice";
        }

        List<ProductdevicesEntity> productdevicesEntities = procuctDeviceRepository.verifyDevice(devicesEntity.getDevId(),devicesEntity.getSn());
        if(productdevicesEntities!=null&&productdevicesEntities.size()>0){
            devicesEntity.setUserMobileNo(userEntity.getUserMobileNo());
            devicesEntity.setCreateTime(new Date());
            devicesEntity.setDevType(productdevicesEntities.get(0).getDevType());
            devicesRepository.saveAndFlush(devicesEntity);
            if(devicesEntity.getDevId().isEmpty()){
                redirectAttributes.addFlashAttribute("code", 1);
                return "redirect:/madddevice";
            }
        }else{
            redirectAttributes.addFlashAttribute("errCode", 4);
            return "redirect:/madddevice";
        }

        return "redirect:/mindex";
    }

    @RequestMapping(value = "/qrdemo", method = RequestMethod.GET)
    public String qrdemo(ModelMap modelMap) {
//        User userEntity = (User) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
//        if (userEntity != null) {
//            modelMap.addAttribute("userMobile", userEntity.getUserMobileNo());
//        }
        return "/mobile/qrdemo";

//        需要登录操作的接口
//        User userEntity = (User) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
//        if (userEntity == null) {
//            return "/mlogin";
//        }
    }
    @RequestMapping(value = "/iwebsocket", method = RequestMethod.GET)
    public String websocketdemo(ModelMap modelMap) {
        return "/websocket";
    }
    @RequestMapping(value = "/mdemo", method = RequestMethod.GET)
    public String demo(ModelMap modelMap) {
        UserEntity userEntity = (UserEntity) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
        if (userEntity != null) {
            modelMap.addAttribute("userMobile", userEntity.getUserMobileNo());
        }
        return "/mobile/demo";

//        需要登录操作的接口
//        User userEntity = (User) Utils.getSession().getAttribute(Utils.USER_SESSION_NAME);
//        if (userEntity == null) {
//            return "/mlogin";
//        }
    }


}
