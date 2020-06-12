package com.cn.fastdfs.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class ZipUtil {

    /**
     * 缓冲大小
     */
    private static int BUFFERSIZE = 2 << 10; //2048

    /**
     * zip压缩
     * path 压缩文件绝对路径
     * filename 压缩到指定目录并起名 "xxx.zip"
     */
    public static void zip(String[] paths, String fileName){

        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(fileName));
            for(String filePath : paths) {
                //递归压缩文件
                File file = new File(filePath);
                String relativePath = file.getName();
                if(file.isDirectory()){
                    relativePath += File.separator;
                }
                zipFile(file, relativePath, zos);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void zipFile(File file, String relativePath, ZipOutputStream zos) {
        InputStream is = null;
        try {
            if(!file.isDirectory()) {
                ZipEntry zp = new ZipEntry(relativePath);
                zos.putNextEntry(zp);
                is = new FileInputStream(file);
                byte[] buffer = new byte[BUFFERSIZE];
                int len = 0;
                while ((len = is.read(buffer))!= -1) {
                    zos.write(buffer, 0, len);
                }
                zos.flush();
                zos.closeEntry();
            } else {
                String tempPath = null;
                for(File f: file.listFiles()) {
                    tempPath = relativePath + f.getName();
                    if(f.isDirectory()) {
                        tempPath += File.separator;
                    }
                    zipFile(f, tempPath, zos);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解压 .zip文件
     * @param fileName 解压文件的名称(绝对路径)
     * @param path 解压到指定路径
     */
    public static void unzip(String fileName, String path){
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            ZipFile zf = new ZipFile(new File(fileName));
            Enumeration en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry zn = (ZipEntry) en.nextElement();
                if (!zn.isDirectory()){
                    is = zf.getInputStream(zn);
                    File f = new File(path + zn.getName());
                    File file = f.getParentFile();
                    file.mkdirs();
                    fos = new FileOutputStream(path + zn.getName());
                    int len = 0;
                    byte[] buffer = new byte[BUFFERSIZE];
                    while ((len = is.read(buffer)) != -1){
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
            }
            zf.close();
        }
        catch (ZipException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(is != null){
                    is.close();
                }
                if(fos != null){
                    fos.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 解压tar.gz文件到指定目录
     * sourcePaths  tar.gz 文件绝对路径
     * targetPath  要解压到的目录(没有则创建)
     */
    public static void unTar(String sourcePaths,String targetPath){

        try {
            // tar.gz 文件绝对路径
            String sourcePath = sourcePaths;
            // 要解压到的目录(没有则创建)
            String extractPath = targetPath;
            File sourceFile = new File(sourcePath);
            if(!sourceFile.exists()){
                return;
            }

            TarArchiveInputStream fin = new TarArchiveInputStream(new GzipCompressorInputStream(new FileInputStream(sourceFile)));

            File extraceFolder = new File(extractPath);
            TarArchiveEntry entry;


            while ((entry = fin.getNextTarEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }
                File curfile = new File(extraceFolder, entry.getName());
                File parent = curfile.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();//解压文件目录不存在则创建
                }
                // 将文件写出到解压的目录
               FileOutputStream out = new FileOutputStream(curfile);
                IOUtils.copy(fin, out);
                if(out != null){
                    out.close();
                }
            }

            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行shell脚本
     */
    public static void  installShell(String shellPath){

        try {
            String  bashCommand = "bash "+shellPath;
            Runtime runtime = Runtime.getRuntime();
            Process pro = runtime.exec(bashCommand);
            try {
                int status = pro.waitFor();
                if (status != 0){
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringBuffer strbr = new StringBuffer();
            String line;
            while ((line = br.readLine())!= null){
                strbr.append(line).append("\n");
            }
            br.close();
        }
        catch (IOException ec){
            ec.printStackTrace();
        }
    }

}