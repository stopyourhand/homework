package com.csto.homework.service.course;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 处理上传课程文件的服务层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
public interface CourseFileService {

    /**
     * 根据课程编号获取课程的资源信息的统计
     *
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    int getCourseResourcesNumber(int courseInfoId, int courseFileType);



    /**
     * 根据课程编号获取课程的资源信息的名称和编号
     *
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    List<Map> listCourseResourcesName(int courseInfoId, int courseFileType);

    /**
     * 根据文件编号获取服务器上传文件后的编码
     *
     * @param courseFileId
     * @return
     */
    String getFileCodeByCourseFileId(@Param("courseFileId") int courseFileId);

    /**
     * 教师为课程创建文件夹
     *
     * @param courseInfoId 课程id
     * @param folderName   文件夹名称列表
     * @return 插入行数
     */
    int createFolder(int courseInfoId, List<String> folderName);

    /**
     * 教师上传文件
     *
     * @param courseInfoId   课程id
     * @param courseFile     上传文件
     * @param courseFileType 上传类型
     * @return 是否上传成功
     */
    int uploadFile(int courseInfoId, MultipartFile courseFile, int courseFileType) throws IOException;

    /**
     * 根据文档id删除指定文档
     *
     * @param courseFileId
     * @return
     */
    int deleteCourseFileById(int courseFileId);

    //    /**
//     * 教师下载教学文档
//     * @param courseFileIdList  需要下载的文档id列表
//     * @return
//     */
//    int downloadFile(List<Integer> courseFileIdList);

    /**
     * 修改文档名称
     * @param courseFileId 文档id
     * @param courseFileName 修改文档名称
     * @return
     */
    int updateCourseFileName(int courseFileId, String courseFileName);

    /**
     * 教师下载学生课程作业
     * @param userInfoId 教师id
     * @param courseFolderName 文件夹名称
     * @param courseClass 班级名称, int userInfoId,
     *                              String courseFolderName, String courseClass
     * @return
     */
    boolean downloadHomewordFile(HttpServletResponse response,int courseFolderId, String courseFolderName)throws IOException ;

}
