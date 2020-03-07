package com.xjt.model;

/**
CREATE BY CHEN ANRAN
2019.7.4
*/
public class Comment
{
    private String commentId;
    private String openid;
    private String nickName;
    private String resourceId;
    private String content;
    private String avatarUrl;
    private String motion;

    public Comment(String commentId,String openid,String nickName, String resourceId,String content,String avatarUrl,String motion)
    {
        this.commentId = commentId;
        this.openid = openid;
        this.nickName = nickName;
        this.resourceId = resourceId;
        this.content = content;
        this.avatarUrl = avatarUrl;
        this.motion = motion;
    }

    public Comment(){}

    public String getCommentId() { return commentId; }
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getOpenid() { return openid; }
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {return nickName;}
    public void setNickName(String nickName){this.nickName = nickName;}

    public String getRecourceId() {return resourceId;}
    public void setRecourceId(String recourceId) {
        this.resourceId = recourceId;
    }

    public String getContent() {return content;}
    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl=avatarUrl; }

    public String getMotion(){
        return this.motion;
    }
    public void setMotion(String motion){
        this.motion = motion;
    }
}
