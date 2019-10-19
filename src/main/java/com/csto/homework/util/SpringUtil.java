package com.csto.homework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @Author czd
 * @Date:createed in 2018/10/18
 * @Version: V1.0
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Logger logger = LoggerFactory.getLogger(SpringUtil.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //创建一个新的ApplicationContext对象
        if (SpringUtil.applicationContext == null){
           SpringUtil.applicationContext = applicationContext;
        }

        logger.info("ApplicationContext配置成功,applicationContext对象："+SpringUtil.applicationContext);
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> tClass){
        return getApplicationContext().getBean(tClass);

    }

    public static <T> T getBean(String name,Class<T> tClass){
        return getApplicationContext().getBean(name,tClass);
    }
}
