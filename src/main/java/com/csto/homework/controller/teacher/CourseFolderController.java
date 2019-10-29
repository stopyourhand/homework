package com.csto.homework.controller.teacher;

import com.csto.homework.dto.Result;
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
@RequestMapping(value = "/teacher/courseFolder")
public class CourseFolderController {
    @Autowired
    CourseFolderService courseFolderService;

    @PostMapping("/createFolder")
    public Result createFolder(@RequestParam("courseInfoId") int courseInfoId,
                               @RequestParam("folderName")String folderName,
                               @RequestParam("courseClass")List<String> courseClass){

        List<String> repeatCourseClassList = courseFolderService.createFolder(courseInfoId, folderName, courseClass);
        StringBuffer repeatCourseClassString = new StringBuffer();
        if(!repeatCourseClassList.isEmpty()){
            for(int i=0;i<repeatCourseClassList.size();i++){
                if(i!=repeatCourseClassList.size()-1)
                    repeatCourseClassString.append(repeatCourseClassList.get(i)+"，");
                else
                    repeatCourseClassString.append(repeatCourseClassList.get(i));
            }
            return new Result(201,"班级"+repeatCourseClassString+"已经在文件夹"+folderName+"中存在，故班级"+repeatCourseClassString+"创建失败!");
        }
        return new Result(200,"创建成功！");
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
            return new Result<>(200,"查询课程文件夹和其班级文件夹成功",resultMap);
        return new Result<>(400,"查询课程文件夹和其班级文件夹错误");
    }
}
