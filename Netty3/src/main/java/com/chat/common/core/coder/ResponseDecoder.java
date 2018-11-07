package com.chat.common.core.coder;

import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 响应解码器
 * <pre>
 * 数据包格式(根据需求定义)
 * +——————+——————+——————+——————+——————+——————+
 * |  包头      |  模块号    |  命令号    |  响应码    |   长度     |  数据      |
 * +——————+——————+——————+——————+——————+——————+
 * </pre>
 * 包头4字节int
 * 模块号2字节short
 * 命令号2字节short
 * 响应码4字节int
 * 长度4字节(描述数据部分字节长度)
 * @author hzk
 * @date 2018/9/29
 */
public class ResponseDecoder extends FrameDecoder{

    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {
        //可读长度必须大于基本长度
        if(channelBuffer.readableBytes() >= Constants.AbstractDataStructure.DATA_STRUCTURE_LENGTH){
            //防止socket字节流攻击
            if(channelBuffer.readableBytes() > 2048){
                channelBuffer.skipBytes(channelBuffer.readableBytes());
            }

            //记录包头开始偏移Index
            int beginIndex = channelBuffer.readerIndex();

            while (true){
                beginIndex = channelBuffer.readerIndex();
                //标记读索引位置
                channelBuffer.markReaderIndex();
                int packHead = channelBuffer.readInt();
                if(Constants.AbstractDataStructure.PACKAGE_HEAD == packHead){
                    break;
                }

                //未读取到包头,还原读索引位置,略过一个字节
                channelBuffer.resetReaderIndex();
                channelBuffer.readByte();

                if(channelBuffer.readableBytes() < Constants.AbstractDataStructure.DATA_RESPONSE_STRUCTURE_LENGTH){
                    //数据包不完整，需要等待后面的数据包
                    return null;
                }

            }

            //模块号
            short module = channelBuffer.readShort();
            //命令号
            short cmd = channelBuffer.readShort();
            //状态码
            int code = channelBuffer.readInt();
            //数据长度
            int length = channelBuffer.readInt();
            if(length < 0){
                channel.close();
            }

            //判断请求数据包 数据是否完整
            if(channelBuffer.readableBytes() < length){
                //还原读指针
                channelBuffer.readerIndex(beginIndex);
                return null;
            }

            //读取data数据
            byte[] data = new byte[length];
            channelBuffer.readBytes(data);

            //解析出消息对象，往下传递(handler)
            return new Response(module,cmd,data,code);
        }

        //数据包不完整，需要等待后面的数据包
        return null;
    }

}
