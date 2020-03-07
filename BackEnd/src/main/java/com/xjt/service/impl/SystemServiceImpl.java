package com.xjt.service.impl;

import com.xjt.dao.ISystemDao;
import com.xjt.dao.IUserDao;
import com.xjt.model.*;
import com.xjt.service.ISystemService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.*;

@Service("systemService")
public class SystemServiceImpl implements ISystemService
{
    /**
     CREATED BY CHEN ANRAN
     2019.7.4
     */

    @Resource
    private ISystemDao systemDao;

    public ReturnClass getCommentList(String resourceId)
    {
        ReturnClass rt = new ReturnClass( );
        List<Comment> resultList = new ArrayList<Comment>();
        List<Comment> list = new ArrayList<Comment>();
        resultList = systemDao.selectComment(resourceId);
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String commentId = resultList.get(i).getCommentId();
                String openid = resultList.get(i).getOpenid();
                String nickName = resultList.get(i).getNickName();
                String content = resultList.get(i).getContent();
                String avatarUrl = resultList.get(i).getAvatarUrl();
                String motion = resultList.get(i).getMotion();

                //新建评论对象
                Comment comment = new Comment(commentId,openid,nickName,resourceId,content,avatarUrl,motion);
                list.add(comment);
            }
            rt.setCode(1);
            rt.setMsg("评论列表获取成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode( 0 );
            rt.setMsg( "评论列表获取失败" );
            rt.setData( null );
        }
        return rt;
    }

