package com.xjt.service.impl;

import com.aliyun.oss.OSSClient;
import com.baidu.aip.nlp.AipNlp;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.xjt.controller.OssController;
import com.xjt.dao.IUserDao;
import com.xjt.model.*;
import com.xjt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;


@Service("userService")
public class UserServiceImpl implements IUserService
{

    /**
    CREATED BY CHEN ANRAN
    2019.7.4
    */

    @Resource
    private IUserDao userDao;

    //获取用户登录数据
    public ReturnClass login(String openid,String nickName,String avatarUrl)
    {
        ReturnClass rt = new ReturnClass();
        //要成功获取到属性，类中的字段必须和表中的属性相同
        User user = userDao.selectUser_id(openid);

        if (user!=null) {
            rt.setCode( 1 );
            rt.setMsg( "用户存在，登录成功" );
        }
        else
        {
        //不存在数据
        userDao.insertUserInfo(openid,nickName,avatarUrl);
        rt.setCode( -1 );
        rt.setMsg( "用户不存在,自动添加为用户" );
        }
        return rt;
    }

    //插入一条新评论
    public ReturnClass makeComment(String resourceId,String openid,String content,String motion)
    {
        ReturnClass rt = new ReturnClass();
        String commentId = RamdomString.generateString();
        userDao.insertComment(commentId,openid,resourceId,content,motion);
        rt.setCode(1);
        rt.setMsg("评论上传成功");
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //获取资源收藏列表
    public ReturnClass getFavResList(String openid)
    {
        ReturnClass rt = new ReturnClass();
        List<Resources> resultList = new ArrayList<Resources>();
        List<Resources> list = new ArrayList<Resources>();
        resultList = userDao.selectFavRes(openid);

        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String resId = resultList.get(i).getResId();
                String Cname = resultList.get(i).getCname();
                String resInfo = resultList.get(i).getResInfo();

                Resources resources = new Resources(Cname,resId,resInfo);
                list.add(resources);
            }
            rt.setCode(1);
            rt.setMsg("收藏资源列表获取成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode(0);
            rt.setMsg("收藏资源列表获取失败");
            rt.setData(null);
        }
        return rt;
    }

    public String judgeCommentEmotion(String content){
        // 初始化一个AipNlp
        AipNlp client = new AipNlp("16712874", "zSXNcK0oUsLTz8n2IBvy3P1Y", "nCznlubp80FahCnK4UGjZk9wDu5ty3U7");
        //接收aip返回的结果并处理得到需要的参数result
        org.json.JSONObject res = client.sentimentClassify(content, null);
        org.json.JSONArray jsonArray = res.getJSONArray( "items" );
        org.json.JSONObject resultObject = (org.json.JSONObject) jsonArray.get( 0 );
        //0代表消极，1代表中性，2代表积极
        String motion = resultObject.get( "sentiment" ).toString();
        return motion;
    }

    /*
UPDATE BY LH 2019.7.8
 */
//获取学校提供的蹭课课程
public ReturnClass getCourseBySchool(String University)
{
    ReturnClass re = new ReturnClass();
    List<Course> resultList = new ArrayList<Course>();
    List<Course> list = new ArrayList<Course>();
    resultList = userDao.selectCourseBySchool(University);
    if(resultList!=null)
    {
        for(int i=0;i<resultList.size();i++)
        {
            String Cname = resultList.get(i).getCname();
            String Cteacher = resultList.get(i).getCteacher();
            String location = resultList.get(i).getLocation();
            String couField = resultList.get(i).getField();
            String courseTime = resultList.get(i).getCourseTime();
            String Cid = resultList.get(i).getCid();

            Course course = new Course(Cname,Cteacher,location,couField,Cid,courseTime,University);
            list.add(course);
        }
        re.setData(list);
        re.setCode(1);
        re.setMsg("加载成功");
    }
    else
    {
        re.setData(null);
        re.setCode(0);
        re.setMsg("加载失败");
    }
    return re;
}


