package com.chat.common.module.chat.request;

import com.chat.common.core.serializable.Serializable;

/**
 * 广播信息请求
 * @author hzk
 * @date 2018/10/23
 */
public class PublicChatRequest extends Serializable{

    /**
     * 内容
     */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected void read() {
        this.content = readString();
    }

    @Override
    protected void write() {
        writeString(content);
    }
}
