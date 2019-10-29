package com.csto.homework.mapper.course;

import com.csto.homework.entity.course.CourseFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 处理上传课程文件的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface CourseFileMapper {

    /**
     * 根据课程编号获取课程的资源信息的统计
     *
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    int getCourseResourcesNumber(@Param("courseInfoId") int courseInfoId,
                                 @Param("courseFileType") int courseFileType);


    /**
     * 根据课程编号获取课程的资源信息的名称和编号
     *
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    List<Map> listCourseResourcesName(@Param("courseInfoId") int courseInfoId,
                                      @Param("courseFileType") int courseFileType);

    /**
     * 根据文件编号获取服务器上传文件后的编码
     *
     * @param courseFileId
     * @return
     */
    String getFileCodeByCourseFileId(@Param("courseFileId") int courseFileId);

    //创建文件夹
    int createFolders(@Param("userInfoId") int userInfoId,
                      @Param("folderName") List<String> folderName,
                      @Param("folderCreateTime") String folderCreateTime);

    /**
     * 教师上传文件
     *
     * @param courseInfoId   课程id
     * @param courseFileName 上传文件名
     * @param courseFileCode 文件编码
     * @param fileUploadTime 上传时间
     * @param courseFileType 上传类型
     * @return
     */
    int insertCourseFile(@Param("courseInfoId") int courseInfoId,
                         @Param("courseFileName") String courseFileName,
                         @Param("courseFileCode") String courseFileCode,
                         @Param("fileUploadTime") String fileUploadTime,
                         @Param("courseFileType") int courseFileType);

    /**
     * 学生端上传文件
     * @param courseFile
     * @return
     */
    int insertCourseFileStudent(CourseFile courseFile);

    /**
     * 修改文档名称
     *
     * @param courseFileId
     * @param courseFileName
     * @return
     */
    int updateCourseFileName(@Param("courseFileId") int courseFileId,
                             @Param("courseFileName") String courseFileName);

    /**
     * 根据文档id删除指定文档
     *
     * @param courseFileId
     * @return
     */
    int deleteCourseFileById(int courseFileId);

    /**
     * 根据课程ID删除指定文件
     * @param courseInfoId
     * @return
     */
    int deleteCourse(@Param("courseInfoId") int courseInfoId);

    //    //下载教学文档的路径
//    String findCourseFileCodeById(int courseFileId);

    //根据文件夹id查询对应所有的文件下载路径
    List<Map<String,String>> findHomewordDownloadUrl(int courseFolderId);
}
