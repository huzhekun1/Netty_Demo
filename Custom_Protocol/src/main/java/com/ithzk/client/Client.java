package com.ithzk.client;

import com.ithzk.coder.RequestEncoder;
import com.ithzk.coder.ResponseDecoder;
import com.ithzk.constans.Constants;
import com.ithzk.model.Request;
import com.ithzk.module.customspass.request.FightRequest;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Netty客户端
 * @author hzk
 * @date 2018/10/8
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        //服务类
        ClientBootstrap clientBootstrap = new ClientBootstrap();

        //boss监听端口,worker线程负责数据读写
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置socket工厂
        clientBootstrap.setFactory(new NioClientSocketChannelFactory());

        //设置管道工厂
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                //RequestEncoder -> ResponseDecoder ->ClientHandler
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder",new ResponseDecoder());
                pipeline.addLast("encoder",new RequestEncoder());
                pipeline.addLast("clientHandler",new ClientHandler());
                return pipeline;
            }
        });

        //连接服务端
        ChannelFuture channelFuture = clientBootstrap.connect(new InetSocketAddress(Constants.AbstractNettyConfig.ADDRESS, Constants.AbstractNettyConfig.PORT));
        Channel channel = channelFuture.sync().getChannel();

        System.out.println("Client Start...");

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Please input:");
            int fightId = Integer.parseInt(scanner.nextLine());
            int count = Integer.parseInt(scanner.nextLine());

            FightRequest fightRequest = new FightRequest(fightId,count);
            Request request = new Request(Constants.AbstractModule.ONE,Constants.AbstractCmd.ONE,fightRequest.getBytes());
            //发送请求
            channel.write(request);
        }
    }
}
