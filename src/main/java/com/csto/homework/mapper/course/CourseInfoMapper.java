package com.csto.homework.mapper.course;

import com.csto.homework.entity.course.CourseInfo;
import org.apache.ibatis.annotations.Mapper;



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

}
