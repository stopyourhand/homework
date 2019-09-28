package com.csto.homework.controller.user;

import com.csto.homework.service.user.UserLoginService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;

    @GetMapping(value = "/getUserPassword")
    public Map<String, String> getUserPassword(@RequestParam(value = "loginId") int loginId){
        Map<String,String> resultMap = new HashMap<>(16);
        String passWord = userLoginService.getUserPassword(loginId);
        resultMap.put("password",passWord);
        return resultMap;
    }
}
