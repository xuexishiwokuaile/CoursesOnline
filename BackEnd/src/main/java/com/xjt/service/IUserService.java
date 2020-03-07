package com.xjt.service;

import com.xjt.model.ReturnClass;
import com.xjt.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface IUserService {

    /********
    *CREATED BY CHEN ANRAN
    *2019.7.4
    */
    //public User selectUser(long userId);

    //用户登录
    ReturnClass login(String openid,String nickName,String avatarUrl);
    //用户评论
    ReturnClass makeComment(String resourceId, String openid, String content,String motion);

    //ReturnClass getCommentList(String resourseId);
    //获取收藏列表
    ReturnClass getFavResList(String openid);
    //情绪判断
    String judgeCommentEmotion(String content);
    //获取蹭课列表
    ReturnClass getCourseBySchool(String University);
    //点击课程收藏按钮
    ReturnClass cilckCourseFavourite(String openid,String Cid);
    //点击资源收藏按钮
    ReturnClass cilckResourceFavourite(String openid,String resId);
    //    CREATE BY LIJIA JUN
    //搜索课程
    ReturnClass SearchCourse(String keyword);
    //取消课程收藏
    ReturnClass cancelCourseFavourite(String openid,String Cid);
    //取消资源收藏
    ReturnClass cancelResourceFavourite(String openid,String resId);
    //    CREATE BY LIJIA JUN
    //根据关键字查询资源
    ReturnClass SearchResource(String keyword);
    //    CREATE BY LI HU

    /**
        CREATE BY LH
        2019.7.5
         */
    //用户评论插入待审核区
    ReturnClass makeAuditComment(String resourceId, String openid, String content, String motion);

    String auditComment(String content);

    //用户上传文件
    //ReturnClass uploadFile(String fileName, MultipartFile multipartFile) throws FileNotFoundException;
    //根据upid获得上传的资源列表
    ReturnClass getUploadResourceList(String open_id);
    //获取文件信息
    ReturnClass getFileInfo(String file_id,String ftype,String url,String chapter,String openid,String resId);
    /*
CREATE BY LH
2017.7.6
 */
    String searchErrorCorrection(String str);
    //发送校友圈信息
    //ReturnClass monentInfo(String openid, String content, List<String> url);
    ReturnClass monentInfo(String openid, String content, String url);
    //对资源评分
    ReturnClass resourceScore(String openid,String resId,String score);
    //用户签到
    ReturnClass signUp(String openid, String date);
    ReturnClass getRecommendByOpenid(String openid);
    //显示用户自己上传的文件
    ReturnClass displayOwnFile(String openid);
    //删除上传的文件
    ReturnClass deleteOwnFile(String file_id);
    //删除消息
    ReturnClass deleteMessage(String mid);
    //添加校友圈文件信息
    //ReturnClass addMomentFileInfo(String file_id,String ftype,String url,String openid);
    //课程号,是否收藏
    Map<String,Boolean> IsCollect(String openid);

    /*
 CREATE BY LH 2019.7.10
 获得Echart
  */
    ReturnClass getEchartInfo(String openid);


}
