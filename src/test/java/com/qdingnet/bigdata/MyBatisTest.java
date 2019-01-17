package com.qdingnet.bigdata;

import com.qdingnet.bigdata.beans.AzkabanErrorInfo;
import com.qdingnet.bigdata.mapper.AzkabanErrorInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yanpf
 * @date 2019/1/17 15:40
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest {

    @Autowired
    AzkabanErrorInfoMapper azkabanErrorInfoMapper;


    @Test
    public void testInsertAzkaban(){
        AzkabanErrorInfo errorInfo = new AzkabanErrorInfo();
        errorInfo.setExecId(1);
        errorInfo.setAttempt(1);
        errorInfo.setProjectId(1);
        errorInfo.setName("test");
        errorInfo.setErrorLog("test");
        errorInfo.setUploadTime(1000L);
        int result = azkabanErrorInfoMapper.insert(errorInfo);
        System.out.println(result);
    }
}
