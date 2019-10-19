package com.csto.homework.controller.user;

import com.csto.homework.entity.user.UserInfo;
import com.csto.homework.service.user.UserInfoService;
import com.csto.homework.service.user.UserLoginService;
import com.csto.homework.util.InterfaceAnalysisUtil;
import com.csto.homework.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户操作相关的业务
 *
 * @Author czd
 * @Date:createed in 2018/10/19
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/homework")
public class UserController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserInfoService userInfoService;
    /**
     * 登录功能
     *
     * @param account
     * @param passWord
     * @return
     */
    @PostMapping(value = "/login")
    public Map login(@RequestParam(value = "account") String account,
                     @RequestParam(value = "passWord") String passWord) {

        //保存返回给前端信息的hashMap
        Map resultMap = new HashMap<>(16);

        //判断输入的账号是否有效
        if (account == null || "".equals(account)) {
            resultMap.put("judge", false);
            resultMap.put("msg", "账号输入有误，请重新输入账号!");
            return resultMap;
        }

        //判断输入的密码是否为空
        if (passWord == null || "".equals(passWord)) {
            resultMap.put("judge", false);
            resultMap.put("msg", "密码不能为空，请重新输入密码!");
            return resultMap;
        }

        //从数据库中获取用户输入账号对应的密码
        String userPassWord = userLoginService.getUserPassword(account);
        //判断从数据库中取出来的密码是否有效
        if (userPassWord == null || "".equals(userPassWord)) {
            //使用学校结口的工具类
            InterfaceAnalysisUtil interfaceAnalysisUtil = new InterfaceAnalysisUtil();
            //调用学校接口进行登录
            String accounts = String.valueOf(account);
            resultMap = interfaceAnalysisUtil.analysis(accounts, passWord);
            //添加学生账号到数据库

            //判断学生或教师是否登录成功0代表失败，1代表成功
            int flag = (int)resultMap.get("flag");
            if (flag == 1){
                //获取用户信息的对象
                UserInfo userInfo = (UserInfo)SpringUtil.getBean("UserInfo");
                //设置用户账号
                userInfo.setUserAccount(String.valueOf(account));

                //获取用户真实姓名并且设置用户名称
                String realName = (String)resultMap.get("userrealname");
                userInfo.setUserRealName(realName);
                //获取用户所在学院名称并且设置学院名称
                String college = (String)resultMap.get("userdwmc");
                userInfo.setUserCollege(college);

                int resultInfo = userInfoService.insertUserInfo(userInfo);
                if (resultInfo <= 0){
                    resultMap.put("judge", false);
                    resultMap.put("msg", "数据库插入信息错误!");
                }

            }

            return resultMap;
        }else if (!userPassWord.equals(passWord)){
            resultMap.put("judge", false);
            resultMap.put("msg", "密码输入有误!");
            return resultMap;
        }

        //判断用户密码是否正确，依次来判断是否登录成功
        if (userPassWord.equals(passWord)) {
            resultMap.put("judge", true);
            resultMap.put("msg", "登录成功!");
            return resultMap;
        }

        return resultMap;

    }
}
