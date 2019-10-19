package com.csto.homework.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来解析学校登录接口并且返回JSON格式
 *
 * @Author czd
 * @Date:createed in 2019/9/29
 * @Version: V1.0
 */
public class InterfaceAnalysisUtil {

    public  Map analysis(String xh,String pwd){
        Map resultMap = null;
        PostMethod post = new PostMethod("http://jwxt.gduf.edu.cn/app.do");
        //封装请求参数
        NameValuePair param01 = new NameValuePair("method", "authUser");
        NameValuePair param02 = new NameValuePair("xh", xh);
        NameValuePair param03 = new NameValuePair("pwd", pwd);

        post.setRequestBody(new NameValuePair[] {param01,param02,param03});
        handleHttpMethod(post);//执行POST请求
        try {
            //获得POST方法，返获取回的byte数组结果
            String result = post.getResponseBodyAsString();
            System.out.println(result);
            resultMap = getResultMessage(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 执行请求方法并且返回状态码
     * @param method
     */
    private  Integer handleHttpMethod(HttpMethod method) {
        HttpClient client = new HttpClient();
        int statusCode = 0;

        try {
            //执行HTTP方法，并返回响应码
            statusCode = client.executeMethod(method);

            //根据返回的状态码，转换对应的状态枚举
            Response.Status status = Response.Status.fromStatusCode(statusCode);
            if(status == Response.Status.OK) {
                System.out.println("后台处理完毕！");
                return statusCode;
            }else if(status == Response.Status.NOT_FOUND) {
                System.out.println("请求路径错误！");
                return statusCode;
            }else if(status == Response.Status.METHOD_NOT_ALLOWED) {
                System.out.println("请求方法不被允许！");
                return statusCode;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return statusCode;
    }

    /**
     * 将解析结果存放到hashMap中
     * @param str
     * @return
     */
    private  Map getResultMessage(String str){
        Map resultMap = new HashMap<>();
        //{"flag":"1","userrealname":"name","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1Njk4NTAyMDgsImF1ZCI6IjE3MTU0MzExNyJ9.mM2Ie-eLomPLyxeFi23ulMo4gWnocZHeC0_B0_wlBDc","userdwmc":"互联网金融与信息工程学院","usertype":"2","msg":"登录成功"}
        if (str == null || "".equals(str)){
            return resultMap;
        }
        //得到"key":"value"形式的字符串元素的数组
        String [] keyValue = str.split(",");
        for (String strs:keyValue) {
            //将"key":"value"形式的键和值分别拿出来存放到hashMap中的key和value中
            String [] strArray = strs.split(":");
            String key = getRidOfTheQuotes(strArray[0]);
            String value = null;
            if (strArray[1].contains("\"")){
                value = getRidOfTheQuotes(strArray[1]);
            }

            resultMap.put(key,value);
        }
        return resultMap;
    }

    /**
     * 去除掉"{"和"\""着两个符号
     * @param str
     * @return
     */
    private String getRidOfTheQuotes(String str){
        String [] strArray = str.split("");
        int i = 0;
        String resultStr = "";
        int length = strArray.length;
        //去掉 "{" 符号
        while (!"\"".equals(strArray[i])){
            i++;
        }
        i++;
        for(int k = i; k < length; k++){
            if ("\"".equals(strArray[k])){
                break;
            }
            resultStr = resultStr + strArray[k];
        }

        return resultStr;

    }

}
