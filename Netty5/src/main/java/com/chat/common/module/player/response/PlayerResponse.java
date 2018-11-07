package com.chat.common.module.player.response;

import com.chat.common.core.serializable.Serializable;

/**
 * 用户信息响应
 * @author hzk
 * @date 2018/10/23
 */
public class PlayerResponse extends Serializable{

    /**
     * 用户ID
     */
    private Long playerId;

    /**
     * 用户名
     */
    private String playerName;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 经验值
     */
    private Integer exp;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    @Override
    protected void read() {
        this.playerId = readLong();
        this.playerName = readString();
        this.level = readInt();
        this.exp = readInt();
    }

    @Override
    protected void write() {
        writeLong(playerId);
        writeString(playerName);
        writeInt(level);
        writeInt(exp);
    }
}
