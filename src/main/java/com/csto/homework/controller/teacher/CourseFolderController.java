package com.csto.homework.controller.teacher;

import com.csto.homework.dto.Result;
import com.csto.homework.service.course.CourseFileService;
import com.csto.homework.service.course.CourseFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 教师对课程文件夹的操作
 * @author fjw
 */
@RestController
@RequestMapping(value = "/CourseFolder")
public class CourseFolderController {
    @Autowired
    CourseFolderService courseFolderService;

    @PostMapping("/createFolder")
    public Result createFolder(@RequestParam("courseInfoId") int courseInfoId,
                               @RequestParam("folderName")String folderName,
                               @RequestParam("courseClass")List<String> courseClass){

        int resultCode = courseFolderService.createFolder(courseInfoId, folderName, courseClass);
        if(resultCode == courseClass.size()){
            return new Result(1,"创建文件夹成功");
        }
        return new Result(2,"创建文件夹异常");
    }

    /**
     * 根据课程的id查询所有的文件夹信息（实验报告以及班级）
     * @param courseInfoId 课程id
     * @return 实验报告和班级文件夹
     */
    @GetMapping("/findListFolder")
    public Result findListFolder(@RequestParam("courseInfoId")int courseInfoId){
        Map resultMap = courseFolderService.findListFolder(courseInfoId);
        List courseFolderNameList = (List)resultMap.get("courseFolderNameList");
        List allCourseClassList = (List)resultMap.get("allCourseClassList");
        //判断文件夹列表长度与班级列表长度是否相等（每一个文件夹对应一个班级列表）
        if(courseFolderNameList.size() == allCourseClassList.size())
            return new Result<>(1,"查询课程文件夹和其班级文件夹成功",resultMap);
        return new Result<>(2,"查询课程文件夹和其班级文件夹错误");
    }
}
