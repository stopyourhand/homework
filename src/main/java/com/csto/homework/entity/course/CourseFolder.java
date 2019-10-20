package com.csto.homework.entity.course;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 创建文件夹实体类
 */
@Data
@Component
public class CourseFolder {
//    course_folder_id	int unsign	主键
//    course_info_id	int unsign	课程编号
//    course_folder_name	varchar(50)	上传的文件名称
//    course_class	varchar(50)	课程班级
//    folder_create_time	datetime	文件夹创建时间
    private Integer courseFolderId;
    private Integer courseInfoId;
    private String courseFolderName;
    private String courseClass;
    private String folderCreateTime;

}
