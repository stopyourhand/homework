package com.csto.homework.controller;

import com.csto.homework.entity.user.UserInfo;
import com.csto.homework.entity.user.UserLogin;
import com.csto.homework.service.user.UserInfoService;
import com.csto.homework.service.user.UserLoginService;
import com.csto.homework.util.InterfaceAnalysisUtil;
import com.csto.homework.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录注销相关的业务
 *
 * @Author czd
 * @Date:createed in 2018/10/19
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/user")
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
                     @RequestParam(value = "passWord") String passWord,
                     HttpServletRequest request) {

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
        HttpSession session = request.getSession();

        //从数据库中获取用户输入账号对应的密码
        String userPassWord = userLoginService.getUserPassword(account);
        //判断从数据库中取出来的密码是否有效
        if (userPassWord == null || "".equals(userPassWord)) {
            //使用学校结口的工具类
            InterfaceAnalysisUtil interfaceAnalysisUtil = new InterfaceAnalysisUtil();
            //调用学校接口进行登录
            resultMap = interfaceAnalysisUtil.analysis(account, passWord);
            //添加学生账号到数据库

            //判断学生或教师是否登录成功0代表失败，1代表成功
            String flag = (String)resultMap.get("flag");
            if ("1".equals(flag)){
                //获取用户信息的对象
                UserInfo userInfo = (UserInfo)SpringUtil.getBean("UserInfo");
                //设置用户账号
                userInfo.setUserAccount(account);

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

                //从Spring容器中获取用户登录对象
                UserLogin userLogin = (UserLogin) SpringUtil.getBean("UserLogin");
                //获取用户信息对应
                int userInfoId = userInfoService.getUserInfoId(account);
                userLogin.setUserInfoId(userInfoId);
                //获取用户登录类型1为教师，2为学生
                Integer userType = Integer.parseInt((String)resultMap.get("usertype"));
                userLogin.setLoginUserType(userType);
                //获取用户账号和密码
                userLogin.setLoginAccount(account);
                userLogin.setLoginPassword(passWord);

                int resultLogin = userLoginService.insertUserLogin(userLogin);
                if (resultLogin <= 0){
                    resultMap.put("judge", false);
                    resultMap.put("msg", "数据库插入信息错误!");
                }

                session.setAttribute("account",account);

                resultMap.put("userInfoId",account);
                resultMap.put("judge", true);
                resultMap.put("msg", "登录成功!");

            }else {
                resultMap.put("judge", false);
                resultMap.put("msg", "密码输入有误!");
                return resultMap;
            }

        }else if (!userPassWord.equals(passWord)){
            resultMap.put("judge", false);
            resultMap.put("msg", "密码输入有误!");
            return resultMap;
        }

        //判断用户密码是否正确，依次来判断是否登录成功
        if (passWord.equals(userPassWord)) {
            session.setAttribute("account",account);

            //根据用户账号获取对应的登录对象 userType 1:教师 2:学生
            Map<String,Integer> parameterMap = userLoginService.getUserTypeAndId(account);
            //获取用户类型和用户id
            Integer userType =  parameterMap.get("userType");
//            Integer userInfoId = parameterMap.get("Id").intValue();

            resultMap.put("userInfoId",account);
            resultMap.put("usertype",userType);
            resultMap.put("judge", true);
            resultMap.put("msg", "登录成功!");

            return resultMap;
        }

        return resultMap;
    }


    /**
     * 退出登录功能
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/exit")
    public Map exit(HttpServletRequest httpServletRequest) {
        //保存返回给前端信息的hashMap
        Map resultMap = new HashMap<>(16);

        //获取当前会话
        HttpSession session = httpServletRequest.getSession();

        //移除studentId这个session属性，相当于注销状态
        session.removeAttribute("account");
        //使整个客户端对应的Session失效，里面的所有东西都会被清空，同时也释放了资源。
//        session.invalidate();

        resultMap.put("judge", true);
        resultMap.put("msg","退出成功");
        return resultMap;
    }
}
