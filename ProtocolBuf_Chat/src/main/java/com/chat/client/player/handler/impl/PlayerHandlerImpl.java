package com.chat.client.player.handler.impl;

import com.chat.client.player.handler.PlayerHandler;
import com.chat.client.swing.ResultCodeHint;
import com.chat.client.swing.SwingClient;
import com.chat.common.core.constants.ResultCodeEnum;
import com.chat.common.module.player.proto.PlayerModule;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hzk
 * @date 2018/10/26
 */
@Component
public class PlayerHandlerImpl implements PlayerHandler {

    @Autowired
    private SwingClient swingClient;

    @Autowired
    private ResultCodeHint resultCodeHint;

    @Override
    public void registerAndLogin(int resultCode, byte[] data) {
        if(ResultCodeEnum.SUCCESS.getResultCode() == resultCode){
            PlayerModule.PlayerResponse playerResponse = null;
            try {
                playerResponse = PlayerModule.PlayerResponse.parseFrom(data);
                swingClient.setPlayerResponse(playerResponse);
                swingClient.getHints().setText("注册并登录成功!");
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }else{
            swingClient.getHints().setText(resultCodeHint.getHintContent(resultCode));
        }
    }

    @Override
    public void login(int resultCode, byte[] data) {
        if(ResultCodeEnum.SUCCESS.getResultCode() == resultCode){
            PlayerModule.PlayerResponse playerResponse = null;
            try {
                playerResponse = PlayerModule.PlayerResponse.parseFrom(data);
                swingClient.setPlayerResponse(playerResponse);
                swingClient.getHints().setText("登录成功!");
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }else{
            swingClient.getHints().setText(resultCodeHint.getHintContent(resultCode));
        }
    }
}
