package com.qdingnet.bigdata.beans;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author yanpf
 * @date 2018/12/3 16:19
 * @description
 */

@Data
public class CanalBinLogData<T> {

    private List<T> data;
    private String database;
    private Long es;
    private Long id;
    private Boolean isIdle;
    private Boolean isDdl;
    private Map<String, Integer> mysqlType;
    private List<T> old;
    private String sql;
    private Map<String, Integer> sqlType;
    private String table;
    private Long ts;
    private String type;
}
