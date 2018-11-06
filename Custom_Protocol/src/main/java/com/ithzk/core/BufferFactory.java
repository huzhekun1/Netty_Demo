package com.ithzk.core;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.nio.ByteOrder;

/**
 * ChannelBuffers工具类
 * @author hzk
 * @date 2018/9/26
 */
public class BufferFactory{

    public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    /**
     * 获取一个ChannelBuffer
     * @return
     */
    public static ChannelBuffer getBuffer(){
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        return channelBuffer;
    }

    /**
     * 获取一个ChannelBuffer 并写入数据
     * @param bytes
     * @return
     */
    public static ChannelBuffer getBuffer(byte[] bytes){
        ChannelBuffer channelBuffer = ChannelBuffers.copiedBuffer(bytes);
        return channelBuffer;
    }


}
