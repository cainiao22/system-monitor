package com.qdingnet.bigdata.mapper;

import com.qdingnet.bigdata.beans.AzkabanMonitorOwner;
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

public interface AzkabanMonitorOwnerMapper {

    @Insert({
            "insert into azkaban_monitor_owner (id, business_type, ",
            "user_name)",
            "values (#{id,jdbcType=INTEGER}, #{businessType,jdbcType=VARCHAR}, ",
            "#{userName,jdbcType=VARCHAR})"
    })
    int insert(AzkabanMonitorOwner record);

    @InsertProvider(type=AzkabanMonitorOwnerSqlProvider.class, method="insertSelective")
    int insertSelective(AzkabanMonitorOwner record);

    @Select({
            "select",
            "id, business_type, user_name",
            "from azkaban_monitor_owner",
            "where business_type = #{businessType,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="business_type", property="businessType", jdbcType=JdbcType.VARCHAR),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR)
    })
    List<AzkabanMonitorOwner> getListByBusinessType(String businessType);

}