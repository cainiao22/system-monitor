package com.qdingnet.bigdata.beans;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * @author yanpf
 * @date 2018/12/4 17:27
 * @description
 */

@Data
public class WechartMsg {

    private String touser;

    private String toparty;

    private String totag;

    protected String msgtype;
    private String agentid;
    private int safe = 0;
}
