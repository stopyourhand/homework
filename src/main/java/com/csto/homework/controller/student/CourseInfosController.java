package com.csto.homework.controller.student;

import com.csto.homework.dto.Result;
import com.csto.homework.service.course.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 教师对增删课程的操作
 * @author fjw
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/student/courseInfo")
public class CourseInfosController {

    @Autowired
    CourseInfoService courseInfoService;
    /**
     * 搜索资源操作
     *
     * @return
     */
    @GetMapping(value = "/search")
    public Result searchResources(@RequestParam(value = "teacherName", required = false) String teacherName,
                                  @RequestParam(value = "courseName", required = false) String courseName) {
        //对空字符串进行null赋值
        if ("".equals(teacherName)) {
            teacherName = null;
        }
        if ("".equals(courseName)) {
            courseName = null;
        }
        List<Map<String, String>> mapList = courseInfoService.listCourseBySearch(courseName, teacherName);


        return new Result<>(200, "查询成功", mapList);
    }


}
