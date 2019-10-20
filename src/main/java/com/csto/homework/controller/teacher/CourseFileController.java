package com.csto.homework.controller.teacher;

import com.csto.homework.dto.Result;
import com.csto.homework.service.course.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 教师对课程文件的操作
 * @author fjw
 */
@RestController
@RequestMapping(value = "/CourseFile")
public class CourseFileController {
    @Autowired
    CourseFileService courseFileService;

    /**
     * 教师上传文件
     * @param courseInfoId 课程id
     * @param courseFile 上传文件
     * @param courseFileType 上传类型
     * @return 是否上传成功
     */
    @CrossOrigin
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam("courseInfoId")int courseInfoId,
                             @RequestParam("courseFile") MultipartFile courseFile,
                             @RequestParam("courseFileType")int courseFileType){
        System.out.println("上传文件");
        try{
            int resultCode = courseFileService.uploadFile(courseInfoId,courseFile,courseFileType);
            if(resultCode == 1)
                return new Result(1,"文件上传成功");
        }catch (Exception e){
            System.out.println(e);
            return new Result(2,"文件上传出现异常");
        }
        return new Result(3,"文件插入数据库出现异常");
    }
}
