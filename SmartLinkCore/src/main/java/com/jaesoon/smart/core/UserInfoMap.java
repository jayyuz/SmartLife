package com.jaesoon.smart.core;

import com.jaesoon.protocol.bean.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaozb on 15-4-11.
 */
public class UserInfoMap {
    private static Map<String, UserInfo> map = new ConcurrentHashMap<String, UserInfo>();

    public static void add(String clientId, UserInfo userInfo) {
        map.put(clientId, userInfo);
    }

    public static UserInfo get(String clientId) {
        return map.get(clientId);
    }

    public static UserInfo getUserBySessionId(String sessionId) {
        for (Map.Entry entry : map.entrySet()) {
            if (((UserInfo)entry.getKey()).sessionId == sessionId) {
                    return (UserInfo) entry.getKey();
            }
        }
        return  null;
    }

    public static void remove(UserInfo userInfo) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() == userInfo) {
                map.remove(entry.getKey());
            }
        }
    }

}