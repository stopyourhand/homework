package com.csto.homework.service.impl.user;

import com.csto.homework.entity.user.UserInfo;
import com.csto.homework.entity.user.UserLogin;
import com.csto.homework.mapper.user.UserInfoMapper;
import com.csto.homework.mapper.user.UserLoginMapper;
import com.csto.homework.service.user.UserInfoService;
import com.csto.homework.service.user.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 添加新的用户信息到数据库中
     * @param userLogin
     * @return
     */
    public int insertUserInfo(UserInfo userInfo){
        return userInfoMapper.insertUserInfo(userInfo);
    }
}
