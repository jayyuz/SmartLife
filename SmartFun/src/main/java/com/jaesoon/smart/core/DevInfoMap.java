package com.jaesoon.smart.core;

import com.jaesoon.protocol.bean.DevInfo;
import com.jaesoon.protocol.bean.UserInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaozb on 15-4-11.
 */
public class DevInfoMap {
    private static Map<String, DevInfo> map = new ConcurrentHashMap<String, DevInfo>();

    public static void add(String devId, DevInfo devInfo) {
        map.put(devId, devInfo);
    }

    public static DevInfo get(String devId) {
        return map.get(devId);
    }

    public static void remove(DevInfo devInfo) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() == devInfo) {
                map.remove(entry.getKey());
            }
        }
    }

}