package com.chat.server.chat.handler;

import com.chat.common.core.annotation.SocketCommand;
import com.chat.common.core.annotation.SocketModule;
import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Result;

/**
 * 聊天处理
 * @author hzk
 * @date 2018/10/25
 */
@SocketModule(module = Constants.AbstractModule.CHAT)
public interface ChatHandler {

    /**
     *  广播消息(群发)
     * @param playerId 发送用户ID
     * @param data {@link com.chat.common.module.chat.proto.ChatModule.PublicChatRequest}
     * @return
     */
    @SocketCommand(cmd = Constants.AbstractCmdChat.PUBLIC_CHAT)
    public Result<?> publicChat(long playerId, byte[] data);

    /**
     * 私聊消息
     * @param playerId 发送用户ID
     * @param data {@link com.chat.common.module.chat.proto.ChatModule.PublicChatRequest}
     * @return
     */
    @SocketCommand(cmd = Constants.AbstractCmdChat.PRIVATE_CHAT)
    public Result<?> privateChat(long playerId, byte[] data);
}
