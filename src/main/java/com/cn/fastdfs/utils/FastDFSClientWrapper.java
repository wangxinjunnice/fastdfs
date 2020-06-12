package com.cn.fastdfs.utils;


import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FastDFSClientWrapper {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 批量上传图片  默认最大限制（10485760字节）  可在配置文件中修改上传文件大小
     * @param file
     * @return
     * @throws IOException
     */
    public List<String> batchUpload(MultipartFile[] file) throws IOException {

        List<String> list=new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            StorePath storePath= fastFileStorageClient.uploadFile(multipartFile.getInputStream(),multipartFile.getSize(),
                    FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
            list.add(storePath.getFullPath());
        }
       return list;
    }

    /**
     * 上传图片  默认最大限制（10485760字节）  可在配置文件中修改上传文件大小
     * @param file
     * @return
     * @throws IOException
     */
    public String upload(MultipartFile file) throws IOException {
        StorePath storePath= fastFileStorageClient.uploadFile(file.getInputStream(),file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return storePath.getFullPath();

    }


    /**
     * 上传图片 (base64)
     */
    public String uploadBase64(byte[] bytes, String format){
        StorePath storePath = fastFileStorageClient.uploadFile(new ByteArrayInputStream(bytes), bytes.length, format, null);
        return storePath.getFullPath();
    }

    /**
     * 删除图片
     */
    public void delete(String filePath){
        fastFileStorageClient.deleteFile(filePath);
    }

    /**
     * 图片下载
     * @param path
     * @return
     */
    public byte[] download(String path){
        StorePath storePath = StorePath.parseFromUrl(path);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), downloadByteArray);
        return bytes;
    }

}
