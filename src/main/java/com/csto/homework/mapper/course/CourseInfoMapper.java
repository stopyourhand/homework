package com.csto.homework.mapper.course;

import com.csto.homework.entity.course.CourseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 处理课程信息的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface CourseInfoMapper {

    /**
     * 老师创建课程
     * @param courseInfo
     * @return
     */
    int createCourse(CourseInfo courseInfo);

    /**
     * 根据老师姓名和课程名称获取对应课程信息列表
     * @param courseName
     * @return
     */
    List<Map<String,String>> listCourseBySearch(@Param("courseName") String courseName,
                                                @Param("teacherName") String teacherName);

}
