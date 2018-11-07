package com.chat.server.boot;

import com.chat.common.core.coder.RequestDecoder;
import com.chat.common.core.coder.ResponseEncoder;
import com.chat.common.core.constants.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

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
        ServerBootstrap bootstrap = new ServerBootstrap();

        //boss线程监听端口,worker线程负责数据读写
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            // 设置循环线程组事例
            bootstrap.group(boss,worker);

            // 设置NioSocket工厂
            bootstrap.channel(NioServerSocketChannel.class);

            // 设置管道
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new RequestDecoder());
                    ch.pipeline().addLast(new ResponseEncoder());
                    ch.pipeline().addLast(new ServerHandler());
                }
            });

            //连接缓冲池队列大小
            bootstrap.option(ChannelOption.SO_BACKLOG,2048);
            ChannelFuture channelFuture = bootstrap.bind(Constants.AbstractNettyConfig.PORT);
            System.out.println("Server Start Success...");
            //等待服务端关闭
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

}
