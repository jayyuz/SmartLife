
package com.jaesoon.websocket.smart;

/**
 * @author Jaesoon
 */
public class Action {
    /**
     * 心跳包
     */
    public static final String Action_Heart_Beat = "HeartBeat";

    /**
     * 设备推送到用户
     */
    public static final String Action_Push_To_User = "PushToUser";

    /**
     * 用户推送到设备
     */
    public static final String Action_Push_To_Dev = "PushToDev";

    /**
     * 服务端推送到用户
     */
    public static final String Action_Server_Push_To_User = "ServerPushToUser";
    /**
     * 服务端推送到用户
     */
    public static final String Action_Server_Push_To_Dev = "ServerPushToDev";

    /**
     * 用户登录
     */
    public static final String Action_User_Login = "UserLogin";

    /**
     * 设备登录
     */
    public static final String Action_Dev_Login = "DevLogin";

    /**
     * 用户注册
     */
    public static final String Action_User_Regist = "UserRegist";

    /**
     * 设备登录
     */
    public static final String Action_Dev_Regist = "DevRegist";

    /**
     * 用户查询所拥有的设备
     */
    public static final String Action_User_Query_Devices = "UserQueryDevices";

    /**
     * 挤下线了
     */
    public static final String Action_Offline = "Offline";
}
