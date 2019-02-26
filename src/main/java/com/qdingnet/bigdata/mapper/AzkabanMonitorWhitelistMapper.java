package com.qdingnet.bigdata.mapper;

import com.qdingnet.bigdata.beans.AzkabanMonitorWhitelist;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface AzkabanMonitorWhitelistMapper {

    @Insert({
            "insert into azkaban_monitor_whitelist (id, business_type, ",
            "type, content)",
            "values (#{id,jdbcType=INTEGER}, #{businessType,jdbcType=VARCHAR}, ",
            "#{type,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR})"
    })
    int insert(AzkabanMonitorWhitelist record);

    @InsertProvider(type=AzkabanMonitorWhitelistSqlProvider.class, method="insertSelective")
    int insertSelective(AzkabanMonitorWhitelist record);

    @Select({
            "select",
            "id, business_type, type, content",
            "from azkaban_monitor_whitelist",
            "where business_type = #{businessType,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="business_type", property="businessType", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
            @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR)
    })
    List<AzkabanMonitorWhitelist> getListByBusinessType(String businessType);
}