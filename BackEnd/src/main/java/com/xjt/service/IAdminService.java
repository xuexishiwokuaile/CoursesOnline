package com.xjt.service;

import com.xjt.model.ReturnClass;

public interface IAdminService
{
    /**
     CREATED BY CHEN ANRAN
     2019.7.7
     */

    //管理员登录
    ReturnClass login(String openid, String nickName, String avatarUrl);
    //查看待审核的评论列表
    ReturnClass commentAudit();
    //通过审核
    ReturnClass passCommentAudit(String commentId);
    //不通过审核
    ReturnClass rejectCommentAudit(String commentId);
    //删除课程
    ReturnClass deleteCourse(String Cid);
    //显示课程
    ReturnClass displayCourse();
    //添加课程
    ReturnClass addCourse(String Cname,String Cteacher,String location,String University,String couField,
                          String courseTime);
    //显示资源列表
    ReturnClass getResourceByField(String resField);
    //显示资源所属领域
    ReturnClass getAllFields();
    //删除资源
    ReturnClass deleteResource(String resId);
    //添加资源
    ReturnClass addResource(String Cname,String resField,String resInfo);
    //显示所有上传文件
    ReturnClass displayFile();
    //通过审核
    ReturnClass auditFile(String file_id);
    //不通过审核
    ReturnClass rejectFile(String file_id);
    //验证管理员身份
    ReturnClass vertifyAdmin(String openid);
    //给用户推送更新信息
    ReturnClass sendUpdateMessage(String file_id);
}
