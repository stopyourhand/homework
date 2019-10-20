package com.csto.homework.service.course;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 处理上传课程文件的服务层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
public interface CourseFileService {

    /**
     * 陈兆东
     * 根据课程编号获取课程的资源信息的统计
     * @param courseInfoId
     * @param courseFileType
     * @return
     */
    int getCourseResourcesNumber(int courseInfoId,int courseFileType);



    /**
     * 教师上传文件
     * @param courseInfoId 课程id
     * @param courseFile 上传文件
     * @param courseFileType 上传类型
     * @return 是否上传成功
     */
    int uploadFile(int courseInfoId, MultipartFile courseFile, int courseFileType) throws IOException;


}
