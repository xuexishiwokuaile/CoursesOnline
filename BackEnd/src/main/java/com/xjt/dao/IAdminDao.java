package com.xjt.dao;

import com.xjt.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAdminDao
{
    /**
     CREATED BY CHEN ANRAN
     2019.7.7
     */

    //验证登录信息
    Admin selectAdmin_id(String openid);
    //插入未重复的管理员信息
    void insertAdminInfo(@Param(value="openid") String openid, @Param(value="nickname") String nickname,
                        @Param(value = "avatarUrl") String avatarUrl);
    //获取待审核列表
    List<Comment> selectCommentAudit();
    //删除待审核内容
    void deleteCommentAudit(String commentId);
    //获取删除内容
    List<Comment> getDeleteInfo(String commentId);
    //插入评论
    void insertComment(@Param(value="commentId") String commentId,@Param(value="openid") String openid,
                       @Param(value="resourceId") String resourceId,@Param(value="content") String content,
                       @Param(value = "motion")String motion);
    //删除课程
    void deleteCourse(String Cid);
    //显示课程列表
    List<Course> selectCourse();
    //添加课程信息
    void insertCourse(@Param(value = "Cname")String Cname,@Param(value = "Cteacher")String Cteacher,
                      @Param(value = "location")String location,@Param(value = "University")String University,
                      @Param(value = "couField") String couField,@Param(value = "courseTime") String courseTime,
                      @Param(value = "Cid")String Cid);
    //获取资源列表
    List<Resources> selectResByField(String resField);
    //获取资源所属的领域
    List<Resources> selectAllFields();
    //删除资源
    void deleteResource(String resId);
    //添加资源
    void insertResource(@Param(value = "resId")String resId,@Param(value = "Cname")String Cname,
                     @Param(value = "resField")String resField,@Param(value = "resInfo")String resInfo,
                     @Param(value = "school")String school);
    //获取文件表中的所有文件
    List<ResFile> selectAllFile();
    //获得待审核文件的信息
    List<ResFile> selectDeleteFileInfo(String file_id);
    //插入到审核完成表中
    void insertAuditFile(@Param(value = "file_id")String file_id,@Param(value = "ftype")String ftype,
                         @Param(value = "url")String url,@Param(value = "chapter")String chapter,
                         @Param(value = "openid")String openid);
    //删除原来表中的内容
    void deleteAuditedFile(String file_id);
    //获取所有管理员的信息
    List<Admin> selectAdminInfo(String openid);
    //获得所有收藏该文件对应资源的用户
    List<Message> selectAllUser(String file_id);
    //给收藏了资源的用户推送消息
    void insertMessage(@Param(value = "openid") String openid,@Param(value = "message")String message,
                       @Param(value = "mid")String mid);
}
