package com.proto.nio.pool;

import java.nio.channels.SocketChannel;

/**
 * Worker接口
 * @author hzk
 * @date 2018/8/20
 */
public interface Worker {

    /**
     * 加入一个新的客户端会话
     * @param socketChannel
     */
    public void registerNewChannelTask(SocketChannel socketChannel);
}
