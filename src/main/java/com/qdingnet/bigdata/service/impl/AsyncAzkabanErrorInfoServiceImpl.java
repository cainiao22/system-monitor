package com.qdingnet.bigdata.service.impl;

import com.qdingnet.bigdata.beans.AzkabanErrorInfo;
import com.qdingnet.bigdata.mapper.AzkabanErrorInfoMapper;
import com.qdingnet.bigdata.service.AzkabanErrorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author yanpf
 * @date 2019/1/17 15:49
 * @description
 */

@Service
public class AsyncAzkabanErrorInfoServiceImpl implements AzkabanErrorInfoService {

    @Autowired
    AzkabanErrorInfoMapper azkabanErrorInfoMapper;

    @Async
    @Override
    public void add(AzkabanErrorInfo azkabanErrorInfo) {
        azkabanErrorInfoMapper.insert(azkabanErrorInfo);
    }
}
