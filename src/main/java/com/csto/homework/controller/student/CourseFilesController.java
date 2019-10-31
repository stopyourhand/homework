package com.csto.homework.controller.student;

import com.csto.homework.dto.Result;
import com.csto.homework.entity.course.CourseFile;
import com.csto.homework.service.course.CourseFileService;
import com.csto.homework.service.course.CourseFolderService;
import com.csto.homework.service.course.CourseInfoService;
import com.csto.homework.util.DownloadUtil;
import com.csto.homework.util.SpringUtil;
import com.csto.homework.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Author czd
 * @Date:createed in 2019/10/20
 * @Version: V1.0
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/student/courseFile")
public class CourseFilesController {


    @Autowired
    private CourseFileService courseFileService;

    /**
     * 查询对应课程的学期授课资源和学期作业文档是否有内容
     *
     * @param courseInfoId
     * @return
     */
    @GetMapping(value = "/read")
    public Map readResources(@RequestParam("courseInfoId") int courseInfoId) {
        //用来保存返回给前端的数据的hashMap
        Map resultMap = new LinkedHashMap(16);

        //判断参数传输是否有误
        if (courseInfoId < 0) {
            resultMap.put("code", 400);
            resultMap.put("msg", "参数传输错误");
            return resultMap;
        }

        //上传文件类型1:教学文档 2:教学案例 3:教学资源 4:其他(授课资源)
        // 5:实验报告模板 6:平时作业模板 7:其他(学期作业文档)
        int courseFileType = 1;
        //从数据库中获取教学文档资源数量
        int teachingDocument = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("teachingDocument", teachingDocument);

        courseFileType = 2;
        //从数据库中获取教学案例资源数量
        int teachingCase = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("teachingCase", teachingCase);

        courseFileType = 3;
        //从数据库中获取教学资源数量
        int teachingResources = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("teachingResources", teachingResources);

        courseFileType = 4;
        //从数据库中获取其他(授课资源)数量
        int otherTeachingResources = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("otherTeachingResources", otherTeachingResources);

        courseFileType = 5;
        //从数据库中获取实验报告模板资源数量
        int experimentalReportTemplate = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("experimentalReportTemplate", experimentalReportTemplate);

        courseFileType = 6;
        //从数据库中获取平时作业模板资源数量
        int dailyWorkTemplate = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("dailyWorkTemplate", dailyWorkTemplate);

        courseFileType = 7;
        //从数据库中获取平时其他(学期作业文档)
        int otherTermAssignments = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("otherTermAssignments", otherTermAssignments);

//        resultMap.put("code", 200);
////        resultMap.put("msg", "查询成功");

        return resultMap;
    }


    /**
     * 跳转到指定课程的指定资源(教学文档 教学案例 教学资源 其他(授课资源)
     * 实验报告模板 平时作业模板 其他(学期作业文档))的下载目录页面
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    @GetMapping(value = "/to")
    public Result toResources(@RequestParam("courseInfoId") int courseInfoId,
                              @RequestParam("courseFileType") int courseFileType) {
        if (courseInfoId < 0){
            return new Result<>(400,"参数传输错误");
        }
        if (courseFileType < 0){
            return new Result<>(400,"参数传输错误");
        }

        List<Map> list = courseFileService.listCourseResourcesName(courseInfoId, courseFileType);
        return new Result<>(200,"查询成功",list);

    }

    /**
     * 下载指定文件
     *
     * @param response
     * @param courseFileId
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping(value = "/download", produces = "application/json;charset=UTF-8")
    public Result downloadResources(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam("idList") List<String> idList) throws UnsupportedEncodingException {
        if (idList.size() <= 0) {
            return new Result<>(400, "参数传输错误");
        }

        //获取资源下载路径
        String realPath = "D://test";//request.getServletContext().getRealPath("/");

        Result result = DownloadUtil.downloadFile(response,idList,realPath,courseFileService);

        return result;
    }
}
