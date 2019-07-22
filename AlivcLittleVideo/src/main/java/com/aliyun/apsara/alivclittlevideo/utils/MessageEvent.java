package com.aliyun.apsara.alivclittlevideo.utils;

/**
 * Created by YD on 2018/9/18.
 */

public class MessageEvent {
    private String message;
    public MessageEvent(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

