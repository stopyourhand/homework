package com.csto.homework.controller.student;
import com.csto.homework.dto.Result;
import com.csto.homework.entity.course.CourseFile;
import com.csto.homework.service.course.CourseFileService;
import com.csto.homework.service.course.CourseFolderService;
import com.csto.homework.util.SpringUtil;
import com.csto.homework.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.Util;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author czd
 * @Date:createed in 2019/10/20
 * @Version: V1.0
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/student/courseFolder")
public class CourseFoldersController {

    @Autowired
    private CourseFolderService courseFolderService;

    @Autowired
    private CourseFileService courseFileService;


    /**
     * 查看课程和班级对应的文件夹的列表
     *
     * @param courseName
     * @param courseClass
     * @return
     */
    @GetMapping(value = "/read")
    public Result listFolderName(@RequestParam("courseName") String courseName,
                                 @RequestParam("courseClass") String courseClass) {
        if (courseName == null || "".equals(courseName)) {
            return new Result<>(400, "参数传输错误");
        }
        if (courseClass == null || "".equals(courseClass)) {
            return new Result<>(400, "参数传输错误");
        }

        List<Map> list = courseFolderService.listFolderName(courseName, courseClass);
        return new Result(200, "查询成功", list);
    }

    /**
     * 文件上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping(value = "/upload")
    public Result uploadFile(MultipartFile file, HttpServletRequest request,
                             @RequestParam(value = "courseFolderId") String courseFolderId,
                             @RequestParam(value = "courseInfoId") String courseInfoId) {

        if (courseFolderId == null || "".equals(courseFolderId)){
            return new Result(500,"参数传输错误");
        }
        if (courseInfoId == null || "".equals(courseInfoId)){
            return new Result(500,"参数传输错误");
        }

        //获取上传路径
        String savePath = "D://test/";//System.getProperty("catalina.home");//request.getServletContext().getRealPath("/") + "resources\\images\\";
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        //保存到数据库中的上传的文件的名称
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));

        //设置上传之后文件的名称
        String Code = Math.abs(UUID.randomUUID().hashCode()) + "";
        String courseFileCode = Code + Math.abs(fileName.hashCode())+"."+suffix;

        //上传文件
        Result result = UploadUtil.uploadFile(file, savePath, courseFileCode);

        if (result.getCode() == 200){
            CourseFile courseFile = (CourseFile) SpringUtil.getBean("CourseFile");

            courseFile.setCourseInfoId(Integer.parseInt(courseInfoId));
            courseFile.setCourseFileName(fileName);
            courseFile.setCourseFileCode(courseFileCode);

            //获取当前文件上传时间
            Date date = new Date();
            //设置时间显示格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取当前设置好的时间
            String nowDate = simpleDateFormat.format(date);
            courseFile.setFileUploadTime(nowDate);

            //学生端上传文件默认为0
            courseFile.setCourseFileType(0);

            courseFile.setCourseFolderId(Integer.parseInt(courseFolderId));

            //将数据插入数据库
            int results = courseFileService.insertCourseFileStudent(courseFile);
            if (results <= 0){
                return new Result(500,"文件插入数据库失败");
            }

        }

        //保存文件信息到数据库,包括文件夹id

        return result;
    }

    /**
     * 文件替换
     *
     * @param file
     * @param request
     * @param courseFileId
     * @return
     */
    @PostMapping(value = "/replace")
    public Result replaceFile(MultipartFile file,
                              HttpServletRequest request,
                              @RequestParam("courseFileId") int courseFileId) {

        //获取上传路径
        String savePath = request.getServletContext().getRealPath("/") + "resources\\images\\";
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());

        //设置上传之后文件的名称，相当于替换
        String fileName = courseFileService.getFileCodeByCourseFileId(courseFileId) + suffix;

        //上传文件
        Result result = UploadUtil.uploadFile(file, savePath, fileName);

        return result;
    }
}
