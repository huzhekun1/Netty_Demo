package com.ithzk.coder;

import com.ithzk.constans.Constants;
import com.ithzk.model.Request;
import com.ithzk.model.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 响应解码器
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
                if(Constants.AbstractDataStructure.PACKAGE_HEAD == channelBuffer.readInt()){
                    break;
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

            //判断请求数据包 数据是否完整
            if(channelBuffer.readableBytes() < length){
                //还原读指针
                channelBuffer.readerIndex(beginIndex);
                return null;
            }

            //读取data数据
            byte[] data = new byte[length];
            channelBuffer.readBytes(data);

            Response response = new Response(module,cmd,data,code);

            //往下传递
            return response;
        }

        //数据包不完整，需要等待后面的包来
        return null;
    }

}
