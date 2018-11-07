package com.chat.client.chat.handler;

import com.chat.common.core.annotation.SocketCommand;
import com.chat.common.core.annotation.SocketModule;
import com.chat.common.core.constants.Constants;

/**
 * 聊天处理
 * @author hzk
 * @date 2018/10/25
 */
@SocketModule(module = Constants.AbstractModule.CHAT)
public interface ChatHandler {

    /**
     * 发送广播消息回调
     * @param resultCode 响应码
     * @param data
     */
    @SocketCommand(cmd =  Constants.AbstractCmdChat.PUBLIC_CHAT)
    public void publicChat(int resultCode,byte[] data);

    /**
     * 私发消息
     * @param resultCode 响应码
     * @param data
     */
    @SocketCommand(cmd = Constants.AbstractCmdChat.PRIVATE_CHAT)
    public void privateChat(int resultCode,byte[] data);

    /**
     * 接收推送聊天消息
     * @param resultCode 响应码
     * @param data {@link com.chat.common.module.chat.response.ChatResponse}
     */
    @SocketCommand(cmd = Constants.AbstractCmdChat.PUSH_CHAT)
    public void receiveMessage(int resultCode,byte[] data);
}
