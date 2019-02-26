package com.qdingnet.bigdata.mapper;

import com.qdingnet.bigdata.beans.AzkabanMonitorWhitelist;
import org.apache.ibatis.jdbc.SQL;

public class AzkabanMonitorWhitelistSqlProvider {

    public String insertSelective(AzkabanMonitorWhitelist record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("azkaban_monitor_whitelist");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getBusinessType() != null) {
            sql.VALUES("business_type", "#{businessType,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            sql.VALUES("content", "#{content,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(AzkabanMonitorWhitelist record) {
        SQL sql = new SQL();
        sql.UPDATE("azkaban_monitor_whitelist");
        
        if (record.getBusinessType() != null) {
            sql.SET("business_type = #{businessType,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=VARCHAR}");
        }
        
        if (record.getContent() != null) {
            sql.SET("content = #{content,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}