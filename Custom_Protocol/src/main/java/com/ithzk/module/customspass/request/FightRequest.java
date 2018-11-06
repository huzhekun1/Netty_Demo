package com.ithzk.module.customspass.request;

import com.ithzk.core.Serializable;

/**
 * @author hzk
 * @date 2018/10/8
 */
public class FightRequest extends Serializable{

    /**
     * 战斗ID
     */
    private int fightId;

    /**
     * 次数
     */
    private int count;

    public int getFightId() {
        return fightId;
    }

    public void setFightId(int fightId) {
        this.fightId = fightId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public FightRequest(int fightId, int count) {
        this.fightId = fightId;
        this.count = count;
    }

    public FightRequest() {
    }

    @Override
    protected void read() {
        fightId = readInt();
        count = readInt();
    }

    @Override
    protected void write() {
        writeInt(fightId);
        writeInt(count);
    }

    @Override
    public String toString() {
        return "FightRequest{" +
                "fightId=" + fightId +
                ", count=" + count +
                '}';
    }
}
