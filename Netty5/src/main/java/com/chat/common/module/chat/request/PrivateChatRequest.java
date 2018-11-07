package com.chat.common.module.chat.request;

import com.chat.common.core.serializable.Serializable;

/**
 * 私聊消息请求
 * @author hzk
 * @date 2018/10/23
 */
public class PrivateChatRequest extends Serializable{

    /**
     * 发送目标用户ID
     */
    private long targetPlayerId;

    /**
     * 内容
     */
    private String content;

    public long getTargetPlayerId() {
        return targetPlayerId;
    }

    public void setTargetPlayerId(long targetPlayerId) {
        this.targetPlayerId = targetPlayerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected void read() {
        this.targetPlayerId = readLong();
        this.content = readString();
    }

    @Override
    protected void write() {
        writeLong(targetPlayerId);
        writeString(content);
    }
}
