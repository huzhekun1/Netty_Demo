package com.chat.client.chat.handler.impl;

import com.chat.client.chat.handler.ChatHandler;
import com.chat.client.swing.ResultCodeHint;
import com.chat.client.swing.SwingClient;
import com.chat.common.core.constants.ResultCodeEnum;
import com.chat.common.module.chat.proto.ChatModule;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hzk
 * @date 2018/10/25
 */
@Component
public class ChatHandlerImpl implements ChatHandler{

    @Autowired
    private SwingClient swingClient;

    @Autowired
    private ResultCodeHint resultCodeHint;

    @Override
    public void publicChat(int resultCode, byte[] data) {
        if(ResultCodeEnum.SUCCESS.getResultCode() == resultCode){
            swingClient.getHints().setText("发送成功!");
        }else{
            swingClient.getHints().setText(resultCodeHint.getHintContent(resultCode));
        }
    }

    @Override
    public void privateChat(int resultCode, byte[] data) {
        if(ResultCodeEnum.SUCCESS.getResultCode() == resultCode){
            swingClient.getHints().setText("发送成功!");
        }else{
            swingClient.getHints().setText(resultCodeHint.getHintContent(resultCode));
        }
    }

    @Override
    public void receiveMessage(int resultCode, byte[] data) {
        ChatModule.ChatResponse chatResponse = null;
        try {
            chatResponse = ChatModule.ChatResponse.parseFrom(data);
            StringBuilder stringBuilder = new StringBuilder();
            if(ChatModule.ChatType.PUBLIC == chatResponse.getChatType()){
                stringBuilder.append(chatResponse.getSendPlayerName());
                stringBuilder.append("[");
                stringBuilder.append(chatResponse.getSendPlayerId());
                stringBuilder.append("]");
                stringBuilder.append(" 说:\n\t");
                stringBuilder.append(chatResponse.getMessage());
                stringBuilder.append("\n\n");
            }else if(ChatModule.ChatType.PRIVATE == chatResponse.getChatType()){
                if(swingClient.getPlayerResponse().getPlayerId() == chatResponse.getSendPlayerId()){
                    stringBuilder.append("您悄悄对 ");
                    stringBuilder.append("[");
                    stringBuilder.append(chatResponse.getTargetPlayerName());
                    stringBuilder.append("]");
                    stringBuilder.append(" 说:\n\t");
                    stringBuilder.append(chatResponse.getMessage());
                    stringBuilder.append("\n\n");
                }else{
                    stringBuilder.append(chatResponse.getSendPlayerName());
                    stringBuilder.append("[");
                    stringBuilder.append(chatResponse.getSendPlayerId());
                    stringBuilder.append("]");
                    stringBuilder.append(" 悄悄对你说:\n\t");
                    stringBuilder.append(chatResponse.getMessage());
                    stringBuilder.append("\n\n");
                }
            }
            swingClient.getChatContent().append(stringBuilder.toString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }


    }
}
