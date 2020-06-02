package com.cn.fastdfs.controller;

import com.cn.fastdfs.utils.FastDFSClientWrapper;
import com.cn.fastdfs.utils.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/fastdfs")
public class FastDfs {

    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper;

    /**
     * 上传图片
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "图片上传", notes = "JSON")
    @PostMapping("/upload")
    public Object upload(@RequestParam MultipartFile file) throws IOException {
        String path = fastDFSClientWrapper.upload(file);
        return Response.ok(path);
    }

    /**
     * 上传图片 base64
     * @param imgBase64
     * @return
     */
    @ApiOperation(value = "图片上传 Base64", notes = "JSON")
    @PostMapping("/upload/base64")
    public Object upload(@RequestBody @ApiParam(value = "base64转码") String imgBase64) throws IOException {
        byte[] bytes = FastDfs.imgupload(imgBase64);
        String path = fastDFSClientWrapper.uploadBase64(bytes,"jpg");
        return Response.ok(path);
    }


    /**
     * 删除文件  按全路径
     * @param path  文件路径
     */
    @ApiOperation(value = "图片删除", notes = "JSON")
    @DeleteMapping("/delete")
    public Object delete(@RequestParam(value = "path")@ApiParam(value = "文件路径") String path){
        fastDFSClientWrapper.delete(path);
        return Response.ok("删除成功");

    }


    /**
     * 下载图片
     *  @param filePath  文件路径
     */
    @ApiOperation(value = "图片下载", notes = "JSON")
    @GetMapping("/download")
    public void download(@RequestParam(value = "filePath")@ApiParam(value = "文件路径") String filePath, HttpServletResponse response) throws IOException {
        byte[] bytes = fastDFSClientWrapper.download(filePath);
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(System.currentTimeMillis()+".jpg", "UTF-8"));
        FastDfs.downImg(bytes,response);
    }

    //fastdfs 上传图片
    private static byte[] imgupload(String imgBase64) throws IOException {
        BASE64Decoder decoder=new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(imgBase64);
        for (int i = 0; i < bytes.length; ++i) {
            if(bytes[i] < 0){
                bytes[i] += 256;
            }
        }
        return bytes;
    }

    //fastdfs下载图片
    private static void downImg(byte[] bytes,HttpServletResponse response) throws IOException {
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
