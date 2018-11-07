package com.proto.client;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Netty客户端
 * @author hzk
 * @date 2018/8/17
 */
public class NettyClient {
    
    public static void main(String[] args){
        //客户端引导
        ClientBootstrap clientBootstrap = new ClientBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置nio socket工厂
        clientBootstrap.setFactory(new NioClientSocketChannelFactory(boss,worker));

        //设置管道的工厂
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("clientHandlerOne", new ClientHandlerOne());
                return pipeline;
            }
        });

        //连接服务端
        ChannelFuture connect = clientBootstrap.connect(new InetSocketAddress(8888));
        Channel channel = connect.getChannel();

        System.out.println("Client start...");
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Please input:");
            channel.write(scanner.next());
        }
    }
}
