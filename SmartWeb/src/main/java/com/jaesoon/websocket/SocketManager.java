package com.jaesoon.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.jaesoon.core.entity.ServerPushToDevReq;
import com.jaesoon.websocket.smart.SmPackage;

import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/5.
 */
public class SocketManager {
    public static final String END_FIX = "&$$&";
    private static Socket socket;
    //    private static BufferedReader sin;
//    private static PrintWriter os;
    private static BufferedReader is;
    private static Writer writer;
    private static Gson gson = new Gson();

    static {
        init();
    }

    public static void pushToDev(String devId, String content) throws IOException {
        if (socket == null) {
            init();
        }
        if (socket != null) {
            ServerPushToDevReq serverPushToDevReq = new ServerPushToDevReq();
            serverPushToDevReq.Content = new JsonParser().parse(content).getAsJsonObject();
            serverPushToDevReq.DevId = devId;
            SmPackage smPackage = new SmPackage();
            smPackage.sequence = 0;
            smPackage.content = new JsonParser().parse(serverPushToDevReq.toString()).getAsJsonObject();
            smPackage.sessionId = "";
            writer.write(new String(smPackage.toString() + END_FIX));
            writer.flush();
        }
    }

    private static void init() {
        try {
            socket = new Socket("127.0.0.1", 9999);
            try {
                //向本机的4700端口发出客户请求
//                sin = new BufferedReader(new InputStreamReader(System.in));
                //由系统标准输入设备构造BufferedReader对象
//                os = new PrintWriter(socket.getOutputStream());
                //由Socket对象得到输出流，并构造PrintWriter对象
                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new OutputStreamWriter(socket.getOutputStream());
            } catch (Exception e) {
                socket = null;
                e.printStackTrace();
            }
        } catch (IOException e) {
            socket = null;
            e.printStackTrace();
        }
    }
}
