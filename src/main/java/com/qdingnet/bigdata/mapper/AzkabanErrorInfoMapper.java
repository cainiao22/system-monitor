package com.qdingnet.bigdata.mapper;

import com.qdingnet.bigdata.beans.AzkabanErrorInfo;
import org.apache.ibatis.annotations.InsertProvider;

/**
 * @author yanpf
 * @date 2019/1/17 15:15
 * @description
 */

public interface AzkabanErrorInfoMapper {

    @InsertProvider(type = AzkabanErrorInfoProvider.class, method = "insertAzkabanErrorInfoSql")
    int insert(AzkabanErrorInfo errorInfo);
}
