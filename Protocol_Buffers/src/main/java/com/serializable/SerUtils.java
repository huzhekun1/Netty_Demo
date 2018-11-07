package com.serializable;

import com.google.protobuf.InvalidProtocolBufferException;
import com.proto.PlayerModule;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * java序列化转换
 * @author hzk
 * @date 2018/9/20
 */
public class SerUtils {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        byte[] bytes = toBytes();
        toPlayer(bytes);
    }

    /**
     * 序列化
     * @return
     * @throws IOException
     */
    private static byte[] toBytes() throws IOException {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(10);
        Player player = new Player(10, "Peter", 18,integers);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        //写入对象
        objectOutputStream.writeObject(player);

        //获取字节数据
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(Arrays.toString(bytes));
        return bytes;
    }

    /**
     * 反序列化
     * @param bs
     * @throws InvalidProtocolBufferException
     */
    private static void toPlayer(byte[] bs) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bs));
        Player player = (Player) objectInputStream.readObject();
        System.out.println("PlayInfo:"+player.getPlayerId()+":"+player.getName()+":"+player.getAge()+":"+player.getSkills());
    }
}
