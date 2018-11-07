package com.chat.common.service;

import com.chat.common.core.session.Session;
import com.chat.common.module.player.proto.PlayerModule;

/**
 * 玩家服务
 * @author hzk
 * @date 2018/10/24
 */
public interface PlayerService {

    /**
     * 注册并登录
     * @param session
     * @param playerName 用户名
     * @param password 密码
     * @return
     */
    public PlayerModule.PlayerResponse registerAndLogin(Session session, String playerName, String password);

    /**
     * 登录
     * @param session
     * @param playerName 用户名
     * @param password 密码
     * @return
     */
    public PlayerModule.PlayerResponse login(Session session, String playerName, String password);
}
