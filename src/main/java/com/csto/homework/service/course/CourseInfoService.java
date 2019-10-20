package com.csto.homework.service.course;

import com.csto.homework.entity.course.CourseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 处理课程信息的服务层
 *
 * @Author fjw
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
public interface CourseInfoService {
    /**
     * 老师创建课程
     * @param courseInfo 课程信息对象
     * @return 插入数据库是否成功
     */
    int createCourse(CourseInfo courseInfo);

    /**
     * 根据老师姓名和课程名称获取对应课程信息列表
     * @param courseName
     * @return
     */
    List<Map<String,String>> listCourseBySearch( String courseName,String teacherName);

}
