package com.csto.homework.service.course;

import com.csto.homework.entity.course.CourseInfo;
import org.apache.ibatis.annotations.Mapper;

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

}
