package com.csto.homework.service.user;

import org.springframework.stereotype.Service;

/**
 * 处理用户登录的服务层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Service
public interface UserLoginService {
    /**
     * 根据学生或者教工id获取密码
     * @param loginId
     * @return
     */
    String getUserPassword(int loginId);

}
