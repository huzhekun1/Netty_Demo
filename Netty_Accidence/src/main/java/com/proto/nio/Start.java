package com.proto.nio;

import com.proto.nio.pool.NioSelectorRunnablePool;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author hzk
 * @date 2018/8/20
 */
public class Start {
    
    public static void main(String[] args){
        //初始化线程
        NioSelectorRunnablePool nioSelectorRunnablePool = new NioSelectorRunnablePool(Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
        //获取服务了
        ServerBootStrap serverBootStrap = new ServerBootStrap(nioSelectorRunnablePool);
        //绑定端口
        serverBootStrap.bind(new InetSocketAddress(8888));
        System.out.println("Start success...");
    }
}
