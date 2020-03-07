package com.xjt.model;

public class Resources
{

    private String resId;
    private String Cname;
    private String resInfo;
    private String resField;
    private String Upid;
    private String school;
    private String score;
    private String imgUrl;


    public Resources(String rname, String rid, String rinfo)
    {
        this.Cname = rname;
        this.resId = rid;
        this.resInfo = rinfo;
    }

    public Resources (String name,String rid ,String rinfo ,String rfield ,String upid,String school)
    {
        this.resField =rfield;
        this.resId = rid;
        this.Cname = name;
        this.Upid =upid;
        this.resInfo  =rinfo;
        this.school = school;
    }

    public Resources (String name,String rid ,String rinfo ,String rfield ,String upid,String school,String imgUrl)
    {
        this.resField =rfield;
        this.resId = rid;
        this.Cname = name;
        this.Upid =upid;
        this.resInfo  =rinfo;
        this.school=school;
        this.imgUrl = imgUrl;
    }

    public Resources(String resField)
    {
        this.resField = resField;
    }


    public Resources() {
    }

    public String getResField() { return this.resField; }
    public void setResField(String field) {
        this.resField = field;
    }

    public String getUpid() { return this.Upid; }
    public void setUpid(String upid) {
        Upid = upid;
    }

    public String getResId() { return this.resId; }
    public void setResId(String id) {
        this.resId = id;
    }

    public String getCname() { return this.Cname; }
    public void setCname(String name) {
        this.Cname = name;
    }

    public String getResInfo() { return this.resInfo; }
    public void setResInfo(String info) {
        this.resInfo = info;
    }

    public String getSchool(){
        return this.school;
    }
    public void setSchool(){
        this.school = school;
    }

    public String getScore(){
        return this.score;
    }
    public void setScore(String score){
        this.score = score;
    }

    public String getImgUrl(){
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public boolean equals(Object obj) {
        Resources r = (Resources)obj;
        return this.resId.equals(r.resId);
    }

    public int hashCode() {
        String in = this.resId + this.Upid;
        return in.hashCode();
    }
}
