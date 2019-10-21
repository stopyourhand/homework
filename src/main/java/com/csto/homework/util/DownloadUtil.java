package com.csto.homework.util;

import com.csto.homework.dto.Result;
import com.csto.homework.service.course.CourseFileService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
public class DownloadUtil {

    /**
     *
     * @param response
     * @param idList 下载文件的id列表
     * @param realPath 下载路径
     * @param courseFileService 文件操作服务层
     * @return
     */
    public static Result downloadFile(HttpServletResponse response, List<Integer> idList,
                                      String realPath, CourseFileService courseFileService) throws UnsupportedEncodingException {
        //获取资源的数量长度
        int length = idList.size();
        for (int index = 0; index < length; index++) {
            int courseFileId = idList.get(index);
            //获取当前资源名称
            String fileName = courseFileService.getFileCodeByCourseFileId(courseFileId);
            if (fileName != null) {
                File newFile = new File(realPath, fileName);
                if (newFile.exists()) {
                    // 下载文件能正常显示中文
                    response.setHeader("content-type", "application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");

                    //设置获取文件的输入流和缓冲流
                    byte[] buffer = new byte[1024];
                    FileInputStream fileInputStream = null;
                    BufferedInputStream bufferedInputStream = null;
                    try {
                        fileInputStream = new FileInputStream(newFile);
                        bufferedInputStream = new BufferedInputStream(fileInputStream);
                        //获取输出流
                        OutputStream outputStream = response.getOutputStream();
                        //将字节流以数字形式读取进来
                        int i = bufferedInputStream.read(buffer);
                        //将读取到的文件内容写入一个新的文件中
                        while (i != -1) {
                            outputStream.write(buffer);//, 0, 1
                            i = bufferedInputStream.read(buffer);
                        }

                    } catch (Exception e) {
                        return new Result(500, "文件下载失败");
                    } finally {
                        //关闭流
                        try {
                            if (bufferedInputStream != null) {
                                bufferedInputStream.close();
                            }

                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }

        }
        return new Result(200, "文件下载成功");

    }
}
