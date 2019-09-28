package com.csto.homework.entity.user;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 用户数据库登录表的实体类
 * @Author czd
 * @Date:createed in 2019/9/28
 * @Version: V1.0
 */
@Data
@Component
public class UserLogin {
    /** 用户登录主键 */
    private Integer loginId;
    /** 学号 */
    private String loginAccount;
    /** 登录密码 */
    private String loginPassword;
    /** 学生或教师类型1：教师，2：学生 */
    private Integer loginUserType;
    /** 用户信息表id */
    private Integer userInfoId;
}
