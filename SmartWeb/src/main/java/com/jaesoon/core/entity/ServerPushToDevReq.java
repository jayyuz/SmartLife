/**
 * @author Jaesoon
 * @version ����ʱ�䣺2016��7��17�� ����9:40:24
 */
package com.jaesoon.core.entity;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jaesoon.websocket.smart.BaseMsg;

/**
 * @author Jaesoon
 *
 */
@SuppressWarnings("serial")
public class ServerPushToDevReq extends BaseMsg {
    /** Action */
    @Expose
    @SerializedName("Action")
    private String Action = "ServerPushToDev";

    /**
     * 设备Id
     *
     */
    @Expose
    @SerializedName("DevId")
    public String DevId;

    /**
     * 内容
     */
    @Expose
    @SerializedName("Content")
    public JsonElement Content;



}
