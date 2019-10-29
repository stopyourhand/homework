package com.csto.homework.service.impl.course;

import com.csto.homework.mapper.course.CourseFolderMapper;
import com.csto.homework.service.course.CourseFolderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CourseFolderServiceImpl implements CourseFolderService {

    @Autowired
    CourseFolderMapper courseFolderMapper;
    /**
     * 教师为课程创建文件夹
     * @param courseInfoId 课程id
     * @param folderName 文件夹名称
     * @param courseClass 班级列表
     * @return 插入行数
     */
    @Override
    public int createFolder(int courseInfoId, String folderName, List<String > courseClass) {
        if(folderName.isEmpty()){
            return 0;
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String folderCreateTime = simpleDateFormat.format(date);
        return courseFolderMapper.createFolders(courseInfoId, folderName, courseClass, folderCreateTime);
    }

    /**
     * 根据课程的id查询所有的文件夹信息（实验报告以及班级）
     * @param courseInfoId 课程id
     * @return 实验报告和其对应的班级文件夹
     */
    @Override
    public Map findListFolder(int courseInfoId) {
        Map resultMap = new LinkedHashMap<>(16);
        List<String> courseFolderNameList = courseFolderMapper.findListCourseFolderName(courseInfoId);
        //所有文件夹
        resultMap.put("courseFolderNameList", courseFolderNameList);
        //文件夹对应的班级列表
        List<List<String>> allCourseClassList = new LinkedList<>();
        for(String courseFolderName : courseFolderNameList){
            //查询课程文件夹对应添加的班级列表
            List<String> courseClassList = courseFolderMapper.findListCourseClass(courseInfoId, courseFolderName);
            allCourseClassList.add(courseClassList);
        }
        resultMap.put("allCourseClassList", allCourseClassList);
        return resultMap;
    }

    /**
     * 根据课程编号和班级代号获取文件夹名称列表
     * @param courseInfoId
     * @param courseClass
     * @return
     */
    public List<Map> listFolderName(String courseName,String courseClass){
        return courseFolderMapper.listFolderName(courseName,courseClass);
    }
    /**
     * 根据课程ID删除指定文件
     * @param courseInfoId
     * @return
     */
    public int deleteCourse(@Param("courseInfoId") int courseInfoId){
        return courseFolderMapper.deleteCourse(courseInfoId);
    }
}
