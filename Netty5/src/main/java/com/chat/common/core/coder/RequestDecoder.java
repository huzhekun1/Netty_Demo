package com.chat.common.core.coder;

import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 请求解码器
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
public class RequestDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
//        while (true){
            //可读长度必须大于基本长度
            if(byteBuf.readableBytes() >= Constants.AbstractDataStructure.DATA_STRUCTURE_LENGTH){
                //防止socket字节流攻击
                if(byteBuf.readableBytes() > 2048){
                    byteBuf.skipBytes(byteBuf.readableBytes());
                }

                //记录包头开始偏移Index,即第一个可读数据的起始位置
                int beginIndex;

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

                    if(byteBuf.readableBytes() < Constants.AbstractDataStructure.DATA_STRUCTURE_LENGTH){
                        //数据包不完整，需要等待后面的数据包
                        return;
                    }
                }
                //模块号
                short module = byteBuf.readShort();
                //命令号
                short cmd = byteBuf.readShort();
                //数据长度
                int length = byteBuf.readInt();
                if(length <0){
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
                list.add(new Request(module,cmd,data));
            }
//            else{
//                break;
//            }
//        }
        //数据包不完整，需要等待后面的数据包
        return;
    }
}
