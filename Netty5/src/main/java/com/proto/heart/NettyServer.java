package com.proto.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 服务类(心跳)
 * @author hzk
 * @date 2018/9/19
 */
public class NettyServer {
    
    public static void main(String[] args){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            //设置线程池
            serverBootstrap.group(boss,worker);
            //设置socket工厂
            serverBootstrap.channel(NioServerSocketChannel.class);
            //设置管道
            serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(new IdleStateHandler(5,5,10));
                    channel.pipeline().addLast(new StringDecoder());
                    channel.pipeline().addLast(new StringEncoder());
                    channel.pipeline().addLast(new ServerHeartHandler());
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

            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8888);
            System.out.println("Netty Server Start..");
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
