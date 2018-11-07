package com.proto.nio;

import com.proto.nio.pool.Boss;
import com.proto.nio.pool.NioSelectorRunnablePool;

import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * 服务启动类
 * @author hzk
 * @date 2018/8/20
 */
public class ServerBootStrap {

    private NioSelectorRunnablePool nioSelectorRunnablePool;

    public ServerBootStrap(NioSelectorRunnablePool nioSelectorRunnablePool) {
        this.nioSelectorRunnablePool = nioSelectorRunnablePool;
    }

    public void bind(final SocketAddress socketAddress){
        try {
            //获得一个serverSocket通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //绑定端口
            serverSocketChannel.bind(socketAddress);
            //获取Boss进程
            Boss boss = nioSelectorRunnablePool.nextBosses();
            //向boss进程注册通道
            boss.regiserAcceptChannelTask(serverSocketChannel);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
