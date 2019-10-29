package com.csto.homework.service.impl.course;

import com.csto.homework.entity.course.CourseInfo;
import com.csto.homework.entity.course.TdownloadPageDto;
import com.csto.homework.mapper.course.CourseInfoMapper;
import com.csto.homework.service.course.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CourseInfoServiceImpl implements CourseInfoService {
    @Autowired
    CourseInfoMapper courseInfoMapper;
    /**
     * 老师创建课程
     * @param courseInfo 课程信息对象
     * @return 插入数据库是否成功
     */
    @Override
    public int createCourse(CourseInfo courseInfo) {
        int code = courseInfoMapper.findCourseByCourseName(courseInfo.getUserInfoId(),courseInfo.getCourseName());
        //判断该课程名是否在该老师课程中存在
        if(code != 0){
            return -1;
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createCourseTime = simpleDateFormat.format(date);
        courseInfo.setCreateCourseTime(createCourseTime);
        return courseInfoMapper.createCourse(courseInfo);
    }


    /**
     * 根据教师编号获取此教师开设的课程名称和课程编号
     * @param userInfoId
     * @return
     */
    @Override
    public List<Map> listCourseByAccount(int userInfoId){
        return courseInfoMapper.listCourseByAccount(userInfoId);
    }

    /**
     * 根据教师id查询创建的所有课程
     * @param userInfoId 教师id
     * @return 课程id，课程名称列表
     */
    @Override
    public List<Map<String, String>> findListMyCourse(int userInfoId) {
        return courseInfoMapper.findListMyCourse(userInfoId);
    }


    /**
     * @param courseInfoId 课程id
     * @param courseName 修改的课程名称
     * @return 返回修改行数
     */
    @Override
    public int updateCourseName(int courseInfoId, String courseName) {
        return courseInfoMapper.updateCourseName(courseInfoId, courseName);
    }

    /**
     * 删除课程信息
     * @param courseInfoId 课程id
     * @return 删除行数
     */
    @Override
    public int deleteCourseById(int courseInfoId) {
        return courseInfoMapper.deleteCourseById(courseInfoId);
    }

    /**
     * 根据老师姓名和课程名称获取对应课程信息列表
     * @param courseName
     * @return
     */
    @Override
    public List<Map<String,String>> listCourseBySearch(String courseName,String teacherName){
        return courseInfoMapper.listCourseBySearch(courseName,teacherName);
    }

    /**
     * 根据课程ID删除指定文件
     * @param courseInfoId
     * @return
     */
    public int deleteCourse(int courseInfoId){
        return courseInfoMapper.deleteCourse(courseInfoId);
    }

    /**
     *     根据课程id查询教师下载课程作业页面信息
     *     TdownloadPageDto
     */
    @Override
    public List<TdownloadPageDto> findListDownloadFolder(int userInfoId) {
        return courseInfoMapper.findListDownloadFolder(userInfoId);
    }
}