//显示资源列表
public ReturnClass getResourceByField(String resField)
{
    ReturnClass rt = new ReturnClass( );
    List<Resources> resultList = new ArrayList<Resources>();
    List<Resources> list = new ArrayList<Resources>();
    resultList = systemDao.selectResByField(resField);
    System.out.println( "resultList获取成功" );
    //根据field得到的resId集合
    List<String> fieldResult = new ArrayList<>(  );
/*
CREATE BY LH 2019.7.10
 */
    if(resultList!=null){
        for(int i = 0; i < resultList.size(); i++){
            String resId = resultList.get( i ).getResId();
            System.out.println( "正在对第" + i + "项进行取分,resId为：" + resId );
            //第一项指标resCountUser
            int resCountUser = systemDao.selectCountUserByResId( resId );
            System.out.println( "resCountUer获取成功:" + resCountUser);
            //第二项指标resScoreToInt
            String resScore = systemDao.selectScoreByResId( resId );
            System.out.println( "resScore获取成功:" + resScore);
            Double resScoreToDouble = 0.0;
            if(resScore != null)Double.parseDouble( resScore );
            //第三项指标finalTime
            List<String> outTime = new ArrayList<>(  );
            System.out.println( "开始获取outTime" );
            outTime = systemDao.selectOutTimeByResId( resId );
            System.out.println( "outTime获取成功"  );
            int outTimeToInt = 0;
            if(outTime!=null){
                for(int j = 0; j < outTime.size(); j++){
                    int time = Integer.parseInt(outTime.get( j ));
                    outTimeToInt += Integer.parseInt(outTime.get( j ));
                }
            }
            List<String> inTime = new ArrayList<>(  );
            inTime = systemDao.selectInTimeByResId( resId );
            int inTimeToInt = 0;
            if(inTime!=null){
                for(int j = 0; j < outTime.size(); j++){
                    int time = Integer.parseInt(inTime.get( j ));
                    inTimeToInt += Integer.parseInt(inTime.get( j ));
                }
            }
            int finalTime = outTimeToInt - inTimeToInt;
            Double finalScore = resCountUser + resScoreToDouble + finalTime;
            System.out.println( "第" + i + "个资源的结果分数为" + finalScore );
            //把该资源的分数记在该资源的score项中
            resultList.get(i).setScore( String.valueOf(finalScore) );
            System.out.println( "将第"+ i + "个资源的score存为" + resultList.get( i ).getScore() );
        }
    }
    //对得到的resulList中的资源根据score值进行排序
    for(int i = 0; i < resultList.size() - 1;i++){
        for(int j = 0; j < resultList.size() - 1 - i; j++){
            if(Double.parseDouble(resultList.get( j ).getScore()) < Double.valueOf(resultList.get(j + 1).getScore())){
                //System.out.println( "需要调整第"+ j +"和" + (j+1) + "个的位置");
                String tempResId = resultList.get( j ).getResId();
                String tempCname = resultList.get( j ).getCname();
                String tempResInfo = resultList.get(j).getResInfo();
                String tempScore = resultList.get( j ).getScore();
                System.out.println( tempResId + " " + tempCname + " " + tempResInfo );
                //Resources resources = new Resources( Cname, resId, resInfo);
                resultList.get( j ).setResId( resultList.get( j+1 ).getResId() );
                resultList.get( j ).setCname( resultList.get(j+1).getCname() );
                resultList.get( j ).setResInfo( resultList.get( j+1 ).getResInfo() );
                resultList.get( j ).setScore( resultList.get( j+1 ).getScore() );

                resultList.get( j+1 ).setResId( tempResId );
                resultList.get( j+1 ).setCname( tempCname );
                resultList.get( j+1 ).setResInfo( tempResInfo );
                resultList.get( j+1 ).setScore( tempScore );
            }
        }
    }
            if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String resId = resultList.get(i).getResId();
                String Cname = resultList.get(i).getCname();
                String resInfo = resultList.get(i).getResInfo();
                String school = resultList.get(i).getSchool();
                String imgUrl = resultList.get(i).getImgUrl();

                Resources resources = new Resources(Cname,resId,resInfo,resField,"",school,imgUrl);
                list.add(resources);
            }
            rt.setCode(1);
            rt.setMsg("资源列表获取成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode( 0 );
            rt.setMsg( "资源列表获取失败" );
            rt.setData( null );
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示已收藏的课程
    public ReturnClass getChosenCourse(String openid)
    {
        ReturnClass rt = new ReturnClass();
        List<Course> resultList = new ArrayList<Course>();
        List<Course> list = new ArrayList<Course>();
        resultList = systemDao.selectChosenCourse(openid);
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String Cname = resultList.get(i).getCname();
                String Cteacher = resultList.get(i).getCteacher();
                String location = resultList.get(i).getLocation();
                String University = resultList.get(i).getUniversity();
                String couField = resultList.get(i).getField();
                String courseTime = resultList.get(i).getCourseTime();
                String Cid = resultList.get(i).getCid();

                //新建评论对象
                Course course = new Course(Cname,Cteacher,location,couField,Cid,courseTime,University);
                list.add(course);
            }
            rt.setCode(1);
            rt.setMsg("选课列表获取成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode( 0 );
            rt.setMsg( "选课列表获取失败" );
            rt.setData( null );
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //获取提供课程的所有学校
    public ReturnClass getAllUniversity()
    {
        ReturnClass rt = new ReturnClass();
        List<Course> resultList = new ArrayList<Course>();
        List<Course> list = new ArrayList<Course>();
        resultList = systemDao.selectAllUniversity();
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String University = resultList.get(i).getUniversity();
                //新建课程对象
                Course course = new Course(University);
                list.add(course);
            }
            rt.setCode(1);
            rt.setMsg("学校列表获取成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode( 0 );
            rt.setMsg( "学校列表获取失败" );
            rt.setData( null );
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示提供资源的所有学校
    public ReturnClass getAllSchool()
    {
        ReturnClass rt = new ReturnClass();
        List<Resources> resultList = systemDao.selectFieldSchool();
        List<String> list = new ArrayList<String>();

        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String school = resultList.get(i).getSchool();

                list.add(school);
            }
            rt.setCode(1);
            rt.setMsg("获取成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode(-1);
            rt.setMsg("获取失败");
            rt.setData(null);
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示资源所属领域
    public ReturnClass getAllFields()
    {
        ReturnClass rt = new ReturnClass( );
        List<Resources> resultList = new ArrayList<Resources>();
        List<Resources> list = new ArrayList<Resources>();
        resultList = systemDao.selectAllFields();
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String resField = resultList.get(i).getResField();

                //新建评论对象
                Resources resources = new Resources(resField);
                list.add(resources);
            }
            rt.setCode(1);
            rt.setMsg("领域列表获取成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode( 0 );
            rt.setMsg( "领域列表获取失败" );
            rt.setData( null );
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //获取资源信息（最后的详情界面）
    public ReturnClass getResInformation(String resId)
    {
        ReturnClass rt = new ReturnClass( );
        List<Resources> resultList = new ArrayList<Resources>();
        List<Resources> list = new ArrayList<Resources>();
        resultList = systemDao.selectResInformation(resId);
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String Cname = resultList.get(i).getCname();
                String resField = resultList.get(i).getResField();
                String Upid = resultList.get(i).getUpid();
                String resInfo = resultList.get(i).getResInfo();
                String school = resultList.get(i).getSchool();
                String imgUrl = resultList.get(i).getImgUrl();

                //新建评论对象
                Resources resources = new Resources(Cname,resId,resInfo,resField,Upid,school,imgUrl);
                list.add(resources);
            }
            rt.setCode(1);
            rt.setMsg("资源信息获取成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode( 0 );
            rt.setMsg( "资源信息获取失败" );
            rt.setData( null );
        }
        return rt;
    }


    //根据id获取章节信息
    public ReturnClass getChapterList(String id) {
        ReturnClass rt = new ReturnClass();
        List<ResFile> resultList = new ArrayList<>(systemDao.selectChapterInfo(id));
        List<ResFile> list = new ArrayList<ResFile>();
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String file_id = resultList.get(i).getFile_id();
                String chapter = resultList.get(i).getChapter();
                String ftype = resultList.get(i).getType();
                String url = resultList.get(i).getType();
                ResFile resFile = new ResFile(file_id,ftype,chapter,url);
                list.add(resFile);
            }
            rt.setCode(1);
            rt.setMsg("加载成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode( 0 );
            rt.setMsg( "加载失败" );
            rt.setData( null );
        }
        return rt;

    }

    //根据资源id获取资源具体信息
    public ReturnClass getResourceInfo(String id) {
        ReturnClass rt = new ReturnClass();
        Resources result =  systemDao.selectResInfoById(id);
        if(result!=null)
        {
            rt.setCode(1);
            rt.setMsg("加载成功");
            rt.setData(result);
        }
        else
        {
            rt.setCode( 0 );
            rt.setMsg( "资源不存在" );
            rt.setData( null );
        }
        return rt;

    }

    public ReturnClass getResChapter(String id)
    {
        ReturnClass rt =new ReturnClass();
        List<ResFile> resultList = systemDao.selectResChapterInfo(id);
        List<ResFile> list = new ArrayList<ResFile>();
        if(!resultList.isEmpty())
        {
            for(int i=0;i<resultList.size();i++)
            {
                ResFile  resFile = new ResFile();
                resFile.setChapter(resultList.get(i).getChapter());
                resFile.setFile_id(resultList.get(i).getFile_id());
                list.add(resFile);
            }
            System.out.println("哈哈哈哈哈哈哈哈哈哈或或或或或或:       "+resultList.size());
            rt.setData(list);
            rt.setCode(1);
            rt.setMsg("返回成功");
        }
        else
        {
            rt.setMsg("该资源无章节");
            rt.setCode(0);
            rt.setData(null);
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //记录资源浏览历史
    public ReturnClass recordHistory(String openid, String resId, String date,String inTime,String outTime)
    {
        ReturnClass rt = new ReturnClass();
        systemDao.insertHistory(openid,resId,date,inTime,outTime);

        rt.setCode(1);
        rt.setMsg("记录浏览历史成功");
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示资源历史
    public ReturnClass displayHistory(String openid)
    {
        ReturnClass rt = new ReturnClass();
        List<Resources> resultList = systemDao.selectHistory(openid);
        List<Resources> list = new ArrayList<Resources>();

        if(resultList.size()>0)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String Cname = resultList.get(i).getCname();
                String resField = resultList.get(i).getResField();
                String imgUrl = resultList.get(i).getImgUrl();

                Resources resources = new Resources(Cname,"","",resField,"","",imgUrl);
                list.add(resources);
            }
            rt.setMsg("显示成功");
            rt.setCode(1);
            rt.setData(list);
        }
        else
        {
            rt.setMsg("显示失败");
            rt.setCode(-1);
           // rt.setData(null);
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示校友圈列表
    public ReturnClass displayMoment()
    {
        ReturnClass rt = new ReturnClass();
        List<Moment> resultList = new ArrayList<Moment>();
        List<Moment> list = new ArrayList<Moment>();
        resultList = systemDao.selectMoment();

        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String Mid = resultList.get(i).getMid();
                String openid = resultList.get(i).getMid();
                String nickName = resultList.get(i).getNickName();
                String avatarUrl = resultList.get(i).getAvatarUrl();
                String content = resultList.get(i).getContent();
                String url = resultList.get(i).getUrl();

                Moment moment = new Moment(Mid,openid,nickName,content,url,avatarUrl);
                list.add(moment);
            }
            rt.setCode(1);
            rt.setMsg("加载成功");
            rt.setData(list);
        }
        else
        {
            rt.setMsg("加载失败");
            rt.setCode(0);
            rt.setData(null);
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //计算每个资源的平均评分
    public ReturnClass calculateScore(String resId)
    {
        ReturnClass rt = new ReturnClass();
        String score =  systemDao.selectAvgScore(resId);
        systemDao.updateAvgScore(score,resId);
        rt.setMsg("计算成功");
        rt.setCode(1);
        rt.setData(score);
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示每个资源对应的文件
    public ReturnClass displayResFile(String resId)
    {
        ReturnClass rt = new ReturnClass();
        List<ResFile> resultList = systemDao.selectResFile(resId);
        List<ResFile> list = new ArrayList<ResFile>();
        if(resultList.size()>0)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String url = resultList.get(i).getUrl();

                ResFile resFile = new ResFile(url);
                list.add(resFile);
            }
            rt.setCode(1);
            rt.setMsg("显示成功");
            rt.setData(list);
        }
        else
        {
            rt.setMsg("显示失败");
            rt.setCode(-1);
            rt.setData(null);
        }
        return rt;
    }

    /**
     CREATED BY CHEN ANRAN
     */
    //显示用户消息
    public ReturnClass displayMessage(String openid)
    {
        ReturnClass rt = new ReturnClass();
        List<Message> resultList=systemDao.selectUserMessage(openid);
        List<Message> list = new ArrayList<Message>();
        if(resultList.size()>0)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String message1 = resultList.get(i).getMessage();
                String mid = resultList.get(i).getMid();
                Message message = new Message(message1,mid);
                list.add(message);
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
    //显示用户排行榜
    public ReturnClass displayRank()
    {
        ReturnClass rt = new ReturnClass();
        List<User> userList = systemDao.selectAllUser();
//        for(int i=0;i<userList.size();i++)
//        {
//            systemDao.updateScore(userList.get(i).getOpenid());
//        }
        List<User> rankList = systemDao.selectRank();
        List<User> list = new ArrayList<User>();
        if(rankList.size()>0)
        {
            for(int i=0;i<rankList.size();i++)
            {
                String nickName = rankList.get(i).getNickName();
                String avatarUrl = rankList.get(i).getavatarUrl();
                String score = rankList.get(i).getScore();
                User user = new User(nickName,avatarUrl,score);
                list.add(user);
            }
            rt.setMsg("获取成功");
            rt.setCode(1);
            rt.setData(list);
        }
        else
        {
            rt.setCode(-1);
            rt.setMsg("获取失败");
            rt.setData(null);
        }
        return rt;
    }

    public Map<String,Boolean> IsCollected(String openid)
    {
        List<String> AllResid = new ArrayList<String>(systemDao.AllResourceId());
        List<String> resultList = new ArrayList<String>(systemDao.CollectIs(openid));
        Map<String,Boolean> resourceBool = new HashMap<String,Boolean>();
        for(int i=0;i<AllResid.size();i++)
        {
            resourceBool.put(AllResid.get(i),false);
        }
        if(!resultList.isEmpty())
        {
            for(int i =0;i<resultList.size();i++)
            {
                resourceBool.replace(resultList.get(i),true);
            }
        }
        return resourceBool;
    }

}
