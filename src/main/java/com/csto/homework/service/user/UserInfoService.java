package com.csto.homework.service.user;

import com.csto.homework.entity.user.UserInfo;
import com.csto.homework.entity.user.UserLogin;
import org.springframework.stereotype.Service;

/**
 * 处理用户信息的服务层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Service
public interface UserInfoService {
    /**
     * 根据用户账号获取用户信息的对应的id
     */
    Integer getUserInfoId(String account);

    /**
     * 添加新的用户到数据库中
     * @param userLogin
     * @return
     */
    Integer insertUserInfo(UserInfo userInfo);

}
