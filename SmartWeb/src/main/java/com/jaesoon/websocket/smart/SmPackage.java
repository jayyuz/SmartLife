package com.jaesoon.websocket.smart;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Jaesoon
 */
public class SmPackage extends BaseMsg {
    /**
     * 请求序列()
     */
    @Expose
    @SerializedName("sequence")
    public int sequence = 0;

    /**
     * 请求内容
     */
    @Expose
    @SerializedName("content")
    public JsonObject content;

    /**
     * SessionId
     */
    @Expose
    @SerializedName("sessionId")
    public String sessionId;

}
