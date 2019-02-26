package com.qdingnet.bigdata.mapper;

import com.qdingnet.bigdata.beans.AzkabanMonitorOwner;
import org.apache.ibatis.jdbc.SQL;

public class AzkabanMonitorOwnerSqlProvider {

    public String insertSelective(AzkabanMonitorOwner record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("azkaban_monitor_owner");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getBusinessType() != null) {
            sql.VALUES("business_type", "#{businessType,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            sql.VALUES("user_name", "#{userName,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(AzkabanMonitorOwner record) {
        SQL sql = new SQL();
        sql.UPDATE("azkaban_monitor_owner");
        
        if (record.getBusinessType() != null) {
            sql.SET("business_type = #{businessType,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            sql.SET("user_name = #{userName,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}