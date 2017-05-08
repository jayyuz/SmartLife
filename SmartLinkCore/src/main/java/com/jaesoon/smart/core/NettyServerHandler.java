package com.jaesoon.smart.core;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jaesoon.protocol.Action;
import com.jaesoon.protocol.SmPackage;
import com.jaesoon.protocol.bean.DevInfo;
import com.jaesoon.protocol.bean.UserInfo;
import com.jaesoon.protocol.core.BaseRequset;
import com.jaesoon.protocol.core.OfflineNotify;
import com.jaesoon.protocol.core.req.connection.PushToDevReq;
import com.jaesoon.protocol.core.req.login.DevLoginReq;
import com.jaesoon.protocol.core.req.login.UserLoginReq;
import com.jaesoon.protocol.core.res.BaseRes;
import com.jaesoon.protocol.core.res.userLogined.QueryDevicesRes;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import okhttp3.*;
import org.apache.log4j.Logger;
import smart.DevicesEntity;
import smart.UserEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/13.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient okHttpClient = new OkHttpClient();

//    private static Logger logger = Logger.getLogger(NettyServerBootstrap.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {
        Gson gson = new Gson();
//        logger.debug("SERVER接收到消息:" + msg);
        MyLogger.log("SERVER接收到消息:" + msg);
        SmPackage smPackage = gson.fromJson(msg, SmPackage.class);
        BaseRes baseRes = new BaseRes();
        if (smPackage.content != null) {
            BaseRequset baseRequset = gson.fromJson(smPackage.content, BaseRequset.class);
            if (baseRequset.Action.equals(Action.Action_Heart_Beat)) {
                smPackage.content = new JsonParser().parse(new BaseRes().toString()).getAsJsonObject();
//                gson.toJsonTree(new BaseRes());
                ctx.channel().writeAndFlush(smPackage.toString() + Constant.END_FIX);

            } else if (baseRequset.Action.equals(Action.Action_Dev_Regist)) {
            } else if (baseRequset.Action.equals(Action.Action_Dev_Login)) {
                DevLoginReq devLoginReq = gson.fromJson(smPackage.content, DevLoginReq.class);
                DevicesEntity devicesEntity = new DevicesEntity();
                devicesEntity.setDevId(devLoginReq.DevId);
                devicesEntity.setSn(devLoginReq.sn);
                List<DevicesEntity> devEntities = null;
                //申明给服务端传递一个json串
                //创建一个OkHttpClient对象
                //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
                RequestBody requestBody = RequestBody.create(JSON, gson.toJson(devicesEntity, DevicesEntity.class));
                //创建一个请求对象
                Request request = new Request.Builder()
                        .url(Constant.SERVER_URL + "devLogin")
                        .post(requestBody)
                        .build();
                //发送请求获取响应
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    //判断请求是否成功
                    if (response.isSuccessful()) {
                        String res = response.body().string();
//                        logger.debug(res);
                        MyLogger.log("http server response:" + res);
                        if (!res.equals("failer")) {
                            devicesEntity = gson.fromJson(res, DevicesEntity.class);
                            //登出已登录设备
                            if (DevInfoMap.get(devLoginReq.DevId) != null) {
                                DevInfo devInfo = DevInfoMap.get(devLoginReq.DevId);
                                SmPackage smPackage1 = new SmPackage();
                                smPackage1.sequence = 0;
                                smPackage1.content = new JsonParser().parse(new OfflineNotify().toString()).getAsJsonObject();
                                SocketChannel socketChannel = NettyChannelMap.get(devInfo.sessionId);
                                socketChannel.writeAndFlush(smPackage1.toString() + Constant.END_FIX);
                                socketChannel.disconnect();
                                socketChannel.close();
                                NettyChannelMap.remove(socketChannel);
                                DevInfoMap.remove(devInfo);
                            }
                            //添加到设备列表
                            DevInfo devInfo = new DevInfo();
                            devInfo.DevId = devLoginReq.DevId;
                            devInfo.SN = devLoginReq.sn;
                            devInfo.DevType = devicesEntity.getDevType();
                            if (devicesEntity.getUsers() != null)
                                devInfo.Users = devicesEntity.getUsers().split(",");
                            devInfo.sessionId = SessionUtil.generateSessionId();
                            DevInfoMap.add(devInfo.DevId, devInfo);
                            NettyChannelMap.add(devInfo.sessionId, (SocketChannel) ctx.channel());
                            smPackage.sessionId = devInfo.sessionId;
                        } else {
                            baseRes.Code = 1;
                            baseRes.Err = "设备Id或SN错误！";
                        }
                        smPackage.content = new JsonParser().parse(baseRes.toString()).getAsJsonObject();
                        ctx.channel().writeAndFlush(smPackage.toString() + Constant.END_FIX);
                        MyLogger.log("server response:" + smPackage.toString());
                    } else {
                        MyLogger.log("http server no response");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (baseRequset.Action.equals(Action.Action_Push_To_User)) {
                if (smPackage.sessionId != null) {
                    if (NettyChannelMap.get(smPackage.sessionId) != null) {
                        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
                        RequestBody requestBody = RequestBody.create(JSON, gson.toJson(smPackage.content));

                        //创建一个请求对象
                        Request request = new Request.Builder()
                                .url(Constant.SERVER_URL + "devUpdate")
                                .post(requestBody)
                                .build();
                        //发送请求获取响应
                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            //判断请求是否成功
                            if (response.isSuccessful()) {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        baseRes.Code = 1;
                        baseRes.Err = "设备未登陆，权限限制";
                    }
                } else {
                    baseRes.Code = 1;
                    baseRes.Err = "设备未登陆，权限限制";
                }
                smPackage.content = new JsonParser().parse(baseRes.toString()).getAsJsonObject();
                ctx.channel().writeAndFlush(smPackage.toString() + Constant.END_FIX);
                MyLogger.log("server response:" + smPackage.toString());
            } else if (baseRequset.Action.equals(Action.Action_User_Regist)) {
            } else if (baseRequset.Action.equals(Action.Action_User_Login)) {
//                logger.debug();
                MyLogger.log("用户登录");
                UserLoginReq userLoginReq = gson.fromJson(smPackage.content, UserLoginReq.class);
                UserEntity userEntity = new UserEntity();
                userEntity.setUserMobileNo(userLoginReq.UserMobileNO);
                userEntity.setPassword(userLoginReq.UserPassword);
                smPackage.content = new JsonParser().parse(baseRes.toString()).getAsJsonObject();
                ctx.channel().writeAndFlush(smPackage.toString() + Constant.END_FIX);
//                logger.debug();
                MyLogger.log("server send:" + smPackage.toString());
            } else if (baseRequset.Action.equals(Action.Action_Push_To_Dev)) {
                if (smPackage.sessionId != null) {
                    if (NettyChannelMap.get(smPackage.sessionId) != null) {
                        PushToDevReq pushToDevReq = gson.fromJson(smPackage.content, PushToDevReq.class);

                        DevInfo devInfo = DevInfoMap.get(pushToDevReq.DevId);
                        boolean has = false;
                        if (has) {
                            if (devInfo != null) {
                                SocketChannel devSocketChannel = NettyChannelMap.get(devInfo.sessionId);
                                if (devSocketChannel != null) {
                                    SmPackage smPackage2 = smPackage;
                                    smPackage2.sequence = 0;
                                    smPackage2.sessionId = null;
                                    devSocketChannel.writeAndFlush(smPackage2.toString() + Constant.END_FIX);
                                    MyLogger.log("server response:" + smPackage2.toString());
                                } else {
                                    //连接消失，保存
                                }
                            } else {
                                //用户不在线，保存
                            }
                        } else {
                            baseRes.Code = 1;
                            baseRes.Err = "没有权限";
                        }
                    } else {
                        baseRes.Code = 1;
                        baseRes.Err = "设备未登陆，权限限制";
                    }
                } else {
                    baseRes.Code = 1;
                    baseRes.Err = "设备未登陆，权限限制";
                }
                smPackage.content = new JsonParser().parse(baseRes.toString()).getAsJsonObject();
                ctx.channel().writeAndFlush(smPackage.toString() + Constant.END_FIX);
                MyLogger.log("server response:" + smPackage.toString());
            } else if (baseRequset.Action.equals(Action.Action_User_Query_Devices)) {
                QueryDevicesRes queryDevicesRes = new QueryDevicesRes();
                if (smPackage.sessionId != null) {
                    UserInfo userInfo = UserInfoMap.getUserBySessionId(smPackage.sessionId);
                    if (userInfo != null && userInfo.UserMobileNO != null) {
                    }
                } else {
                    queryDevicesRes.Code = 1;
                    queryDevicesRes.Err = "用户未登陆，权限限制";
                }
                smPackage.content = new JsonParser().parse(queryDevicesRes.toString()).getAsJsonObject();
                ctx.channel().writeAndFlush(smPackage.toString() + Constant.END_FIX);
                MyLogger.log("server response:" + smPackage.toString());
            } else if (baseRequset.Action.equals(Action.Action_Server_Push_To_User)) {

            } else if (baseRequset.Action.equals(Action.Action_Server_Push_To_Dev)) {
                if (ctx.channel().remoteAddress().toString().split(":")[0].equals(ctx.channel().localAddress().toString().split(":")[0])) {
                    //推送到设备
                    PushToDevReq pushToDevReq = gson.fromJson(smPackage.content, PushToDevReq.class);

                    DevInfo devInfo = DevInfoMap.get(pushToDevReq.DevId);
//                    DevicesEntity devEntity1 = DatabaseAccess.getDevEntity(pushToDevReq.DevId).get(0);
                    if (devInfo != null) {
                        SocketChannel devSocketChannel = NettyChannelMap.get(devInfo.sessionId);

                        if (devSocketChannel != null) {
                            if (devSocketChannel.isActive()) {
                                SmPackage smPackage1 = smPackage;
                                smPackage1.sequence = 0;
                                smPackage1.sessionId = null;
                                devSocketChannel.writeAndFlush(smPackage1.toString() + Constant.END_FIX);
                                MyLogger.log("server response:" + smPackage1.toString());
                            } else {
                                DevInfoMap.remove(devInfo);
                                NettyChannelMap.remove(devSocketChannel);
                                //创建一个请求对象
                                Request request = new Request.Builder()
                                        .url(Constant.SERVER_URL + "devUpdateState" + "/" + pushToDevReq.DevId + "/false")
                                        .build();
                                //发送请求获取响应
                                try {
                                    Response response = okHttpClient.newCall(request).execute();
                                    //判断请求是否成功
                                    if (response.isSuccessful()) {

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            //更新设备信息
                        }
                    } else {
                        //创建一个请求对象
                        Request request = new Request.Builder()
                                .url(Constant.SERVER_URL + "devUpdateState" + "/" + pushToDevReq.DevId + "/false")
                                .build();
                        //发送请求获取响应
                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            //判断请求是否成功
                            if (response.isSuccessful()) {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    ctx.channel().close();
                }
            } else {
                baseRes.Code = 1;
                baseRes.Err = "行动未知，系统故障！";
                smPackage.content = new JsonParser().parse(baseRes.toString()).getAsJsonObject();
                MyLogger.log("server response:" + baseRes.toString());
                ctx.channel().writeAndFlush(smPackage.toString() + Constant.END_FIX);
                ctx.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
//        logger.debug("Unexpected exception from downstream.", cause);
        ctx.close();
    }


}
