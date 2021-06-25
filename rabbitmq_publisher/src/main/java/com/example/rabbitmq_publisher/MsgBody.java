package com.example.rabbitmq_publisher;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MsgBody {

    private String msg;
    private String time;

    public MsgBody() {
    }

    public MsgBody(String msg, String time) {
        this.msg = msg;
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
