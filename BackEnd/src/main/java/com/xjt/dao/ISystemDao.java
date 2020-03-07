package com.xjt.dao;

import com.xjt.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ISystemDao
{
    /**
     CREATED BY CHEN ANRAN
     2019.7.4
     */

    //获取评论信息
    List<Comment> selectComment(String resourceId);
    //获取资源列表
    List<Resources> selectResByField(String resField);
   //获取已选择的课程（收藏课程）
    List<Course> selectChosenCourse(String openid);
    //获取提供课程的所有学校
    List<Course> selectAllUniversity();
    //获取提供资源的所有学校
    List<Resources> selectAllSchool();
    //获取资源所属的领域
    List<Resources> selectAllFields();
    //获取资源信息
    List<Resources> selectResInformation(String resId);
    //根据id获取章节信息
    List<ResFile> selectChapterInfo(String file_id);
    //搭配使用
    Resources selectResInfoById(String resId);
    List<ResFile> selectResChapterInfo(String resId);

    //记录浏览历史
    void insertHistory(@Param(value = "openid") String openid, @Param(value = "resId") String resId,
                       @Param(value = "date") String date, @Param(value = "inTime") String inTime,
                       @Param(value = "outTime")String outTime);
    //获取浏览历史
    List<Resources> selectHistory(String openid);
    //显示校友圈列表
    List<Moment> selectMoment();
    //获取资源每个领域所包含的学校
    List<Resources> selectFieldSchool();
    //计算每个资源的平均分
    String selectAvgScore(String resId);
    //平均分插入到表中
    void updateAvgScore(@Param(value = "score") String score,@Param(value = "resId") String resId);
    //显示资源对应的文件
    List<ResFile> selectResFile(String resId);
    //获取每个用户的消息
    List<Message> selectUserMessage(String openid);
 /*
CREATE BY LH 2019.7.10
 */
 //获取该资源在user_resource表中出现的次数
 int selectCountUserByResId(@Param( value = "resId" ) String resId);
 //获取该资源在Resource表中记录的score
 String selectScoreByResId(@Param( value = "resId" ) String resId);
 //先获取某资源所有的结束时间
 List<String> selectOutTimeByResId(@Param( value = "resId" ) String resId);
 //再获取某资源所有的开始时间
 List<String> selectInTimeByResId(@Param( value = "resId" ) String resId);
 //显示用户排行榜
 List<User> selectAllUser();
 void updateScore(String openid);
 List<User> selectRank();

 //获取收藏的资源列表
 List<String> CollectIs(String openid);
 //获取全部的资源id
 List<String> AllResourceId();
}
