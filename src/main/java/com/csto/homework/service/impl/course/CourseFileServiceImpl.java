package com.csto.homework.service.impl.course;

import com.csto.homework.entity.course.CourseFile;
import com.csto.homework.mapper.course.CourseFileMapper;
import com.csto.homework.service.course.CourseFileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CourseFileServiceImpl implements CourseFileService {

    @Autowired
    CourseFileMapper courseFileMapper;


    /**
     * 根据课程编号获取课程的资源信息的统计
     *
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    @Override
    public int getCourseResourcesNumber(int courseInfoId, int courseFileType) {
        return courseFileMapper.getCourseResourcesNumber(courseInfoId, courseFileType);
    }



    /**
     * 根据课程编号获取课程的资源信息的名称和编号
     *
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    @Override
    public List<Map> listCourseResourcesName(int courseInfoId, int courseFileType) {
        return courseFileMapper.listCourseResourcesName(courseInfoId, courseFileType);
    }

    /**
     * 根据文件编号获取服务器上传文件后的编码
     *
     * @param courseFileId
     * @return
     */
    @Override
    public String getFileCodeByCourseFileId(@Param("courseFileId") int courseFileId) {
        return courseFileMapper.getFileCodeByCourseFileId(courseFileId);
    }


    /**
     * 教师为课程创建文件夹
     *
     * @param courseInfoId 课程id
     * @param folderName   文件夹名称列表
     * @return 插入行数
     */
    @Override
    public int createFolder(int courseInfoId, List<String> folderName) {
        if (folderName.isEmpty()) {
            return 0;
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String folderCreateTime = simpleDateFormat.format(date);
        return courseFileMapper.createFolders(courseInfoId, folderName, folderCreateTime);
    }

    /**
     * 教师上传文件
     *
     * @param courseInfoId   课程id
     * @param courseFile     上传文件
     * @param courseFileType 上传类型
     * @return 是否上传成功
     */
    @Override
    public int uploadFile(int courseInfoId, MultipartFile courseFile, int courseFileType) throws IOException {
        String fileName = courseFile.getOriginalFilename();
        InputStream input = courseFile.getInputStream();
        OutputStream out = new FileOutputStream("f://" + fileName);
        byte[] bs = new byte[1024];
        int len = -1;
        while ((len = input.read(bs)) != -1) {
            out.write(bs, 0, len);
        }
        out.close();
        input.close();
        System.out.println("上传成功");
        return 0;
    }

    /**
     * 根据文档id删除指定文档
     *
     * @param courseFileId
     * @return
     */
    @Override
    public int deleteCourseFileById(int courseFileId){
        return courseFileMapper.deleteCourseFileById(courseFileId);
    }
}
