package com.chat.client.boot;

import com.chat.client.swing.SwingClient;
import com.chat.common.core.coder.RequestEncoder;
import com.chat.common.core.coder.ResponseDecoder;
import com.chat.common.core.constants.Constants;
import com.chat.common.core.model.Request;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    ClientBootstrap clientBootstrap = new ClientBootstrap();

    /**
     * 会话通道
     */
    private Channel channel;

    /**
     * 线程池
     */
    private ExecutorService boss = Executors.newCachedThreadPool();
    private ExecutorService worker = Executors.newCachedThreadPool();

    /**
     * 初始化客户端
     * 服务器加载Servlet的时候运行，并且只会被服务器执行一次
     */
    @PostConstruct
    public void init(){
        //设置ClientSocket工厂
        clientBootstrap.setFactory(new NioClientSocketChannelFactory(boss,worker));
        //设置管道
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder",new ResponseDecoder());
                pipeline.addLast("encoder",new RequestEncoder());
                pipeline.addLast("clientHandler",new ClientHandler(swingClient));
                return pipeline;
            }
        });
    }

    /**
     * 连接服务端
     * @throws InterruptedException
     */
    public void connect() throws InterruptedException {
        //连接服务器
        ChannelFuture connect = clientBootstrap.connect(new InetSocketAddress(Constants.AbstractNettyConfig.ADDRESS, Constants.AbstractNettyConfig.PORT));
        connect.sync();
        channel = connect.getChannel();
    }

    /**
     * 关闭连接
     */
    public void shutdown(){
        channel.close();
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
        if(null == channel || !channel.isConnected()){
            connect();
        }
        channel.write(request);
    }
}
