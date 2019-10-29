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
     *
     * @param courseInfo
     * @return
     */
    int createCourse(CourseInfo courseInfo);

    //查询数据库中是否有该老师添加的课程名
    int findCourseByCourseName(@Param("userInfoId") int userInfoId, @Param("courseName") String courseName);

    //查询老师已经添加了的全部课程
    List<Map<String, String>> findListMyCourse(int userInfoId);


    //修改课程名称
    int updateCourseName(@Param("courseInfoId") int courseInfoId, @Param("courseName") String courseName);

    /**
     * 根据教师编号获取此教师开设的课程名称和课程编号
     * @param userInfoId
     * @return
     */
    List<Map> listCourseByAccount(@Param("userInfoId") int userInfoId);

    /**
     * 根据老师姓名和课程名称获取对应课程信息列表
     *
     * @param courseName
     * @return
     */
    List<Map<String, String>> listCourseBySearch(@Param("courseName") String courseName,
                                                 @Param("teacherName") String teacherName);

    //删除课程
    int deleteCourseById(int courseInfoId);

    /**
     * 根据课程ID删除指定文件
     * @param courseInfoId
     * @return
     */
    int deleteCourse(@Param("courseInfoId") int courseInfoId);

}
