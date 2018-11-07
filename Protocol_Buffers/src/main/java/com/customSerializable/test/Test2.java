package com.customSerializable.test;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * ByteBuffer 需要指定大小
 * @author hzk
 * @date 2018/9/26
 */
public class Test2 {
    
    public static void main(String[] args){
        int id = 111;
        int age = 23;

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(id);
        buffer.putInt(age);
        byte[] array = buffer.array();
        System.out.println(Arrays.toString(array));

        ByteBuffer wrap = ByteBuffer.wrap(array);
        System.out.println(wrap.getInt());
        System.out.println(wrap.getInt());
    }
}
