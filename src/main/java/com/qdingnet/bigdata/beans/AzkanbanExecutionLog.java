package com.qdingnet.bigdata.beans;

import lombok.Data;

/**
 * @author yanpf
 * @date 2018/12/3 15:28
 * @description
 */

@Data
public class AzkanbanExecutionLog {

    private Integer execId;
    private String name;
    private Integer attempt;
    private Integer encType;
    private Long startByte;
    private Long endByte;
    private byte[] log;
    private Long uploadTime;

}
