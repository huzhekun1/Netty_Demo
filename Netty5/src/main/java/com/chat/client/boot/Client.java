package com.chat.client.boot;

import com.chat.client.swing.SwingClient;
import com.chat.common.core.coder.RequestEncoder;
import com.chat.common.core.coder.ResponseDecoder;
import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;


/**
 * netty客户端
 * @author hzk
 * @date 2018/10/25
 */
@Component
public class Client {

    /**
     * 界面客户端
     */
    @Autowired
    private SwingClient swingClient;

    /**
     * 客户端引导程序
     */
    private Bootstrap bootstrap = new Bootstrap();

    /**
     * 会话通道
     */
    private Channel channel;

    /**
     * 线程池
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     * 初始化客户端
     * 服务器加载Servlet的时候运行，并且只会被服务器执行一次
     */
    @PostConstruct
    public void init(){
        //设置循环线程组事例
        bootstrap.group(workerGroup);
        // 设置channel工厂
        bootstrap.channel(NioSocketChannel.class);
        //设置管道
        bootstrap.handler(new ChannelInitializer<Channel>() {

            @Override
            public void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new ResponseDecoder());
                ch.pipeline().addLast(new RequestEncoder());
                ch.pipeline().addLast(new ClientHandler(swingClient));
            }
        });
    }

    /**
     * 连接服务端
     * @throws InterruptedException
     */
    public void connect() throws InterruptedException {
        //连接服务器
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(Constants.AbstractNettyConfig.ADDRESS, Constants.AbstractNettyConfig.PORT));
        connect.sync();
        channel = connect.channel();
    }

    /**
     * 关闭连接
     */
    public void shutdown(){
        workerGroup.shutdownGracefully();
    }

    /**
     * 获取会话通道
     * @return {@link Channel}
     */
    public Channel getChannel(){
        return channel;
    }

    /**
     * 发送消息
     * @param request {@link Request}
     * @throws InterruptedException
     */
    public void sendMessage(Request request) throws InterruptedException {
        if(null == channel || !channel.isActive()){
            connect();
        }
        channel.writeAndFlush(request);
    }
}
