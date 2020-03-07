package com.xjt.service;

import com.xjt.model.Comment;
import com.xjt.model.ReturnClass;
import com.xjt.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ISystemService
{
    /**
    CREATED BY CHEN ANRAN
    2019.7.4
    */

    //获取评论列表
    ReturnClass getCommentList(String resourceId);
    //ReturnClass GetSchoolList();
    //获取资源列表
    ReturnClass getResourceByField(String resField);
    //显示已选择的课程
    ReturnClass getChosenCourse(String openid);
    //获取提供课程的所有学校
    ReturnClass getAllUniversity();
    //显示资源所属领域
    ReturnClass getAllFields();
    //获取资源信息
    ReturnClass getResInformation(String resId);
    //获取章节信息
    ReturnClass getChapterList(String id);

    //获取特定具体资源信息..搭配使用
    ReturnClass getResourceInfo(String id);
    ReturnClass getResChapter(String id);
    //记录资源浏览历史
    ReturnClass recordHistory(String resField, String openid, String resId, String date,String onTime);
    //显示校友圈
    ReturnClass displayMoment();
    //显示提供资源的所有学校
    ReturnClass getAllSchool();
    //计算资源平均分
    ReturnClass calculateScore(String resId);
    //显示资源历史
    ReturnClass displayHistory(String openid);
    //显示每个资源对应的文件
    ReturnClass displayResFile(String resId);
    //显示用户消息
    ReturnClass displayMessage(String openid);
    //显示用户排行榜
    ReturnClass displayRank();
    //资源号，是否收藏
    Map<String,Boolean> IsCollected(String openid);

}
