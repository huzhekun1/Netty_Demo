package com.chat.common.core.coder;

import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Request;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * 请求编码器
 * <pre>
 * 数据包格式(根据需求定义)
 * +——————+————-——+——————+——————+————-——+
 * | 包头       | 模块号      | 命令号     |  长度      |   数据      |
 * +——————+————-——+——————+——————+————-——+
 * </pre>
 * 包头4字节
 * 模块号2字节short
 * 命令号2字节short
 * 长度4字节(描述数据部分字节长度)
 * @author hzk
 * @date 2018/9/29
 */
public class RequestEncoder extends OneToOneEncoder{

    @Override
    protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object rs) throws Exception {
        Request request = (Request) rs;
        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        //包头
        channelBuffer.writeInt(Constants.AbstractDataStructure.PACKAGE_HEAD);
        //模块Module
        channelBuffer.writeShort(request.getModule());
        //命令号cmd
        channelBuffer.writeShort(request.getCmd());
        //数据长度
        channelBuffer.writeInt(request.getDataLength());
        //数据
        if(null != request.getData()){
            channelBuffer.writeBytes(request.getData());
        }
        return channelBuffer;
    }
}
