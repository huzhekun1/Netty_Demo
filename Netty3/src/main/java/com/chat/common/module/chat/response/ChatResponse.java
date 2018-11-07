package com.chat.common.module.chat.response;

import com.chat.common.core.serializable.Serializable;

/**
 * 聊天消息响应
 * @author hzk
 * @date 2018/10/23
 */
public class ChatResponse extends Serializable{

    /**
     * 发送用户ID
     */
    private long sendPlayerId;

    /**
     * 发送用户名称
     */
    private String sendPlayerName;

    /**
     * 目标用户名称
     */
    private String targetPlayerName;

    /**
     * 消息类型
     * 0 广播类型
     * 1 私聊
     * {@link com.chat.common.core.constants.Constants.AbstractChatType}
     */
    private byte chatType;

    /**
     * 消息
     */
    private String message;

    public long getSendPlayerId() {
        return sendPlayerId;
    }

    public void setSendPlayerId(long sendPlayerId) {
        this.sendPlayerId = sendPlayerId;
    }

    public String getSendPlayerName() {
        return sendPlayerName;
    }

    public void setSendPlayerName(String sendPlayerName) {
        this.sendPlayerName = sendPlayerName;
    }

    public String getTargetPlayerName() {
        return targetPlayerName;
    }

    public void setTargetPlayerName(String targetPlayerName) {
        this.targetPlayerName = targetPlayerName;
    }

    public byte getChatType() {
        return chatType;
    }

    public void setChatType(byte chatType) {
        this.chatType = chatType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    protected void read() {
        this.sendPlayerId = readLong();
        this.sendPlayerName = readString();
        this.targetPlayerName = readString();
        this.chatType = readByte();
        this.message = readString();
    }

    @Override
    protected void write() {
        writeLong(sendPlayerId);
        writeString(sendPlayerName);
        writeString(targetPlayerName);
        writeByte(chatType);
        writeString(message);
    }
}
