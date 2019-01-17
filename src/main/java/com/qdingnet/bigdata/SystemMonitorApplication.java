package com.qdingnet.bigdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan(basePackages = "com.qdingnet.bigdata.mapper")
@SpringBootApplication
public class SystemMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemMonitorApplication.class, args);
	}
}
