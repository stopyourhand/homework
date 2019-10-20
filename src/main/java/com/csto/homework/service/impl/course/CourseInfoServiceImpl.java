package com.csto.homework.service.impl.course;

import com.csto.homework.entity.course.CourseInfo;
import com.csto.homework.entity.user.UserLogin;
import com.csto.homework.mapper.course.CourseInfoMapper;
import com.csto.homework.service.course.CourseInfoService;
import org.apache.ibatis.annotations.Param;
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
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createCourseTime = simpleDateFormat.format(date);
        courseInfo.setCreateCourseTime(createCourseTime);
        return courseInfoMapper.createCourse(courseInfo);
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
}
