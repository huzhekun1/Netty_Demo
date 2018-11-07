package com.customSerializable.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author hzk
 * @date 2018/9/25
 */
public class Test1 {
    
    public static void main(String[] args) throws IOException {
        int id = 111; // 0110 1111
        int age = 23; //0001 0111

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] idBytes = int2byte(id);
        byte[] ageBytes = int2byte(age);
        System.out.println("Bytes id:"+ Arrays.toString(idBytes));
        System.out.println("Bytes age:"+ Arrays.toString(ageBytes));
        byteArrayOutputStream.write(idBytes);
        byteArrayOutputStream.write(ageBytes);
        byte[] outBytes = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outBytes);
        byte[] idBytesIn = new byte[4];
        byteArrayInputStream.read(idBytesIn);
        System.out.println("Read Bytes id:"+Arrays.toString(idBytesIn));
        System.out.println("Read Bytes Trans id:"+byte2int(idBytesIn));

        byte[] ageBytesIn = new byte[4];
        byteArrayInputStream.read(ageBytesIn);
        System.out.println("Read Bytes age:"+Arrays.toString(ageBytesIn));
        System.out.println("Read Bytes Trans age:"+byte2int(ageBytesIn));

    }

    /**
     * 大端字节序列(先写高位，再写低位)
     * @param i
     * @return
     */
    private static byte[] int2byte(int i){
        byte[] bytes = new byte[4];
        bytes[0] = (byte)(i >> 3*8);
        bytes[1] = (byte)(i >> 2*8);
        bytes[2] = (byte)(i >> 1*8);
        bytes[3] = (byte)(i >> 0*8);
        return bytes;
    }

    /**
     * 大端字节反序列
     * @param bytes
     * @return
     */
    private static int byte2int(byte[] bytes){
        return (bytes[0] << 3*8)|(bytes[1] << 2*8)|(bytes[2] << 1*8)|(bytes[3] << 0*8);
    }


}
