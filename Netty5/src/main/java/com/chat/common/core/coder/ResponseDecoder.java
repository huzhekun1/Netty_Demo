package com.chat.common.core.coder;

import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

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
public class ResponseDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
       // while (true){
            //可读长度必须大于基本长度
            if(byteBuf.readableBytes() >= Constants.AbstractDataStructure.DATA_STRUCTURE_LENGTH){
                //防止socket字节流攻击
                if(byteBuf.readableBytes() > 2048){
                    byteBuf.skipBytes(byteBuf.readableBytes());
                }

                //记录包头开始偏移Index
                int beginIndex = byteBuf.readerIndex();

                while (true){
                    beginIndex = byteBuf.readerIndex();
                    //标记读索引位置
                    byteBuf.markReaderIndex();
                    int packHead = byteBuf.readInt();
                    if(Constants.AbstractDataStructure.PACKAGE_HEAD == packHead){
                        break;
                    }

                    //未读取到包头,还原读索引位置,略过一个字节
                    byteBuf.resetReaderIndex();
                    byteBuf.readByte();

                    if(byteBuf.readableBytes() < Constants.AbstractDataStructure.DATA_RESPONSE_STRUCTURE_LENGTH){
                        //数据包不完整，需要等待后面的数据包
                        return;
                    }

                }

                //模块号
                short module = byteBuf.readShort();
                //命令号
                short cmd = byteBuf.readShort();
                //状态码
                int code = byteBuf.readInt();
                //数据长度
                int length = byteBuf.readInt();
                if(length < 0){
                    channelHandlerContext.channel().close();
                }

                //判断请求数据包 数据是否完整
                if(byteBuf.readableBytes() < length){
                    //还原读指针
                    byteBuf.readerIndex(beginIndex);
                    return;
                }

                //读取data数据
                byte[] data = new byte[length];
                byteBuf.readBytes(data);

                //解析出消息对象，往下传递(handler)
                list.add(new Response(module,cmd,data,code));
            }
//            else{
//                break;
//            }
//        }
        //数据包不完整，需要等待后面的数据包
        return;
    }
}
