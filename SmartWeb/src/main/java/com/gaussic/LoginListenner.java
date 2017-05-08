package com.gaussic;

import com.gaussic.controller.MobileController;
import com.gaussic.controller.Utils;
import com.gaussic.model.UserEntity;
import org.hibernate.Session;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  
 * @ClassName: LoginListenner 
 * @Description: 登录监听类-处理同一时间只允许账号，单地点登录 
 * @author Jaesoon
 *  
 */  
public class LoginListenner implements HttpSessionAttributeListener {

    /**
     * 用于存放账号和session对应关系的map 
     */  
    private static Map<String, HttpSession> map = new ConcurrentHashMap<String, HttpSession>();


    /** 
     * 当向session中放入数据触发 
     */  
    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();
        if (name.equals(Utils.USER_SESSION_NAME)) {
            UserEntity user = (UserEntity) event.getValue();
            if (map.get(user.getUserMobileNo()) != null) {
                HttpSession session = map.get(user.getUserMobileNo());
                session.removeAttribute(user.getUserMobileNo());
                session.invalidate();  
            }  
            map.put(user.getUserMobileNo(), event.getSession());
        }
    }

    /** 
     * 当向session中移除数据触发 
     */  
    public void attributeRemoved(HttpSessionBindingEvent event) {  
        String name = event.getName();
        if (name.equals(Utils.USER_SESSION_NAME)) {
            UserEntity user = (UserEntity) event.getValue();
            map.remove(user.getUserMobileNo());
        }  
    }  
  
    public void attributeReplaced(HttpSessionBindingEvent event) {  
  
    }  
  
    public Map<String, HttpSession> getMap() {  
        return map;  
    }  
  
    public void setMap(Map<String, HttpSession> map) {  
        this.map = map;  
    }  
  

    public static HttpSession getSession( String userMobileNO){
        return  (HttpSession) map.get(userMobileNO);
    }


}