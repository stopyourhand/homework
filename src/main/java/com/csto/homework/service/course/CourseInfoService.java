package com.csto.homework.service.course;

import com.csto.homework.entity.course.CourseInfo;
import com.csto.homework.entity.course.TdownloadPageDto;
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
     *
     * @param courseInfo 课程信息对象
     * @return 插入数据库是否成功
     */
    int createCourse(CourseInfo courseInfo);

    /**
     * 根据教师id查询创建的所有课程
     *
     * @param userInfoId 教师id
     * @return 课程id，课程名称列表
     */
    List<Map<String, String>> findListMyCourse(int userInfoId);

    /**
     * 根据教师编号获取此教师开设的课程名称和课程编号
     *
     * @param userInfoId
     * @return
     */
    List<Map> listCourseByAccount(int userInfoId);


    /**
     * @param courseInfoId 课程id
     * @param courseName   修改的课程名称
     * @return 返回修改行数
     */
    int updateCourseName(int courseInfoId, String courseName);

    /**
     * 删除课程信息
     *
     * @param courseInfoId 课程id
     * @return 删除行数
     */
    int deleteCourseById(int courseInfoId);

    /**
     * 根据老师姓名和课程名称获取对应课程信息列表
     *
     * @param courseName
     * @return
     */
    List<Map<String, String>> listCourseBySearch(String courseName, String teacherName);
    /**
     * 根据课程ID删除指定文件
     * @param courseInfoId
     * @return
     */
    int deleteCourse(int courseInfoId);

    /**
     *     根据课程id查询教师下载课程作业页面信息
     *     TdownloadPageDto
     */
    List<TdownloadPageDto> findListDownloadFolder(int userInfoId);
}
