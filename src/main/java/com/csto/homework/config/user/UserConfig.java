package com.csto.homework.config.user;

import com.csto.homework.entity.user.UserInfo;
import com.csto.homework.entity.user.UserLogin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Configuration
public class UserConfig {

    @Bean("UserLogin")
    public UserLogin getUserLogin(){
        return new UserLogin();
    }

    @Bean("UserInfo")
    public UserInfo getUserInfo(){
        return new UserInfo();
    }
}
