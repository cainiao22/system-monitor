package com.qdingnet.bigdata.beans;

import lombok.Data;

/**
 * @author yanpf
 * @date 2019/1/17 15:20
 * @description
 */

@Data
public class AzkabanErrorInfo {

    private Integer execId;
    private Integer projectId;
    private String name;
    private Integer attempt;
    private String errorLog;
    private Long uploadTime;
}
