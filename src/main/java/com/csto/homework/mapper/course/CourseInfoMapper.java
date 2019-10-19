package com.csto.homework.mapper.course;

import com.csto.homework.entity.course.CourseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 处理课程信息的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface CourseInfoMapper {

    //老师创建课程
    int createCourse(CourseInfo courseInfo);

    //查询数据库中是否有该老师添加的课程名
    int findCourseByCourseName(@Param("userInfoId") int uid, @Param("courseName") String courseName);

    //查询老师已经添加了的全部课程


}
