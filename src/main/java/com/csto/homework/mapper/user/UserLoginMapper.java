package com.csto.homework.mapper.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 处理用户登录的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface UserLoginMapper {
    /**
     * 根据学生或者教工id获取密码
     * @param loginId
     * @return
     */
    String getUserPassword(int loginId);


}
