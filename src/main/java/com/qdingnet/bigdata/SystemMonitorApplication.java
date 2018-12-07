package com.qdingnet.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SystemMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemMonitorApplication.class, args);
	}
}
