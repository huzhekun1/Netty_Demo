package com.pk.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @author hzk
 * @date 2018/10/22
 */
public class MyDecoderHandler extends FrameDecoder {
    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {
        if(channelBuffer.readableBytes()>4){
            
            if(channelBuffer.readableBytes() >2048){
                channelBuffer.skipBytes(channelBuffer.readableBytes());
            }

            //标记
            channelBuffer.markReaderIndex();
            //长度
            int length = channelBuffer.readInt();

            if(channelBuffer.readableBytes() < length){
                channelBuffer.resetReaderIndex();
                //数据包不完整,缓存当前剩余的buffer数据,等待接下来的数据包
                return null;
            }

            //读数据
            byte[] bytes = new byte[length];
            channelBuffer.readBytes(bytes);
            //传递
            return new String(bytes);
        }

        return null;
    }
}
