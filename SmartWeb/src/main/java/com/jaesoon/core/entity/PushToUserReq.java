package com.jaesoon.core.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/10/5.
 */
public class PushToUserReq {
    /**
     * Action
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
