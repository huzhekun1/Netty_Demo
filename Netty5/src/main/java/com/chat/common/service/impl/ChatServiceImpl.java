package com.chat.common.service.impl;

import com.chat.common.core.constants.Constants;
import com.chat.common.core.constants.ResultCodeEnum;
import com.chat.common.core.exception.ErrorCodeException;
import com.chat.common.core.session.SessionManager;
import com.chat.common.entity.Player;
import com.chat.common.module.chat.response.ChatResponse;
import com.chat.common.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 聊天服务
 * @author hzk
 * @date 2018/10/24
 */
@Component
public class ChatServiceImpl implements ChatService{

    @Autowired
    private com.chat.common.dao.read.PlayerMapper playerMapper_r;

    @Override
    public void publicChat(long playerId, String content) {
        Player player = playerMapper_r.selectByPrimaryKey(playerId);

        //获取所有在线玩家
        Set<Long> onlinePlayers = SessionManager.getOnlinePlayers();

        //创建消息对象
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setSendPlayerId(playerId);
        chatResponse.setSendPlayerName(player.getName());
        chatResponse.setMessage(content);
        chatResponse.setChatType(Constants.AbstractChatType.PUBLIC_CHAT);

        for (Long onlinePlayer : onlinePlayers) {
            SessionManager.sendMessage(onlinePlayer,Constants.AbstractModule.CHAT,Constants.AbstractCmdChat.PUSH_CHAT,chatResponse);
        }
    }

    @Override
    public void privateChat(long playerId, long targetPlayerId, String content) {
        //检测是否私聊对象为自己
        if(playerId == targetPlayerId){
            throw new ErrorCodeException(ResultCodeEnum.CANNOT_CHAT_YOURSELF.getResultCode());
        }

        Player player = playerMapper_r.selectByPrimaryKey(playerId);

        Player targetPlayer = playerMapper_r.selectByPrimaryKey(targetPlayerId);
        //检测目标用户是否存在
        if(null == targetPlayer){
            throw new ErrorCodeException(ResultCodeEnum.PLAYER_NO_EXIST.getResultCode());
        }

        //检测目标用户是否在线
        if(!SessionManager.isOnlinePlayer(targetPlayerId)){
            throw new ErrorCodeException(ResultCodeEnum.PLAYER_NO_ONLINE.getResultCode());
        }

        //创建消息对象
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setSendPlayerId(playerId);
        chatResponse.setSendPlayerName(player.getName());
        chatResponse.setTargetPlayerName(targetPlayer.getName());
        chatResponse.setMessage(content);
        chatResponse.setChatType(Constants.AbstractChatType.PRIVATE_CHAT);

        //给目标用户发送信息
        SessionManager.sendMessage(targetPlayerId,Constants.AbstractModule.CHAT,Constants.AbstractCmdChat.PUSH_CHAT,chatResponse);
        //给自己回一个信息
        SessionManager.sendMessage(playerId,Constants.AbstractModule.CHAT,Constants.AbstractCmdChat.PUSH_CHAT,chatResponse);
    }
}
