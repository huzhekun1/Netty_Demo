package com.proto.server;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Netty服务端
 * @author hzk
 * @date 2018/8/16
 */
public class NettyServer {

    public static void main(String[] args){

        //服务类引导
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //boss线程监听端口，worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置nio socket工厂
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

        //设置管道的工厂
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                //接收
                pipeline.addLast("decoder",new StringDecoder());
                //回写
                pipeline.addLast("encoder",new StringEncoder());
                pipeline.addLast("serverHandlerOne",new ServerHandlerOne());
                return pipeline;
            }
        });

        serverBootstrap.bind(new InetSocketAddress(8888));

        System.out.println("Netty server start...");
    }
}
