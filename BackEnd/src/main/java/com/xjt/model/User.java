package com.xjt.model;

/**
CREATED BY CHEN ANRAN
2019.7.4
*/
public class User
{
    private String nickName;
    private String openid;
    private String avatarUrl;
    private String score;

    public User() {}

    public User(String nickName,String avatarUrl)
    {
        this.nickName=nickName;
        this.avatarUrl=avatarUrl;
    }

    public User(String nickName,String avatarUrl,String score)
    {
        this.nickName=nickName;
        this.avatarUrl=avatarUrl;
        this.score=score;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

    public String getavatarUrl() { return this.avatarUrl; }
    public void setavatarUrl(String avatarUrl) { this.avatarUrl=avatarUrl; }

    public String getScore(){
        return this.score;
    }
    public void setScore(String score){
        this.score=score;
    }

}
