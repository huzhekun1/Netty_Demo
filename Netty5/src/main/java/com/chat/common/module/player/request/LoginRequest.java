package com.chat.common.module.player.request;

import com.chat.common.core.serializable.Serializable;

/**
 * 登录请求
 * @author hzk
 * @date 2018/10/23
 */
public class LoginRequest extends Serializable{

    /**
     * 用户名
     */
    private String playerName;

    /**
     * 密码
     */
    private String password;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void read() {
        this.playerName = readString();
        this.password = readString();
    }

    @Override
    protected void write() {
        writeString(playerName);
        writeString(password);
    }
}
