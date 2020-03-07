package com.xjt.dao;

import com.xjt.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserDao {

    /**
    CREATED BY CHEN ANRAN
     2019.7.4
     */

    //User selectUser(long id);

    //添加用户
    public void addUser(User user);

    //根据用户名查询用户
    //自动封装为map集合
    //public User findByUsername(@Param("nickName") String nickName);

    //验证登录信息是否重复
    User selectUser_id(String openid);
    //插入未重复的用户信息
    void insertUserInfo(@Param(value="openid") String openid,@Param(value="nickname") String nickname,
                        @Param(value = "avatarUrl") String avatarUrl);
    //插入评论
    void insertComment(@Param(value="commentId") String commentId,@Param(value="openid") String openid,
                       @Param(value="resourceId") String resourceId,@Param(value="content") String content,
                       @Param(value = "motion")String motion);
    //获取收藏列表
    List<Resources> selectFavRes(String openid);
    //获取学校提供的蹭课列表
    List<Course> selectCourseBySchool(String University);
    //点击课程收藏按钮
    void insertCourseFavourite(@Param(value = "openid") String openid,@Param(value = "Cid") String Cid);
    //点击资源收藏按钮
    void insertResourceFavourite(@Param(value = "openid") String openid,@Param(value = "resId") String resId);
    //    CREATE BY LIJIA JUN
    //根据关键字搜索课程
    List<Course> SearchCourseByKeyword(@Param(value ="Cname")String Cname,@Param(value = "Cteacher") String Cteacher,
                                       @Param(value = "couField") String couField,@Param(value = "location")String location);
    //取消课程收藏
    void cancelCourseFavourite(@Param(value = "openid") String openid,@Param(value = "Cid") String Cid);
    //取消资源收藏
    void cancelResourceFavourite(@Param(value = "openid") String openid,@Param(value = "resId") String resId);
    //    CREATE BY LIJIA JUN
    //关键字搜索资源
    List<Resources>SearchResourceByKeyword(@Param(value ="Cname")String Cname,@Param(value = "resField") String resField,
                                           @Param(value = "resInfo") String resInfo);
    /**
        CREATE BY LH
        2019.7.5
         */
    //插入评论到审核区
    void insertAuditComment(@Param(value="commentId") String commentId,@Param(value="openid") String openid,
                            @Param(value="resourceId") String resourceId,@Param(value="content") String content,
                            @Param(value = "motion") String motion);
    //上传文件
    void insertFile(@Param(value = "file_id") String file_id,@Param(value = "ftype") String ftype,@Param(value = "url")
                    String url,@Param(value = "chapter") String chapter,@Param(value = "openid") String openid);
    void insertResourceFile(@Param(value = "resId")String resId,@Param(value = "file_id")String file_id);
    //根据upid获得上传的资源

    List<Resources> getUploadResourceList(String Upid);
    //插入校友圈信息
    void insertMomentInfo(@Param(value = "Mid") String Mid,@Param(value = "openid") String openid,
                          @Param(value = "content") String content,@Param(value = "url") String url);
    //对资源评分
    void insertResourceScore(@Param(value = "openid") String openid,@Param(value = "resId") String resId,
                             @Param(value = "score") String score);
    //添加签到信息
    void insertCheck(@Param("openid") String openid,@Param("date") String date);
    //获取用户签到信息
    List<Check> selectCheck(String openid);
    //更新用户表中的连续签到天数(check栏)
    void updateCheck(String openid);
    //清除签到表的签到数据
    void deleteDay(String openid);
    //清除用户表中的连续签到天数
    void deleteCheck(String openid);
    //从用户表中获取连续签到天数
    String selectCheckDay(String openid);
    /*
CREATE BY LH 2019.7.9
 */
    //搜索选资源资源表中用户个数
    int getUserNumFromUserRes();
    //搜索选资源表中的所有用户id
    List<String> getUserIdFromUserRes();
    //获取某用户选的资源数
    int getResNumByOpenid(@Param( value = "openid" ) String openid);
    //获取某用户选择的所有资源id
    List<String>getResByOpenid(@Param( value = "openid")String openid);
    Resources getRecommendResult(@Param( value = "recommendResult" )String recommendResult);
    //获取用户上传的文件
    List<ResFile> selectOwnFile(String openid);
    //删除上传的文件
    void deleteOwnFile(String file_id);
    //删除消息
    void deleteMessage(String mid);
    //添加校友圈文件信息
//    void insertMomentFile(@Param(value = "file_id")String file_id,@Param(value = "ftype")String ftype,
//                          @Param(value = "url")String url,@Param(value = "openid")String openid);
    //获取收藏的列表
    List<String> IsCollect(String openid);
    //获取全部的课程id
    List<String> AllCourseId();



    /*
CREATE BY LH 2019.7.11
 */
    List<String> getResFieldList();
    List<String >getOutTime(@Param( value = "handleField" ) String handleField, @Param( value = "openid") String openid, @Param( value = "handleDate") String handleDate);
    List<String >getInTime(@Param( value = "handleField" ) String handleField, @Param( value = "openid") String openid, @Param( value = "handleDate") String handleDate);

}
