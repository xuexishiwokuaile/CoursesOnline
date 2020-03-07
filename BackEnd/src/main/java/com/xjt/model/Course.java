package com.xjt.model;

public class Course
{
    private String Cname;
    private String Cteacher;
    private String location;
    private String couField;
    private String Cid;
    private String courseTime;
    private String University;

    public Course()
    {}

    public Course(String University)
    {
        this.University=University;
    }

    public Course(String Cname,String Cteacher,String location,String couField,String Cid,String courseTime,String University)
    {
        this.Cname=Cname;
        this.Cteacher=Cteacher;
        this.location=location;
        this.couField=couField;
        this.Cid=Cid;
        this.courseTime=courseTime;
        this.University=University;
    }

    public String getCname() { return this.Cname; }
    public void setCname(String Cname) { this.Cname=Cname; }

    public String getCteacher() {return this.Cteacher; }
    public void setCteacher(String Cteacher) { this.Cteacher=Cteacher; }

    public String getLocation() { return this.location; }
    public void setLocation(String location) { this.location=location; }

    public String getField() { return this.couField; }
    public void setField(String couField) { this.couField=couField; }

    public String getCid() { return this.Cid; }
    public void setCid(String Cid) { this.Cid=Cid; }

    public String getCourseTime() { return this.courseTime; }
    public void setCourseTime(String courseTime) { this.courseTime=courseTime; }

    public String getUniversity() { return this.University; }
    public void setUniversity(String University) { this.University = University;}

    public boolean equals(Object obj) {
        Course c = (Course)obj;
        return this.Cid.equals(c.Cid);
    }

    public int hashCode() {
        String in = this.Cid+ this.Cname + this.Cteacher;
        return in.hashCode();
    }
}
