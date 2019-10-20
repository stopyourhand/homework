package com.csto.homework.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author fjw
 * 返回结果
 * @param <T>
 */

@Data
public class Result<T> {
    //状态码
    private Integer code;
    //返回提示信息
    private String msg;
    //返回数据
    private T data;

    public Result(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }
}
