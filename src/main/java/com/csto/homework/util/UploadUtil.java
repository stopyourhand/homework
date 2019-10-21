package com.csto.homework.util;

import com.csto.homework.dto.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
public class UploadUtil {
    public static Result uploadFile(MultipartFile file, String savePath,String fileName){

        if (file == null) {
            return new Result<>(400,"参数传输错误");
        }
        if (file.getSize() > 1024 * 1024 * 1024) {
            return new Result<>(400,"文件大小不能超过10M");
        }
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在改目录，则创建目录
            savePathFile.mkdirs();
        }
        try {
            //将文件保存至指定目录
            file.transferTo(new File(savePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(500,"保存文件异常");
        }
        return new Result<>(200,"图片上传成功");
    }
}
