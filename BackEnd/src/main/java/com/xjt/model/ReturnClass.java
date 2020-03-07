package com.xjt.model;

import com.xjt.dao.IUserDao;

public class ReturnClass  {
    private int Code;
    private String msg ;
    private Object data ;

    public int getCode() { return this.Code; }
    public void setCode(int code) {
        Code = code;
    }

    public Object getData() { return this.data; }
    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() { return this.msg; }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ReturnClass(int code,String message,Object d)
    {
        Code =code;
        msg = message;
        data =d;
    }
    public ReturnClass()
    {

    }
}

