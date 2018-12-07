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

    @NonNull
    private String title;

    //@NonNull
    private String content;

    //@NonNull
    private List<String> mobiles;
}
