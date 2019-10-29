package com.csto.homework.entity.course;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 教师下载课程作业页面数据
 */

@Data
@Component
public class TdownloadPageDto {
    //文件夹id
    private Integer courseFolderId;
    //课程名称
    private String courseName;
    //文件夹名称
    private String courseFolderName;
    //班级
    private String courseClass;
}
