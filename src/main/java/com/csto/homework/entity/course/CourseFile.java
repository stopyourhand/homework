package com.csto.homework.entity.course;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.security.DenyAll;

/**
 * 文件上传数据库对应的表的实体类
 * @Author czd
 * @Date:createed in 2019/9/28
 * @Version: V1.0
 */
@Data
@Component
public class CourseFile {
    /** 上传文件的主键 */
    private Integer courseFileId;
    /** 课程编号 */
    private Integer courseInfoId;
    /** 上传文件的名称 */
    private String courseFileName;
    /** 上传的文件名称编码 */
    private String courseFileCode;
    /** 文件上传时间 */
    private String fileUploadTime;
    /** 是否删除文件，0：否 1：是，默认0 */
    private Integer deleteFile;
    /** 文件删除时间 */
    private String fileDeleteTime;
    /**
     * 上传文件类型 ->
     * 0:教学文档 1:教学案例 2:教学资源 3:其他(授课资源)
     * 4:实验报告模板 5:平时作业模板 6:其他(学期作业文档)
     */
    private Integer courseFileType;
}
