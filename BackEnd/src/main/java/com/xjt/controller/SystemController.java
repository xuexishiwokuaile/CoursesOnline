package com.xjt.controller;


//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
import com.xjt.model.*;
import com.xjt.service.ISystemService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.xjt.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController
{
    /**
    CREATED BY CHEN ANRAN
    */

    @Autowired
    private ISystemService systemService;

    @RequestMapping("/comment")
    public void Comment(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String resourceId = request.getParameter("resId");
        ReturnClass rt = systemService.getCommentList(resourceId);
        List<Comment> commentList = (List<Comment>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<commentList.size();i++)
        {
            jsonObject.put("commentId",commentList.get(i).getCommentId());
            jsonObject.put("openid",commentList.get(i).getOpenid());
            jsonObject.put("content",commentList.get(i).getContent());
            jsonObject.put("nickName",commentList.get(i).getNickName());
            jsonObject.put("avatarUrl",commentList.get(i).getAvatarUrl());
            jsonObject.put("motion",commentList.get(i).getMotion());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示资源列表
    @RequestMapping("/resDisplay")
    public void ResDisplay(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String resField = request.getParameter("resField");
        ReturnClass rt = systemService.getResourceByField(resField);
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

    /**
     CREATED BY CHEN ANRAN
     */
    //显示已收藏的课程
    @RequestMapping("/chosenCourseDisplay")
    public void ChosenCourseDisplay(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        ReturnClass rt = systemService.getChosenCourse(openid);
        List<Course> courseList = (List<Course>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<courseList.size();i++)
        {
            jsonObject.put("Cname",courseList.get(i).getCname());
            jsonObject.put("Cteacher",courseList.get(i).getCteacher());
            jsonObject.put("location",courseList.get(i).getLocation());
            jsonObject.put("University",courseList.get(i).getLocation());
            jsonObject.put("couField",courseList.get(i).getField());
            jsonObject.put("courseTime",courseList.get(i).getCourseTime());
            jsonObject.put("Cid",courseList.get(i).getCid());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示提供课程的所有学校
    @RequestMapping("/universityDisplay")
    public void UniversityDisplay(HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        ReturnClass rt = systemService.getAllUniversity();
        List<Course> courseList = (List<Course>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<courseList.size();i++)
        {
            jsonObject.put("University",courseList.get(i).getUniversity());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示资源所属的所有领域
    @RequestMapping("/fieldDisplay")
    public void FieldDisplay(HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        ReturnClass rt = systemService.getAllFields();
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

    /**
     CREATED BY CHEN ANRAN
     */
    //显示每个领域下的所有学校
    @RequestMapping("/schoolDisplay")
    public void SchoolDisplay(HttpServletResponse response )throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        ReturnClass rt = systemService.getAllSchool();
        List<String> schoolList = (List<String>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<schoolList.size();i++)
        {
            jsonObject.put("school",schoolList.get(i));
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //获取资源信息
    @RequestMapping("/getResInformation")
    public void GetResInformation(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String resId = request.getParameter("resId");
        String openid = request.getParameter("openid");
        ReturnClass rt = systemService.getResInformation(resId);
        List<Resources> resourceList = (List<Resources>)rt.getData();
        Map <String,Boolean> map = systemService.IsCollected(openid);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<resourceList.size();i++)
        {
            jsonObject.put("Cname",resourceList.get(i).getCname());
            jsonObject.put("resField",resourceList.get(i).getResField());
            jsonObject.put("resInfo",resourceList.get(i).getResInfo());
            jsonObject.put("resId",resourceList.get(i).getResId());
            jsonObject.put("Upid",resourceList.get(i).getUpid());
            jsonObject.put("school",resourceList.get(i).getSchool());
            jsonObject.put("imgUrl",resourceList.get(i).getImgUrl());
            jsonObject.put("iscollect",map.get(resId));
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    /****
     *
     * @param request  file_id
     * @param response  []file_id, []type,[]file_url,[]chapter
     * @throws ServletException
     * @throws IOException
     */

    //根据章节id获取info
    @RequestMapping("/chapterInfo")
    public void GetChapter(HttpServletRequest request ,HttpServletResponse response) throws ServletException,IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();
        String id = request.getParameter("file_id") ;/**前端输入*/

        ReturnClass rt = systemService.getChapterList(id);
        List<ResFile> resFiles = (List<ResFile>) rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(rt.getCode() ==1)
        {
            for(int i=0; i<resFiles.size();i++)
            {
                jsonObject.put("file_id",resFiles.get(i).getFile_id());
                jsonObject.put("type",resFiles.get(i).getType());
                jsonObject.put("file_url",resFiles.get(i).getUrl());
                jsonObject.put("chapter",resFiles.get(i).getChapter());

                jsonArray.add(jsonObject);
            }
        }
        out.println(jsonArray);


    }

    //根据资源id获取info

    /***
     *
     * @param request  resId资源id
     * @param response  name,field,info,detail[chapter,field]
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/resInfo")
    public void GetResourceDetails(HttpServletRequest request ,HttpServletResponse response) throws ServletException,IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();
        String id = request.getParameter("resId") ;/**前端输入*/


        ReturnClass rt = systemService.getResourceInfo(id);//章节
        ReturnClass re = systemService.getResChapter(id);//章节名，章节id

        Resources resources = (Resources) rt.getData();
        List<ResFile> resFiles = (List<ResFile>) re.getData();

        JSONObject jsonObject = new JSONObject();

        if(resources !=null)
        {
            jsonObject.put("name",resources.getCname());
            jsonObject.put("field",resources.getResField());
            jsonObject.put("info",resources.getResInfo());

            if(!resFiles.isEmpty())
            {
                JSONArray jsonArray_2 = new JSONArray();
                for(int i=0;i<resFiles.size();i++)
                {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("chapter",resFiles.get(i).getChapter());
                    jsonObject1.put("file_id",resFiles.get(i).getFile_id());
                    jsonArray_2.add(jsonObject1);
                }
                jsonObject.put("details",jsonArray_2);
            }

        }
        out.println(jsonObject);
    }


    /**
     CREATED BY CHEN ANRAN
     */
    //记录资源浏览历史
    @RequestMapping("/recordHistory")
    public void RecordHistory(HttpServletRequest request ,HttpServletResponse response) throws ServletException,IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        String resId = request.getParameter("resId");
        String inTime = request.getParameter("inTime");
        String date = request.getParameter("date");
        String outTime = request.getParameter("outTime");

        systemService.recordHistory(openid,resId,date,inTime,outTime);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("openid",openid);
        jsonObject.put("resId",resId);
        jsonObject.put("inTime",inTime);
        jsonObject.put("date",date);
        jsonObject.put("outTime",outTime);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示浏览历史
    @RequestMapping("/displayHistory")
    public void DisplayHistory(HttpServletRequest request ,HttpServletResponse response)throws ServletException,IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        ReturnClass rt = systemService.displayHistory(openid);
        List<Resources> resourcesList = (List<Resources>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(resourcesList!=null)
        {
            for(int i=0;i<resourcesList.size();i++)
            {
                jsonObject.put("Cname",resourcesList.get(i).getCname());
                jsonObject.put("resField",resourcesList.get(i).getResField());
                jsonObject.put("imgUrl",resourcesList.get(i).getImgUrl());
                jsonArray.add(jsonObject);
            }
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示校友圈
    @RequestMapping("/displayMoment")
    public void DisplayMoment(HttpServletResponse response)throws ServletException,IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        ReturnClass rt = systemService.displayMoment();
        List<Moment> momentList = (List<Moment>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<momentList.size();i++)
        {
            jsonObject.put("nickName",momentList.get(i).getNickName());
            jsonObject.put("avatarUrl",momentList.get(i).getAvatarUrl());
            jsonObject.put("content",momentList.get(i).getContent());
            jsonObject.put("url",momentList.get(i).getUrl());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示资源平均分
    @RequestMapping("/calculateScore")
    public void CalculateScore(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String resId = request.getParameter("resId");
        ReturnClass rt = systemService.calculateScore(resId);
        String score = (String)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("score",score);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示资源对应的文件
    @RequestMapping("/displayResFile")
    public void DisplayResFile(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String resId = request.getParameter("resId");
        ReturnClass rt = systemService.displayResFile(resId);
        List<ResFile> resFileList = (List<ResFile>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(resFileList!=null)
        {
            for(int i=0;i<resFileList.size();i++)
            {
                jsonObject.put("url",resFileList.get(i).getUrl());
                jsonArray.add(jsonObject);
            }
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示用户消息
    @RequestMapping("/displayUserMessage")
    public void DisplayUserMessage(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        ReturnClass rt = systemService.displayMessage(openid);
        List<Message> messageList = (List<Message>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(messageList!=null)
        {
            for(int i=0;i<messageList.size();i++)
            {
                jsonObject.put("message",messageList.get(i).getMessage());
                jsonObject.put("mid",messageList.get(i).getMid());
                jsonArray.add(jsonObject);
            }
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示用户排行榜
    @RequestMapping("/displayRank")
    public void DisplayRank(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        ReturnClass rt = systemService.displayRank();
        List<User> userList = (List<User>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(userList!=null)
        {
            for(int i=0;i<userList.size();i++)
            {
                jsonObject.put("nickName",userList.get(i).getNickName());
                jsonObject.put("avatarUrl",userList.get(i).getavatarUrl());
                jsonObject.put("score",userList.get(i).getScore());
                jsonArray.add(jsonObject);
            }
        }
        out.println(jsonArray);
    }
}
