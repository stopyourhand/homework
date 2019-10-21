package com.csto.homework.service.impl.course;

import com.csto.homework.mapper.course.CourseFileMapper;
import com.csto.homework.service.course.CourseFileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
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
        String courseFileName = courseFile.getOriginalFilename();
        InputStream input = courseFile.getInputStream();
        OutputStream out = new FileOutputStream("f://"+courseFileName);
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
        String courseFileCode = Math.abs(courseFileName.hashCode())+"";
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
     * @param userInfoId 教师id
     * @param courseFolderName 文件夹名称
     * @param courseClass 班级名称, int userInfoId, String courseFolderName, String courseClass
     * @return 下载的文件个数
     */
    @Override
    public int downloadHomewordFile(HttpServletResponse response)throws IOException {
//        List<String> downloadUrlList = courseFileMapper.findHomewordDownloadUrl();
        List<Map<String,String>> downloadFile = new LinkedList<>();

        Map<String,String> map = new HashMap<>();
        map.put("homewordFileName","图片1.jpg");
        map.put("homewordFileCode","1.jpg");
        downloadFile.add(map);
        Map<String,String> map1 = new HashMap<>();
        map1.put("homewordFileName","图片2.jpg");
        map1.put("homewordFileCode","2.jpg");
        downloadFile.add(map1);

        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename=test.zip");
        response.setCharacterEncoding("utf-8");
        String path = "E:\\images/";
        String str = "";
        String rt = "/n";
        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
        for(Map<String,String> homewordFile:downloadFile) {
            System.out.println(homewordFile.get("homewordFileName")+"$"+homewordFile.get("homewordFileCode"));
            zos.putNextEntry(new ZipEntry(homewordFile.get("homewordFileName")));
            str += homewordFile.get("homewordFileName") + rt;
            File file = new File(path+(homewordFile.get("homewordFileCode")));
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int n = -1;
            while((n=fis.read(b))!=-1) {
                zos.write(b,0,n);
            }
            zos.flush();
            fis.close();
        }
        zos.setComment("download success:"+rt+str);
        zos.flush();
        zos.close();
        return 1;
    }
}
