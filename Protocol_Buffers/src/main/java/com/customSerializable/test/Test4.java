package com.customSerializable.test;

import com.customSerializable.entity.Player;

import java.util.Arrays;

/**
 * 自定义序列化
 * @author hzk
 * @date 2018/9/27
 */
public class Test4 {
    
    public static void main(String[] args){
        Player player = new Player();
        player.setPlayerId(1111L);
        player.setAge(23);
        player.getSkills().add(77);
        player.getSkills().add(88);
        player.getResource().setEnergy(100);
        player.getResource().setGold(15000.00D);

        byte[] bytes = player.getBytes();
        System.out.println(Arrays.toString(bytes));

        Player player2 = new Player();
        player2.readFromBytes(bytes);
        System.out.println(player2.toString());

    }

}
