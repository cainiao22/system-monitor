package com.qdingnet.bigdata.beans;

import lombok.Data;

/**
 * @author yanpf
 * @date 2019/2/20 9:47
 * @description
 */

@Data
public class TextWechartMsg extends WechartMsg {

    public static class Text {
        public String content;

    }

    private Text text = new Text();

    public void setContent(String content) {
        text.content = content;
    }

    public TextWechartMsg() {
        this.msgtype = "text";
    }
}
