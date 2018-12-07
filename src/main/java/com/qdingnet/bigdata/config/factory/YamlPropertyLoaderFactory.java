package com.qdingnet.bigdata.config.factory;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * @author yanpf
 * @date 2018/12/6 15:23
 * @description
 */
public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        if(resource.getResource().getFilename().endsWith("yaml") || resource.getResource().getFilename().endsWith("yml")){
            return new YamlPropertySourceLoader().load(name, resource.getResource()).get(0);
        }

        return super.createPropertySource(name, resource);
    }
}
