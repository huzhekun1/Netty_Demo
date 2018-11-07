package com.chat.server.player.handler;

import com.chat.common.core.annotation.SocketCommand;
import com.chat.common.core.annotation.SocketModule;
import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Result;
import com.chat.common.core.session.Session;
import com.chat.common.module.player.response.PlayerResponse;

/**
 * 用户处理
 * @author hzk
 * @date 2018/10/25
 */
@SocketModule(module = Constants.AbstractModule.PLAYER)
public interface PlayerHandler {

    /**
     *  创建并登录账号
     * @param session 会话
     * @param data {@link com.chat.common.module.player.request.RegisterRequest}
     * @return
     */
    @SocketCommand(cmd = Constants.AbstractCmdPlayer.REGISTER_AND_LOGIN)
    public Result<PlayerResponse> registerAndLogin(Session session,byte[] data);

    /**
     * 登录账号
     * @param session 会话
     * @param data {@link com.chat.common.module.player.request.RegisterRequest}
     * @return
     */
    @SocketCommand(cmd = Constants.AbstractCmdPlayer.LOGIN)
    public Result<PlayerResponse> login(Session session,byte[] data);
}
