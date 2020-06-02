package com.cn.fastdfs.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadUtil {

    public static Object upload(MultipartFile file){

        if(file.isEmpty()){
            return "请选择文件";
        }

        String filename = file.getOriginalFilename();
        String path="/home/upload/";
        File file1=new File(path+filename);
        if(file1.exists()){
            return "该文件已经存在";
        }

        File dest = new File(path + filename);

        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败@@@@"+e.getMessage();
        }
    }


    /**
     * 下载文件  根据名称下载
     * @param fileName
     * @param response
     * @return
     */
    public static HttpServletResponse download(String fileName, HttpServletResponse response){

        try {
            //在指定目录下载文件
            String paths="/home/upload/";
            File file = new File(paths+fileName);
            String filename = file.getName();
            InputStream fis = new BufferedInputStream(new FileInputStream(paths+fileName));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            try {
                response.reset();
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"),"ISO-8859-1"));
                response.addHeader("Content-Length", "" + file.length());
                response.setContentType("application/octet-stream");
            } catch (Exception e) {
                e.printStackTrace();
            }
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 下载文件  根据绝对路径
     * @param path
     * @param response
     * @return
     */
    public static HttpServletResponse downloadPath(String path, HttpServletResponse response){

        try {
            //在指定目录下载文件
            String paths="/home/upload/";
            File file = new File(path);
            String filename = file.getName();
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            try {
                response.reset();
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"),"ISO-8859-1"));
                response.addHeader("Content-Length", "" + file.length());
                response.setContentType("application/octet-stream");
            } catch (Exception e) {
                e.printStackTrace();
            }
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }




}
