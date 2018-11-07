package com.chat.common.core.serializable;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

import java.nio.ByteOrder;

/**
 * ChannelBuffers工具类
 * @author hzk
 * @date 2018/9/26
 */
public class BufferFactory{

    public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    private static ByteBufAllocator bufAllocator = PooledByteBufAllocator.DEFAULT;
    /**
     * 获取一个ChannelBuffer
     * @return
     */
    public static ByteBuf getBuffer(){
        ByteBuf byteBuf = bufAllocator.heapBuffer();
        byteBuf = byteBuf.order(BYTE_ORDER);
        return byteBuf;
    }

    /**
     * 获取一个ChannelBuffer 并写入数据
     * @param bytes
     * @return
     */
    public static ByteBuf getBuffer(byte[] bytes){
        ByteBuf byteBuf = bufAllocator.heapBuffer();
        byteBuf = byteBuf.order(BYTE_ORDER);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }


}
