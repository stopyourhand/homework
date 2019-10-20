package com.csto.homework.service.impl.course;

import com.csto.homework.mapper.course.CourseFileMapper;
import com.csto.homework.service.course.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class CourseFileServiceImpl implements CourseFileService {

    @Autowired
    CourseFileMapper courseFileMapper;

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
}
