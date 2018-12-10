package com.qdingnet.bigdata;

import com.qdingnet.bigdata.config.AzkabanProperties;
import com.qdingnet.bigdata.utils.GZIPUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemMonitorApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAzkanban() throws Exception {
        String URL = "jdbc:mysql://127.0.0.1:3306/azkaban?charset=UTF-8";
        String USER = "root";
        String PASSWORD = "root123";
        //1.加载驱动程序
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获得数据库链接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT log from azkaban.execution_logs  limit 1");
		while (rs.next()){
            Blob blob = rs.getBlob("log");
            byte[] bytes = blob.getBytes(1, new Long(blob.length()).intValue());
            String s = Base64.getEncoder().encodeToString(bytes);

            System.out.println(s);

            System.out.println(GZIPUtils.uncompressToString(Base64.getDecoder().decode(s.getBytes())));
        }

        System.out.println();
    }

    @Autowired
    ApplicationContext context;

    @Resource
    AzkabanProperties azkabanProperties;


    @Test
    public void getAlarmMobiles(){
        System.out.println(azkabanProperties.getBlacklist());
        System.out.println(azkabanProperties.getWhitelist());
    }

    public static void main(String[] args) {
        List<String> mobiles = new ArrayList<>();
        mobiles.add("13734838383");
        mobiles.add("15090909090");
        mobiles.add("12314324234");
        System.out.println(mobiles.stream().collect(Collectors.joining("|")));
        System.out.println(String.join(",", mobiles));
    }

}
