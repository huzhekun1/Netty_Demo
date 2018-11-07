package com.chat.client.player.handler;

import com.chat.common.core.annotation.SocketCommand;
import com.chat.common.core.annotation.SocketModule;
import com.chat.common.core.constants.Constants;

/**
 * 用户处理
 * @author hzk
 * @date 2018/10/26
 */
@SocketModule(module = Constants.AbstractModule.PLAYER)
public interface PlayerHandler {

    /**
     * 注册并登录
     * @param resultCode
     * @param data
     */
    @SocketCommand(cmd = Constants.AbstractCmdPlayer.REGISTER_AND_LOGIN)
    public void registerAndLogin(int resultCode,byte[] data);


    /**
     * 登录账号
     * @param resultCode
     * @param data
     */
    @SocketCommand(cmd = Constants.AbstractCmdPlayer.LOGIN)
    public void login(int resultCode,byte[] data);
}
