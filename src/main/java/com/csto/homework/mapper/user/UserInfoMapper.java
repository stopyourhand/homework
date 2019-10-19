package com.csto.homework.mapper.user;

import com.csto.homework.entity.user.UserInfo;
import com.csto.homework.entity.user.UserLogin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 处理用户信息的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 根据用户账号获取用户信息的对应的id
     */
    Integer getUserInfoId(String account);

    /**
     * 添加新的用户信息到数据库中
     * @param userLogin
     * @return
     */
    Integer insertUserInfo(UserInfo userInfo);

}
