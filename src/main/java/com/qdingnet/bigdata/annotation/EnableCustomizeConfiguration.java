package com.qdingnet.bigdata.annotation;

import com.qdingnet.bigdata.config.CustomizeConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author yanpf
 * @date 2018/12/6 16:09
 * @description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(CustomizeConfiguration.class)
public @interface EnableCustomizeConfiguration {
}
