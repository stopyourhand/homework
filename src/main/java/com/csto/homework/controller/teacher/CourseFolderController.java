package com.csto.homework.controller.course;

import com.csto.homework.dto.Result;
import com.csto.homework.service.course.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 教师对课程文件夹的操作
 * @author fjw
 */
@RestController
@RequestMapping(value = "/CourseFolder")
public class CourseFolderController {
    @Autowired
    CourseFileService courseFileService;

    @PostMapping("/createFolder")
    public Result createFolder(@RequestParam("courseInfoId") int courseInfoId,
                               @RequestParam("folderName") List<String> folderName){
        for(String s:folderName)
            System.out.println(s);
        int resultCode = courseFileService.createFolder(courseInfoId,folderName);
        if(resultCode == folderName.size()){
            return new Result(1,"创建文件夹成功");
        }
        return new Result(2,"创建文件夹异常");
    }
}
