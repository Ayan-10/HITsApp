package io.realworld.android.hitsapp.Models;

public class Message {

    private String uId, message;
    private long time;

    public Message(String uId, String message, long time) {
        this.uId = uId;
        this.message = message;
        this.time = time;
    }

    public Message(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

    public Message() {
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
