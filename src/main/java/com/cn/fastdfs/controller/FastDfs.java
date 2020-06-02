package com.cn.fastdfs.controller;

import com.cn.fastdfs.conf.FastDFSClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/dfs")
public class FastDfs {

    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper;

    /**
     * 上传图片
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String path = fastDFSClientWrapper.upload(file);
        return path;
    }

    /**
     * 上传图片 base64
     * @param imgBase64
     * @return
     */
    @PostMapping("/upload/base64")
    public String upload(@RequestBody String imgBase64) throws IOException {
        BASE64Decoder decoder=new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(imgBase64);
        for (int i = 0; i < bytes.length; ++i) {
            if(bytes[i] < 0){
                bytes[i] += 256;
            }
        }
        return fastDFSClientWrapper.uploadBase64(bytes,"jpg");
    }


    /**
     * 删除文件  按全路径
     * @param path
     */
    @GetMapping("/delete")
    public Object delete(String path){
        fastDFSClientWrapper.delete(path);
        return "删除成功";

    }


    /**
     * 下载图片
     */
    @GetMapping("/download")
    public void download(String fileUrl, HttpServletResponse response) throws IOException {
        byte[] bytes = fastDFSClientWrapper.download(fileUrl);
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(System.currentTimeMillis()+".jpg", "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
