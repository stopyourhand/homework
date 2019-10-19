package com.csto.homework.controller.course;

import com.csto.homework.entity.course.CourseInfo;
import com.csto.homework.service.course.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/CourseInfo")
public class CourseInfoController {

    @Autowired
    CourseInfoService courseInfoService;

    @PutMapping("/createCourse")
    public Map<String,String> createCourse(CourseInfo courseInfo){
        Map<String,String> resultMap = new HashMap(16);
        int resultCode = courseInfoService.createCourse(courseInfo);
        if(resultCode == 1){
//            resultMap.put()
        }
        return resultMap;
    }
}
