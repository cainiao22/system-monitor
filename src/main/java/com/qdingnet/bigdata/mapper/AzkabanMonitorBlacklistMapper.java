package com.qdingnet.bigdata.mapper;

import com.qdingnet.bigdata.beans.AzkabanMonitorBlacklist;
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

public interface AzkabanMonitorBlacklistMapper {

    @Insert({
        "insert into azkaban_monitor_blacklist (id, business_type, ",
        "type, content)",
        "values (#{id,jdbcType=INTEGER}, #{businessType,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR})"
    })
    int insert(AzkabanMonitorBlacklist record);

    @InsertProvider(type=AzkabanMonitorBlacklistSqlProvider.class, method="insertSelective")
    int insertSelective(AzkabanMonitorBlacklist record);

    @Select({
        "select",
        "id, business_type, type, content",
        "from azkaban_monitor_blacklist",
        "where business_type = #{businessType,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="business_type", property="businessType", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR)
    })
    List<AzkabanMonitorBlacklist> getListByBusinessType(String businessType);

}