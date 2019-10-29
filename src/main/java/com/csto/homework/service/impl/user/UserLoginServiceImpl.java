package com.csto.homework.service.impl.user;

import com.csto.homework.entity.user.UserLogin;
import com.csto.homework.mapper.user.UserLoginMapper;
import com.csto.homework.service.user.UserLoginService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Service("UserLoginService")
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginMapper userLoginMapper;

    /**
     * 根据学生或者教工id获取密码
     * @param loginId
     * @return
     */
    public String getUserPassword(String account){
        return userLoginMapper.getUserPassword(account);
    }

    /**
     * 根据用户账号获取登录的用户类型 userType 1:教师 2:学生
     * @param account
     * @return
     */
    public Map<String,Integer> getUserTypeAndId(String account){
        return userLoginMapper.getUserTypeAndId(account);
    }

    /**
     * 添加新的用户到数据库中
     * @param userLogin
     * @return
     */
    public int insertUserLogin(UserLogin userLogin){
        return userLoginMapper.insertUserLogin(userLogin);
    }
}
