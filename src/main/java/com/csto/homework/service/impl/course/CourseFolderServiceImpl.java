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
    public List<String> createFolder(int courseInfoId, String folderName, List<String> courseClass) {
        List<String> repeatCourseClassList = new ArrayList<>();
        if(courseClass.isEmpty()){
            return null;
        }
        //根据课程id，文件夹名称，班级名称判断班级名称是否在该文件夹存在，若
        //若存在，则不创建，将班级名称返回前端页面
        for (String className :courseClass){
            List<Integer> courseFolderId = courseFolderMapper.findFolder(courseInfoId,folderName,className);
            //若不为空，则班级名称已经存在
            if(!courseFolderId.isEmpty()){
                //将该班级名称从列表中剔除
                courseClass.remove(className);
                //将班级名称添加到返回列表中
                repeatCourseClassList.add(className);
            }
        }
        //若添加的文件夹名称刚好全部都已经创建，则直接返回
        if(courseClass.isEmpty()){
            return repeatCourseClassList;
        }
        //否则它们添加到数据库中
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String folderCreateTime = simpleDateFormat.format(date);
        courseFolderMapper.createFolders(courseInfoId, folderName, courseClass, folderCreateTime);
        return repeatCourseClassList;
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
    public List<Map> listFolderName(int courseInfoId,String courseClass){
        return courseFolderMapper.listFolderName(courseInfoId,courseClass);
    }
}
