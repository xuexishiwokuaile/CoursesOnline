package com.xjt.controller;

import com.xjt.model.*;
import com.xjt.service.IAdminService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 CREATED BY CHEN ANRAN
 */

@Controller
@RequestMapping("/admin")
public class AdminController
{
    /**
     CREATED BY CHEN ANRAN
     2019.7.6
     */
    @Autowired
    private  IAdminService adminService;

    //管理员登录
    @RequestMapping("/login")
    public void Login(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String nickName = request.getParameter("nickName");
        String openid = request.getParameter("openid");
        String avatarUrl = request.getParameter("avatarUrl");

        ReturnClass rt = adminService.login(openid,nickName,avatarUrl);

        JSONObject jsonObject = new JSONObject();
        if(rt.getCode()==1)  //存在该管理员信息
        {
            jsonObject.put("nickName", nickName);
            jsonObject.put("openid",openid);
            jsonObject.put("avatarUrl",avatarUrl);
        }
        else  //不存在该管理员信息
        {
            jsonObject.put("nickName",null);
            jsonObject.put("openid",null);
            jsonObject.put("avatarUrl",null);
        }
        out.println(jsonObject);
    }

    //验证管理员身份
    @RequestMapping("/vertifyAdmin")
    public void VertifyAdmin(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        ReturnClass rt = adminService.vertifyAdmin(openid);
        boolean flag = (Boolean)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("flag",flag);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    //查看待审核评论列表
    @RequestMapping("/commentAudit")
    public void CommentAudit(HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        ReturnClass rt = adminService.commentAudit();
        List<Comment> list = (List<Comment>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<list.size();i++)
        {
            jsonObject.put("commentId",list.get(i).getCommentId());
            jsonObject.put("openid",list.get(i).getOpenid());
            jsonObject.put("content",list.get(i).getContent());
            jsonObject.put("nickName",list.get(i).getNickName());
            jsonObject.put("avatarUrl",list.get(i).getAvatarUrl());
            jsonObject.put("motion",list.get(i).getMotion());
            jsonObject.put("resourceId",list.get(i).getRecourceId());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    //通过审核
    @RequestMapping("/passCommentAudit")
    public void PassCommentAudit(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String commentId = request.getParameter("commentId");

        ReturnClass rt = adminService.passCommentAudit(commentId);
        List<Comment> list = (List<Comment>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("commentId",commentId);
        jsonObject.put("openid",list.get(0).getOpenid());
        jsonObject.put("resourceId",list.get(0).getRecourceId());
        jsonObject.put("content",list.get(0).getContent());
        jsonObject.put("motion",list.get(0).getMotion());
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    //不通过审核
    @RequestMapping("/rejectCommentAudit")
    public void RejectCommentAudit(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String commentId = request.getParameter("commentId");

        adminService.rejectCommentAudit(commentId);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("commentId",commentId);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    //删除课程
    @RequestMapping("/deleteCourse")
    public void DeleteCourse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String Cid = request.getParameter("Cid");

        adminService.deleteCourse(Cid);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("Cid",Cid);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    //显示课程列表
    @RequestMapping("/displayCourse")
    public void DisplayCourse(HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        ReturnClass rt = adminService.displayCourse();
        List<Course> courseList = (List<Course>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<courseList.size();i++)
        {
            jsonObject.put("Cname",courseList.get(i).getCname());
            jsonObject.put("Cteacher",courseList.get(i).getCteacher());
            jsonObject.put("location",courseList.get(i).getLocation());
            jsonObject.put("University",courseList.get(i).getUniversity());
            jsonObject.put("couField",courseList.get(i).getField());
            jsonObject.put("courseTime",courseList.get(i).getCourseTime());
            jsonObject.put("Cid",courseList.get(i).getCid());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    //添加课程
    @RequestMapping("/addCourse")
    public void AddCourse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String Cname = request.getParameter("Cname");
        String Cteacher = request.getParameter("Cteacher");
        String location = request.getParameter("location");
        String University = request.getParameter("University");
        String couField = request.getParameter("couField");
        String courseTime = request.getParameter("courseTime");

        adminService.addCourse(Cname,Cteacher,location,University,couField,courseTime);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("Cname",Cname);
        jsonObject.put("Cteacher",Cteacher);
        jsonObject.put("location",location);
        jsonObject.put("University",University);
        jsonObject.put("couField",couField);
        jsonObject.put("courseTime",courseTime);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    //显示资源列表
    @RequestMapping("/resDisplay")
    public void ResDisplay(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String resField = request.getParameter("resField");
        ReturnClass rt = adminService.getResourceByField(resField);
        List<Resources> resourceList = (List<Resources>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<resourceList.size();i++)
        {
            jsonObject.put("Cname",resourceList.get(i).getCname());
            jsonObject.put("resField",resField);
            jsonObject.put("resInfo",resourceList.get(i).getResInfo());
            jsonObject.put("resId",resourceList.get(i).getResId());
            jsonObject.put("school",resourceList.get(i).getSchool());
            jsonObject.put("imgUrl",resourceList.get(i).getImgUrl());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    //显示资源所属的所有领域
    @RequestMapping("/fieldDisplay")
    public void FieldDisplay(HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        ReturnClass rt = adminService.getAllFields();
        List<Resources> resourceList = (List<Resources>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<resourceList.size();i++)
        {
            jsonObject.put("resField",resourceList.get(i).getResField());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    //删除资源
    @RequestMapping("/deleteResource")
    public void DeleteResource(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String resId = request.getParameter("resId");
        adminService.deleteResource(resId);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("resId",resId);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    //添加资源
    @RequestMapping("/addResource")
    public void AddResource(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String Cname = request.getParameter("Cname");
        String resField = request.getParameter("resField");
        String resInfo = request.getParameter("resInfo");
        adminService.addResource(Cname,resField,resInfo);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("Cname",Cname);
        jsonObject.put("resField",resField);
        jsonObject.put("resInfo",resInfo);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    //显示所有上传文件
    @RequestMapping("/displayFile")
    public void DisplayFile(HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        ReturnClass rt = adminService.displayFile();
        List<ResFile> resFileList = (List<ResFile>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<resFileList.size();i++)
        {
            jsonObject.put("file_id",resFileList.get(i).getFile_id());
            jsonObject.put("ftype",resFileList.get(i).getType());
            jsonObject.put("url",resFileList.get(i).getUrl());
            jsonObject.put("chapter",resFileList.get(i).getChapter());
            jsonObject.put("openid",resFileList.get(i).getOpenid());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    //通过审核
    @RequestMapping("/passFileAudit")
    public void PassFileAudit(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String file_id = request.getParameter("file_id");
        //给收藏这个资源的用户推送消息
        adminService.sendUpdateMessage(file_id);
        //通过审核
        adminService.auditFile(file_id);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("file_id",file_id);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    //不通过审核
    @RequestMapping("/rejectFileAudit")
    public void RejectFileAudit(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String file_id = request.getParameter("file_id");
        adminService.rejectFile(file_id);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("file_id",file_id);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }
}
