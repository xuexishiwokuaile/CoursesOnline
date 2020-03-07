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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    /**
    CREATED BY CHEN ANRAN
    2019.7.4
    */

    //获取用户登录信息并存储
    @RequestMapping("/Login")
    public void Login(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String nickName = request.getParameter("nickName");
        String openid = request.getParameter("openid");
        String avatarUrl = request.getParameter("avatarUrl");

        userService.login(openid,nickName,avatarUrl);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickName", nickName);
        jsonObject.put("openid",openid);
        jsonObject.put("avatarUrl",avatarUrl);
        out.println(jsonObject);
    }

    @RequestMapping("/comment")  //存储前端的评论数据
    public void Comment(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        //获取前端返回的信息
        String content = request.getParameter("content");
        String openid = request.getParameter("openid");
        String resourceId = request.getParameter("resourceId");
        //根据评论内容情绪分析
        String auditResult = userService.auditComment( content );
        System.out.println("--------------");
        System.out.println(auditResult);
        if(auditResult.equals("0") ) {
            String motion = userService.judgeCommentEmotion( content );
            userService.makeComment( resourceId, openid, content, motion );
        }else if(auditResult.equals("2")){
            String motion = userService.judgeCommentEmotion( content );
            userService.makeAuditComment( resourceId, openid, content, motion );
        }else if(auditResult.equals("1")) {
        }

        //接收到的数据插入到json串中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content",content);
        jsonObject.put("openid",openid);
        jsonObject.put("resourceId",resourceId);
        out.println(jsonObject);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示资源收藏列表
    @RequestMapping("/favourite")
    public void Favourite(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        ReturnClass rt = userService.getFavResList(openid);
        List<Resources> favResList = (List<Resources>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<favResList.size();i++)
        {
            jsonObject.put("resId",favResList.get(i).getResId());
            jsonObject.put("Cname",favResList.get(i).getCname());
            jsonObject.put("resInfo",favResList.get(i).getResInfo());
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示蹭课列表
    @RequestMapping("/couDisplay")
    public void CouDisplay(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String University = request.getParameter("University");
        String openid = request.getParameter("openid");
        System.out.println("-----------------");
        System.out.println(openid);
        ReturnClass rt = userService.getCourseBySchool(University);
        List<Course> courseList = (List<Course>)rt.getData();
        Map<String,Boolean> map = userService.IsCollect(openid);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<courseList.size();i++)
        {
            jsonObject.put("Cname",courseList.get(i).getCname());
            jsonObject.put("Cteacher",courseList.get(i).getCteacher());
            jsonObject.put("location",courseList.get(i).getLocation());
            jsonObject.put("University",University);
            jsonObject.put("couField",courseList.get(i).getField());
            jsonObject.put("courseTime",courseList.get(i).getCourseTime());
            String cid = courseList.get(i).getCid();
            jsonObject.put("Cid",cid);
            jsonObject.put("iscollect",map.get(cid));
            jsonArray.add(jsonObject);
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //点击课程收藏按钮
    @RequestMapping("/clickCourseFavourite")
    public void ClickCourseFavourite(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        String Cid = request.getParameter("Cid");

        userService.cilckCourseFavourite(openid,Cid);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid",openid);
        jsonObject.put("Cid",Cid);
        out.println(jsonObject);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //点击资源收藏按钮
    @RequestMapping("/clickResourceFavourite")
    public void ClickResourceFavourite(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        String resId = request.getParameter("resId");

        userService.cilckResourceFavourite(openid,resId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid",openid);
        jsonObject.put("resId",resId);
        out.println(jsonObject);
    }

    //CREATED BY LI JIAJUN
    //课程搜索
    @RequestMapping("/searchCourse")
    public void SearchCourse(HttpServletResponse response,HttpServletRequest request) throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();
        String keyword = request.getParameter("keyword") ;
        String openid = request.getParameter("openid");
        Map<String,Boolean> map = userService.IsCollect(openid);
        //new
        keyword.replace(" ","");
        String str=keyword.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        if(str.isEmpty())
        {
            out.println("0");
        }
        else{
            /*
            CREATE BY LH
            2017.7.6
             */
            //当re没有搜索到相关课程时，即re为null时，进行自动文本纠错，显示“您是否要找：xxxxx（改正的课程名）”
            String correctStr = userService.searchErrorCorrection( str );
            JSONObject jsonObject1 = new JSONObject();
            JSONArray jsonArray1 = new JSONArray();
            if(correctStr != null ) {
                //jsonObject存储改正的查询字符串
                jsonObject1.put( "Text", "您是不是在找：" );
                jsonObject1.put( "CorrectString", correctStr );
                jsonArray1.add(jsonObject1);
            }else {
                //do nothing
            }
            //查询的课程信息
            ReturnClass re = userService.SearchCourse(str);
            List<Course> courseList = (List<Course>) re.getData();
            //打印小程序传过来的数据
            System.out.println(keyword) ;
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for(int i =0;i<courseList.size();i++)
            {
                jsonObject.put("Cname",courseList.get(i).getCname());
                jsonObject.put("Cteacher",courseList.get(i).getCteacher());
                jsonObject.put("location",courseList.get(i).getLocation());
                jsonObject.put("University",courseList.get(i).getUniversity());
                jsonObject.put("couField",courseList.get(i).getField());
                jsonObject.put("courseTime",courseList.get(i).getCourseTime());
                String cid = courseList.get(i).getCid();
                jsonObject.put("Cid",cid);
                jsonObject.put("iscollect",map.get(cid));
                jsonArray.add(jsonObject);
            }

            JSONArray jsonArrayNew = new JSONArray();
            jsonArrayNew.add( jsonArray1);
            jsonArrayNew.add( jsonArray );
            out.println(jsonArrayNew);
        }

    }

    /**
     CREATED BY CHEN ANRAN
     */
    //取消课程收藏
    @RequestMapping("/cancelCourseFavourite")
    public void CancelCourseFavourite(HttpServletResponse response,HttpServletRequest request)throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        String Cid = request.getParameter("Cid");

        userService.cancelCourseFavourite(openid,Cid);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid",openid);
        jsonObject.put("Cid",Cid);
        out.println(jsonObject);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //取消资源收藏
    @RequestMapping("/cancelResourceFavourite")
    public void CancelResourceFavourite(HttpServletResponse response,HttpServletRequest request)throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        String resId = request.getParameter("resId");

        userService.cancelResourceFavourite(openid,resId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid",openid);
        jsonObject.put("resId",resId);
        out.println(jsonObject);
    }

    //CREATED BY LI JIAJUN
    //根据关键字查询资源
    @RequestMapping("/searchResource")
    public void SearchResource(HttpServletResponse response,HttpServletRequest request) throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();
        String keyword = request.getParameter("keyword") ;
        //new
        keyword.replace(" ","");
        String str=keyword.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");

        if(str.isEmpty())
        {
            out.println(0);
        }
        else{
            ReturnClass re = userService.SearchResource(str);
            List<Resources> resourceList = (List<Resources>) re.getData();
            //打印小程序传过来的数据
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for(int i =0;i<resourceList.size();i++)
            {
                jsonObject.put("Cname",resourceList.get(i).getCname());
                jsonObject.put("resField",resourceList.get(i).getResField());
                jsonObject.put("resInfo",resourceList.get(i).getResInfo());
                jsonObject.put("resId",resourceList.get(i).getResId());
                jsonArray.add(jsonObject);

            }
            out.println(jsonArray);

        }
    }

    //根据upid获取上传资源列表
    /**
     *
     * @param response  upid--上传资源用户的id
     * @param request    cname资源名,resid资源id,resfield资源领域
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/uploadList")
    public void getUploadResources(HttpServletResponse response,HttpServletRequest request) throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();
        String id = request.getParameter("upid") ;

        ReturnClass rt = userService.getUploadResourceList(id);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(rt.getCode()==0)
        {
            jsonArray.add(0);
        }
        else {

            List<Resources> list = (List<Resources>) rt.getData();
            for(int i=0;i<list.size();i++)
            {
                jsonObject.put("cname",list.get(i).getCname());
                jsonObject.put("resid",list.get(i).getResId());
                jsonObject.put("resfield",list.get(i).getResField());
                jsonArray.add(jsonObject);
            }
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //获取文件信息
    @RequestMapping("/fileInfo")
    public void GetFileInfo(HttpServletResponse response,HttpServletRequest request) throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String fileName = OssController.fileName;
        String ftype = OssController.ftype;
        String url = OssController.url;
        String chapter = request.getParameter("chapter");
        String openid = request.getParameter("openid");
        String resId = request.getParameter("resId");
        userService.getFileInfo(fileName,ftype,url,chapter,openid,resId);
        OssController.url=null;
        OssController.fileName=null;
        OssController.ftype=null;

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("fileName",fileName);
        jsonObject.put("ftype",ftype);
        jsonObject.put("url",url);
        jsonObject.put("chapter",chapter);
        jsonObject.put("openid",openid);
        jsonObject.put("resId",resId);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //获取校友圈信息
    @RequestMapping("/getMomentInfo")
    public void GetMomentInfo(HttpServletResponse response,HttpServletRequest request) throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        String content = request.getParameter("content");
        String url = OssController.url;
        System.out.println("----------------");
        System.out.println(url);
        userService.monentInfo(openid,content,url);
        OssController.url=null;
        OssController.ftype=null;
        OssController.fileName=null;

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("openid",openid);
        jsonObject.put("content",content);
        jsonObject.put("url",url);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //对资源评分
    @RequestMapping("/resourceScore")
    public void ResourceScore(HttpServletResponse response,HttpServletRequest request)throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        String resId = request.getParameter("resId");
        String score = request.getParameter("score");
        userService.resourceScore(openid,resId,score);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("openid",openid);
        jsonObject.put("resId",resId);
        jsonObject.put("score",score);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //签到
    @RequestMapping("/checkIn")
    public void CheckIn(HttpServletResponse response,HttpServletRequest request)throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        String date = request.getParameter("date");
        ReturnClass rt = userService.signUp(openid,date);
        String check = (String)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("openid",openid);
        jsonObject.put("check",check);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //个人推荐
    @RequestMapping("/getPersonalRecommend")
    public void getPersonalRecommend(HttpServletResponse response,HttpServletRequest request) throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter( "openid" );
        ReturnClass rt = userService.getRecommendByOpenid( openid );
        List<Resources> resourcesList = (List<Resources>)rt.getData();

        JSONObject jsonObject = new JSONObject(  );
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < resourcesList.size(); i++){
            jsonObject.put("Cname",resourcesList.get(i).getCname());
            jsonObject.put("resField",resourcesList.get(i).getResField());
            jsonObject.put("resInfo",resourcesList.get(i).getResInfo());
            jsonObject.put("resId",resourcesList.get(i).getResId());
            jsonObject.put("imgUrl",resourcesList.get(i).getImgUrl());
            jsonArray.add(jsonObject);
        }
        out.println( jsonArray );
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示用户上传的文件
    @RequestMapping("/displayOwnFile")
    public void DisplayOwnFile(HttpServletResponse response,HttpServletRequest request)throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter("openid");
        ReturnClass rt = userService.displayOwnFile(openid);
        List<ResFile> resFileList = (List<ResFile>)rt.getData();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if(resFileList!=null)
        {
            for(int i=0;i<resFileList.size();i++)
            {
                jsonObject.put("url",resFileList.get(i).getUrl());
                jsonObject.put("file_id",resFileList.get(i).getFile_id());
                jsonArray.add(jsonObject);
            }
        }
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //删除用户上传的文件
    @RequestMapping("/deleteOwnFile")
    public void DeleteOwnFile(HttpServletResponse response,HttpServletRequest request)throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String file_id = request.getParameter("file_id");
        userService.deleteOwnFile(file_id);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("file_id",file_id);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //删除消息
    @RequestMapping("/deleteMessage")
    public void DeleteMessage(HttpServletResponse response,HttpServletRequest request)throws IOException,ServletException
    {
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String mid = request.getParameter("mid");
        System.out.println("------");
        System.out.println(mid);
        //userService.deleteMessage(mid);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("mid",mid);
        jsonArray.add(jsonObject);
        out.println(jsonArray);
    }


    @RequestMapping("/getEchart")
    public void getEchart(HttpServletResponse response,HttpServletRequest request) throws IOException,ServletException{
        response.setContentType("text/json;charset=utf-8");//设置文件格式为json
        PrintWriter out = response.getWriter();

        String openid = request.getParameter( "openid" );
        ReturnClass rt = userService.getEchartInfo( openid );
        List<List> resultList = new ArrayList<>();
        resultList = (List<List>)rt.getData();
        JSONArray jsonArrayResult = new JSONArray();
        for(int i = 0; i < resultList.size(); i++){
            List<String[]> dayInfo = resultList.get( i );
            JSONArray jsonArray = new JSONArray();

            for(int j = 0; j < dayInfo.size(); j++){
                String[] field_time_date = dayInfo.get( j );
                JSONObject jsonObject = new JSONObject(  );
                String field = field_time_date[0];
                String time = field_time_date[1];
                String date = field_time_date[2];
                jsonObject.put( "field", field );
                jsonObject.put( "time", time );
                jsonObject.put( "date", date );
                jsonArray.add( jsonObject );
            }
            jsonArrayResult.add( jsonArray );
        }
        out.println( jsonArrayResult );
    }

}

