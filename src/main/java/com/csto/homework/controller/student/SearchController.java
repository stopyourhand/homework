package com.csto.homework.controller.student;

import com.csto.homework.dto.Result;
import com.csto.homework.service.course.CourseInfoService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 学生管理搜索资源相关操作
 * @Author czd
 * @Date:createed in 2019/10/19
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/student")
public class SearchController {

    @Autowired
    private CourseInfoService courseInfoService;

    /**
     * 搜索资源操作
     * @return
     */
    @GetMapping(value = "/search")
    public Result searchResources(@RequestParam(value = "teacherName",required = false) String teacherName,
                                  @RequestParam(value = "courseName",required = false) String courseName){
        //对空字符串进行null赋值
        if ("".equals(teacherName)){
            teacherName = null;
        }
        if ("".equals(courseName)){
            courseName = null;
        }
        List<Map<String,String>> mapList = courseInfoService.listCourseBySearch(courseName,teacherName);


        return new Result<>(200,"查询成功",mapList);
    }
}
