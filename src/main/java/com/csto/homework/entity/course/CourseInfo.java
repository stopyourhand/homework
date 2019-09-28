package com.csto.homework.entity.course;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 课程信息数据库对应的表的实体类
 *
 * @Author czd
 * @Date:createed in 2019/9/28
 * @Version: V1.0
 */
@Data
@Component
public class CourseInfo {
    /** 课程信息主键 */
    private Integer courseInfoId;
    /** 教师账号 */
    private Integer userInfoId;
    /** 课程名称 */
    private String courseName;
}
