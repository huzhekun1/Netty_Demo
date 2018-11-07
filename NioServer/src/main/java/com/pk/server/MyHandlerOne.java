package com.pk.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.UpstreamMessageEvent;

/**
 * 粘包分包
 * @author hzk
 * @date 2018/10/22
 */
public class MyHandlerOne extends SimpleChannelHandler{

    private int count = 1;
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(e.getMessage()+":"+(count++));
    }
}
