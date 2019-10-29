package com.csto.homework.service.user;

import com.csto.homework.entity.user.UserLogin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    String getUserPassword(String account);

    /**
     * 根据用户账号获取登录的用户类型 userType 1:教师 2:学生
     * @param account
     * @return
     */
    Map<String,Integer> getUserTypeAndId(String account);

    /**
     * 添加新的用户到数据库中
     * @param userLogin
     * @return
     */
    int insertUserLogin(UserLogin userLogin);

}
