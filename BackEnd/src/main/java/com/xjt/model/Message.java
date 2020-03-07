package com.xjt.model;

public class Message
{
    private String openid;
    private String message;
    private String Cname;
    private String mid;

    public Message(String openid,String message,String Cname)
    {
        this.openid=openid;
        this.message=message;
        this.Cname = Cname;
    }

    public Message(String message,String mid)
    {
        this.message=message;
        this.mid = mid;
    }

    public Message() {}

    public String getOpenid(){
        return this.openid;
    }
    public void setOpenid(String openid){
        this.openid=openid;
    }

    public String getMessage(){
        return this.message;
    }
    public void setMessage(String message){
        this.message=message;
    }

    public String getCname(){
        return this.Cname;
    }
    public void setCname(String Cname){
        this.Cname = Cname;
    }

    public String getMid(){
        return this.mid;
    }
    public void setMid(String mid){
        this.mid=mid;
    }
}
