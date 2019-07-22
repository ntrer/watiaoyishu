package com.watiao.yishuproject.base;

/**
 * Created by YD on 2018/9/18.
 */

public class MessageEvent4 {
    private String message;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public MessageEvent4(String message,String price){
        this.message=message;
        this.price=price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

