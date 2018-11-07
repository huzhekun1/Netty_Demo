package com.customSerializable.entity;

import com.customSerializable.core.AbstractSerializable;

/**
 * @author hzk
 * @date 2018/9/27
 */
public class Resource extends AbstractSerializable {

    /**
     * 体力
     */
    private Integer energy;
    /**
     * 金币
     */
    private Double gold;

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Double getGold() {
        return gold;
    }

    public void setGold(Double gold) {
        this.gold = gold;
    }

    @Override
    protected void read() {
        energy = readInt();
        gold = readDouble();
    }

    @Override
    protected void write() {
        writeInt(energy);
        writeDouble(gold);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "energy=" + energy +
                ", gold=" + gold +
                '}';
    }
}
