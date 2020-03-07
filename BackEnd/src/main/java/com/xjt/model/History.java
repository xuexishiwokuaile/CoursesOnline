package com.xjt.model;

import java.util.Date;

public class History
{
    private String openid;
    private String resId;
    private String inTime;
    private String date;
    private String outTime;
    private String totalTime;

    public History(){

    }

    public History(String openid,String resId,String inTime,String date,String outTime)
    {
        this.openid = openid;
        this.resId = resId;
        this.inTime = inTime;
        this.date = date;
        this.outTime = outTime;
        this.totalTime = String.valueOf(Integer.parseInt(this.outTime) - Integer.parseInt(this.inTime));
    }

    public String getOpenid(){
        return this.openid;
    }
    public void setOpenid(String openid){
        this.openid = openid;
    }

    public String getResId(){
        return this.resId;
    }
    public void setResId(String resId){
        this.resId = resId;
    }

    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public String getInTime(){
        return this.inTime;
    }
    public void setInTime(String inTime){
        this.inTime = inTime;
    }

    public String getOutTime(){
        return this.outTime;
    }
    public void setOutTime(String outTime){
        this.outTime = outTime;
    }

    public String getTotalTime(){
        return this.totalTime;
    }
    public void setTotalTime(String totalTime){
        this.totalTime = totalTime;
    }
}
