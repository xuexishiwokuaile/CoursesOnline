package com.xjt.model;

public class Moment
{
    private String Mid;
    private String openid;
    private String nickName;
    private String content;
    private String url;
    private String avatarUrl;


    public Moment(){

    }

    public Moment(String Mid,String openid,String nickName,String content,String url,String avatarUrl)
    {
        this.Mid = Mid;
        this.openid = openid;
        this.nickName = nickName;
        this.content = content;
        this.url = url;
        this.avatarUrl = avatarUrl;
    }

    public String getMid(){
        return this.Mid;
    }
    public void setMid(String Mid){
        this.Mid = Mid;
    }

    public String getOpenid(){
        return this.openid;
    }
    public void setOpenid(String openid){
        this.openid = openid;
    }

    public String getNickName(){
        return this.nickName;
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String getContent(){
        return this.content;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getUrl(){
        return this.url;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public String getAvatarUrl(){
        return this.avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }
}
