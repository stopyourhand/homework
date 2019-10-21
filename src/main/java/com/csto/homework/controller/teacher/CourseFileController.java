package com.csto.homework.controller.teacher;

import com.csto.homework.dto.Result;
import com.csto.homework.service.course.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    /**
     * 删除课程内的文档信息
     * @param idList 文档编号
     * @return 删除行数
     */
    @DeleteMapping("/deleteDocument")
    public Result deleteDocument(@RequestParam(value = "idList") List<Integer> idList){
        //获取要删除的文档的id列表
        int length = idList.size();
        if (length <= 0){
            return new Result<>(400,"参数参数错误");
        }

        for (int index = 0; index < length; index++){
            int fileId = idList.get(index);

            //删除指定id的文档
            int result = courseFileService.deleteCourseFileById(fileId);
            if (result <= 0){
                return new Result<>(500,"服务器删除数据错误");
            }
        }

        return new Result<>(200,"删除成功");
    }

    /**
     * 教师修改教学文档信息
     * @param courseFileId 文档id
     * @param courseFileName 修改的文档名称
     * @return
     */
    @PutMapping("/updateCourseFile")
    public Result updateCourseFile(@RequestParam("courseFileId")int courseFileId,
                                   @RequestParam("courseFileName")String courseFileName){
        int resultCode = courseFileService.updateCourseFileName(courseFileId,courseFileName);
        if(resultCode == 1)
            return new Result(1,"修改文档名称成功");
        return new Result(2,"修改文档名称发生错误");
    }

    /**
     * 教师下载学生课程作业
     * @param userInfoId 教师id
     * @param courseFolderName 文件夹名称
     * @param courseClass 班级名称
     * @return 下载的文件个数
     */
    @GetMapping("/downloadHomewordFile")
    public Result downloadHomewordFile(HttpServletResponse response) throws IOException {
        courseFileService.downloadHomewordFile(response);
        return new Result(1,"下载成功");
    }
}
