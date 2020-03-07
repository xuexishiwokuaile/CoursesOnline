package com.xjt.model;

public class Admin
{
    private String nickName;
    private String openid;
    private String avatarUrl;

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
}
