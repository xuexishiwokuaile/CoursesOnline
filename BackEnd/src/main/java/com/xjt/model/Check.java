package com.xjt.model;

public class Check
{
    private String openid;
    private String date;  //签到日期

    public String getOpenid(){
        return this.openid;
    }
    public void setOpenid(String openid){
        this.openid = openid;
    }

    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
    }
}
