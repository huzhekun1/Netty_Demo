package com.proto.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author hzk
 * @date 2018/9/10
 */
public class NettyServer {
    
    public static void main(String[] args){
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            //1.设置线程池
            serverBootstrap.group(boss,worker);

            //2.设置socket工厂
            serverBootstrap.channel(NioServerSocketChannel.class);

            //3.设置管道工厂
            serverBootstrap.childHandler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(new StringDecoder());
                    channel.pipeline().addLast(new StringEncoder());
                    channel.pipeline().addLast(new ServerHandler());
                }
            });

            //设置TCP参数
            //netty3设置方式
//            serverBootstrap.setOption("backlog",1024);
//            serverBootstrap.setOption("tcpNoDelay",true);
//            serverBootstrap.setOption("keepAlive",true);
            serverBootstrap.option(ChannelOption.SO_BACKLOG,2048);//serverSocketChannel设置,连接缓冲池大小
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);//socketChannel设置,维持链接活跃,清除死连接
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY,true);//socketChannel设置,关闭延迟发送

            //4.绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8888);
            System.out.println("Netty Server Start...");
            //5.等待服务端关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
