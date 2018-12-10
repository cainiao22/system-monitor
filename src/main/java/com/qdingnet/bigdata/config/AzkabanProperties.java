package com.qdingnet.bigdata.config;

import com.qdingnet.bigdata.config.factory.YamlPropertyLoaderFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanpf
 * @date 2018/12/6 11:50
 * @description
 */

@Component
@PropertySource(value = "classpath:azkaban.yml", factory = YamlPropertyLoaderFactory.class)
@ConfigurationProperties("azkaban.alarm")
public class AzkabanProperties {

    private List<String> alarmPhones = new ArrayList<>();

    private List<String> whitelist = new ArrayList<>();

    private List<String> blacklist = new ArrayList<>();

    public List<String> getAlarmPhones() {
        return alarmPhones;
    }

    public void setAlarmPhones(List<String> alarmPhones) {
        this.alarmPhones = alarmPhones;
    }

    public List<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(List<String> blacklist) {
        this.blacklist = blacklist;
    }
}
