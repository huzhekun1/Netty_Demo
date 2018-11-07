package com.proto.nio.pool;

import java.nio.channels.ServerSocketChannel;

/**
 * Boss接口
 * @author hzk
 * @date 2018/8/20
 */
public interface Boss {

    /**
     * 加入一个新的serverSocket
     * @param serverSocketChannel
     */
    public void regiserAcceptChannelTask(ServerSocketChannel serverSocketChannel);
}
