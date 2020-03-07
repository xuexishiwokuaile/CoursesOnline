package com.xjt.service.impl;

import com.xjt.dao.IAdminDao;
import com.xjt.dao.IUserDao;
import com.xjt.model.*;
import com.xjt.service.IAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 CREATED BY CHEN ANRAN
 */

@Service("adminService")
public class AdminServiceImpl implements IAdminService
{
    @Resource
    private IAdminDao adminDao;
    private IUserDao userDao;

    //管理员登录
    public ReturnClass login(String openid, String nickName, String avatarUrl)
    {
        ReturnClass rt = new ReturnClass();
        //要成功获取到属性，类中的字段必须和表中的属性相同
        Admin admin = adminDao.selectAdmin_id(openid);

        if (admin!=null) {
            rt.setCode( 1 );
            rt.setMsg( "管理员存在，登录成功" );
        }
        else
        {
            rt.setCode( -1 );
            rt.setMsg("管理员不存在");
        }
        return rt;
    }

    //验证管理员身份，不符合则无法进入开发者界面
    public ReturnClass vertifyAdmin(String openid)
    {
        ReturnClass rt = new ReturnClass();

        boolean flag = false;
        List<Admin> resultList = adminDao.selectAdminInfo(openid);
        for(int i=0;i<resultList.size();i++)
        {
            if(openid.equals(resultList.get(i).getOpenid()))
            {
                flag=true;
                break;
            }
        }
        rt.setCode(1);
        rt.setMsg("验证完成");
        rt.setData(flag);
        return rt;
    }

