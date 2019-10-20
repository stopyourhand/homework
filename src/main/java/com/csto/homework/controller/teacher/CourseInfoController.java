package com.csto.homework.controller.teacher;

import com.csto.homework.dto.Result;
import com.csto.homework.entity.course.CourseInfo;
import com.csto.homework.service.course.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教师对增删课程的操作
 * @author fjw
 */
@RestController
@RequestMapping(value = "/CourseInfo")
public class CourseInfoController {

    @Autowired
    CourseInfoService courseInfoService;

    /**
     * 教师添加课程
     * @param courseInfo 课程信息
     * @return 提示结果
     */
    @PostMapping("/createCourse")
    public Result createCourse(CourseInfo courseInfo){

        int resultCode = courseInfoService.createCourse(courseInfo);
        if(resultCode == 1){
            return new Result<>(1,"创建课程成功");
        }
        return new Result<>(2,"添加课程失败");
    }
}
