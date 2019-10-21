package com.csto.homework.controller.student;
import com.csto.homework.dto.Result;
import com.csto.homework.service.course.CourseFileService;
import com.csto.homework.service.course.CourseFolderService;
import com.csto.homework.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author czd
 * @Date:createed in 2019/10/20
 * @Version: V1.0
 */
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
     * @param courseInfoId
     * @param courseClass
     * @return
     */
    @GetMapping(value = "/read")
    public Result listFolderName(@RequestParam("courseInfoId") int courseInfoId,
                                 @RequestParam("courseClass") String courseClass) {
        if (courseInfoId <= 0) {
            return new Result<>(400, "参数传输错误");
        }
        if (courseClass == null || "".equals(courseClass)) {
            return new Result<>(400, "参数传输错误");
        }

        List<Map> list = courseFolderService.listFolderName(courseInfoId, courseClass);
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
    public Result uploadFile(MultipartFile file, HttpServletRequest request) {


        //获取上传路径
        String savePath = request.getServletContext().getRealPath("/") + "resources\\images\\";
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());

        //设置上传之后文件的名称
        String fileName = new Date().getTime() + "." + suffix;

        //上传文件
        Result result = UploadUtil.uploadFile(file, savePath, fileName);

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
