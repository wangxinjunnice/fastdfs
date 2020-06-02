package com.cn.fastdfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;


@SpringBootApplication
public class FastdfsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastdfsApplication.class, args);
	}
	
	
	//该对象将会覆盖配置文件中的配置文件
	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory=new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.parse("500MB"));
		factory.setMaxRequestSize(DataSize.parse("500MB"));
		return 	factory.createMultipartConfig();
	}

}
