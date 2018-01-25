package com.xulc.algorithmstudy.model;

/**
 * Date：2018/1/25
 * Desc：
 * Created by xuliangchun.
 */

public class ChatModel {
    private String message;
    private boolean fromMe;

    public ChatModel(String message, boolean fromMe) {
        this.message = message;
        this.fromMe = fromMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }
}
