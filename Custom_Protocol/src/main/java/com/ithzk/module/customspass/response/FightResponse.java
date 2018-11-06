package com.ithzk.module.customspass.response;

import com.ithzk.core.Serializable;

/**
 * @author hzk
 * @date 2018/10/8
 */
public class FightResponse extends Serializable{

    /**
     * 获取金币
     */
    private double gold;

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    @Override
    protected void read() {
        gold = readDouble();
    }

    @Override
    protected void write() {
        writeDouble(gold);
    }

    @Override
    public String toString() {
        return "FightResponse{" +
                "gold=" + gold +
                '}';
    }
}

