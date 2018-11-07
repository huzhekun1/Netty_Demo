package com.proto.multiclient;

import com.proto.client.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多连接客户端
 * @author hzk
 * @date 2018/9/18
 */
public class MultiClient {

    /**
     * 服务类
     */
    private Bootstrap bootstrap = new Bootstrap();

    /**
     * 会话
     */
    private List<Channel> channels = new ArrayList<>();

    /**
     * 引用计数
     */
    private final AtomicInteger index = new AtomicInteger();

    public MultiClient(String address, int port, int count) {
        init(address,port,count);
    }

    /**
     * 初始化
     * @param address 连接地址
     * @param port 端口
     * @param count 创建客户端数量
     */
    private void init(String address,int port,int count){
        NioEventLoopGroup worker = new NioEventLoopGroup();

        //设置线程池
        bootstrap.group(worker);

        //设置工厂
        bootstrap.channel(NioSocketChannel.class);

        //设置管道
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new StringDecoder());
                channel.pipeline().addLast(new StringEncoder());
                channel.pipeline().addLast(new ClientHandler());
            }
        });

        for (int i = 1;i<= count;i++){
            ChannelFuture connect = bootstrap.connect(address, port);
            channels.add(connect.channel());
        }
    }

    /**
     * 返回当前使用的会话
     * @param channel
     * @return
     */
    public int indexChannel(Channel channel){
        return channels.indexOf(channel);
    }

    /**
     * 获取会话
     * @param address
     * @param port
     * @return
     */
    public Channel nextChannel(String address,int port){
        return getFirstActiveChannel(address,port,0);
    }

    private Channel getFirstActiveChannel(String address,int port,int count){
        Channel channel = channels.get(Math.abs(index.getAndIncrement() % channels.size()));
        if(!channel.isActive()){
            //重连
            reconnect(channel,address,count);
            if(count >= channels.size()){
                throw new RuntimeException("Not Have Effective Channel!");
            }
            return getFirstActiveChannel(address,port,++count);
        }
        return channel;

    }

    private void reconnect(Channel channel,String address,int port){
        synchronized (channel){
            if(channels.indexOf(channel) == -1){
                return;
            }
            ChannelFuture connect = bootstrap.connect(address, port);
            channels.set(channels.indexOf(channel),connect.channel());
        }

    }


}