    /**
     CREATED BY CHEN ANRAN
     */
    //点击课程收藏按钮
    public ReturnClass cilckCourseFavourite(String openid,String Cid)
    {
        ReturnClass rt = new ReturnClass();
        userDao.insertCourseFavourite(openid,Cid);
        rt.setCode(1);
        rt.setMsg("课程收藏成功");
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //点击资源收藏按钮
    public ReturnClass cilckResourceFavourite(String openid,String resId)
    {
        ReturnClass rt = new ReturnClass();
        userDao.insertResourceFavourite(openid,resId);
        rt.setCode(1);
        rt.setMsg("资源收藏成功");
        return rt;
    }

    //    CREATE BY LIJIA JUN
    //根据课程查询资源
    public  ReturnClass SearchCourse(String keyword)
    {
        ReturnClass re = new ReturnClass();
        List<Course> resultList = new ArrayList<Course>();
        List<Course> list = new ArrayList<Course>();
        resultList = userDao.SearchCourseByKeyword(keyword,keyword,keyword,keyword);
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String Cname = resultList.get(i).getCname();
                String Cteacher = resultList.get(i).getCteacher();
                String location = resultList.get(i).getLocation();
                String couField = resultList.get(i).getField();
                String courseTime = resultList.get(i).getCourseTime();
                String Cid = resultList.get(i).getCid();
                String University =resultList.get(i).getUniversity();
                Course course = new Course(Cname,Cteacher,location,couField,Cid,courseTime,University);
                list.add(course);
            }
            re.setData(list);
            re.setCode(1);
            re.setMsg("加载成功");
        }
        else
        {
            re.setData(null);
            re.setCode(0);
            re.setMsg("加载失败");
        }
        return re;

    }

    //点击课程取消收藏按钮
    public ReturnClass cancelCourseFavourite(String openid,String Cid)
    {
        ReturnClass rt = new ReturnClass();
        userDao.cancelCourseFavourite(openid,Cid);
        rt.setCode(1);
        rt.setMsg("取消课程收藏成功");
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //点击资源取消收藏按钮
    public ReturnClass cancelResourceFavourite(String openid,String resId)
    {
        ReturnClass rt = new ReturnClass();
        userDao.cancelResourceFavourite(openid,resId);
        rt.setCode(1);
        rt.setMsg("取消资源收藏成功");
        return rt;
    }

    /**
     *CREATE BY LI JIAJUN
     **/
    //根据关键字查询资源
    public ReturnClass SearchResource(String keyword) {
        ReturnClass re = new ReturnClass();
        List<Resources> resultList = new ArrayList<Resources>();
        List<Resources> list = new ArrayList<Resources>();
        resultList = userDao.SearchResourceByKeyword(keyword,keyword,keyword);
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String Cname = resultList.get(i).getCname();
                String resid = resultList.get(i).getResId();
                String resInfo = resultList.get(i).getResInfo();
                String upid = resultList.get(i).getUpid();
                String resField = resultList.get(i).getResField();
                String school = resultList.get(i).getSchool();
                Resources  res = new Resources(Cname,resid,resInfo,resField,upid,school);
                list.add(res);
            }
            re.setData(list);
            re.setCode(1);
            re.setMsg("加载成功");
        }
        else
        {
            re.setData(null);
            re.setCode(0);
            re.setMsg("加载失败");
        }
        return re;
    }

    /**
     CREATE BY LH
     2019.7.5
     */
    public ReturnClass makeAuditComment(String resourceId,String openid,String content, String motion)
    {
        ReturnClass rt = new ReturnClass();
        String commentId = RamdomString.generateString();
        userDao.insertAuditComment(commentId,openid,resourceId,content,motion);

        rt.setCode(1);
        rt.setMsg("评论上传成功");
        return rt;
    }

    //自动审核评论
    public String auditComment(String content){
        HttpURLConnection connection = null;
        try{
            //获取assess_token
            String access_token = AuthService.getAuth("MCG9yylKs3hZFXhPWiBzZmRc", "qFnrhvO6GCSAnCYSWqYK88vdkfPt2Pfc");
            String httpUrl = "https://aip.baidubce.com/rest/2.0/antispam/v2/spam?access_token="+access_token;
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection)url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);
            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            //设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
            OutputStreamWriter out = new OutputStreamWriter( connection.getOutputStream(),"UTF-8");// utf-8编码
            out.append("content=" + content);
            out.flush();
            out.close();
            connection.connect();

            //将接收到的数据处理得到object
            JsonObject object = null;
            StringBuffer buffer = new StringBuffer(  );
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader( is, "utf-8" );
            BufferedReader br = new BufferedReader( isr );
            String str = null;
            //读取返回数据
            while ((str = br.readLine())!= null){
                buffer.append( str );
            }
            String res = buffer.toString();
            com.google.gson.JsonParser parse = new com.google.gson.JsonParser();
            is.close();
            isr.close();
            br.close();
            object = (JsonObject)parse.parse( res );
            //object是得到的完整JsonObject， 从中得到需要的参数auditResult，0代表非违禁，1代表违禁，2代表建议人工复查
            JsonPrimitive object3 = object.getAsJsonObject( "result" ).getAsJsonPrimitive( "spam" );
            String auditResult = object3.toString();
            return auditResult;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据upid获取上传列表
    public ReturnClass getUploadResourceList(String open_id)
    {
        ReturnClass re = new ReturnClass();
        List<Resources> resultList = new ArrayList<Resources>(userDao.getUploadResourceList(open_id));
        List<Resources> list = new ArrayList<Resources>();
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                Resources  res = new Resources();
                res.setCname(resultList.get(i).getCname());
                res.setResId(resultList.get(i).getResId());
                res.setResField(resultList.get(i).getResField());
                list.add(res);
            }
            re.setData(list);
            re.setCode(1);
            re.setMsg("加载成功");
        }
        else
        {
            re.setData(null);
            re.setCode(0);
            re.setMsg("无上传信息");
        }
        return re;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //获取文件信息
    public ReturnClass getFileInfo(String file_id,String ftype,String url,String chapter,String openid,String resId)
    {
        ReturnClass rt = new ReturnClass();
        userDao.insertFile(file_id,ftype,url,chapter,openid);
        userDao.insertResourceFile(resId,file_id);
        System.out.println("------------");

        rt.setCode(1);
        rt.setMsg("文件信息存储成功");
        return rt;
    }


    /*
CREATE BY LH
2017.7.6
 */
    public String searchErrorCorrection(String str){
        AipNlp client = new AipNlp("16712874", "zSXNcK0oUsLTz8n2IBvy3P1Y", "nCznlubp80FahCnK4UGjZk9wDu5ty3U7");
        String content = str;
        org.json.JSONObject res = client.ecnet(content, null);
        System.out.println(res.toString(2));
        //score为0时，说明不许进行改正；score大于0时，返回改正后的str
        double score = res.getJSONObject( "item" ).getDouble( "score" );
        if(score > 0){
            String corret_query = (String) res.getJSONObject( "item" ).get( "correct_query" );
            return corret_query;
        }else{
            return null;
        }
    }

    /**
     CREATED BY CHEN ANRAN
     */
    public ReturnClass monentInfo(String openid,String content,String url)
    {
        ReturnClass rt = new ReturnClass();

        String Mid = RamdomString.generateString();
        userDao.insertMomentInfo(Mid,openid,content,url);

        rt.setCode(1);
        rt.setMsg("获取信息成功");
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //用户对资源评分
    public ReturnClass resourceScore(String openid,String resId,String score)
    {
        ReturnClass rt = new ReturnClass();
        userDao.insertResourceScore(openid,resId,score);

        rt.setMsg("评分成功");
        rt.setCode(1);
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //用户签到
    public ReturnClass signUp(String openid, String date)
    {
        ReturnClass rt = new ReturnClass();

        //userDao.insertCheck(openid,date);
        //验证是否连续签到
        //遍历该用户的签到数据
        List<Check> resultList = userDao.selectCheck(openid);
        if(resultList.size()==0)  //这是第一次签到
        {
            //更新user表中的check信息
            userDao.updateCheck(openid);
            //在签到表中插入这条签到信息
            userDao.insertCheck(openid,date);
        }
        //如果之前存在签到数据
        //找出其中值最大的值（代表最近的签到）
        else
        {
            int maxDate = 0;
            for(int i=0;i<resultList.size();i++)
            {
                if(Integer.parseInt(resultList.get(i).getDate())>maxDate)
                {
                    maxDate = Integer.parseInt(resultList.get(i).getDate());
                }
            }
            if(Integer.parseInt(date)-1==maxDate)  //在一个月内连续签到
            {
                //更新签到数据
                userDao.updateCheck(openid);
                //加入这一天
                userDao.insertCheck(openid,date);
            }
            //跨月签到 (这一天是月初)
            else if (date.charAt(2)=='0'&&date.charAt(3)=='1')
            {
                if(date.equals("0201"))
                {
                    if(maxDate==131)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("0301"))
                {
                    if(maxDate==228)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("0401"))
                {
                    if(maxDate==331)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("0501"))
                {
                    if(maxDate==430)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("0601"))
                {
                    if(maxDate==531)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("0701"))
                {
                    if(maxDate==630)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("0801"))
                {
                    if(maxDate==731)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("0901"))
                {
                    if(maxDate==831)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("1001"))
                {
                    if(maxDate==930)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("1101"))
                {
                    if(maxDate==1031)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
                else if(date.equals("1201"))
                {
                    if(maxDate==1130)
                    {
                        userDao.updateCheck(openid);
                        userDao.insertCheck(openid,date);
                    }
                    else
                    {
                        //没有连续签到，清除签到表中的签到数据
                        userDao.deleteDay(openid);
                        //清除用户表中的连续签到天数
                        userDao.deleteCheck(openid);
                        //插入这一天的签到数据
                        userDao.insertCheck(openid,date);
                        userDao.updateCheck(openid);
                    }
                }
            }
            else if(maxDate==Integer.parseInt(date))
            {

            }
            else if(Integer.parseInt(date)-1!=maxDate&&maxDate!=Integer.parseInt(date)&&!(date.charAt(2)=='0'&&date.charAt(3)=='1')) //没有连续签到
            {
                //没有连续签到，清除签到表中的签到数据
                userDao.deleteDay(openid);
                //清除用户表中的连续签到天数
                userDao.deleteCheck(openid);
                //插入这一天的签到数据
                userDao.insertCheck(openid,date);
                userDao.updateCheck(openid);
            }
        }
        //获取连续签到天数
        String check = userDao.selectCheckDay(openid);
        rt.setCode(1);
        rt.setMsg("签到成功");
        rt.setData(check);
        return rt;
    }
    /*
CREATE BY LH 2019.7.9
*/
//根据用户id获得个性化推荐
    public ReturnClass getRecommendByOpenid(String openid){
        ReturnClass rt = new ReturnClass();
        List<Resources> Recommendlist = new ArrayList<Resources>();
        String[] recommendResult = new String[20];
        //List<Resources> resultList = new ArrayList<Resources>();
        //List<Resources> list = new ArrayList<Resources>();
        //选资源的用户个数N
        int N = userDao.getUserNumFromUserRes();

        int[][] sparseMatrix = new int[N][N];
        //建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<String, Integer> userItemLength = new HashMap<>();
        //存储每一个用户对应的不同物品总数 eg: A 3
        Map<String, Set<String>> itemUserCollection = new HashMap<>();
        //建立物品到用户的倒排表 eg: a A B
        Set<String> items = new HashSet<>();
        //辅助存储物品集合
        Map<String, Integer> userID = new HashMap<>();
        //辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();

        // /存储用户id
        String[] users = new String[N];
        List<String> userResultList = new ArrayList<>(  );
        userResultList = userDao.getUserIdFromUserRes();
        boolean ifopenid = false;
        if(userResultList!=null){
            for(int i = 0; i < userResultList.size(); i++){
                String userIdnum = userResultList.get( i ).toString();
                users[i] = userIdnum;
                if(users[i].equals( openid )) {
                    ifopenid = true;
                    //System.out.println( "ifopenid设为true" );
                }
            }
        }
        if(!ifopenid) {
            Resources resources = new Resources();
            resources.setCname( null );
            resources.setResField( null );
            resources.setResId( null );
            resources.setResInfo( null );
            resources.setUpid( null );
            Recommendlist.add( resources );
            rt.setCode( 1 );
            rt.setMsg( "当前用户不存在，推荐资源为空" );
            rt.setData( Recommendlist );
            return rt;
        }
        // /存储各用户课程数
        int[] itemlengths = new int[N];
        for(int i = 0; i < N; i++){
            itemlengths[i] = userDao.getResNumByOpenid(users[i]);
        }

        for(int i = 0; i < N; i++) {
            System.out.println( users[i] + itemlengths[i] );
            //用户课程数表
            userItemLength.put(users[i], itemlengths[i]);
            //用户id表
            userID.put(users[i], i);
            //id用户表
            idUser.put(i,users[i]);
        }
        //建立资源用户倒排表
        for(int i = 0; i < N; i++){
            List<String>result = new ArrayList<>(  );
            result = userDao.getResByOpenid( users[i] );
            if(result!=null){
                for(int j = 0; j < result.size();j++){
                    String s = result.get( j );
                    if(items.contains(s)){
                        //如果已经包含对应的物品--用户映射，直接添加对应的用户
                        itemUserCollection.get(s).add(users[i]);
                        //System.out.println( s + " " + users[i] );
                    } else{
                        //否则创建对应物品--用户集合映射
                        items.add(s);
                        itemUserCollection.put(s, new HashSet<String>());
                        //创建物品--用户倒排关系
                        itemUserCollection.get(s).add(users[i]);
                        System.out.println( s + " " + users[i] );
                    }
                }
            }
        }
        System.out.println(itemUserCollection.toString());

        Set<Map.Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {
                for (String user_v : commonUsers) {
                    if(user_u.equals(user_v)){
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;
                    //计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        int i = 0;
        for (String item: items){
            //遍历每一件物品
            Set<String> userss = itemUserCollection.get(item);
            //得到购买当前物品的所有用户集合
            if(!userss.contains(openid)){
                //如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for (String user: userss){
                    itemRecommendDegree += sparseMatrix[userID.get(openid)][userID.get(user)]/Math.sqrt(userItemLength.get(openid)*userItemLength.get(user));
                    //推荐度计算
                }
                System.out.println("The item "+item+" for "+openid +"'s recommended degree:"+itemRecommendDegree);
                //**************************************在这里每个循环得到一个item，推荐度倒序排列*******************
                recommendResult[i] = item;
                System.out.println( recommendResult[i] );
                i++;
            }
        }

        int k = 0;
        while(recommendResult[k] != null) {
            Resources resources = userDao.getRecommendResult( recommendResult[k] );
            Recommendlist.add( resources );
            k++;
        }
        rt.setCode( 1 );
        rt.setMsg( "获取个性化推荐成功" );
        rt.setData( Recommendlist );
        return  rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示用户自己上传的文件
    public ReturnClass displayOwnFile(String openid)
    {
        ReturnClass rt = new ReturnClass();

        List<ResFile> resultList = userDao.selectOwnFile(openid);
        List<ResFile> list = new ArrayList<ResFile>();

        if(resultList.size()>0)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String url = resultList.get(i).getUrl();
                String file_id = resultList.get(i).getFile_id();
                ResFile resFile = new ResFile(file_id,"","",url);
                list.add(resFile);
            }
            rt.setCode(1);
            rt.setMsg("显示成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode(-1);
            rt.setMsg("显示失败");
            rt.setData(null);
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //删除上传的文件
    public ReturnClass deleteOwnFile(String file_id)
    {
        ReturnClass rt = new ReturnClass();

        userDao.deleteOwnFile(file_id);
        rt.setCode(1);
        rt.setMsg("删除成功");
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //删除消息
    public ReturnClass deleteMessage(String mid)
    {
        ReturnClass rt = new ReturnClass();

        userDao.deleteMessage(mid);
        rt.setMsg("删除成功");
        rt.setCode(1);
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //获取是否被收藏的课程信息
    public Map<String,Boolean> IsCollect(String openid)
    {
        List<String> AllCid =new ArrayList<String>(userDao.AllCourseId());
        List<String> resultList = new ArrayList<String>(userDao.IsCollect(openid));
        Map<String,Boolean> IsCollected = new HashMap<String,Boolean>();
        for(int i=0;i<AllCid.size();i++)
        {
            IsCollected.put(AllCid.get(i),false);
        }
        if(!resultList.isEmpty())
        {
            for(int i =0;i<resultList.size();i++)
            {
                IsCollected.replace(resultList.get(i),true);
            }
        }

        return IsCollected;
    }


    /*
 CREATE BY LH 2019.7.10
  */
    //获得生成ECHART的信息
    public ReturnClass getEchartInfo(String openid){
        ReturnClass rt = new ReturnClass();
        List<List> result = new ArrayList<>();
        //dates得到当前日期以及前九天的日期，以长度为四的字符串形式，如0711
        Calendar cal = Calendar.getInstance();
        List<String> dates = new ArrayList<String>(  );
        for(int i = 0; i < 10;i++) {
            int m = (cal.get( Calendar.MONTH ) + 1);
            int d = cal.get( Calendar.DATE );
            String month, day;
            if (m <= 9) {
                month = "0" + String.valueOf( m );
            } else {
                month = String.valueOf( m );
            }
            if (d <= 9) {
                day = "0" + String.valueOf( d );
            } else {
                day = String.valueOf( d );
            }
            String date = month + day;
            dates.add( date ) ;
            cal.add(Calendar.DATE,   -1);
        }
        //获取全部领域list
        List<String>resFieldList = new ArrayList<>(  );
        resFieldList = userDao.getResFieldList();
        if(dates!=null){
            for(int j = 0; j < dates.size(); j ++){
                //对于某一天，分领域获得学习时间
                String handleDate = dates.get( j );
                System.out.println( "当前handleDate是："+ handleDate );
                //取出某领域字符串
                if(resFieldList!=null) {
                    List<String[]>dayInfo = new ArrayList<>(  );
                    for (int i = 0; i < resFieldList.size(); i++) {
                        String handleField = resFieldList.get( i );
                        System.out.println( "当前handleField是：" + handleField );
                        List<String> outTimes = new ArrayList<>();
                        outTimes = userDao.getOutTime( handleField, openid, handleDate );
                        List<String> inTimes = new ArrayList<>();
                        int outTimeToInt = 0;
                        int inTimeToInt = 0;
                        inTimes = userDao.getInTime( handleField, openid, handleDate );
                        if (outTimes != null) {
                            for (int m = 0; m < outTimes.size(); m++) {
                                System.out.println( outTimes.get( m ) );
                                outTimeToInt += Integer.parseInt( outTimes.get( m ) );
                            }
                        }
                        if (inTimes != null) {
                            for (int m = 0; m < inTimes.size(); m++) {
                                System.out.println( inTimes.get( m ) );
                                inTimeToInt += Integer.parseInt( inTimes.get( m ) );
                            }
                        }
                        //以分钟为单位
                        int timeResult = (outTimeToInt - inTimeToInt)/60;
                        System.out.println( "日期：" + handleDate + " 领域：" + handleField + " 时间：" + timeResult );
                        String [] field_time_date = new  String[3];
                        field_time_date[0] = handleField;
                        field_time_date[1] = String.valueOf( timeResult );
                        field_time_date[2] = handleDate;
                        System.out.println( "当前field_time：" + field_time_date );
                        dayInfo.add( field_time_date );
                    }
                    System.out.println( "当前dayInfo：" + dayInfo );
                    result.add( dayInfo );
                }
                System.out.println( "当前result" + result );
            }
        }
        if(result!=null){
            rt.setCode( 1 );
            rt.setMsg( "获取echartInfo成功" );
            rt.setData(result);
        }else{
            rt.setCode( 0 );
            rt.setMsg( "获取echartInfo失败" );
            rt.setData(null);
        }
        return  rt;
    }

}
