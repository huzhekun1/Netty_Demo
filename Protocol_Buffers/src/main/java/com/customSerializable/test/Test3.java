package com.customSerializable.test;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.util.Arrays;

/**
 * ChannelBuffers Netty工具
 * @author hzk
 * @date 2018/9/26
 */
public class Test3 {
    
    public static void main(String[] args){
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        channelBuffer.writeInt(111);
        channelBuffer.writeDouble(23.5);

        byte[] bytes = new byte[channelBuffer.writerIndex()];
        channelBuffer.readBytes(bytes);
        System.out.println(Arrays.toString(bytes));

        ChannelBuffer channelBuffer1 = ChannelBuffers.wrappedBuffer(bytes);
        System.out.println(channelBuffer1.readInt());
        System.out.println(channelBuffer1.readDouble());
    }
}
