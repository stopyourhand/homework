package com.csto.homework.controller.student;

import com.csto.homework.dto.Result;
import com.csto.homework.service.course.CourseFileService;
import com.csto.homework.service.course.CourseInfoService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生管理搜索资源相关操作
 *
 * @Author czd
 * @Date:createed in 2019/10/19
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/student")
public class SearchController {

    @Autowired
    private CourseInfoService courseInfoService;

    @Autowired
    private CourseFileService courseFileService;

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

    /**
     * 查询对应课程的学期授课资源和学期作业文档是否有内容
     *
     * @param courseInfoId
     * @return
     */
    @GetMapping(value = "/read")
    public Map readResources(@RequestParam("courseInfoId") int courseInfoId) {
        //用来保存返回给前端的数据的hashMap
        Map resultMap = new HashMap<>(16);

        //判断参数传输是否有误
        if (courseInfoId < 0) {
            resultMap.put("code", 400);
            resultMap.put("msg", "参数传输错误");
            return resultMap;
        }

        //上传文件类型0:教学文档 1:教学案例 2:教学资源 3:其他(授课资源)
        // 4:实验报告模板 5:平时作业模板 6:其他(学期作业文档)
        int courseFileType = 0;
        //从数据库中获取教学文档资源数量
        int teachingDocument = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("teachingDocument", teachingDocument);

        courseFileType = 1;
        //从数据库中获取教学案例资源数量
        int teachingCase = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("teachingCase", teachingCase);

        courseFileType = 2;
        //从数据库中获取教学资源数量
        int teachingResources = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("teachingResources", teachingResources);

        courseFileType = 3;
        //从数据库中获取其他(授课资源)数量
        int otherTeachingResources = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("otherTeachingResources", otherTeachingResources);

        courseFileType = 4;
        //从数据库中获取实验报告模板资源数量
        int experimentalReportTemplate = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("experimentalReportTemplate", experimentalReportTemplate);

        courseFileType = 5;
        //从数据库中获取平时作业模板资源数量
        int dailyWorkTemplate = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("dailyWorkTemplate", dailyWorkTemplate);

        courseFileType = 6;
        //从数据库中获取平时作业模板资源数量
        int otherTermAssignments = courseFileService.getCourseResourcesNumber(courseInfoId, courseFileType);
        resultMap.put("otherTermAssignments", otherTermAssignments);

        resultMap.put("code", 200);
        resultMap.put("msg", "查询成功");

        return resultMap;
    }

    @GetMapping("/download")
    public Result downloadResources(HttpServletResponse response,
                                    @RequestParam("courseFileId") List<Integer> courseFileId) throws UnsupportedEncodingException {
        //获取资源所在目录
        File file = new File("D://test");
        //获取资源目录下所有的资源
        File[] files = file.listFiles();
        //获取资源的数量长度
        int length = files.length;

        for (int index = 0; index < length; index++) {
            //获取当前资源名称
            String fileName = files[index].getName();

            if (fileName != null) {
                String realPath = "D://test/";
                File newFile = new File(realPath, fileName);
                if (newFile.exists()) {
                    response.setHeader("content-type", "application/octet-stream");
                    // 下载文件能正常显示中文
                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");

                    byte[] buffer = new byte[1024];
                    FileInputStream fileInputStream = null;
                    BufferedInputStream bufferedInputStream = null;
                    try {
                        fileInputStream = new FileInputStream(newFile);
                        bufferedInputStream = new BufferedInputStream(fileInputStream);

                        OutputStream outputStream = response.getOutputStream();

                        int i = bufferedInputStream.read(buffer);

                        while (i != -1) {
                            outputStream.write(buffer);//, 0, 1
                            i = bufferedInputStream.read(buffer);
                        }

                    } catch (Exception e) {
                        return new Result(500, "文件下载失败");
                    } finally {
                        try {
                            if (bufferedInputStream != null) {
                                bufferedInputStream.close();
                            }

                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }

        }

        return new Result(200, "文件下载成功");
    }
}
