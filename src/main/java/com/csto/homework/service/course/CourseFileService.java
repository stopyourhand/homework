package com.csto.homework.service.course;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 处理上传课程文件的服务层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
public interface CourseFileService {

    /**
     * 教师为课程创建文件夹
     * @param courseInfoId 课程id
     * @param folderName 文件夹名称列表
     * @return 插入行数
     */
    int createFolder(int courseInfoId, List<String> folderName);


}
