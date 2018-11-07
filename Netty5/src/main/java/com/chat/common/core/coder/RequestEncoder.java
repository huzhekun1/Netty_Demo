package com.chat.common.core.coder;

import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

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
public class RequestEncoder extends MessageToByteEncoder<Request> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Request request, ByteBuf byteBuf) throws Exception {
        //包头
        byteBuf.writeInt(Constants.AbstractDataStructure.PACKAGE_HEAD);
        //模块Module
        byteBuf.writeShort(request.getModule());
        //命令号cmd
        byteBuf.writeShort(request.getCmd());
        //数据长度
        byteBuf.writeInt(request.getDataLength());
        //数据
        if(null != request.getData()){
            byteBuf.writeBytes(request.getData());
        }
    }
}
