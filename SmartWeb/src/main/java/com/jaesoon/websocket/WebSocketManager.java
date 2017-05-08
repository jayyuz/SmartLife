package com.jaesoon.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/10/5.
 */
public class WebSocketManager {

    /**
     * 用于存放账号和session对应关系的map
     */
    private static Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<String, WebSocketSession>();

    public static void addWebSocketSession(String userMobileNO, WebSocketSession socketSession) {
        webSocketSessionMap.put(userMobileNO, socketSession);
    }

    public static WebSocketSession getWebSocketSession(String serialNo) {
        return (WebSocketSession) webSocketSessionMap.get(serialNo);
    }

    public static boolean isContains(WebSocketSession socketSession) {
        return webSocketSessionMap.containsValue(socketSession);
    }

    public static void removeSession(WebSocketSession socketSession) {
        String key = null;
        for (Map.Entry entry : webSocketSessionMap.entrySet()) {
            if (socketSession.equals(entry.getValue())) {
                key = (String) entry.getKey();
                break;
            }
        }
        if (key != null) {
            webSocketSessionMap.remove(key);
        }
    }

    public static boolean sendToUser(String userMobileNo, String msg) {
        if (webSocketSessionMap.containsKey(userMobileNo)) {
            try {
                webSocketSessionMap.get(userMobileNo).sendMessage(new TextMessage(msg));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

}
