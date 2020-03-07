package com.xjt.model;

public class ResFile {
    private String file_id;
    private String ftype;
    private String url;
    private String chapter;
    private String fileName;
    private String openid;


    public void setChapter(String chapter) {
        this.chapter = chapter;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.ftype = type;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getChapter() {
        return chapter;
    }

    public String getFile_id() {
        return file_id;
    }

    public String getType() {
        return ftype;
    }

    public String getUrl() {
        return url;
    }

    public String getOpenid(){
        return this.openid;
    }
    public void setOpenid(String openid){
        this.openid = openid;
    }

    public ResFile(String file_id, String type, String chapter, String url)
    {

        this.chapter =chapter;
        this.file_id =file_id;
        this.ftype =type;
        this.url= url;
    }

    public ResFile(String file_id, String type, String chapter, String url,String openid)
    {
        this.chapter =chapter;
        this.file_id =file_id;
        this.ftype =type;
        this.url= url;
        this.openid = openid;
    }

    public ResFile(String url)
    {
        this.url=url;
    }

    public ResFile()
    {}

}

