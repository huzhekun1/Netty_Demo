package com.pk.server;

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
 * 粘包分包
 * @author hzk
 * @date 2018/10/22
 */
public class Server {
    
    public static void main(String[] args){
        //服务类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //boos线程监听端口 worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置NioSocket工厂
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

        //设置管道工厂
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline channelPipeline = Channels.pipeline();
                channelPipeline.addLast("decoder",new MyDecoderHandler());
                //channelPipeline.addLast("decoder",new StringDecoder());
                channelPipeline.addLast("encoder",new StringEncoder());
                channelPipeline.addLast("handlerOne",new MyHandlerOne());
                return channelPipeline;
            }
        });

        serverBootstrap.bind(new InetSocketAddress(8888));
        System.out.println("Server Start...");
    }
}
