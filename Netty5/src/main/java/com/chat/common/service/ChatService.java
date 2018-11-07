package com.chat.common.service;

/**
 * 聊天服务
 * @author hzk
 * @date 2018/10/24
 */
public interface ChatService {

    /**
     * 广播消息(群发)
     * @param playerId 用户ID
     * @param content 消息内容
     */
    public void publicChat(long playerId, String content);

    /**
     * 私聊
     * @param playerId 用户ID
     * @param targetPlayerId 目标用户ID
     * @param content 消息内容
     */
    public void privateChat(long playerId, long targetPlayerId, String content);

}
