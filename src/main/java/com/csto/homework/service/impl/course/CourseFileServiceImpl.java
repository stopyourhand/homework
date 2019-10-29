package com.csto.homework.service.impl.course;

import com.csto.homework.entity.course.CourseFile;
import com.csto.homework.mapper.course.CourseFileMapper;
import com.csto.homework.service.course.CourseFileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CourseFileServiceImpl implements CourseFileService {

    @Autowired
    CourseFileMapper courseFileMapper;


    /**
     * 根据课程编号获取课程的资源信息的统计
     *
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    @Override
    public int getCourseResourcesNumber(int courseInfoId, int courseFileType) {
        short s1 = 1;s1+=s1;
        return courseFileMapper.getCourseResourcesNumber(courseInfoId, courseFileType);
    }



    /**
     * 根据课程编号获取课程的资源信息的名称和编号
     *
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    @Override
    public List<Map> listCourseResourcesName(int courseInfoId, int courseFileType) {
        return courseFileMapper.listCourseResourcesName(courseInfoId, courseFileType);
    }

    /**
     * 根据文件编号获取服务器上传文件后的编码
     *
     * @param courseFileId
     * @return
     */
    @Override
    public String getFileCodeByCourseFileId(@Param("courseFileId") int courseFileId) {
        return courseFileMapper.getFileCodeByCourseFileId(courseFileId);
    }


    /**
     * 教师为课程创建文件夹
     *
     * @param courseInfoId 课程id
     * @param folderName   文件夹名称列表
     * @return 插入行数
     */
    @Override
    public int createFolder(int courseInfoId, List<String> folderName) {
        if (folderName.isEmpty()) {
            return 0;
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String folderCreateTime = simpleDateFormat.format(date);
        return courseFileMapper.createFolders(courseInfoId, folderName, folderCreateTime);
    }

    /**
     * 教师上传文件
     *
     * @param courseInfoId   课程id
     * @param courseFile     上传文件
     * @param courseFileType 上传类型
     * @return 是否上传成功
     */
    @Override
    public int uploadFile(int courseInfoId, MultipartFile courseFile, int courseFileType) throws IOException {
        //获取文件原名称
        String courseFileName = courseFile.getOriginalFilename();
        //截取文件后缀名
        String suffix = courseFileName.substring(courseFileName.lastIndexOf("."),courseFileName.length());
        //生成文件名唯一编码，防止文件名重复覆盖上一文件
        String Code = Math.abs(UUID.randomUUID().hashCode()) + "";
        String courseFileCode = Code + Math.abs(courseFileName.hashCode())+suffix;
        //获取tomcat路径，用于上传文件
        String path=System.getProperty("catalina.home") + "/resource/";
        File file = new File(path);
        //判断文件路径是否存在，不存在则创建文件路径
        if(!file.exists())
            file.mkdir();
        System.out.println("---------------------"+path);
        InputStream input = courseFile.getInputStream();
        OutputStream out = new FileOutputStream(path+courseFileCode);
        byte[] bs = new byte[1024];
        int len = -1;
        while ((len = input.read(bs))!=-1) {
            out.write(bs, 0, len);
        }
        out.close();
        input.close();
        System.out.println("上传成功");
        /**
         * @param courseInfoId 课程id
         * @param courseFileName 上传文件名
         * @param courseFileCode 文件编码
         * @param fileUploadTime 上传时间
         * @param courseFileType 上传类型
         * @return
         */
        System.out.println(courseFileCode);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileUploadTime = simpleDateFormat.format(date);
        return courseFileMapper.insertCourseFile(courseInfoId,courseFileName,courseFileCode,fileUploadTime,courseFileType);
    }

    //    /**
//     * 教师下载教学文档
//     * @param courseFileIdList  需要下载的文档id列表
//     * @return
//     */
//    @Override
//    public int downloadFile(List<Integer> courseFileIdList) {
//
//        return 0;
//    }

    /**
     * 根据文档id删除指定文档
     *
     * @param courseFileId
     * @return
     */
    public int deleteCourseFileById(int courseFileId){
        return courseFileMapper.deleteCourseFileById(courseFileId);
    }

    /**
     * 修改文档名称
     * @param courseFileId 文档id
     * @param courseFileName 修改文档名称
     * @return
     */
    @Override
    public int updateCourseFileName(int courseFileId, String courseFileName) {
        return courseFileMapper.updateCourseFileName(courseFileId,courseFileName);
    }


    /**
     * 教师下载学生课程作业
     * @return 下载的文件个数
     */
    @Override
    public boolean downloadHomewordFile(HttpServletResponse response, int courseFolderId, String courseFolderName)throws IOException {
        System.out.println("----------------------------------开始下载");
        List<Map<String,String>> downloadUrlMap = courseFileMapper.findHomewordDownloadUrl(courseFolderId);

        for(Map map:downloadUrlMap){
            System.out.println(map.get("courseFileName")+"-----------------------------------"+ map.get("courseFileCode"));
        }

        //response.setContentType("application/x-msdownload");
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="+courseFolderName+".zip");
        response.setCharacterEncoding("utf-8");

//        response.setHeader("content-type", "application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment;filename=1.zip");
//        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//        response.setCharacterEncoding("UTF-8");
        String path="C:\\Users\\ASUS\\AppData\\Local\\Temp\\tomcat.2351508898301915500.8080\\resource\\";//System.getProperty("catalina.home") + "/resource/";
        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
        for(Map<String,String> homewordFile:downloadUrlMap) {
            zos.putNextEntry(new ZipEntry(homewordFile.get("courseFileName")));
            File file = new File(path+(homewordFile.get("courseFileCode")));
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int n = -1;
            while((n=fis.read(b))!=-1) {
                zos.write(b,0,n);
            }
            zos.flush();
            fis.close();
        }
        zos.flush();
        zos.close();
        System.out.println("--------------------------下载完成");
        return true;
    }

    /**
     * 根据课程ID删除指定文件
     * @param courseInfoId
     * @return
     */
    public int deleteCourse(int courseInfoId){
        return courseFileMapper.deleteCourse(courseInfoId);
    }

    /**
     * 学生端上传文件
     * @param courseFile
     * @return
     */
    @Override
    public int insertCourseFileStudent(CourseFile courseFile){
        return courseFileMapper.insertCourseFileStudent(courseFile);
    }
}
