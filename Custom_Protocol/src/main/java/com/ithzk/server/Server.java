package com.ithzk.server;

import com.ithzk.coder.RequestDecoder;
import com.ithzk.coder.RequestEncoder;
import com.ithzk.coder.ResponseEncoder;
import com.ithzk.constans.Constants;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * netty服务端
 * @author hzk
 * @date 2018/10/8
 */
public class Server {

    public static void main(String[] args){
        //服务类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //boss监听端口,worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置socket工厂
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

        //设置管道的工厂
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                //RequestDecoder -> ServerHandler ->ResponseEncoder
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder",new RequestDecoder());
                pipeline.addLast("encoder",new ResponseEncoder());
                pipeline.addLast("serverHandler",new ServerHandler());
                return pipeline;
            }
        });

        serverBootstrap.bind(new InetSocketAddress(Constants.AbstractNettyConfig.PORT));
        System.out.println("Server Start...");
    }
}
