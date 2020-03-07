package com.xjt.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xjt.dao.IUserDao;
import com.xjt.model.OSSAtrribute;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 CREATED BY CHEN ANRAN
 */

@RestController
@RequestMapping(value = "/oss",method = RequestMethod.POST)
@ResponseBody
public class OssController
{
    public static String url;
    public static String fileName ;
    public static String ftype ;
    //public static List<String> urlList;

    @Resource
    private IUserDao userDao;
    //上传文件
    @PostMapping
    @CrossOrigin(origins = "*")
    @RequestMapping("/fileObj")
    public void fileUpload(@RequestParam(value="myfile") MultipartFile multipartFile)
            throws FileNotFoundException, IOException
    {
        String fileType = getFileType(multipartFile.getContentType());

        this.fileName = "file"+RamdomString.generateString()+"."+fileType;
        this.ftype = getFileKind(multipartFile.getContentType());
        String url = OSSAtrribute.uploadFile(fileName, multipartFile);
        this.url = url;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("source","111");
        jsonArray.add(jsonObject);



        //urlList.add(url);
//        userDao.insertFile(file_id,ftype,url,chapter,openid);
//        userDao.insertResourseFile(resId,file_id);
    }

    //获取文件后缀名
    private String getFileType(String content)
    {
        String pattern ="(.*)(/)(.*)";
        String fileType = null;

        Pattern r =Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        if(m.find())
        {
            fileType =m.group(3);
        }
        return fileType;
    }

    //获取文件类型
    private String getFileKind(String content)
    {
        String pattern ="(.*)(/)(.*)";
        String fileKind = null;

        Pattern r =Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        if(m.find())
        {
            fileKind =m.group(1);
        }
        return fileKind;
    }

}
