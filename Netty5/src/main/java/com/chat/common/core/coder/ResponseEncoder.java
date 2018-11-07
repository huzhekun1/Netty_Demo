package com.chat.common.core.coder;

import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 响应编码器
 * <pre>
 * 数据包格式(根据需求定义)
 * +——————+——————+——————+——————+——————+——————+
 * |  包头      |  模块号    |  命令号    |  响应码    |   长度     |  数据      |
 * +——————+——————+——————+——————+——————+——————+
 * </pre>
 * 包头4字节
 * 模块号2字节short
 * 命令号2字节short
 * 长度4字节(描述数据部分字节长度)
 * @author hzk
 * @date 2018/9/29
 */
public class ResponseEncoder extends MessageToByteEncoder<Response> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws Exception {
        System.out.println("ResponseEncoder response:" + response.toString());

        //包头
        byteBuf.writeInt(Constants.AbstractDataStructure.PACKAGE_HEAD);
        //模块Module
        byteBuf.writeShort(response.getModule());
        //命令号cmd
        byteBuf.writeShort(response.getCmd());
        //状态
        byteBuf.writeInt(response.getCode());
        //数据长度
        byteBuf.writeInt(response.getDataLength());
        //数据
        if(null != response.getData()){
            byteBuf.writeBytes(response.getData());
        }
    }
}
