package com.csto.homework.mapper.course;

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

    //创建文件夹
    int createFolders(@Param("userInfoId") int userInfoId,
                      @Param("folderName") List<String> folderName,
                      @Param("folderCreateTime")String folderCreateTime);
}
