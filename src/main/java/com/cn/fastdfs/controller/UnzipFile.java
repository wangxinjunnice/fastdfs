package com.cn.fastdfs.controller;


import com.cn.fastdfs.utils.Response;
import com.cn.fastdfs.utils.ZipUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/unZipFile")
public class UnzipFile {


    @Value("${upgrade.sourcePath}")
    private String sourcePaths;//源文件绝对路径

    @Value("${upgrade.targetPath}")
    private String targetPath;//解压地址

    @Value("${upgrade.shellPath}")
    private String shellPath;//脚本绝对路径



    /**
     * 解压文件到指定位置  tar文件
     *
     */
    @ApiOperation(value = "解压tar包")
    @GetMapping("/unTar")
    public Object unTar(){

        try {
            ZipUtil.unTar(sourcePaths,targetPath);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("解压失败"+e.getMessage());
        }
        return Response.ok("解压成功");

    }


    /**
     * 压缩.zip文件
     */
    @ApiOperation(value = "压缩zip文件")
    @GetMapping(value = "/zip")
    public Object clears(@RequestParam(value = "paths")@ApiParam(value = "压缩文件绝对路径")String[] paths, String filename){

        try {
            ZipUtil.zip(paths,filename);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("压缩失败@@@@@@@@@"+e.getMessage());
        }

        return Response.ok("压缩成功");

    }


    /**
     * 解压.zip文件
     */
    @ApiOperation(value = "解压zip文件")
    @GetMapping("/unzip")
    public Object unzip(@RequestParam(value = "filename")@ApiParam(value = "解压文件的名称(绝对路径)")String fileName,
                        @RequestParam(value = "path")@ApiParam(value = "解压到指定路径")String path){

        try {
            ZipUtil.unzip(fileName,path);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("解压失败"+e.getMessage());
        }
        return Response.ok("解压成功");
    }


    /**
     * 执行shell脚本
     */
    @ApiOperation(value = "执行shell脚本")
    @GetMapping("/installShell")
    public Object installShell() {

        try {
            ZipUtil.installShell(shellPath);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("执行失败");
        }
        return Response.ok("执行成功");
    }

}