    //查看待审核的评论列表
    public ReturnClass commentAudit()
    {
        ReturnClass rt = new ReturnClass();
        List<Comment>resultList = new ArrayList<Comment>();
        List<Comment> list = new ArrayList<Comment>();

        resultList = adminDao.selectCommentAudit();

        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String commentId = resultList.get(i).getCommentId();
                String openid = resultList.get(i).getOpenid();
                String nickName = resultList.get(i).getNickName();
                String resourceId = resultList.get(i).getRecourceId();
                String content = resultList.get(i).getContent();
                String avatarUrl = resultList.get(i).getAvatarUrl();
                String motion = resultList.get(i).getMotion();

                Comment comment = new Comment(commentId,openid,nickName,resourceId,content,avatarUrl,motion);
                list.add(comment);
            }
            rt.setCode(1);
            rt.setMsg("待审核列表获取成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode( 0 );
            rt.setMsg( "待审核列表获取失败" );
            rt.setData( null );
        }
        return rt;
    }

    //通过审核
    public ReturnClass passCommentAudit(String commentId)
    {
        ReturnClass rt = new ReturnClass();
        List<Comment>list = adminDao.getDeleteInfo(commentId);
        if(list.size()>0)
        {
            String openid = list.get(0).getOpenid();
            String resourceId = list.get(0).getRecourceId();
            String content = list.get(0).getContent();
            String motion = list.get(0).getMotion();
            adminDao.deleteCommentAudit(commentId);
            adminDao.insertComment(commentId,openid,resourceId,content,motion);
            rt.setCode(1);
            rt.setMsg("评论审核通过！");
            rt.setData(list);
        }
        else
        {
            rt.setCode(-1);
            rt.setMsg("评论审核失败！");
            rt.setData(null);
        }
        return rt;
    }

    //不通过审核
    public ReturnClass rejectCommentAudit(String commentId)
    {
        ReturnClass rt = new ReturnClass();
        adminDao.deleteCommentAudit(commentId);
        rt.setMsg("删除审核成功");
        rt.setCode(1);
        return rt;
    }

    //删除课程
    public ReturnClass deleteCourse(String Cid)
    {
        ReturnClass rt = new ReturnClass();
        adminDao.deleteCourse(Cid);
        rt.setMsg("删除课程成功");
        rt.setCode(1);
        return rt;
    }


    //显示课程列表
    public ReturnClass displayCourse()
    {
        ReturnClass rt = new ReturnClass();
        List<Course> resultList = new ArrayList<Course>();
        List<Course> list = new ArrayList<Course>();
        resultList = adminDao.selectCourse();
        if(resultList!=null)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String Cname = resultList.get(i).getCname();
                String Cteacher = resultList.get(i).getCteacher();
                String location = resultList.get(i).getLocation();
                String University = resultList.get(i).getUniversity();
                String couField = resultList.get(i).getField();
                String courseTime = resultList.get(i).getField();
                String Cid = resultList.get(i).getCid();

                Course course = new Course(Cname,Cteacher,location,couField,Cid,courseTime,University);
                list.add(course);
            }
            rt.setCode(1);
            rt.setMsg("课程显示成功");
            rt.setData(list);
        }
        else
        {
            rt.setCode(-1);
            rt.setMsg("课程显示失败");
            rt.setData(null);
        }
        return rt;
    }

    //添加课程
    public ReturnClass addCourse(String Cname,String Cteacher,String location,String University,String couField,
                                 String courseTime)
    {
        ReturnClass rt = new ReturnClass();

        String Cid = RamdomString.generateString();
        adminDao.insertCourse(Cname,Cteacher,location,University,couField,courseTime,Cid);
        rt.setMsg("添加课程");
        rt.setCode(1);
        return rt;
    }

    //显示资源列表
    public ReturnClass getResourceByField(String resField)
    {
        ReturnClass rt = new ReturnClass( );
        List<Resources> resultList = new ArrayList<Resources>();
        List<Resources> list = new ArrayList<Resources>();
        resultList = adminDao.selectResByField(resField);
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

    //显示资源所属领域
    public ReturnClass getAllFields()
    {
        ReturnClass rt = new ReturnClass( );
        List<Resources> resultList = new ArrayList<Resources>();
        List<Resources> list = new ArrayList<Resources>();
        resultList = adminDao.selectAllFields();
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

    //删除资源
    public ReturnClass deleteResource(String resId)
    {
        ReturnClass rt = new ReturnClass();

        adminDao.deleteResource(resId);
        rt.setMsg("删除成功");
        rt.setCode(1);
        return rt;
    }

    //添加资源
    public ReturnClass addResource(String Cname,String resField,String resInfo)
    {

        ReturnClass rt = new ReturnClass();

        String resId = RamdomString.generateString();
        String school = "其他";
        adminDao.insertResource(resId,Cname,resField,resInfo,school);

        rt.setCode(1);
        rt.setMsg("添加成功");
        return rt;
    }

    //显示所有上传文件
    public ReturnClass displayFile()
    {
        ReturnClass rt = new ReturnClass();

        List<ResFile> resultList = adminDao.selectAllFile();
        List<ResFile> list = new ArrayList<>();

        if(resultList.size()!=0)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String file_id = resultList.get(i).getFile_id();
                String ftype = resultList.get(i).getType();
                String url = resultList.get(i).getUrl();
                String chapter = resultList.get(i).getChapter();
                String openid = resultList.get(i).getOpenid();

                ResFile resFile = new ResFile(file_id,ftype,chapter,url,openid);
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

    //通过审核
    public ReturnClass auditFile(String file_id)
    {
        ReturnClass rt = new ReturnClass();

        //获得待审核文件的所有信息
        List<ResFile> infoList = adminDao.selectDeleteFileInfo(file_id);
        if(infoList.size()>0)
        {
            String ftype = infoList.get(0).getType();
            String url = infoList.get(0).getUrl();
            String chapter = infoList.get(0).getChapter();
            String openid = infoList.get(0).getOpenid();

            adminDao.insertAuditFile(file_id,ftype,url,chapter,openid);
            adminDao.deleteAuditedFile(file_id);
            rt.setCode(1);
            rt.setMsg("审核通过成功");
        }
        else
        {
            rt.setCode(-1);
            rt.setMsg("审核通过失败");
        }
        return rt;
    }

    //不通过审核
    public ReturnClass rejectFile(String file_id)
    {
        ReturnClass rt = new ReturnClass();

        adminDao.deleteAuditedFile(file_id);
        rt.setCode(1);
        rt.setMsg("成功");
        return rt;
    }

    //给用户推送更新信息
    public ReturnClass sendUpdateMessage(String file_id)
    {
        ReturnClass rt = new ReturnClass();
        List<Message> resultList = adminDao.selectAllUser(file_id);
        List<Message> list = new ArrayList<Message>();
        if(resultList.size()>0)
        {
            for(int i=0;i<resultList.size();i++)
            {
                String openid = resultList.get(i).getOpenid();
                String Cname = resultList.get(i).getCname();
                String message1 = "您选择的"+Cname+"课程有更新啦！快来看看吧~";
                Message message = new Message(openid,message1,Cname);
                list.add(message);
            }
            //给list中的每个用户推送消息
            for(int i=0;i<list.size();i++)
            {
                String mid = RamdomString.generateString();
                adminDao.insertMessage(list.get(i).getOpenid(),list.get(i).getMessage(),mid);
            }
            rt.setMsg("推送成功");
            rt.setCode(1);
        }
        else
        {
            rt.setMsg("推送失败");
            rt.setCode(-1);
        }
        return rt;
    }
}
