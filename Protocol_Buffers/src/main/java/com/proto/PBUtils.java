package com.proto;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.Arrays;

/**
 * protocol buffers 转换
 * @author hzk
 * @date 2018/9/20
 */
public class PBUtils {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        byte[] bytes = toBytes();
        toPlayer(bytes);
    }

    /**
     * 序列化
     * @return
     */
    private static byte[] toBytes(){
        //获取PBPlayer构造器
        PlayerModule.PBPlayer.Builder builder = PlayerModule.PBPlayer.newBuilder();
        //设置数据
        builder.setName("hhh").setAge(10).setSkills(10).setPlayerId(1);
        //构造对象
        PlayerModule.PBPlayer player = builder.build();
        //序列化成字节数组
        //This is supposed to be overridden by subclasses. 需要maven引用和生成java文件的版本相同
        byte[] bytes = player.toByteArray();
        System.out.println(Arrays.toString(bytes));
        return bytes;
    }

    /**
     * 反序列化
     * @param bs
     * @throws InvalidProtocolBufferException
     */
    private static void toPlayer(byte[] bs) throws InvalidProtocolBufferException {
        PlayerModule.PBPlayer player = PlayerModule.PBPlayer.parseFrom(bs);
        System.out.println("PlayInfo:"+player.getPlayerId()+":"+player.getName()+":"+player.getAge()+":"+player.getSkills());
    }


}
