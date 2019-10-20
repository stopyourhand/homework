package com.csto.homework.service.impl.course;

import com.csto.homework.mapper.course.CourseFileMapper;
import com.csto.homework.service.course.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class CourseFileServiceImpl implements CourseFileService {

    @Autowired
    CourseFileMapper courseFileMapper;


    /**
     * 陈兆东
     * 根据课程编号获取课程的资源信息的统计
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    public int getCourseResourcesNumber(int courseInfoId,int courseFileType){
        return courseFileMapper.getCourseResourcesNumber(courseInfoId,courseFileType);
    }

    /**
     * 教师为课程创建文件夹
     * @param courseInfoId 课程id
     * @param folderName 文件夹名称列表
     * @return 插入行数
     */
    @Override
    public int createFolder(int courseInfoId, List<String> folderName) {
        if(folderName.isEmpty()){
            return 0;
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String folderCreateTime = simpleDateFormat.format(date);
        return courseFileMapper.createFolders(courseInfoId, folderName, folderCreateTime);
    }

    /**
     * 教师上传文件
     * @param courseInfoId 课程id
     * @param courseFile 上传文件
     * @param courseFileType 上传类型
     * @return 是否上传成功
     */
    @Override
    public int uploadFile(int courseInfoId, MultipartFile courseFile, int courseFileType) throws IOException {
        String courseFileName = courseFile.getOriginalFilename();
        InputStream input = courseFile.getInputStream();
        OutputStream out = new FileOutputStream("f://"+courseFileName);
        byte[] bs = new byte[1024];
        int len = -1;
        while ((len = input.read(bs))!=-1) {
            out.write(bs, 0, len);
        }
        out.close();
        input.close();
        System.out.println("上传成功");
        /**
         * @param courseInfoId 课程id
         * @param courseFileName 上传文件名
         * @param courseFileCode 文件编码
         * @param fileUploadTime 上传时间
         * @param courseFileType 上传类型
         * @return
         */
        String courseFileCode = Math.abs(courseFileName.hashCode())+"";
        System.out.println(courseFileCode);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileUploadTime = simpleDateFormat.format(date);
        return courseFileMapper.insertCourseFile(courseInfoId,courseFileName,courseFileCode,fileUploadTime,courseFileType);
    }
}
