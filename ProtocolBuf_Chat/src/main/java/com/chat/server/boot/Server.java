package com.chat.server.boot;

import com.chat.common.core.coder.RequestDecoder;
import com.chat.common.core.coder.ResponseEncoder;
import com.chat.common.core.constants.Constants;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * netty服务端启动类
 * @author hzk
 * @date 2018/10/25
 */
@Component
public class Server {

    /**
     * 服务启动
     */
    public void start(){
        //服务引导程序
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //boss线程监听端口,worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置NioSocket工厂
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

        //设置管道
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder",new RequestDecoder());
                pipeline.addLast("encoder",new ResponseEncoder());
                pipeline.addLast("serverHandler",new ServerHandler());
                return pipeline;
            }
        });

        serverBootstrap.setOption("backlog",1024);
        serverBootstrap.bind(new InetSocketAddress(Constants.AbstractNettyConfig.PORT));
        System.out.println("Server Start Success...");

    }

}
