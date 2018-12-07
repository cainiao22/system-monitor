package com.qdingnet.bigdata.config;

import com.qdingnet.bigdata.config.factory.YamlPropertyLoaderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * @author yanpf
 * @date 2018/12/6 16:08
 * @description
 */
public class CustomizeConfiguration {

    @Bean
    public PropertySourceFactory propertySourceFactory(){
        return new YamlPropertyLoaderFactory();
    }
}
