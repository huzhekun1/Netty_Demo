package com.chat.server.chat.handler.impl;

import com.chat.common.core.constants.ResultCodeEnum;
import com.chat.common.core.exception.ErrorCodeException;
import com.chat.common.core.model.Result;
import com.chat.common.module.chat.proto.ChatModule;
import com.chat.common.service.ChatService;
import com.chat.server.chat.handler.ChatHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 消息处理实现类
 * @author hzk
 * @date 2018/10/25
 */
@Component
public class ChatHandlerImpl implements ChatHandler{

    @Autowired
    private ChatService chatService;

    @Override
    public Result<?> publicChat(long playerId, byte[] data) {
        try {
            //反序列化
            ChatModule.PublicChatRequest publicChatRequest = ChatModule.PublicChatRequest.parseFrom(data);

            //参数校验
            if(StringUtils.isEmpty(publicChatRequest.getContent())){
                return Result.error(ResultCodeEnum.PARAM_ERROR.getResultCode());
            }

            //执行业务
            chatService.publicChat(playerId,publicChatRequest.getContent());
        }catch (ErrorCodeException e){
            return Result.error(e.getErrorCode());
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResultCodeEnum.UNKNOWN_EXCEPTION.getResultCode());
        }
        return Result.success();
    }

    @Override
    public Result<?> privateChat(long playerId, byte[] data) {
        try {
            //反序列化
            ChatModule.PrivateChatRequest privateChatRequest = ChatModule.PrivateChatRequest.parseFrom(data);

            //参数校验
            if(StringUtils.isEmpty(privateChatRequest.getContent()) || privateChatRequest.getTargetPlayerId() <= 0){
                return Result.error(ResultCodeEnum.PARAM_ERROR.getResultCode());
            }

            //执行业务
            chatService.privateChat(playerId,privateChatRequest.getTargetPlayerId(),privateChatRequest.getContent());
        }catch (ErrorCodeException e){
            return Result.error(e.getErrorCode());
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResultCodeEnum.UNKNOWN_EXCEPTION.getResultCode());
        }
        return Result.success();
    }
}
