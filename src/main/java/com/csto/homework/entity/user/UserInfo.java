package com.csto.homework.entity.user;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 用户信息数据库表的实体类
 * @Author czd
 * @Date:createed in 2019/9/28
 * @Version: V1.0
 */
@Data
@Component
public class UserInfo {
    /** 用户信息主键 */
    private Integer userInfoId;
    /** 用户学号或者教工号码 */
    private String userAccount;
    /** 用户真实姓名 */
    private String userRealName;
    /** 用户所属学院名称 */
    private String userCollege;
}
