package com.cn.fastdfs.controller;

import com.cn.fastdfs.utils.DownloadUtil;
import com.cn.fastdfs.utils.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/springboot")
public class springbootDownload {


    /**
     * 上传文件
     * @param file
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public Object upload(MultipartFile file){

        String message = (String) DownloadUtil.upload(file);
        return Response.ok(message);
    }


    /**
     * 下载文件  根据文件名称
     * @param fileName 文件名称
     */
    @ApiOperation(value = "文件下载 根据文件名称")
    @GetMapping("/download")
    public Object download(@RequestParam(value = "fileName")@ApiParam(value = "文件名称") String fileName, HttpServletResponse response){
        DownloadUtil.download(fileName,response);
        return Response.ok();
    }

    /**
     * 下载文件   根据绝对路径下载
     * @param path 绝对路径
     */
    @ApiOperation(value = "文件下载 根据绝对路径下载")
    @GetMapping("/downloadPath")
    public Object downloadPath(@RequestParam(value = "path")@ApiParam(value = "文件绝对路径") String path, HttpServletResponse response){
        DownloadUtil.downloadPath(path,response);
        return Response.ok();
    }


}
