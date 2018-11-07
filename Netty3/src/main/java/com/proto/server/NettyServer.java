package com.proto.server;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * netty服务类
 * @author hzk
 * @date 2018/9/19
 */
public class NettyServer {

    public static void main(String[] args){
        //服务类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //Boss线程监听 worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        //设置NioSocket工厂
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));
        //设置管道
        final HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("idle",new IdleStateHandler(hashedWheelTimer,5,5,10));
                pipeline.addLast("decoder",new StringDecoder());
                pipeline.addLast("encoder",new StringEncoder());
//                pipeline.addLast("heart",new ServerHeartHandler());
                pipeline.addLast("heart",new ServerHeartHandler2());
                return pipeline;
            }
        });

        serverBootstrap.bind(new InetSocketAddress(8888));

        System.out.println("Netty Server start...");
    }
}
